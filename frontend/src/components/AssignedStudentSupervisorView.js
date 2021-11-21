import React, { useEffect, useState, useContext } from "react";
import { Box, Avatar, Typography, Grid, Card } from "@mui/material";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";
import StudentState from "./StudentState";
import StudentInternshipDetailsDialog from "./StudentInternshipDetailsDialog";
import { motion } from "framer-motion";
import SemesterSelect from "./SemesterSelect";
import { DialogContext } from "../stores/DialogStore";

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

const AssignedStudentSupervisorView = () => {
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [userInfo] = useContext(UserInfoContext);
  const [openedStudentEmail, setOpenedStudentEmail] = useState("");
  const [semesterFullName, setSemesterFullName] = useState("");
  const [dialog, dialogDispatch] = useContext(DialogContext);

  useEffect(async () => {
    if (semesterFullName === "") return;
    const supervisorId = await axios({
      method: "GET",
      url: `http://localhost:8080/supervisor/${userInfo.email}`,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    }).then((response) => {
      return response.data.id;
    });

    axios({
      method: "GET",
      url: "http://localhost:8080/supervisor/getAssignedStudents",
      params: {
        supervisorId: supervisorId,
        semesterFullName: semesterFullName,
      },
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    }).then((response) => {
      setAssignedStudents(response.data);
    });
  }, [semesterFullName]);

  const updateSemesterFullName = (fullName) => {
    setSemesterFullName(fullName);
  };

  const resetOpenedStudentEmail = () => {
    setOpenedStudentEmail("");
  };

  return (
    <>
      <SemesterSelect updateSemesterFullName={updateSemesterFullName} />
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
                  dialogDispatch({
                    type: "OPEN",
                    dialogName: "internshipDetailsDialog",
                  });
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

      <StudentInternshipDetailsDialog
        resetOpenedStudentEmail={resetOpenedStudentEmail}
        openedStudentEmail={openedStudentEmail}
      />
    </>
  );
};

export default AssignedStudentSupervisorView;
