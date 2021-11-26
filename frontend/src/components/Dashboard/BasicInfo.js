import React from "react";
import { Card, Typography, Grid, Container } from "@mui/material";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import TodayIcon from "@mui/icons-material/Today";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const BasicInfo = ({ profile }) => {
  return (
    <>
      <Typography
        gutterBottom
        variant="subtitle1"
        component="div"
        sx={{ m: 1 }}
      >
        Name : {profile.lastName}, {profile.firstName}
        <AccountCircleIcon />
      </Typography>
      <Typography variant="body2" component="div" gutterBottom sx={{ m: 1 }}>
        N° de téléphone : {profile.phoneNumber}
        <PhoneAndroidIcon />
      </Typography>
      <Typography variant="body2" component="div" gutterBottom sx={{ m: 1 }}>
        Membre depuis : {profile.registrationDate}
        <TodayIcon />
      </Typography>
    </>
  );
};

export default BasicInfo;
