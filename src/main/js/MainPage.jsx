import React from 'react';

const layoutProps = {
    textAlign: 'center',
    color: 'white'
};

export class MainPage extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div style={layoutProps}>
                <h1>Ready Up</h1>
            </div>
        )
    }
}
