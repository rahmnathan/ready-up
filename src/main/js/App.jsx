import React from 'react';
import ReactDOM from 'react-dom';
import { MainPage } from './MainPage.jsx';

class App extends React.Component {

    constructor(props) {
        super(props);
    }

    render(){
        return(
            <MainPage/>
        );
    }
}

ReactDOM.render(
    <div>
        <App/>
    </div>,
    document.getElementById('react')
);
