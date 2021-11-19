import { ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import "./App.css";
import { sidebarList } from "./components/Configuration";
import Content from "./components/Content";
import Sidebar from "./components/Sidebar";
import theme from "./components/Theme";
import Topbar from "./components/Topbar";
import UserInfoStore from "./stores/UserInfoStore";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [selection, setSelection] = useState(sidebarList[0]);
  const [dialogData, setDialogData] = useState();

  const handleDialogs = (dialogName, show, data = null) => {
    setDialogData(data)
    setDialogVisibility((dialogs) => ({ ...dialogs, [dialogName]: show }));
  };

  const [dialogVisibility, setDialogVisibility] = useState({
    loginDialog: false,
    registerDialog: false,
    internshipOfferDialog: false,
    internshipOfferDialogValidation: false,
    emailSenderDialog: false,
    internshipDetailsDialog: false,
    reportDialog: false,
    cvDialog: false,
    signContractMonitorDialog: false,
    cvRejectionExplanationDialog: false,
    signContractDialog: false,
  });

  const onSelectionChanged = (item) => {
    if (item.isDialog) handleDialogs(item.dialogName, true);
    else setSelection(item);
  };

  return (
    <UserInfoStore>
      <div className="App">
        <ThemeProvider theme={theme}>
          <Topbar
            toggleDialog={handleDialogs}
            sidebarOpen={sidebarOpen}
            setSidebarOpen={setSidebarOpen}
            onSelectionChanged={onSelectionChanged}
          ></Topbar>
          <Content
            toggleDialog={handleDialogs}
            dialogVisibility={dialogVisibility}
            isSidebarOpen={sidebarOpen}
            selection={selection}
            dialogData={dialogData}
          ></Content>
          <Sidebar
            open={sidebarOpen}
            onSelectionChanged={onSelectionChanged}
          ></Sidebar>
        </ThemeProvider>
      </div>
    </UserInfoStore>
  );
}

export default App;
