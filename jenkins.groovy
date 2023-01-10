node {
    def mvnHome
    def jdk
    def server
    def buildInfo
    def rtMaven

    try {
        stage('Setup') {
            mvnHome = tool 'Maven'
            jdk = tool name: 'Java 19'
            env.JAVA_HOME = "${jdk}"

            server = Artifactory.server 'Artifactory'

            rtMaven = Artifactory.newMavenBuild()
            rtMaven.tool = 'Maven'
            rtMaven.deployer releaseRepo: 'rahmnathan-services', snapshotRepo: 'rahmnathan-services', server: server

            buildInfo = Artifactory.newBuildInfo()
        }
        stage('Checkout') {
            git branch: 'main', url: 'https://github.com/rahmnathan/ready-up.git'
        }
        stage('Set Version') {
            PROJECT_VERSION = sh(
                    script: "'${mvnHome}/bin/mvn' help:evaluate -Dexpression=project.version -q -DforceStdout",
                    returnStdout: true
            ).trim()
            env.NEW_VERSION = "${PROJECT_VERSION}.${BUILD_NUMBER}"
            sh "'${mvnHome}/bin/mvn' -DnewVersion='${NEW_VERSION}' versions:set"
        }
        stage('Tag') {
            sh 'git config --global user.email "rahm.nathan@gmail.com"'
            sh 'git config --global user.name "rahmnathan"'
            sshagent(credentials: ['Github-Git']) {
                sh 'mkdir -p /home/jenkins/.ssh'
                sh 'ssh-keyscan  github.com >> ~/.ssh/known_hosts'
                sh "'${mvnHome}/bin/mvn' -Dtag=${NEW_VERSION} scm:tag"
            }
        }
        stage('Test') {
            sh "'${mvnHome}/bin/mvn' test"
        }
        stage('Package & Deploy Jar to Artifactory') {
            rtMaven.run pom: 'pom.xml', goals: 'install -DskipTests', buildInfo: buildInfo
        }
        stage('Docker Build') {
            sh "'${mvnHome}/bin/mvn' dockerfile:build"
        }
        withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: 'Dockerhub',
                          usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
            stage('Docker Push') {
                sh "'${mvnHome}/bin/mvn' dockerfile:push -Ddockerfile.username=$USERNAME -Ddockerfile.password='$PASSWORD'"
            }
        }
        stage('Deploy to Kubernetes') {
            withCredentials([
                    file(credentialsId: 'Kubeconfig', variable: 'KUBE_CONFIG'),
                    string(credentialsId: 'VaultToken', variable: 'VAULT_TOKEN')
            ]) {
                sh 'helm upgrade --install -n ready-up ready-up ./target/classes/ready-up/ --set ready_up.vaultToken=$VAULT_TOKEN --kubeconfig $KUBE_CONFIG'
            }
        }
        stage('Wait for Deployment') {
            sh 'sleep 60s'
        }
        stage('Functional Test') {
            try {
                sh 'jmeter -n -t src/test/jmeter/ready-up-web-test.jmx'
            } catch (e) {
                stage('Rollback') {
                    withCredentials([file(credentialsId: 'Kubeconfig', variable: 'KUBE_CONFIG')]) {
                        sh 'helm -n ready-up rollback ready-up 0 --kubeconfig $KUBE_CONFIG'
                    }
                }

                throw e
            }
        }
    } catch (e) {
        currentBuild.result = "FAILED"
        emailext(
                subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
        <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
                to: 'rahm.nathan@protonmail.com'
        )
        throw e
    }
}
