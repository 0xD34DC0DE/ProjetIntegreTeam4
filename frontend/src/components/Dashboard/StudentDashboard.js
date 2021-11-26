import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { Card, Typography, Grid, Container } from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import { motion } from "framer-motion";
import PeopleIcon from "@mui/icons-material/People";
import SelectInterviewDate from "./SelectInterviewDate";
import SelectStudentState from "./SelectStudentState";
import IconImage from "./IconImage";
import BasicInfo from "./BasicInfo";

const StudentDashBoard = ({jwt}) => {
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

  useEffect(() => {
    const getProfile = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/student/getProfile",
        headers: {
          Authorization: jwt,
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

  const hasInternship = () => {
    return profile.studentState === listState[1];
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
              <IconImage profile={profile} />
              <Grid item xs={12} sm container justifyContent="center">
                <Grid item xs container direction="column" spacing={3}>
                  <Grid item xs sx={{ textAlign: "center" }}>
                    <BasicInfo profile={profile} />
                    <SelectInterviewDate
                      hasInternship={hasInternship}
                      profile={profile}
                      setProfile={setProfile}
                      jwt={jwt}
                      fadeIn={fadeIn}
                    />
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

                  <SelectStudentState
                    profile={profile}
                    setProfile={setProfile}
                    isDisabled={isDisabled}
                    setIsDisabled={setIsDisabled}
                    jwt={jwt}
                    fadeIn={fadeIn}
                    listState={listState}
                    listStateFrench={listStateFrench}
                  />
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
