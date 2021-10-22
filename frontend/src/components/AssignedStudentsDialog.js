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

const AssignedStudentsDialog = ({ open, user, assignedStudents }) => {
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
    };
    getAssignedStudents();
  }, []);
  return (
    <Dialog open={open}>
      <DialogContent sx={{ p: 0 }}>
        <List>
          {assignedStudents &&
            assignedStudents.map((student, key) => {
              console.log("studentt", student);
              return (
                <ListItem key={key}>
                  <Person />
                  <ListItemText sx={{ ml: 5 }}>
                    {student.user.lastName}, {student.user.firstName}
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
