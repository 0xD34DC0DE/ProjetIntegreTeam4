import "./App.css";
import { useState } from "react";
import Content from "./components/Content";
import Sidebar from "./components/Sidebar";
import Topbar from "./components/Topbar";
import UserInfoStore from "./stores/UserInfoStore";
import { sidebarList } from "./components/Configuration";
import theme from "./components/Theme";
import { ThemeProvider } from "@mui/material/styles";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [selection, setSelection] = useState(sidebarList[0]);

  const handleDialogs = (dialogName, show) => {
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
    signContractMonitorDialog: false,
    evaluationDialogPreview: false,
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
