import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import Home from "./components/Home";
import Register from "./components/Register";
import React from "react";
import StickyFooter from "./components/Footer";
import SideBar from "./components/SideBar";
import TopBar from "./components/TopBar";

function App() {
  const [open, setOpen] = React.useState(true);
  return (
    <>
      <TopBar setOpen={setOpen} open={open} />
      <SideBar setOpen={setOpen} open={open} />
      <Router>
        <div className="App">
          <Switch>
            <Route path="/home" exact component={Home} />
            <Route path="/register" extact component={Register} />
          </Switch>
        </div>
      </Router>
      <StickyFooter />
    </>
  );
}

export default App;
