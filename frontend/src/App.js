import { Container } from "@mui/material";
import React, { useState } from "react";
import { BrowserRouter, Redirect, Switch } from "react-router-dom";
import "./App.css";
import Content from "./components/Content";
import InternshipOfferValidation from "./components/InternshipOfferValidation";
import TopBar from "./components/TopBar";
import UserInfoStore, { UserInfoContext } from "./stores/UserInfoStore";

function App() {
  const handleDialogs = (dialogName, show) => {
    setDialogVisibility((dialogs) => ({ ...dialogs, [dialogName]: show }));
  };
  const [open, setOpen] = useState(false);
  const [dialogVisibility, setDialogVisibility] = useState({
    loginDialog: false,
    registerDialog: false,
    depositInternshipOfferDialog: false,
    internshipOfferDialog: false,
    internshipOfferDialogValidation: false,
  });

  return (
    <BrowserRouter>
      <UserInfoStore>
        <div className="App">
          <TopBar
            openDrawer={open}
            setOpenDrawer={setOpen}
            loginVisible={dialogVisibility.loginDialog}
            registerVisible={dialogVisibility.registerDialog}
            toggleDialogs={handleDialogs}
            intershipOfferDialogVisible={dialogVisibility.internshipOfferDialog}
          />
          <Switch>
            <Content open={open} setOpen={setOpen} exact path="/"></Content>
            <InternshipOfferValidation
              internshipOfferDialogVisible={
                dialogVisibility.internshipOfferDialogValidation
              }
              toggleDialogs={handleDialogs}
              exact
              path="/internshipOfferValidation"
            />
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
