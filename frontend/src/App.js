import { ThemeProvider } from "@mui/material/styles";
import { useState } from "react";
import "./App.css";
import Content from "./components/Content";
import Sidebar from "./components/Sidebar";
import theme from "./components/Theme";
import Topbar from "./components/Topbar";
import DialogStore from "./stores/DialogStore";
import SelectionStore from "./stores/SelectionStore";
import UserInfoStore from "./stores/UserInfoStore";

function App() {
  const [sidebarOpen, setSidebarOpen] = useState(true);

  return (
    <UserInfoStore>
      <DialogStore>
        <SelectionStore>
          <div className="App">
            <ThemeProvider theme={theme}>
              <Topbar
                sidebarOpen={sidebarOpen}
                setSidebarOpen={setSidebarOpen}
              ></Topbar>
              <Content isSidebarOpen={sidebarOpen}></Content>
              <Sidebar open={sidebarOpen}></Sidebar>
            </ThemeProvider>
          </div>
        </SelectionStore>
      </DialogStore>
    </UserInfoStore>
  );
}

export default App;
