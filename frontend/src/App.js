import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Home from './components/Home';

function App() {
  return (
    <Router>
      <div className="App"> 
        <Switch>
          <Home />
          <Route path="/home" exact component={Home}/>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
