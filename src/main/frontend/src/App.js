import './App.css';
import {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import Navbar from "./navbar/Navbar";
import VideoclubsPage from "./videoclubs/videoclubs";
import HomePage from "./home/home";

class App extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <Switch>
                    <Route path="/homePage" component={HomePage} />
                    <Route path="/videoClubs" component={VideoclubsPage} />
                </Switch>

            </div>
        );
    }
}

export default App;

