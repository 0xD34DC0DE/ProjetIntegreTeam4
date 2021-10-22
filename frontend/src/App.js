import React, { useState } from "react";
import { BrowserRouter, Redirect, Switch } from "react-router-dom";
import "./App.css";
import Content from "./components/Content";
import InternshipOfferValidation from "./components/InternshipOfferValidation";
import ListCvInternshipManagerView from "./components/ListCvInternshipManagerView";
import UploadCV from "./components/UploadCV";
import TopBar from "./components/TopBar";
import UserInfoStore, { UserInfoContext } from "./stores/UserInfoStore";
import OfferViews from "./components/OfferViews";
import ListUserDraggable from "./components/ListUserDraggable";

function App() {
  const handleDialogs = (dialogName, show) => {
    setDialogVisibility((dialogs) => ({ ...dialogs, [dialogName]: show }));
  };
  const [open, setOpen] = useState(false);
  const [dialogVisibility, setDialogVisibility] = useState({
    loginDialog: false,
    registerDialog: false,
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
            internshipOfferDialogVisible={
              dialogVisibility.internshipOfferDialog
            }
            loginVisible={dialogVisibility.loginDialog}
            registerVisible={dialogVisibility.registerDialog}
            toggleDialogs={handleDialogs}
          />
          <Switch>
            <Content exact path="/"></Content>
            <InternshipOfferValidation
              internshipOfferDialogVisible={
                dialogVisibility.internshipOfferDialogValidation
              }
              toggleDialogs={handleDialogs}
              exact
              path="/validerOffreStage"
            />
            <ListCvInternshipManagerView
              exact
              path="/validerCV"
              sx={{ marginTop: "50px" }}
            ></ListCvInternshipManagerView>
            <UploadCV exact path="/televerserCV" />
            <OfferViews exact path="/offres" />
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
