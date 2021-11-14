import {
  Grid,
  Snackbar,
  SnackbarContent,
  Typography,
  Button,
  Tooltip,
  IconButton,
} from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import CheckCircleOutlineOutlinedIcon from "@mui/icons-material/CheckCircleOutlineOutlined";
import EventSource from "eventsource";
import { UserInfoContext } from "../../stores/UserInfoStore";

const Notification = ({ addNotification, deleteNotification }) => {
  const [showSnackbar, setShowSnackbar] = useState(false);
  const [userInfo] = useContext(UserInfoContext);
  const [notification, setNotification] = useState({
    id: "",
    title: "",
    data: {},
    content: "",
  });
  const [eventSource, setEventSource] = useState(undefined);
  const [webSocket, setWebSocket] = useState(undefined);

  useEffect(() => {
    if (!userInfo.loggedIn) return;
    setEventSource(
      new EventSource("http://localhost:8080/notification/sse", {
        headers: { Authorization: userInfo.jwt },
      })
    );
    setWebSocket(new WebSocket("ws://localhost:8080/ws"));
  }, [userInfo]);

  useEffect(() => {
    if (eventSource === undefined) return;
    eventSource.onopen = (event) => {
      console.log("[EventSource] Connection established.");
    };
    eventSource.onerror = (error) => {
      console.error(error);
      eventSource.close();
    };
    eventSource.onmessage = (event) => {
      setShowSnackbar(true);
      const data = JSON.parse(event.data);
      setNotification({
        id: data.id,
        content: data.content,
        title: data.title,
      });
      addNotification(data);
    };
  }, [eventSource]);

  useEffect(() => {
    if (webSocket === undefined) return;
    webSocket.onopen = (event) => {
      console.log("[WebSocket] Connection established.");
    };
    webSocket.onclose = (event) => {
      console.log(event);
    };
    webSocket.onmessage = (event) => {
      console.log(event);
    };
    webSocket.onerror = (e) => {
      console.error(e);
    };

    setTimeout(() => {
      ping();
    }, 5000);
  }, [webSocket]);

  const handleOnClose = () => {
    setShowSnackbar(false);
  };

  const ping = () => {
    webSocket.send(userInfo.email);
    console.log("Sending email payload to server...");
    setTimeout(() => {
      ping();
    }, 10000);
  };

  return (
    <>
      <Snackbar
        open={showSnackbar}
        autoHideDuration={520300}
        onClose={handleOnClose}
      >
        <SnackbarContent
          sx={{
            backgroundColor: "rgba(0, 0, 0, 0.3)",
            boxShadow: "3px 3px 10px 1px rgba(0, 0, 0, 0.3)",
            p: 0,
            m: 0,
          }}
          message={
            <Grid container ml={2} sx={{ maxWidth: "250px" }}>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="subtitle2" sx={{ fontSize: "1.35em" }}>
                  {notification.title}
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="caption">
                  {notification.content}
                </Typography>
              </Grid>
            </Grid>
          }
          action={
            <Grid container mr={2}>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Tooltip title="Accuser la réception">
                  <IconButton
                    variant="text"
                    sx={{ fontSize: "0.75em" }}
                    onClick={handleOnClose}
                  >
                    <CheckCircleOutlineOutlinedIcon
                      sx={{
                        color: "white",
                        ":hover": { color: "rgba(100, 200, 100, 1)" },
                      }}
                      fontSize="small"
                    />
                  </IconButton>
                </Tooltip>
                <Tooltip title="Supprimer la notification">
                  <IconButton
                    variant="text"
                    sx={{ fontSize: "0.75em" }}
                    onClick={() => {
                      deleteNotification(notification.id);
                      setShowSnackbar(false);
                    }}
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
          }
        />
      </Snackbar>
    </>
  );
};

export default Notification;
