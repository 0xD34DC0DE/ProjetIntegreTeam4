import React, { useEffect, useState, useContext } from "react";
import { Box, Avatar, Typography, Grid, Card, Container } from "@mui/material";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";
import StudentState from "./StudentState";
import StudentInternshipDetailsDialog from "./StudentInternshipDetailsDialog";
import { motion } from "framer-motion";

const fadeIn = {
  hidden: { opacity: 0 },
  show: {
    opacity: [0, 1],
    transition: {
      delay: 0.1,
      staggerChildren: 0.5,
    },
  },
};

const AssignedStudentSupervisorView = ({
  visible,
  toggleDialog,
  dialogVisibility,
}) => {
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [userInfo] = useContext(UserInfoContext);
  const [openedStudentEmail, setOpenedStudentEmail] = useState("");

  useEffect(async () => {
    const getSupervisor = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/supervisor/${userInfo.email}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      return response.data;
    };
    const getAssignedStudents = async (id) => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/supervisor/getAssignedStudents/${id}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setAssignedStudents(response.data);
    };
    var supervisor = await getSupervisor();
    getAssignedStudents(supervisor.id);
  }, []);

  const resetOpenedStudentEmail = () => {
    setOpenedStudentEmail("");
  };

  return (
    <>
      {visible && (
        <>
          <Typography variant="h4" sx={{ color: "white", ml: 2, mt: 2 }}>
            Liste des étudiants assignés
          </Typography>
          <Grid
            sx={{ py: "5vh", mt: "10%", display: "flex" }}
            container
            spacing={{ xs: 2, md: 3 }}
            columns={{ xs: 4, sm: 8, md: 12 }}
          >
            {assignedStudents.map((student) => (
              <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={student.id}>
                <motion.div variants={fadeIn} initial="hidden" animate="show">
                  <Card
                    onClick={() => {
                      setOpenedStudentEmail(student.email);
                      toggleDialog("internshipDetailsDialog", true);
                    }}
                    sx={{
                      backgroundColor: "#1F2020",
                      boxShadow: 6,
                      "&:hover": {
                        backgroundColor: "#272929",
                        cursor: "pointer",
                      },
                      alignItem: "center",
                      justifyContent: "center",
                      p: 2,
                      mx: 2,
                    }}
                  >
                    <Box sx={{ textAlign: "center" }}>
                      <Avatar sx={{ mx: "auto", my: 2 }}></Avatar>
                      <Typography>{student.email}</Typography>
                      <Typography>
                        {student.firstName}, {student.lastName}
                      </Typography>
                      <Box sx={{ mt: 2 }}>
                        <StudentState studentState={student.studentState} />
                      </Box>
                    </Box>
                  </Card>
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </>
      )}
      <StudentInternshipDetailsDialog
        open={dialogVisibility.internshipDetailsDialog}
        resetOpenedStudentEmail={resetOpenedStudentEmail}
        openedStudentEmail={openedStudentEmail}
        toggleDialog={toggleDialog}
      />
    </>
  );
};

export default AssignedStudentSupervisorView;
