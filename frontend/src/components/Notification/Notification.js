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

const Notification = () => {
  const [showSnackbar, setShowSnackbar] = useState(false);
  const [snackbarContent, setSnackbarContent] = useState("");
  const [snackbarTitle, setSnackbarTitle] = useState("");
  const [userInfo] = useContext(UserInfoContext);

  const event = new EventSource("http://localhost:8080/notification/sse", {
    headers: { Authorization: userInfo.jwt },
  });

  useEffect(() => {
    event.onopen = () => {
      console.log("[EventSource] Connection established.");
    };
    event.onmessage = (event) => {
      setShowSnackbar(true);
      const data = JSON.parse(event.data);
      setSnackbarTitle(data.title);
      setSnackbarContent(data.content);
    };
  }, []);

  const handleOnClose = () => {
    setShowSnackbar(false);
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
            <Grid container ml={2}>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="subtitle2" sx={{ fontSize: "1.35em" }}>
                  {snackbarTitle}
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="caption">{snackbarContent}</Typography>
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
                    onClick={handleOnClose}
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
