import { IconButton, Toolbar, MenuItem, Menu, Typography } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import { createTheme, styled, ThemeProvider } from "@mui/material/styles";
import React, { useState, useRef } from "react";
import LoginIcon from "@mui/icons-material/Login";
import Login from "../components/Login";
import Register from "../components/Register";
import PersonIcon from "@mui/icons-material/Person";

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop,
})(({ theme, open }) => ({
  overflowX: "hidden",
  float: "right",
  width: `calc(100% - ${theme.spacing(40)})`,
  ...(!open && {
    width: `calc(100% - ${theme.spacing(9)})`,
  }),
}));

const mdTheme = createTheme();

const TopBar = ({
  open,
  toggleDialogs,
  userInformation,
  setUserInformation,
  registerVisible,
  logout,
  loginVisible,
}) => {
  const [menuVisible, setMenuVisible] = useState(false);
  const menuAnchor = useRef();

  const handleClose = () => {
    menuAnchor.current = undefined;
    setMenuVisible(false);
  };

  const handleOpen = (event) => {
    menuAnchor.current = event.currentTarget;
    setMenuVisible(true);
    console.log(userInformation);
  };

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
          <Typography variant="caption" sx={{ ml: "auto" }}>
            {userInformation.email}
          </Typography>
          <IconButton color="inherit" onClick={handleOpen}>
            {userInformation.loggedIn ? <LoginIcon /> : <PersonIcon />}
          </IconButton>
          <Menu
            id="demo-positioned-menu"
            aria-labelledby="demo-positioned-button"
            anchorEl={menuAnchor.current}
            ml={"auto"}
            open={menuVisible}
            onClose={handleClose}
          >
            {!userInformation.loggedIn
              ? [
                  [
                    <MenuItem
                      onClick={() => {
                        toggleDialogs("registerDialog", true);
                        setMenuVisible(false);
                      }}
                    >
                      Enregistrement
                    </MenuItem>,
                  ],
                  [
                    <MenuItem
                      onClick={() => {
                        toggleDialogs("loginDialog", true);
                        setMenuVisible(false);
                      }}
                    >
                      Connexion
                    </MenuItem>,
                  ],
                ]
              : [
                  <MenuItem
                    onClick={() => {
                      setMenuVisible(false);
                      logout();
                    }}
                  >
                    Déconnexion
                  </MenuItem>,
                ]}
          </Menu>
        </Toolbar>
      </AppBar>
      <Register toggleDialogs={toggleDialogs} open={registerVisible}></Register>
      <Login
        open={loginVisible}
        toggleDialogs={toggleDialogs}
        userInformation={userInformation}
        setUserInformation={setUserInformation}
      ></Login>
    </ThemeProvider>
  );
};

export default TopBar;
