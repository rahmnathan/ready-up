import React from 'react';

const layoutProps = {
    textAlign: 'center'
};

export class MainPage extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        return (
            <div style={layoutProps}>
                Ready Up
            </div>
        )
    }
}
