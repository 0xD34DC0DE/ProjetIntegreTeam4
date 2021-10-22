import { Person } from "@mui/icons-material";
import {
  Dialog,
  DialogContent,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";

const AssignedStudentsDialog = ({
  open,
  user,
  handleStudentAssignment,
  assignedStudents,
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
    <Dialog open={true}>
      <DialogContent sx={{ p: 0 }}>
        <List>
          {assignedStudents &&
            assignedStudents.map((student, key) => {
              return (
                <ListItem key={key}>
                  <Person />
                  <ListItemText sx={{ ml: 5 }}>
                    {student.firstName}, {student.lastName}
                  </ListItemText>
                </ListItem>
              );
            })}
        </List>
      </DialogContent>
    </Dialog>
  );
};

export default AssignedStudentsDialog;
