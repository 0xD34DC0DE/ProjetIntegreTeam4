import { Grid, Typography } from "@mui/material";
import { UserInfoContext } from "../stores/UserInfoStore";
import React, { useState, useEffect, useContext } from "react";
import UserCard from "./DraggableUserCard";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
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

const ListUserDraggable = ({ role, isDragging, students, setStudents }) => {

  return (
    <DndProvider backend={HTML5Backend}>
      <motion.div variants={fadeIn} initial="hidden" animate="show">
        <Typography
          variant="subtitle2"
          sx={{ color: "white", ml: 2, fontSize: "2.2em", pt: "1rem" }}
        >
          Étudiants
        </Typography>
      </motion.div>

      <Grid
        sx={{ py: "1vh", mt: "10%", display: "flex" }}
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >

        {students.length > 0 ? (
          students.map((user, index) => (
            <Grid
              item
              xs={6}
              sm={4}
              md={4}
              lg={3}
              xl={2}
              key={index}
              sx={{
                "&:hover": {
                  cursor: "grab",
                },
              }}
            >
              <motion.div variants={fadeIn} initial="hidden" animate="show">
                <UserCard isDragging={isDragging} user={user} key={user.id}/>
              </motion.div>
            </Grid>
          ))
        ) : (
          <Typography color={"text.primary"} sx={{mx: "auto", py: "2rem", fontSize: "2rem"}}>
            Aucun étudiant sans superviseur
          </Typography>
        )}

      </Grid>
    </DndProvider>
  );
};

export default ListUserDraggable;
