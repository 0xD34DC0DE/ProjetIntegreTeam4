import React, { useEffect, useState, useContext } from "react";
import { Box, Avatar, Typography, Grid, Card } from "@mui/material";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";

const AsssignedStudentSupervisorView = ({ visible }) => {
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [userInfo] = useContext(UserInfoContext);

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
      console.log("userinfo email", userInfo.email);
      console.log("res", response.data);
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
    console.log("supervisor", supervisor.id);
    getAssignedStudents(supervisor.id);
  }, []);
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
            {assignedStudents.map((student, index) => (
              <>
                <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
                  <Card
                    sx={{
                      backgroundColor: "#1F2020",
                      boxShadow: 6,
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
                    </Box>
                  </Card>
                </Grid>
              </>
            ))}
          </Grid>
        </>
      )}
    </>
  );
};

export default AsssignedStudentSupervisorView;
