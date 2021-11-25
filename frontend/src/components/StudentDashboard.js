import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import {
  Avatar,
  Card,
  Typography,
  Grid,
  Select,
  MenuItem,
  Container,
  TextField,
} from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import PhoneAndroidIcon from "@mui/icons-material/PhoneAndroid";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import BlockIcon from "@mui/icons-material/Block";
import TodayIcon from "@mui/icons-material/Today";
import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { motion } from "framer-motion";
import PublishedWithChangesIcon from "@mui/icons-material/PublishedWithChanges";
import WarningIcon from "@mui/icons-material/Warning";
import ScheduleIcon from "@mui/icons-material/Schedule";
import PeopleIcon from "@mui/icons-material/People";
import { UserInfoContext } from "../stores/UserInfoStore";

const StudentDashBoard = () => {
  const listState = [
    "WAITING_INTERVIEW",
    "INTERNSHIP_FOUND",
    "WAITING_FOR_RESPONSE",
  ];

  const listStateFrench = [
    "EN ATTENTE D'ENTREVUE",
    "STAGE TROUVÉE",
    "EN ATTENTE DE RÉPONSE",
  ];

  const currentDate = new Date();

  const [profile, setProfile] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    registrationDate: "",
    phoneNumber: "",
    studentState: "",
    closestInterviewDate: "",
    nbrOfExclusiveOffers: 0,
    nbrOfAppliedOffers: 0,
    nbrOfInterviews: 0,
    hasValidCv: false,
  });

  const [isDisabled, setIsDisabled] = useState(true);
  const [isStatusUpdated, setIsStatusUpdated] = useState(false);
  const [isDateValid, setIsDateValid] = useState(true);
  const [userInfo] = useContext(UserInfoContext);
  const [isInterviewDateUpdated, setIsInterviewDateUpdated] = useState(false);

  useEffect(() => {
    const getProfile = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/student/getProfile",
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then((response) => {
          setProfile(response.data);
          setIsDisabled(
            response.data.studentState !== listState[2] ||
              !response.data.hasValidCv
          );
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getProfile();
  }, []);

  const showAndHideStatusUpdateSuccessMsg = () => {
    setTimeout(() => {
      setIsStatusUpdated(false);
      setIsDateValid(true);
    }, 3000);
  };

  const updateStudentStatus = () => {
    axios({
      method: "PATCH",
      url: "http://localhost:8080/student/updateStudentState",
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then(() => {
        showAndHideStatusUpdateSuccessMsg();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const showAndHideInterviewDateErrorMsg = () => {
    setIsDateValid(false);
    setTimeout(() => {
      setIsDateValid(true);
    }, 3000);
  };

  const showAndHideInterviewUpdateSuccessMsg = () => {
    setIsInterviewDateUpdated(true);

    setTimeout(() => {
      setIsInterviewDateUpdated(false);
    }, 3000);
  };

  const updateInterviewDate = (date) => {
    axios({
      method: "PATCH",
      url: "http://localhost:8080/student/updateInterviewDate/" + date,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then(() => {
        showAndHideInterviewUpdateSuccessMsg();
      })
      .catch((error) => {
        showAndHideInterviewDateErrorMsg();
        console.error(error);
      });
  };

  const hasInternship = () => {
    return profile.studentState === listState[1];
  };

  const handleChange = ($event) => {
    setProfile({ ...profile, studentState: $event.target.value });
    updateStudentStatus();
    setIsDisabled(true);
  };

  const handleChangeDate = ($event) => {
    const value = $event.target.value;
    const dateValues = value.split("-");

    if (
      (currentDate.getFullYear() === parseInt(dateValues[0]) &&
        parseInt(dateValues[1]) > currentDate.getMonth() + 1) ||
      (parseInt(dateValues[2], 10) >= currentDate.getDate() &&
        parseInt(dateValues[1]) === currentDate.getMonth() + 1 &&
        !hasInternship())
    ) {
      setProfile({ ...profile, closestInterviewDate: value });
      updateInterviewDate(value);
      setIsDateValid(true);
    } else {
      showAndHideInterviewDateErrorMsg();
    }
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
      <Container>
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Card
            sx={{
              p: 15,
              mt: 10,
              maxWidth: 1000,
              border: "0.5px solid grey",
              alignItems: "center",
              backgroundColor: "rgba(0, 0, 0, 0.1)",
              flexGrow: 1,
              boxShadow: 6,
            }}
          >
            <Grid
              container
              spacing={2}
              sx={{ alignItems: "center" }}
              justifyContent="center"
            >
              <Grid item justifyContent="center">
                <Avatar
                  sx={{
                    width: 200,
                    height: 200,
                    border: "1px solid white",
                    boxShadow: 6,
                  }}
                >
                  {profile.firstName.charAt(0)}
                </Avatar>
                {profile.hasValidCv ? (
                  <Typography
                    sx={{ color: "green", textAlign: "center", m: 1 }}
                    variant="subtitle1"
                    component="div"
                    gutterBottom
                  >
                    {"Vous avez un CV est valide"}
                    <CheckCircleIcon />
                  </Typography>
                ) : (
                  <Typography
                    sx={{ color: "red", textAlign: "center", m: 1 }}
                    variant="subtitle1"
                    component="div"
                    gutterBottom
                  >
                    {"Vous n'avez aucun CV valide"}
                    <BlockIcon />
                  </Typography>
                )}
              </Grid>
              <Grid item xs={12} sm container justifyContent="center">
                <Grid item xs container direction="column" spacing={3}>
                  <Grid item xs sx={{ textAlign: "center" }}>
                    <Typography
                      gutterBottom
                      variant="subtitle1"
                      component="div"
                      sx={{ m: 1 }}
                    >
                      Name : {profile.lastName}, {profile.firstName}
                      <AccountCircleIcon />
                    </Typography>
                    <Typography
                      variant="body2"
                      component="div"
                      gutterBottom
                      sx={{ m: 1 }}
                    >
                      N° de téléphone : {profile.phoneNumber}
                      <PhoneAndroidIcon />
                    </Typography>
                    <Typography
                      variant="body2"
                      component="div"
                      gutterBottom
                      sx={{ m: 1 }}
                    >
                      Membre depuis : {profile.registrationDate}
                      <TodayIcon />
                    </Typography>

                    {!hasInternship() && profile.hasValidCv ? (
                      <TextField
                        sx={{
                          border: "1px white",
                          boxShadow: 4,
                          borderRadius: 2,
                          display: "flex",
                          m: 2,
                        }}
                        InputLabelProps={{
                          shrink: true,
                        }}
                        label="Date d'entrevue la plus proche"
                        value={profile.closestInterviewDate}
                        onChange={handleChangeDate}
                        type={"date"}
                      />
                    ) : (
                      <motion.div
                        variants={fadeIn}
                        initial="hidden"
                        animate="show"
                      >
                        <Typography
                          variant="subtitle1"
                          sx={{
                            color: "gray",
                            textAlign: "center",
                            alignItems: "center",
                            m: 2,
                          }}
                        >
                          {
                            "L'ajout d'entrevue n'est pas disponible pour l'instant"
                          }
                          <ScheduleIcon />
                        </Typography>
                      </motion.div>
                    )}

                    {isInterviewDateUpdated ? (
                      <motion.div
                        variants={fadeIn}
                        initial="hidden"
                        animate="show"
                      >
                        <Typography
                          variant="subtitle1"
                          sx={{ color: "green", textAlign: "center" }}
                        >
                          {"DATE D'ENTREVUE AJOUTÉE"}
                          <PublishedWithChangesIcon />
                        </Typography>
                      </motion.div>
                    ) : null}

                    {!isDateValid ? (
                      <motion.div
                        variants={fadeIn}
                        initial="hidden"
                        animate="show"
                      >
                        <Typography
                          variant="subtitle1"
                          sx={{ color: "red", textAlign: "center" }}
                        >
                          {"DATE D'ENTREVUE INVALIDE"}
                          <WarningIcon />
                        </Typography>
                      </motion.div>
                    ) : null}
                  </Grid>
                </Grid>
                <Grid
                  item
                  sx={{ alignItems: "center", textAlign: "center" }}
                  justifyContent="center"
                >
                  <Typography variant="subtitle1" sx={{ m: 1 }}>
                    <PeopleIcon /> Nombres d'entrevues :{" "}
                    {profile.nbrOfInterviews}
                  </Typography>
                  <Typography variant="subtitle1" sx={{ m: 1 }}>
                    <TouchAppIcon /> Nombres d'offres appliquées :{" "}
                    {profile.nbrOfAppliedOffers}
                  </Typography>
                  <Typography variant="subtitle1" sx={{ m: 1 }}>
                    <StarBorderPurple500Icon /> Nombres d'offres exlusives :{" "}
                    {profile.nbrOfExclusiveOffers}
                  </Typography>

                  <Select
                    sx={{
                      margin: "auto",
                      border: "1px white",
                      display: "flex",
                      justifyContent: "center",
                      boxShadow: 3,
                      textAlign: "center",
                      m: 1,
                    }}
                    labelId="demo-simple-select-label"
                    id="demo-simple-select"
                    value={profile.studentState}
                    label="Age"
                    onChange={handleChange}
                    disabled={isDisabled}
                  >
                    {listState.map((value, key) => (
                      <MenuItem
                        key={key}
                        value={value}
                        disabled={value !== listState[1]}
                        sx={{ color: "white" }}
                      >
                        {listStateFrench[listState.indexOf(value)]}
                      </MenuItem>
                    ))}
                  </Select>
                  {isStatusUpdated ? (
                    <motion.div
                      variants={fadeIn}
                      initial="hidden"
                      animate="show"
                    >
                      <Typography
                        variant="subtitle1"
                        sx={{ color: "green", textAlign: "center", m: 1 }}
                      >
                        {"STATUT MODIFIÉE"}
                        <PublishedWithChangesIcon />
                      </Typography>
                    </motion.div>
                  ) : null}
                </Grid>
              </Grid>
            </Grid>
          </Card>
        </motion.div>
      </Container>
    </>
  );
};

export default StudentDashBoard;
