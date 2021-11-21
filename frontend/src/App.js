import { ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import "./App.css";
import { sidebarList } from "./components/Configuration";
import Content from "./components/Content";
import Sidebar from "./components/Sidebar";
import theme from "./components/Theme";
import Topbar from "./components/Topbar";
import DialogStore from "./stores/DialogStore";
import UserInfoStore from "./stores/UserInfoStore";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);
  const [selection, setSelection] = useState(sidebarList[0]);

  const onSelectionChanged = (item) => {
    if (!item.isDialog) setSelection(item);
  };

  return (
    <UserInfoStore>
      <DialogStore>
        <div className="App">
          <ThemeProvider theme={theme}>
            <Topbar
              sidebarOpen={sidebarOpen}
              setSidebarOpen={setSidebarOpen}
              onSelectionChanged={onSelectionChanged}
            ></Topbar>
            <Content
              isSidebarOpen={sidebarOpen}
              selection={selection}
            ></Content>
            <Sidebar
              open={sidebarOpen}
              onSelectionChanged={onSelectionChanged}
            ></Sidebar>
          </ThemeProvider>
        </div>
      </DialogStore>
    </UserInfoStore>
  );
}

export default App;
