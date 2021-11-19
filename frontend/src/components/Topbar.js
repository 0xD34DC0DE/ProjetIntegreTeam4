import ArrowBackIosOutlinedIcon from "@mui/icons-material/ArrowBackIosOutlined";
import ArrowForwardIosOutlinedIcon from "@mui/icons-material/ArrowForwardIosOutlined";
import ExitToAppOutlinedIcon from "@mui/icons-material/ExitToAppOutlined";
import LoginOutlinedIcon from "@mui/icons-material/LoginOutlined";
import NotificationsNoneOutlinedIcon from "@mui/icons-material/NotificationsNoneOutlined";
import PersonAddOutlinedIcon from "@mui/icons-material/PersonAddOutlined";
import PersonOutlineOutlinedIcon from "@mui/icons-material/PersonOutlineOutlined";
import {
  AppBar, Avatar, Box, Button, Divider, Grid,
  IconButton, Menu,
  MenuItem, Toolbar, Tooltip, Typography
} from "@mui/material";
import React, { useContext, useRef, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { roles, topbarMenuList } from "./Configuration";
import NotificationList from "./Notification/NotificationList";

const Topbar = ({
  sidebarOpen,
  setSidebarOpen,
  toggleDialog,
  onSelectionChanged
}) => {
  const menuAnchorRef = useRef();
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  const [menuOpen, setMenuOpen] = useState(false);
  const [notificationMenuOpen, setNotificationMenuOpen] = useState(false);
  const [notficationMenuAnchorEl, setNotficationMenuAnchorEl] = useState(null);

  const handleSidebarClick = () => {
    setSidebarOpen(!sidebarOpen);
  };

  const handleMenuClick = () => {
    setMenuOpen(!menuOpen);
  };

  const handleNotificationMenuOpen = (event) => {
    setNotificationMenuOpen(true);
    setNotficationMenuAnchorEl(event.currentTarget);
  };

  const handleNotificationMenuClose = () => {
    setNotficationMenuAnchorEl(null);
    setNotificationMenuOpen(false);
  };

  return (
    <Box>
      <AppBar sx={{ position: "fixed" }}>
        <Toolbar>
          <IconButton
            onClick={handleSidebarClick}
            sx={{ color: "text.primary" }}
          >
            {sidebarOpen ? (
              <ArrowBackIosOutlinedIcon />
            ) : (
              <ArrowForwardIosOutlinedIcon />
            )}
          </IconButton>
          <Tooltip title="Notifications">
            <IconButton
              sx={{ ml: "auto", px: 0 }}
              onClick={handleNotificationMenuOpen}
            >
              <NotificationsNoneOutlinedIcon
                sx={{ color: "text.primary", px: 0, mx: 0 }}
              />
            </IconButton>
          </Tooltip>
          <Tooltip title="Menu">
            <Button ref={menuAnchorRef} onClick={handleMenuClick}>
              <PersonOutlineOutlinedIcon
                sx={{ color: "text.primary", px: 0, mx: 0 }}
              />
              <Menu
                open={menuOpen}
                onClose={() => {
                  setMenuOpen(false);
                }}
                anchorEl={menuAnchorRef.current}
                sx={{ left: "-20px", p: 0, m: 0 }}
                PaperProps={{
                  sx: {
                    backgroundColor: "primary.main",
                    pr: 1,
                    pl: 1,
                  },
                  square: true,
                  elevation: 3,
                }}
              >
                {userInfo.loggedIn ? (
                  <>
                    <Grid container flexDirection="row" sx={{ mb: 1 }}>
                      <Grid item md={2}>
                        <Avatar
                          sx={{
                            width: 40,
                            height: 40,
                            mb: 2,
                            p: 0,
                            m: 0,
                            ml: 1,
                          }}
                        >
                          AA
                        </Avatar>
                      </Grid>
                      <Grid item md={10}>
                        <Grid container flexDirection="column" sx={{ ml: 2 }}>
                          <Typography
                            variant="subtitle2"
                            sx={{ ml: 1, fontSize: "1em" }}
                          >
                            {userInfo.email}
                          </Typography>
                          <Typography
                            variant="subtitle2"
                            fontSize="0.8em"
                            sx={{ ml: 1 }}
                          >
                            {roles[userInfo.role]}
                          </Typography>
                        </Grid>
                      </Grid>
                    </Grid>
                    <Divider
                      sx={{
                        backgroundColor: "rgba(150, 150, 150, 0.3)",
                        width: "90%",
                        ml: "0.5em",
                        mb: 1,
                      }}
                    />

                    {topbarMenuList.map((item, key) => {
                      return (
                        <MenuItem
                          key={key}
                          onClick={() => {
                            onSelectionChanged(item);
                          }}
                          sx={{
                            ":hover": {
                              backgroundColor: "rgba(100, 100, 100, 0.2)",
                            },
                            pl: 1,
                          }}
                        >
                          {item.icon}
                          <Typography variant="subtitle2" sx={{ p: 0, m: 0 }}>
                            {item.label}
                          </Typography>
                        </MenuItem>
                      );
                    })}

                    <Divider
                      sx={{
                        backgroundColor: "rgba(150, 150, 150, 0.3)",
                        width: "90%",
                        ml: "0.5em",
                      }}
                    />
                    <MenuItem
                      sx={{
                        ":hover": {
                          backgroundColor: "rgba(100, 100, 100, 0.4)",
                          color: "red",
                        },
                        pl: 1,
                      }}
                      onClick={() => {
                        userInfoDispatch({ type: "LOGOUT" });
                      }}
                    >
                      <ExitToAppOutlinedIcon fontSize="small" sx={{ mr: 1 }} />
                      <Typography variant="subtitle2" sx={{ p: 0, m: 0 }}>
                        Déconnexion
                      </Typography>
                    </MenuItem>
                  </>
                ) : (
                  [
                    [
                      <MenuItem
                        sx={{
                          ":hover": {
                            backgroundColor: "rgba(100, 100, 100, 0.2)",
                          },
                          pl: 1,
                        }}
                        onClick={() => {
                          toggleDialog("loginDialog", true);
                        }}
                      >
                        <LoginOutlinedIcon fontSize="small" sx={{ mr: 1 }} />
                        <Typography variant="subtitle2" sx={{ p: 0, m: 0 }}>
                          Connexion
                        </Typography>
                      </MenuItem>,
                    ],
                    [
                      <MenuItem
                        sx={{
                          ":hover": {
                            backgroundColor: "rgba(100, 100, 100, 0.2)",
                          },
                          pl: 1,
                        }}
                        onClick={() => {
                          toggleDialog("registerDialog", true);
                        }}
                      >
                        <PersonAddOutlinedIcon
                          fontSize="small"
                          sx={{ mr: 1 }}
                        />
                        <Typography variant="subtitle2" sx={{ p: 0, m: 0 }}>
                          Enregistrement
                        </Typography>
                      </MenuItem>,
                    ],
                  ]
                )}
              </Menu>
            </Button>
          </Tooltip>
        </Toolbar>
      </AppBar>
      <Toolbar />
      {userInfo && userInfo.loggedIn && (
        <NotificationList
          anchorEl={notficationMenuAnchorEl}
          handleMenuClose={handleNotificationMenuClose}
          toggleDialog={toggleDialog}
          onSelectionChanged={onSelectionChanged}
          setMenuOpen={setNotificationMenuOpen}
          menuOpen={notificationMenuOpen}
        />
      )}
    </Box>
  );
};

export default Topbar;
