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
import { drawerListItems } from "../models/drawerListItems";
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
          {drawerListItems
            .filter((item) => {
              console.log("item 0", item[0]);
              console.log("user info", userInfo.role);
              return item[0] === userInfo.role;
            })
            .map((item, key) => {
              return (
                <ListItemButton
                  key={key}
                  onClick={() => {
                    toggleDialogs(item[3], true);
                  }}
                >
                  <ListItemIcon>
                    <Icon>{item[2]}</Icon>
                  </ListItemIcon>
                  <ListItemText>{item[1]}</ListItemText>
                </ListItemButton>
              );
            })}
        </List>
      </Drawer>
      <OfferForm
        isMounted={intershipOfferDialogVisible}
        toggleDialogs={toggleDialogs}
      />
    </ThemeProvider>
  );
}

export default SideBar;
