import { AppBar, Icon, Toolbar, Typography, Button } from "@mui/material";
import { makeStyles } from "@mui/styles";
import { drawerWidth } from "./SideBar";
import React from "react";

const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    float: "right",
    border: "2px solid black",
    width: `calc(100% - ${drawerWidth}px`,
  },
  loginIcon: {
    textAlign: "right",
    flexGrow: 1,
  },
}));

export default function TopBar() {
  const classes = useStyles();

  return (
    <AppBar position="static" className={classes.root}>
      <Toolbar>
        <Icon className={classes.loginIcon} color="inherit">
          person
        </Icon>
      </Toolbar>
    </AppBar>
  );
}
