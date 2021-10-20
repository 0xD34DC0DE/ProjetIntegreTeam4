import React, { useState, useEffect } from "react";
import axios from "axios";
import { styled } from "@mui/material/styles";
import {
  Avatar,
  Card,
  Typography,
  Grid,
} from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import BlockIcon from '@mui/icons-material/Block';
import TodayIcon from '@mui/icons-material/Today';

const StudentDashBoard = () => {
  const [profile, setProfile] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    registrationDate: "",
    phoneNumber: "",
    studentState: "",
    nbrOfExclusiveOffers: 0,
    nbrOfAppliedOffers: 0,
    hasValidCv: false,
  });

  useEffect(() => {
    const getProfile = (valid) => {
      {
        axios({
          method: "GET",
          url: "http://localhost:8080/student/getProfile",
          headers: {
            Authorization: sessionStorage.getItem("jwt"),
          },
          responseType: "json",
        })
          .then((response) => {
            setProfile(response.data);
            console.log(profile);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    };

    getProfile();
  }, []);

  return (
    <>
      <Card
        sx={{
          p: 15,
          margin: "auto",
          maxWidth: 1000,
          flexGrow: 1,
          border: "1px solid black",
          boxShadow: 6,
        }}
      >
        <Grid container spacing={2} sx={{alignItems:"center"}}>
          <Grid item
          >
              <Avatar sx={{ width: 200, height: 200 }}>
                {profile.firstName.charAt(0)}
              </Avatar>
          </Grid>

          <Grid item xs={12} sm container sx={{textAlign:"start",alignItems:"center"}}>
            <Grid item xs container direction="column" spacing={2}>
              <Grid item xs>
                <Typography gutterBottom variant="subtitle1" component="div">
                  {profile.lastName}, {profile.firstName}
                </Typography>
                <Typography variant="body2" component="div" gutterBottom>
                  {profile.phoneNumber}
                  <PhoneAndroidIcon />
                </Typography>
                <Typography variant="body2" component="div" gutterBottom>
                  {profile.registrationDate}
                  <TodayIcon/>
                </Typography>
                {profile.hasValidCv ? (
                  <Typography sx={{color:"green"}} variant="body2" component="div" gutterBottom>
                    {"Vous avez un cv est valide"}
                    <CheckCircleIcon/>
                  </Typography>
                ) : (
                  <Typography sx={{color:"red"}} variant="body2" component="div" gutterBottom>
                    {"Vous n'avez aucun cv valide"}
                    <BlockIcon />
                  </Typography>
                )}
              </Grid>
            </Grid>

            <Grid item sx={{ alignItems: "center" }}>
              <Typography variant="subtitle1">
                <TouchAppIcon /> Nombres d'offres appliquées :{" "}
                {profile.nbrOfAppliedOffers}
              </Typography>
              <Typography variant="subtitle1">
                <StarBorderPurple500Icon /> Nombres d'offres exlusives :{" "}
                {profile.nbrOfExclusiveOffers}
              </Typography>
            </Grid>
          </Grid>
        </Grid>
      </Card>
    </>
  );
};

export default StudentDashBoard;
