import React from "react";
import { Typography } from "@mui/material";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import TodayIcon from "@mui/icons-material/Today";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";

const BasicInfo = ({ profile }) => {
  return (
    <>
      <Typography variant="subtitle1" sx={{ m: 1 }}>
        Nom : {profile.lastName}, {profile.firstName}
        <AccountCircleIcon sx={{ ml: 2, verticalAlign: "middle" }} />
      </Typography>
      <Typography variant="subtitle1" sx={{ m: 1 }}>
        N° de téléphone : {profile.phoneNumber}
        <PhoneAndroidIcon sx={{ ml: 2, verticalAlign: "middle" }} />
      </Typography>
      <Typography variant="subtitle1" sx={{ m: 1 }}>
        Membre depuis : {profile.registrationDate}
        <TodayIcon sx={{ ml: 2, verticalAlign: "middle" }} />
      </Typography>
    </>
  );
};

export default BasicInfo;
