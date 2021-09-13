import './App.css';
import { BrowserRouter as Router, Switch, Route } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';

function App() {
  return (
    <Router>
      <div className="App"> 
        <Switch>
          <Route path="/login" exact component={Login}/>
          <Route path="/register" exact component={Register}/>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
