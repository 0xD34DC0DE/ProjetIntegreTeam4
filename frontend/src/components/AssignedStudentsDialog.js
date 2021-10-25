import { Person } from "@mui/icons-material";
import {
  Dialog,
  DialogContent,
  List,
  ListItem,
  ListItemText,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

const AssignedStudentsDialog = ({
  open,
  user,
  handleStudentAssignment,
  assignedStudents,
  handleClose,
}) => {
  const [userInfo] = useContext(UserInfoContext);
  useEffect(() => {
    const getAssignedStudents = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/supervisor/getAssignedStudents/${user.id}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      handleStudentAssignment(response.data);
    };

    getAssignedStudents();
  }, []);
  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogContent sx={{ textAlign: "center" }}>
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
            <Typography
              variant="subtitle1"
              sx={{ fontStyle: "italic", color: "rgba(255, 255, 255, 0.5)" }}
            >
              Aucun étudiant assigné
            </Typography>
          )}
        </List>
      </DialogContent>
    </Dialog>
  );
};

export default AssignedStudentsDialog;
