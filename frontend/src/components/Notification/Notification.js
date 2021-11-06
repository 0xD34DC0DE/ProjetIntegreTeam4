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
  const [snackbackContent, setSnackbackContent] = useState("");
  const [userInfo] = useContext(UserInfoContext);

  const ev = new EventSource("http://localhost:8080/notification/stream", {
    headers: { Authorization: userInfo.jwt },
  });

  useEffect(() => {
    ev.onopen = (a) => {
      console.log("[EventSource] Connection established.");
    };
    ev.onmessage = (a) => {
      setShowSnackbar(true);
      setSnackbackContent("Test");
      console.log(a.data);
    };
  }, []);

  const handleOnClose = () => {
    setShowSnackbar(false);
  };

  return (
    <>
      <Snackbar
        open={showSnackbar}
        autoHideDuration={5000}
        onClose={handleOnClose}
      >
        <SnackbarContent
          sx={{
            backgroundColor: "rgba(0, 0, 0, 0.3)",
            boxShadow: "3px 3px 10px 1px rgba(0, 0, 0, 0.3)",
            maxWidth: "350px",
          }}
          message={
            <Grid container>
              <Grid container>
                <Grid item>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Typography variant="subtitle2" sx={{ fontSize: "1.35em" }}>
                      Nouvelle notification!
                    </Typography>
                  </Grid>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Typography variant="caption">
                      {snackbackContent}
                    </Typography>
                  </Grid>
                </Grid>
                <Grid item alignSelf="center" ml={2}>
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
            </Grid>
          }
        />
      </Snackbar>
    </>
  );
};

export default Notification;
