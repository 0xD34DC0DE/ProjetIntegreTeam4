import { Person } from "@mui/icons-material";
import {
  Dialog,
  DialogContent,
  List,
  ListItem,
  ListItemText,
} from "@mui/material";
import axios from "axios";
import React, { useEffect, useState, useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

const AssignedStudentsDialog = ({ open, user }) => {
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [userInfo] = useContext(UserInfoContext);
  useEffect(() => {
    const getAssignedStudents = async () => {
      let response = await axios({
        method: "PATCH",
        url: `http://localhost:8080/supervisor/getAssignedStudents?id=${user.id}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setAssignedStudents(response.data);
    };
    getAssignedStudents();
  }, []);
  return (
    <Dialog open={open}>
      <DialogContent sx={{ p: 0 }}>
        <List>
          {assignedStudents.map((student, key) => {
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
