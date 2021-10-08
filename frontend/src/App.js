import React, { useState, useEffect, useContext } from "react";
import { Container } from "@mui/material";
import { BrowserRouter, Redirect, Switch } from "react-router-dom";
import "./App.css";
import SideBar from "./components/SideBar";
import TopBar from "./components/TopBar";
import UserInfoStore, { UserInfoContext } from "./stores/UserInfoStore";
import Content from "./components/Content";
import ListCvInternshipManagerView from "./components/ListCvInternshipManagerView";

function App() {
  const [open, setOpen] = useState(false);

  const handleDialogs = (dialogName, show) => {
    setDialogVisibility((dialogs) => ({ ...dialogs, [dialogName]: show }));
  };

  const [dialogVisibility, setDialogVisibility] = useState({
    loginDialog: false,
    registerDialog: false,
    depositInternshipOfferDialog: false,
    internshipOfferDialog: false,
  });

  return (
    <BrowserRouter>
      <UserInfoStore>
        <div className="App">
          <TopBar
            open={open}
            loginVisible={dialogVisibility.loginDialog}
            registerVisible={dialogVisibility.registerDialog}
            toggleDialogs={handleDialogs}
          />
          <SideBar
            intershipOfferDialogVisible={dialogVisibility.internshipOfferDialog}
            setOpen={setOpen}
            open={open}
            toggleDialogs={handleDialogs}
          />

          <Switch>
            <Content open={open} setOpen={setOpen} exact path="/"></Content>
            <ListCvInternshipManagerView
              exact path="/cvValidation"
              sx={{ marginTop: "50px" }}
            ></ListCvInternshipManagerView>
          </Switch>
          <UserInfoContext.Consumer>
            {({ loggedIn }) => (loggedIn ? <Redirect push to="/" /> : null)}
          </UserInfoContext.Consumer>
        </div>
      </UserInfoStore>
    </BrowserRouter>
  );
}

export default App;
