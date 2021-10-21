import React, { useState, useEffect } from "react";
import axios from "axios";
import {
  Avatar,
  Card,
  Typography,
  Grid,
  Select,
  MenuItem,
} from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import BlockIcon from "@mui/icons-material/Block";
import TodayIcon from "@mui/icons-material/Today";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { motion } from "framer-motion";
const StudentDashBoard = () => {
  const listState = [
    "INTERNSHIP_NOT_FOUND",
    "INTERNSHIP_FOUND",
    "WAITING_FOR_RESPONSE",
  ];

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

  const [isReadOnly, setIsReadOnly] = useState(true);
  const [isStatusUpdated, setIsStatusUpdated] = useState(false);

  useEffect(() => {
    const getProfile = () => {
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
            setIsReadOnly(response.data.studentState != listState[2]);
            console.log(profile);
          })
          .catch((error) => {
            console.error(error);
          });
      }
    };

    getProfile();
  }, []);

  const updateStudentStatus = () => {
    axios({
      method: "PATCH",
      url: "http://localhost:8080/student/updateStudentState",
      headers: {
        Authorization: sessionStorage.getItem("jwt"),
      },
      responseType: "json",
    })
      .then(() => {
        setIsStatusUpdated(true);
        console.log(profile);
      })
      .catch((error) => {
        console.error(error);
      });
  }

  const handleChange = ($event) => {
    setProfile({...profile,studentState:$event.target.value});
    console.log(profile.studentState);
    updateStudentStatus();
  };

  const fadeIn = {
    hidden: { opacity: 0 },
    show: {
      opacity: 1,
      transition: {
        staggerChildren: 0.5,
      },
    },
  };

  return (
    <>
      <motion.div variants={fadeIn} initial="hidden" animate="show">
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
          <Grid container spacing={2} sx={{ alignItems: "center" }}>
            <Grid item>
              <Avatar sx={{ width: 200, height: 200 }}>
                {profile.firstName.charAt(0)}
              </Avatar>
            </Grid>

            <Grid
              item
              xs={12}
              sm
              container
              sx={{ textAlign: "start", alignItems: "center" }}
            >
              <Grid item xs container direction="column" spacing={2}>
                <Grid item xs>
                  <Typography gutterBottom variant="subtitle1" component="div">
                    {profile.lastName}, {profile.firstName}
                    <AccountCircleIcon />
                  </Typography>
                  <Typography variant="body2" component="div" gutterBottom>
                    {profile.phoneNumber}
                    <PhoneAndroidIcon />
                  </Typography>
                  <Typography variant="body2" component="div" gutterBottom>
                    {profile.registrationDate}
                    <TodayIcon />
                  </Typography>
                  {profile.hasValidCv ? (
                    <Typography
                      sx={{ color: "green" }}
                      variant="body2"
                      component="div"
                      gutterBottom
                    >
                      {"Vous avez un cv est valide"}
                      <CheckCircleIcon />
                    </Typography>
                  ) : (
                    <Typography
                      sx={{ color: "red" }}
                      variant="body2"
                      component="div"
                      gutterBottom
                    >
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
                <Select
                  sx={{ margin: "auto", justifyContent: "center" }}
                  labelId="demo-simple-select-label"
                  id="demo-simple-select"
                  value={profile.studentState}
                  label="Age"
                  onChange={handleChange}
                  readOnly={isReadOnly}
                >
                  {listState.map((value, key) => (
                    <MenuItem key={key} value={value}>
                      {value}
                    </MenuItem>
                  ))}
                </Select>
              </Grid>
            </Grid>
          </Grid>
        </Card>
      </motion.div>
    </>
  );
};

export default StudentDashBoard;
