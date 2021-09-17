import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './components/Home';
import Register from './components/Register'

function App() {
  return (
    <Router>
      <div className="App"> 
        <Switch>
          <Route path="/home" exact component={Home}/>
          <Route path="/register" extact component={Register}/>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
