import {
  List,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import MuiDrawer from "@mui/material/Drawer";
import Icon from "@mui/material/Icon";
import IconButton from "@mui/material/IconButton";
import { createTheme, styled, ThemeProvider } from "@mui/material/styles";
import Toolbar from "@mui/material/Toolbar";
import React, { useContext } from "react";
import { useHistory } from "react-router";
import { drawerListDialogs } from "../models/drawerListItems";
import OfferForm from "../components/OfferForm";
import { UserInfoContext } from "../stores/UserInfoStore";

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop,
})(({ theme, open }) => ({
  "& .MuiDrawer-paper": {
    position: "relative",
    float: "left",
    width: theme.spacing(40),
    transition: theme.transitions.create("width", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.enteringScreen,
    }),
    boxSizing: "border-box",
    ...(!open && {
      overflowX: "hidden",
      transition: theme.transitions.create("width", {
        duration: theme.transitions.duration.leavingScreen,
      }),
      [theme.breakpoints.up("xs")]: {
        width: theme.spacing(9),
      },
    }),
  },
}));

const mdTheme = createTheme();

function SideBar({
  setOpen,
  open,
  intershipOfferDialogVisible,
  toggleDialogs,
}) {
  const history = useHistory();
  const [userInfo] = useContext(UserInfoContext);

  const toggleDrawer = () => {
    setOpen(!open);
  };

  return (
    <>
      {userInfo.loggedIn && (
        <ThemeProvider theme={mdTheme}>
          <Drawer variant="permanent" open={open} anchor="left">
            <Toolbar
              sx={{
                display: "flex",
                alignItems: "center",
                justifyContent: "flex-end",
                px: [1],
              }}
            >
              <IconButton onClick={toggleDrawer}>
                {open ? <Icon>chevron_left</Icon> : <Icon>chevron_right</Icon>}
              </IconButton>
            </Toolbar>
            <List>
              {drawerListDialogs
                .filter((item) => item.roles.includes(userInfo.role))
                .map((item, key) => {
                  return (
                    <ListItemButton
                      key={key}
                      onClick={() => {
                        console.log("item name", item.name);
                        console.log("visible?", intershipOfferDialogVisible);
                        toggleDialogs(item.name, true);
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
            dialogVisible={intershipOfferDialogVisible}
            toggleDialogs={toggleDialogs}
          />
        </ThemeProvider>
      )}
    </>
  );
}

export default SideBar;
