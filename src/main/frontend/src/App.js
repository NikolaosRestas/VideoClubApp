import './App.css';
import Navbar from './navbar/navbar';
import HomePage from './home/home';
import { BrowserRouter as Router, Route, Switch, Routes} from 'react-router-dom';

const App = () => {
  return (
    <Router>
      <Navbar/>
      <Switch>
        <Route exact path="/homePage" component={HomePage} />
      </Switch>
    </Router>
  );
};

export default App;
