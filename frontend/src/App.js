import React, { useState } from "react";
import { BrowserRouter, Route, Switch } from "react-router-dom";
import "./App.css";
import StickyFooter from "./components/Footer";
import Home from "./components/Home";
import Login from "./components/Login";
import OfferForm from "./components/OfferForm";
import Register from "./components/Register";
import SideBar from "./components/SideBar";
import TopBar from "./components/TopBar";

function App() {
  const [open, setOpen] = React.useState(false);
  const userInformationsObject = {
    email: "",
    role: "",
    loggedIn: false,
  };

  const [userInformations, setUserInformations] = useState(
    userInformationsObject
  );

  return (
    <BrowserRouter>
      <div className="App">
        <TopBar setOpen={setOpen} open={open} />
        <SideBar setOpen={setOpen} open={open} />
        <Switch>
          <Route path="/" exact component={Home} />
          <Route
            path="/login"
            extact
            component={() => (
              <Login
                userInformations={userInformations}
                setUserInformations={setUserInformations}
              />
            )}
          />
        </Switch>
        {userInformations.loggedIn ? <Redirect push to="/home" /> : null}
        <StickyFooter />
      </div>
    </BrowserRouter>
  );
}

export default App;
