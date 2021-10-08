import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import {
  Divider,
  List,
  ListItemIcon,
  ListItemText,
  ListItemButton,
  ThemeProvider,
} from "@mui/material";
import Drawer from "@mui/material/Drawer";
import IconButton from "@mui/material/IconButton";
import { createTheme, styled } from "@mui/material/styles";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { drawerListDialogs, drawerListRoutes } from "../models/drawerListItems";
import { useHistory } from "react-router-dom";
import OfferForm from "./OfferForm";


const drawerWidth = 25;
const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  padding: theme.spacing(0, 1),
  ...theme.mixins.toolbar,
  justifyContent: "flex-end",
}));

const mdTheme = createTheme();

function SideBar({
  openDrawer,
  internshipOfferDialogVisible,
  setOpenDrawer,
  toggleDialogs,
}) {
  const [userInfo] = useContext(UserInfoContext);
  const history = useHistory();

  const toggleDrawer = () => {
    setOpenDrawer(!openDrawer);
  };

  const handleClose = (_, reason) => {
    if(reason === "backdropClick")
      toggleDrawer();
  }

  return (
    <ThemeProvider theme={mdTheme}>
      <Drawer
        onClose={handleClose}
        sx={{
          width: `${drawerWidth}rem`,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: `${drawerWidth}rem`,
            boxSizing: "border-box",
          },
        }}
        variant="temporary"
        anchor="left"
        open={openDrawer}
      >
        <DrawerHeader>
          <IconButton onClick={toggleDrawer}>
            <ChevronLeftIcon />
          </IconButton>
        </DrawerHeader>
        <Divider sx={{pb: 0}} />
        <List sx={{mt: 0, pt: 0, pb: 0, mb: 0}}>
          {drawerListDialogs
            .filter((item) => item.roles.includes(userInfo.role))
            .map((item, key) => {
              return (
                <ListItemButton
                  key={key}
                  onClick={() => toggleDialogs(item.name, true)}
                >
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText>{item.text}</ListItemText>
                </ListItemButton>
              );
            })}
        </List>
        <List sx={{mt: 0, pt: 0, pb: 0, mb: 0}}>
          {drawerListRoutes
            .filter((item) => item.roles.includes(userInfo.role))
            .map((item, key) => {
              return (
                <ListItemButton
                  key={key}
                  onClick={() => {
                    history.push(item.url);
                    toggleDrawer();
                  }}
          
                >
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText>{item.text}</ListItemText>
                </ListItemButton>
              );
            })}
        </List>
      </Drawer>
      <OfferForm
              dialogVisible={internshipOfferDialogVisible}
              toggleDialogs={toggleDialogs}
            />
    </ThemeProvider>
  );
}
export default SideBar;
