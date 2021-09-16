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
import * as React from "react";
import { drawerListItems } from "../modals/drawerListItems";

const Drawer = styled(MuiDrawer, {
  shouldForwardProp: (prop) => prop,
})(({ theme, open }) => ({
  "& .MuiDrawer-paper": {
    position: "relative",
    float: "left",
    width: theme.spacing(50),
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
      [theme.breakpoints.up("sm")]: {
        width: theme.spacing(9),
      },
    }),
  },
}));

const mdTheme = createTheme();

function SideBar({ setOpen, open }) {
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
          {drawerListItems.map((item, key) => {
            return (
              <ListItemButton key={key}>
                <ListItemIcon>
                  <Icon>{item[1]}</Icon>
                </ListItemIcon>
                <ListItemText>{item[0]}</ListItemText>
              </ListItemButton>
            );
          })}
        </List>
      </Drawer>
    </ThemeProvider>
  );
}

export default SideBar;
