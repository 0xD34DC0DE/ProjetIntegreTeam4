import { Person } from "@mui/icons-material";
import {
  Dialog,
  DialogContent,
  Divider,
  List,
  ListItem,
  ListItemText,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";

const AssignedStudentsDialog = ({
  open,
  user,
  handleStudentAssignment,
  assignedStudents,
  handleClose,
}) => {
  useEffect(() => {
    const getAssignedStudents = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/supervisor/getAssignedStudents/${user.id}`,
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        responseType: "json",
      });
      handleStudentAssignment(response.data);
    };

    getAssignedStudents();
  }, []);
  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogContent sx={{ p: 5, py: 2 }}>
        <Typography variant="h6">Étudiants assignés</Typography>
        <List>
          {assignedStudents.length > 0 ? (
            assignedStudents.map((student, key) => {
              return (
                <ListItem key={key}>
                  <Person />
                  <ListItemText sx={{ ml: 5 }}>
                    {student.firstName}, {student.lastName}
                  </ListItemText>
                </ListItem>
              );
            })
          ) : (
            <Typography>Aucun étudiant assigné</Typography>
          )}
        </List>
      </DialogContent>
    </Dialog>
  );
};

export default AssignedStudentsDialog;
