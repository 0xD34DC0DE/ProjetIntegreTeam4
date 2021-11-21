import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import {
  Grid,
  IconButton,
  Menu,
  MenuItem,
  Tooltip,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useRef, useState } from "react";
import { UserInfoContext } from "../../stores/UserInfoStore";
import Notification from "./Notification";
import dispatchNotificationClickEvent from "./NotificationClickDispatch";
import CachedIcon from "@mui/icons-material/Cached";
import { DialogContext } from "../../stores/DialogStore";

const NotificationList = ({
  anchorEl,
  menuOpen,
  setMenuOpen,
  handleMenuClose,
  onSelectionChanged,
  setNotificationCount,
}) => {
  const [notifications, setNotifications] = useState([]);
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [page, setPage] = useState(0);
  const [canLoadOlderNotifications, setCanLoadOlderNotifications] =
    useState(true);
  const notificationListBottomRef = useRef(null);

  useEffect(() => {
    if (page === 0) return;
    axios({
      method: "GET",
      url: "http://localhost:8080/notification",
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        page: page,
        receiverId: userInfo.id,
      },
      responseType: "json",
    })
      .then((response) => {
        if (response.data.length === 0) {
          setCanLoadOlderNotifications(false);
        } else {
          setNotifications((notifications) => [
            ...notifications,
            ...response.data,
          ]);
          scrollToBottom();
        }
      })
      .catch((error) => console.error(error));
  }, [page]);

  const scrollToBottom = () => {
    if (notifications.length === 0) return;
    if (menuOpen) notificationListBottomRef.current.scrollIntoView();
  };

  useEffect(() => {
    if (userInfo === undefined) return;
    axios({
      method: "GET",
      url: "http://localhost:8080/notification",
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        receiverId: userInfo.id,
      },
      responseType: "json",
    })
      .then((response) => {
        setNotifications(response.data);
      })
      .catch(console.error);
  }, [userInfo]);

  const addNotification = (notification) => {
    setNotifications((notifications) => [notification, ...notifications]);
    scrollToBottom();
  };

  useEffect(() => {
    setNotificationCount(notifications.length);
  }, [notifications]);

  const deleteNotification = (id) => {
    axios({
      method: "DELETE",
      url: "http://localhost:8080/notification",
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        notificationId: id,
        userId: userInfo.id,
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

  const onNotificationClick = (notification) => {
    dispatchNotificationClickEvent({
      notificationType: notification.notificationType,
      data: notification.data,
      dialogDispatch,
      onSelectionChanged,
    });
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
            maxHeight: "400px",
            width: "350px",
            py: 0,
          },
        }}
        sx={{ "& .MuiList-root": { py: 0 } }}
      >
        <Typography
          variant="subtitle2"
          fontSize="2em"
          mx={2}
          pt={1}
          sx={{
            borderBottom: "1px rgba(255, 255, 255, 0.1) solid",
          }}
        >
          Notifications
        </Typography>

        {notifications.map((notification, key) => {
          return [
            <Tooltip title={notification.content} placement="left">
              <MenuItem
                key={key}
                onClick={() => onNotificationClick(notification)}
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
                          color: "white",
                        }}
                      >
                        <Typography
                          variant="subtitle2"
                          sx={{ fontSize: "1em", display: "inline" }}
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
                        sx={{
                          overflow: "hidden",
                          textOverflow: "ellipsis",
                          whiteSpace: "nowrap",
                          color: "rgba(255, 255, 255, 0.7)",
                        }}
                      >
                        <Typography
                          variant="caption"
                          sx={{
                            fontSize: "0.7em",
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
                    xs={2}
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
        {canLoadOlderNotifications && (
          <MenuItem
            onClick={() => {
              setPage(page + 1);
            }}
            sx={{
              color: "rgba(255, 255, 255, 1)",
              ":hover": {
                color: "rgba(255, 255, 255, 1)",
                "& .MuiTypography-root": {
                  color: "rgba(255, 255, 255, 1)",
                },
                backgroundColor: "rgba(255, 255, 255, 0.03) !important",
              },
            }}
          >
            <Grid container justifyContent="center" alignItems="center">
              <CachedIcon sx={{ mr: 2 }}></CachedIcon>
              <Typography
                sx={{
                  fontSize: "0.8em",
                  color: "rgba(250, 250, 250, 0.8)",
                }}
              >
                Charger plus de notifications
              </Typography>
            </Grid>
          </MenuItem>
        )}
        <div ref={notificationListBottomRef}></div>
      </Menu>
      <Notification
        addNotification={addNotification}
        deleteNotification={deleteNotification}
      />
    </Grid>
  );
};

export default NotificationList;
