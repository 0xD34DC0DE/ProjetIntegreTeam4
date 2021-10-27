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
    nbrOfExclusiveOffers: 0,
    nbrOfAppliedOffers: 0,
    hasValidCv: false,
  });

  const [isDisabled, setIsDisabled] = useState(true);
  const [isStatusUpdated, setIsStatusUpdated] = useState(false);
  const [userInfo] = useContext(UserInfoContext);

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

  const handleChange = ($event) => {
    setProfile({ ...profile, studentState: $event.target.value });
    updateStudentStatus();
    setIsDisabled(true);
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
                border:"0.5px solid grey",
                alignItems:"center",
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
                <Grid item>
                  <Avatar sx={{ width: 200, height: 200, border:"1px solid white",boxShadow:6 }}>
                    {profile.firstName.charAt(0)}
                  </Avatar>
                </Grid>

                <Grid
                  item
                  xs={12}
                  sm
                  container
                  justifyContent="center"
                  sx={{ textAlign: "center", alignItems: "center" }}
                >
                  <Grid item xs container direction="column" spacing={2}>
                    <Grid item xs>
                      <Typography
                        gutterBottom
                        variant="subtitle1"
                        component="div"
                      >
                        Name : {profile.lastName}, {profile.firstName}
                        <AccountCircleIcon />
                      </Typography>
                      <Typography variant="body2" component="div" gutterBottom>
                        N° de téléphone : {profile.phoneNumber}
                        <PhoneAndroidIcon />
                      </Typography>
                      <Typography variant="body2" component="div" gutterBottom>
                        Membre depuis : {profile.registrationDate}
                        <TodayIcon />
                      </Typography>
                      {profile.hasValidCv ? (
                        <Typography
                          sx={{ color: "green" }}
                          variant="body2"
                          component="div"
                          gutterBottom
                        >
                          {"Vous avez un CV est valide"}
                          <CheckCircleIcon />
                        </Typography>
                      ) : (
                        <Typography
                          sx={{ color: "red" }}
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

                  <Grid item sx={{ alignItems: "center", textAlign: "center" }}>
                    <Typography variant="subtitle1">
                      <TouchAppIcon /> Nombres d'offres appliquées :{" "}
                      {profile.nbrOfAppliedOffers}
                    </Typography>
                    <Typography variant="subtitle1">
                      <StarBorderPurple500Icon /> Nombres d'offres exlusives :{" "}
                      {profile.nbrOfExclusiveOffers}
                    </Typography>
                    <Select
                      sx={{
                        margin: "auto",
                        border:"1px solid white",
                        display: "flex",
                        justifyContent: "center",
                        boxShadow: 3,
                        textAlign: "center",
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
                          disabled={value === listState[2] || value === listState[0]}
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
