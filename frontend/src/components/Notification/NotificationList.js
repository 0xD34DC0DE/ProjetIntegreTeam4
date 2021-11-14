import {
  IconButton,
  Menu,
  MenuItem,
  Typography,
  Grid,
  Tooltip,
} from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import axios from "axios";
import Notification from "./Notification";
import { UserInfoContext } from "../../stores/UserInfoStore";

const severity = {
  high: { color: "rgba(200, 100, 100, 1)" },
  low: { color: "rgba(100, 200, 100, 1)" },
};

const NotificationList = ({
  anchorEl,
  menuOpen,
  setMenuOpen,
  handleMenuClose,
}) => {
  const [notifications, setNotifications] = useState([]);
  const [userInfo] = useContext(UserInfoContext);

  useEffect(() => {
    axios({
      method: "GET",
      url: "http://localhost:8080/notification",
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then((response) => {
        setNotifications(response.data);
      })
      .catch(console.error);
  }, [userInfo]);

  const addNotification = (notification) => {
    setNotifications((notifications) => [...notifications, notification]);
  };

  const deleteNotification = (id) => {
    axios({
      method: "DELETE",
      url: "http://localhost:8080/notification/" + id,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then(() => {
        const tempArr = notifications.filter(
          (notification) => notification.id != id
        );
        setNotifications(tempArr);
        if (tempArr.length === 0) setMenuOpen(false);
      })
      .catch(console.error);
  };

  return (
    <Grid container justifyContent="flex-end">
      <Menu
        onClose={handleMenuClose}
        id="notification-menu"
        anchorEl={anchorEl}
        open={menuOpen && notifications.length > 0}
        PaperProps={{
          style: {
            maxHeight: "200px",
            width: "350px",
            backgroundColor: "rgba(0, 0, 0, 0.3)",
            py: 0,
          },
        }}
        sx={{ "& .MuiList-root": { py: 0 } }}
      >
        {notifications.map((notification, key) => {
          return [
            <Tooltip title={notification.content} placement="left">
              <MenuItem
                key={key}
                sx={{
                  backgroundColor: "rgba(100, 100, 100, 0.05)",
                  mb: 0.5,
                }}
              >
                <Grid container>
                  <Grid item xl={10} lg={10} md={10} sm={10} xs={10}>
                    <Grid container>
                      <Grid
                        item
                        xs={12}
                        sm={12}
                        md={12}
                        lg={12}
                        xl={12}
                        sx={{
                          color: severity[notification.severity.toLowerCase()],
                        }}
                      >
                        <Typography
                          variant="subtitle2"
                          sx={{ fontSize: "1em" }}
                        >
                          {notification.title}
                        </Typography>
                      </Grid>
                      <Grid
                        item
                        xs={12}
                        sm={12}
                        md={12}
                        lg={12}
                        xl={12}
                        xs={12}
                        sx={{
                          overflow: "hidden",
                          textOverflow: "ellipsis",
                          whiteSpace: "nowrap",
                        }}
                      >
                        <Typography
                          variant="caption"
                          sx={{
                            fontSize: "0.7em",
                            color: "white",
                          }}
                        >
                          {notification.content}
                        </Typography>
                      </Grid>
                    </Grid>
                  </Grid>
                  <Grid
                    item
                    xl={2}
                    lg={2}
                    md={2}
                    sm={2}
                    item
                    alignSelf="center"
                    textAlign="end"
                  >
                    <Tooltip title="Supprimer">
                      <IconButton
                        variant="text"
                        onClick={() => {
                          deleteNotification(notification.id);
                        }}
                        sx={{ fontSize: "0.75em" }}
                      >
                        <CancelOutlinedIcon
                          sx={{
                            color: "white",
                            ":hover": { color: "rgba(255, 100, 100, 1)" },
                          }}
                          fontSize="small"
                        />
                      </IconButton>
                    </Tooltip>
                  </Grid>
                </Grid>
              </MenuItem>
            </Tooltip>,
          ];
        })}
      </Menu>
      <Notification
        addNotification={addNotification}
        deleteNotification={deleteNotification}
      />
    </Grid>
  );
};

export default NotificationList;