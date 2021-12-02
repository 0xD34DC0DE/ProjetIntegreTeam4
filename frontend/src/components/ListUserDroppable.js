import { Grid, Typography } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState, useContext } from "react";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import { UserInfoContext } from "../stores/UserInfoStore";
import DroppableUserCard from "./DroppableUserCard";
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

const ListUserDroppable = ({ role, students, setStudents, fetchStudents }) => {
  const [supervisors, setSupervisors] = useState([]);
  const [userInfo] = useContext(UserInfoContext);

  useEffect(() => {
    const getAllUsersByRole = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/user/getAll?role=${role}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setSupervisors(response.data);
    };
    getAllUsersByRole();
  }, []);
  return (
    <DndProvider backend={HTML5Backend}>
      <motion.div variants={fadeIn} initial="hidden" animate="show">
        <Typography
          variant="subtitle2"
          sx={{ color: "white", fontSize: "2.2em", ml: 2, mt: 2 }}
        >
          Superviseurs
        </Typography>
      </motion.div>
      <Grid
        sx={{ py: "1vh", mt: "10%", display: "flex" }}
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        {supervisors.length > 0 ? (
          supervisors.map((user, index) => (
            <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
              <motion.div variants={fadeIn} initial="hidden" animate="show">
                <DroppableUserCard user={user} key={index} index={index} students={students} setStudents={setStudents} fetchStudents={fetchStudents}/>
              </motion.div>
            </Grid>
          ))
        ) : (
          <Typography color={"text.primary"} sx={{mx: "auto", py: "2rem", fontSize: "2rem"}}>
            Aucun superviseur
          </Typography>
        )}
      </Grid>
    </DndProvider>
  );
};

export default ListUserDroppable;
