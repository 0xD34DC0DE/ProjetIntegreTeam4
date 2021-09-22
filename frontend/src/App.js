import React from "react";
import { Route, Switch, BrowserRouter } from "react-router-dom";
import Register from "./components/Register";
import OfferForm from "./components/OfferForm";
import StickyFooter from "./components/Footer";
import SideBar from "./components/SideBar";
import TopBar from "./components/TopBar";
import Home from "./components/Home";
import "./App.css";

function App() {
  const [open, setOpen] = React.useState(false);
  return (
    <BrowserRouter>
      <div className="App">
        <TopBar setOpen={setOpen} open={open} />
        <SideBar setOpen={setOpen} open={open} />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route path="/register" exact component={Register} />
          <Route path="/offerForm" exact component={OfferForm} />
        </Switch>
        <StickyFooter />
      </div>
    </BrowserRouter>
  );
}

export default App;
