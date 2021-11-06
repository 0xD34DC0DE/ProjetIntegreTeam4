import {
  IconButton,
  Badge,
  Menu,
  MenuItem,
  Typography,
  Grid,
} from "@mui/material";
import React, { useState } from "react";
import NotificationsOutlinedIcon from "@mui/icons-material/NotificationsOutlined";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";

const notifs = [
  {
    title: "ATTENTION!",
    content: "Votre CV a été refusé, veuillez le retéléverser.",
    severity: "high",
  },
  {
    title: "Dépôt de votre CV",
    content: "Votre CV a été approuvé.",
    severity: "low",
  },
  {
    title: "Offres de stage",
    content: "12 offres de stage sont maintenant disponible.",
    severity: "low",
  },
  {
    title: "URGENT!",
    content: "Vous devez déposer votre CV pour utiliser la platforme.",
    severity: "high",
  },
];

const severity = {
  high: { color: "rgba(200, 100, 100, 1)" },
  low: { color: "rgba(100, 200, 100, 1)" },
};

const NotificationList = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [notifications, setNotifications] = useState(notifs);
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleButtonClick = (event) => {
    setMenuOpen(true);
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
    setMenuOpen(false);
  };

  return (
    <Grid container justifyContent="flex-end">
      <Badge
        badgeContent={notifications.length}
        color="primary"
        sx={{
          color: "white",
          mt: 2,
          mr: 3,
        }}
        max={5}
      >
        <IconButton
          onClick={handleButtonClick}
          sx={{
            color: "white",
            ":hover": { color: "rgba(255, 255, 255, 0.7)" },
          }}
        >
          <NotificationsOutlinedIcon />
        </IconButton>
      </Badge>
      <Menu
        onClose={handleClose}
        id="notification-menu"
        anchorEl={anchorEl}
        open={menuOpen}
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
            <MenuItem
              key={key}
              onclick={handleClose}
              sx={{ backgroundColor: "rgba(100, 100, 100, 0.05)", mb: 0.5 }}
            >
              <Grid container>
                <Grid item xl={10}>
                  <Grid container>
                    <Grid
                      item
                      xs={12}
                      sm={12}
                      md={12}
                      lg={12}
                      xl={12}
                      sx={{ color: severity[notification.severity] }}
                    >
                      <Typography variant="subtitle2" sx={{ fontSize: "1em" }}>
                        {notification.title}
                      </Typography>
                    </Grid>
                    <Grid item xs={12} sm={12} md={12} lg={12} xl={12}>
                      <Typography
                        variant="caption"
                        sx={{ fontSize: "0.7em", color: "white" }}
                      >
                        {notification.content}
                      </Typography>
                    </Grid>
                  </Grid>
                </Grid>
                <Grid xl={2} item alignSelf="center" textAlign="end">
                  <IconButton variant="text" sx={{ fontSize: "0.75em" }}>
                    <CancelOutlinedIcon
                      sx={{
                        color: "white",
                        ":hover": { color: "rgba(255, 100, 100, 1)" },
                      }}
                      fontSize="small"
                    />
                  </IconButton>
                </Grid>
              </Grid>
            </MenuItem>,
          ];
        })}
      </Menu>
    </Grid>
  );
};

export default NotificationList;
