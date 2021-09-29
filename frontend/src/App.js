import React, { useState, useEffect } from "react";
import { Container } from "@mui/material";
import { BrowserRouter, Redirect, Switch } from "react-router-dom";
import "./App.css";
import SideBar from "./components/SideBar";
import TopBar from "./components/TopBar";
import Home from "./components/Home";
import jwt_decode from "jwt-decode";

function App() {
  const [open, setOpen] = useState(false);
  const [userInformation, setUserInformation] = useState({});

  useEffect(() => {
    const jwtToken = sessionStorage.getItem("jwt");
    if (!jwtToken) {
      setUserInformation({ email: "", role: "", loggedIn: false });
    } else {
      const decodedJwtToken = jwt_decode(jwtToken);
      setUserInformation({
        email: decodedJwtToken.sub,
        role: decodedJwtToken.role,
        loggedIn: true,
      });
    }
  }, []);

  const handleDialogs = (dialogName, show) => {
    setDialogVisibility((dialogs) => ({ ...dialogs, [dialogName]: show }));
  };

  const logout = () => {
    sessionStorage.removeItem("jwt");
    setUserInformation({
      email: "",
      role: "",
      loggedIn: false,
    });
  };

  const [dialogVisibility, setDialogVisibility] = useState({
    loginDialog: false,
    registerDialog: false,
    depositInternshipOfferDialog: false,
    internshipOfferDialog: false,
  });

  return (
    <BrowserRouter>
      <div className="App">
        <TopBar
          open={open}
          loginVisible={dialogVisibility.loginDialog}
          registerVisible={dialogVisibility.registerDialog}
          userInformation={userInformation}
          setUserInformation={setUserInformation}
          toggleDialogs={handleDialogs}
          logout={logout}
        />
        <SideBar
          intershipOfferDialogVisible={dialogVisibility.internshipOfferDialog}
          setOpen={setOpen}
          open={open}
          toggleDialogs={handleDialogs}
        />
        <Container fluid>
          <Switch>
            <Home userInformation={userInformation} />
          </Switch>
        </Container>
        {userInformation.loggedIn ? <Redirect push to="/" /> : null}
      </div>
    </BrowserRouter>
  );
}

export default App;
