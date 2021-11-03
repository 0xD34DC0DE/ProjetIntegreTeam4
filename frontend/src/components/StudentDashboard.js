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
  FormControl,
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
import PeopleIcon from "@mui/icons-material/People";
import { UserInfoContext } from "../stores/UserInfoStore";

const StudentDashBoard = ({ visible }) => {
  const listState = [
    "INTERNSHIP_NOT_FOUND",
    "INTERNSHIP_FOUND",
    "WAITING_FOR_RESPONSE",
  ];

  const listStateFrench = [
    "STAGE NON TROUVÉE",
    "STAGE TROUVÉE",
    "EN ATTENTE DE RÉPONSE",
  ];

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
  const [userInfo] = useContext(UserInfoContext);
  const [isInterviewDateUpdated, setIsInterviewDateUpdated] = useState(false);

  useEffect(() => {
    const getProfile = () => {
      {
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
      }
    };

    getProfile();
  }, []);

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
        setIsStatusUpdated(true);
      })
      .catch((error) => {
        console.error(error);
      });
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
        setIsInterviewDateUpdated(true);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleChange = ($event) => {
    setProfile({ ...profile, studentState: $event.target.value });
    updateStudentStatus();
    setIsDisabled(true);
  };

  const handleChangeDate = ($event) => {
    const value = $event.target.value;

    setProfile({ ...profile, closestInterviewDate: value });
    updateInterviewDate(value);
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
      {visible && (
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
              <Grid container spacing={2} sx={{ alignItems: "center" }} justifyContent="center">
                <Grid item>
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
                </Grid>
                <Grid item xs={12} sm container justifyContent="center">
                  <Grid item xs container direction="column" spacing={3}>
                    <Grid item xs sx={{ textAlign: "center" }} >
                      <Typography
                        gutterBottom
                        variant="subtitle1"
                        component="div"
                        sx={{m:1}}
                      >
                        Name : {profile.lastName}, {profile.firstName}
                        <AccountCircleIcon />
                      </Typography>
                      <Typography variant="body2" component="div" gutterBottom sx={{m:1}}>
                        N° de téléphone : {profile.phoneNumber}
                        <PhoneAndroidIcon />
                      </Typography>
                      <Typography variant="body2" component="div" gutterBottom sx={{m:1}}>
                        Membre depuis : {profile.registrationDate}
                        <TodayIcon />
                      </Typography>

                      <TextField
                        sx={{
                          border: "1px white",
                          boxShadow: 4,
                          borderRadius: 2,
                          m: 2,
                        }}
                        label="Date d'entrevue la plus proche"
                        value={profile.closestInterviewDate}
                        onChange={handleChangeDate}
                        type={"date"}
                      />

                      {isInterviewDateUpdated ? (
                        <Typography
                          variant="subtitle1"
                          sx={{ color: "green", textAlign: "center" }}
                        >
                          {"DATE D'ENTREVUE AJOUTÉE"}
                          <PublishedWithChangesIcon />
                        </Typography>
                      ) : null}
                    </Grid>
                  </Grid>
                  <Grid item sx={{ alignItems: "center", textAlign: "center" }} justifyContent="center">
                    <Typography variant="subtitle1" sx={{m:1}}>
                      <PeopleIcon /> Nombres d'entrevues :{" "}
                      {profile.nbrOfInterviews}
                    </Typography>
                    <Typography variant="subtitle1" sx={{m:1}}>
                      <TouchAppIcon /> Nombres d'offres appliquées :{" "}
                      {profile.nbrOfAppliedOffers}
                    </Typography>
                    <Typography variant="subtitle1" sx={{m:1}}>
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
                        m:1
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
                          disabled={
                            value === listState[2] || value === listState[0]
                          }
                          sx={{ color: "white" }}
                        >
                          {listStateFrench[listState.indexOf(value)]}
                        </MenuItem>
                      ))}
                    </Select>
                    {isStatusUpdated ? (
                      <Typography
                        variant="subtitle1"
                        sx={{ color: "green", textAlign: "center" }}
                      >
                        {"STATUT MODIFIÉE"}
                        <PublishedWithChangesIcon />
                      </Typography>
                    ) : null}

                    {profile.hasValidCv ? (
                      <Typography
                        sx={{ color: "green", textAlign: "center" }}
                        variant="body2"
                        component="div"
                        gutterBottom
                      >
                        {"Vous avez un CV est valide"}
                        <CheckCircleIcon />
                      </Typography>
                    ) : (
                      <Typography
                        sx={{ color: "red", textAlign: "center" }}
                        variant="body2"
                        component="div"
                        gutterBottom
                      >
                        {"Vous n'avez aucun CV valide"}
                        <BlockIcon />
                      </Typography>
                    )}
                  </Grid>
                </Grid>
              </Grid>
            </Card>
          </motion.div>
        </Container>
      )}
    </>
  );
};

export default StudentDashBoard;
