import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from "@reach/router";
import { MainPage } from './MainPage.jsx';

class App extends React.Component {

    constructor(props) {
        super(props);
    }

    render(){
        return(
            <Router>
                <MainPage path='/'/>
            </Router>
        );
    }
}

ReactDOM.render(
    <div>
        <App/>
    </div>,
    document.getElementById('react')
);
