import DashboardCustomizeOutlinedIcon from "@mui/icons-material/DashboardCustomizeOutlined";
import ExitToAppOutlinedIcon from "@mui/icons-material/ExitToAppOutlined";
import LockOpenOutlinedIcon from "@mui/icons-material/LockOpenOutlined";
import MenuIcon from "@mui/icons-material/Menu";
import MoreVertIcon from "@mui/icons-material/MoreVert";
import NotificationsNoneOutlinedIcon from "@mui/icons-material/NotificationsNoneOutlined";
import PersonAddOutlinedIcon from "@mui/icons-material/PersonAddOutlined";
import SettingsOutlinedIcon from "@mui/icons-material/SettingsOutlined";
import {
  AppBar,
  Avatar,
  Divider,
  IconButton,
  ListItemAvatar,
  ListItemIcon,
  ListItemText,
  Menu,
  MenuItem,
  Toolbar,
  Tooltip,
  Typography,
} from "@mui/material";
import React, { useContext, useRef, useState } from "react";
import Login from "../components/Login";
import Register from "../components/Register";
import { UserInfoContext } from "../stores/UserInfoStore";
import SideBar from "./SideBar";
import { useHistory } from "react-router-dom";

const TopBar = ({
  openDrawer,
  setOpenDrawer,
  toggleDialogs,
  internshipOfferDialogVisible,
  registerVisible,
  loginVisible
}) => {
  const [menuVisible, setMenuVisible] = useState(false);
  const menuAnchor = useRef();
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  const history = useHistory();

  const handleClose = () => {
    menuAnchor.current = undefined;
    setMenuVisible(false);
  };

  const handleOpen = (event) => {
    menuAnchor.current = event.currentTarget;
    setMenuVisible(true);
  };

  return (
    <>
      <AppBar position="fixed" open={openDrawer}>
        <Toolbar
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "flex-end",
          }}
        >
          {userInfo.loggedIn && (
            <IconButton
              color="inherit"
              aria-label="open drawer"
              onClick={() => {setOpenDrawer(!openDrawer)}}
              edge="start"
              sx={{ mr: 2, ...(openDrawer && { display: "none" }) }}
            >
              <MenuIcon />
            </IconButton>
          )}
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
                        <PersonAddOutlinedIcon />
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
                        history.push("/");
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
        </Toolbar>
      </AppBar>
      <Register toggleDialogs={toggleDialogs} open={registerVisible}></Register>
      <Login open={loginVisible} toggleDialogs={toggleDialogs}></Login>
      <SideBar
        openDrawer={openDrawer}
        setOpenDrawer={setOpenDrawer}
        internshipOfferDialogVisible={internshipOfferDialogVisible}
        toggleDialogs={toggleDialogs}
      />
    </>
  );
};

export default TopBar;
