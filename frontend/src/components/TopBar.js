import { Icon, Toolbar } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import Badge from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton";
import { createTheme, styled, ThemeProvider } from "@mui/material/styles";
import React from "react";

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop,
})(({ theme, open }) => ({
  overflowX: "hidden",
  float: "right",
  width: `calc(100% - ${theme.spacing(50)})`,
  ...(!open && {
    width: `calc(100% - ${theme.spacing(9)})`,
  }),
}));

const mdTheme = createTheme();

const TopBar = ({ open }) => {
  return (
    <ThemeProvider theme={mdTheme}>
      <AppBar position="static" open={open}>
        <Toolbar
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "flex-end",
            px: [1],
          }}
        >
          <IconButton color="inherit">
            <Badge badgeContent={4} color="secondary">
              <Icon>notifications</Icon>
            </Badge>
          </IconButton>
          <IconButton color="inherit">
            <Icon>account_circle</Icon>
          </IconButton>
        </Toolbar>
      </AppBar>
    </ThemeProvider>
  );
};

export default TopBar;
