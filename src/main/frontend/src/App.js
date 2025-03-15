import './App.css';
import {Component} from "react";
import { Switch, Route } from 'react-router-dom';
import Navbar from "./navbar/navbar";
import VideoclubsPage from "./videoclubs/videoclubs";
import HomePage from "./home/home";
import StaffsPage from "./staff/staffs";
import CustomersPage from "./customers/customers";
import CdsPage from "./cd/cds";

class App extends Component {

    render() {
        return (
            <div>
                <Navbar/>
                <Switch>
                    <Route path="/homePage" component={HomePage} />
                    <Route path="/videoClubs" component={VideoclubsPage} />
                    <Route path="/staff" component={StaffsPage} />
                    <Route path="/customers" component={CustomersPage} />
                    <Route path="/cd" component={CdsPage} />
                </Switch>
            </div>
        );
    }
}

export default App;

