import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { Card, Typography, Grid, Container } from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import { UserInfoContext } from "../../stores/UserInfoStore";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import { motion } from "framer-motion";
import PeopleIcon from "@mui/icons-material/People";
import SelectInterviewDate from "./SelectInterviewDate";
import SelectStudentState from "./SelectStudentState";
import IconImage from "./IconImage";
import BasicInfo from "./BasicInfo";

const SupervisorDashBoard = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [profile, setProfile] = useState({
    id: "",
    email: "",
    firstName: "",
    lastName: "",
    registrationDate: "",
    phoneNumber: "",
    nbrOfAssignedStudents: 0,
  });

  useEffect(() => {
    const getProfile = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/supervisor/getProfile",
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then((response) => {
          setProfile(response.data);
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getProfile();
  }, []);

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
              backgroundColor: "rgba(135, 135, 135, 0.03)",
              flexGrow: 1,
              boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
              ":hover": {
                boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
              }
            }}
          >
            <Grid
              container
              spacing={2}
              sx={{ alignItems: "center" }}
              justifyContent="center"
            >
              <IconImage profile={profile} role={userInfo.role} />
              <Grid item xs={12} sm container justifyContent="center">
                <Grid item xs container direction="column" spacing={3}>
                  <Grid item xs sx={{ textAlign: "center" }}>
                    <BasicInfo profile={profile} />
                  </Grid>
                </Grid>
                <Grid
                  item
                  sx={{ alignItems: "center", textAlign: "center",alignSelf:"center" }}
                  justifyContent="center"
                >
                  <Typography variant="subtitle1" sx={{ m: 1 }}>
                    <PeopleIcon /> Nombres d'élèves assignés :{" "}
                    {profile.nbrOfAssignedStudents}
                  </Typography>
                </Grid>
              </Grid>
            </Grid>
          </Card>
        </motion.div>
      </Container>
  </>
  );
};

export default SupervisorDashBoard;
