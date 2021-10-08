import {
  IconButton,
  Toolbar,
  MenuItem,
  Menu,
  Typography,
  Divider,
  ListItemIcon,
  ListItemText,
  Tooltip,
  Avatar,
  Button,
  AppBar,
  ListItemAvatar,
  AppBar
} from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import { createTheme, styled, ThemeProvider } from "@mui/material/styles";
import React, { useState, useRef, useContext } from "react";
import Login from "../components/Login";
import Register from "../components/Register";
import { UserInfoContext } from "../stores/UserInfoStore";
import ExitToAppOutlinedIcon from "@mui/icons-material/ExitToAppOutlined";
import PersonAddOutlinedIcon from "@mui/icons-material/PersonAddOutlined";
import LockOpenOutlinedIcon from "@mui/icons-material/LockOpenOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import DashboardCustomizeOutlinedIcon from "@mui/icons-material/DashboardCustomizeOutlined";
import NotificationsNoneOutlinedIcon from "@mui/icons-material/NotificationsNoneOutlined";

const mdTheme = createTheme();

const TopBar = ({ open, toggleDialogs, registerVisible, loginVisible }) => {
  const [menuVisible, setMenuVisible] = useState(false);
  const menuAnchor = useRef();
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

  const handleClose = () => {
    menuAnchor.current = undefined;
    setMenuVisible(false);
  };

  const handleOpen = (event) => {
    menuAnchor.current = event.currentTarget;
    setMenuVisible(true);
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
          <Tooltip title="Notifications">
            <IconButton color="inherit" sx={{ ml: "auto" }}>
              <NotificationsNoneOutlinedIcon fontSize="medium" />
            </IconButton>
          </Tooltip>
          <Tooltip title="Paramètres">
            <IconButton color="inherit" onClick={handleOpen}>
              <MoreVertIcon fontSize="medium" />
            </IconButton>
          </Tooltip>
          <Menu
            id="demo-positioned-menu"
            aria-labelledby="demo-positioned-button"
            anchorEl={menuAnchor.current}
            ml={"auto"}
            sx={{ pt: 0, pb: 0 }}
            open={menuVisible}
            onClose={handleClose}
          >
            {!userInfo.loggedIn
              ? [
                  [
                    <MenuItem
                      sx={{ pt: 0.5, pb: 0.5 }}
                      onClick={() => {
                        toggleDialogs("registerDialog", true);
                        setMenuVisible(false);
                      }}
                    >
                      <ListItemIcon>
                        <PersonAddOutlinedIcon fontSize="small" />
                      </ListItemIcon>
                      <ListItemText>Enregistrement</ListItemText>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                      ></Typography>
                    </MenuItem>,
                  ],
                  [
                    <MenuItem
                      sx={{ pt: 0.5, pb: 0.5 }}
                      onClick={() => {
                        toggleDialogs("loginDialog", true);
                        setMenuVisible(false);
                      }}
                    >
                      <ListItemIcon>
                        <LockOpenOutlinedIcon fontSize="small" />
                      </ListItemIcon>
                      <ListItemText>Connexion</ListItemText>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                      ></Typography>
                    </MenuItem>,
                  ],
                ]
              : [
                  [
                    <MenuItem>
                      <ListItemAvatar>
                        <Avatar>AL</Avatar>
                      </ListItemAvatar>
                      <ListItemText
                        secondary={
                          <Typography variant="caption">
                            {userInfo.role}
                          </Typography>
                        }
                      >
                        {userInfo.email}
                      </ListItemText>
                    </MenuItem>,
                  ],
                  [<Divider />],
                  [
                    <MenuItem
                      onMouseEnter={() => {}}
                      onClick={() => {
                        setMenuVisible(false);
                        userInfoDispatch({ type: "LOGOUT" });
                      }}
                    >
                      <ListItemIcon>
                        <DashboardCustomizeOutlinedIcon fontSize="small" />
                      </ListItemIcon>
                      <ListItemText>Espace personnel</ListItemText>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                      ></Typography>
                    </MenuItem>,
                  ],
                  [
                    <MenuItem
                      onClick={() => {
                        setMenuVisible(false);
                        userInfoDispatch({ type: "LOGOUT" });
                      }}
                    >
                      <ListItemIcon>
                        <SettingsOutlinedIcon fontSize="small" />
                      </ListItemIcon>
                      <ListItemText>Paramètres</ListItemText>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                      ></Typography>
                    </MenuItem>,
                  ],
                  [
                    <MenuItem
                      onClick={() => {
                        setMenuVisible(false);
                        userInfoDispatch({ type: "LOGOUT" });
                      }}
                    >
                      <ListItemIcon>
                        <ExitToAppOutlinedIcon fontSize="small" />
                      </ListItemIcon>
                      <ListItemText>Déconnexion</ListItemText>
                      <Typography
                        variant="body2"
                        color="text.secondary"
                      ></Typography>
                    </MenuItem>,
                  ],
                ]}
          </Menu>
          <Register
            toggleDialogs={toggleDialogs}
            open={registerVisible}
          ></Register>
          <Login open={loginVisible} toggleDialogs={toggleDialogs}></Login>
        </Toolbar>
      </AppBar>
    </ThemeProvider>
  );
};

export default TopBar;
