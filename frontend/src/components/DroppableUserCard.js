import {
  Cancel,
  Delete,
  DeleteOutline,
  People,
  Person,
} from "@mui/icons-material";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  Dialog,
  DialogContent,
  IconButton,
  ListItemAvatar,
  ListItemText,
  Tooltip,
  ListItem,
  List,
  Typography,
  ListItemButton,
  ListItemIcon,
} from "@mui/material";
import { useDrop } from "react-dnd";
import axios from "axios";
import { useState } from "react";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
const DroppableUserCard = ({ user, index }) => {
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [open, setOpen] = useState(false);
  const [justDropped, setJustDropped] = useState(false);
  const [{ canDrop, isOver }, drop] = useDrop(() => ({
    // The type (or types) to accept - strings or symbols
    accept: "UserCard",
    // Props to collect
    collect: (monitor) => ({
      isOver: monitor.isOver(),
      canDrop: monitor.canDrop(),
    }),
    drop: async (item, monitor) => {
      if (monitor.isOver()) {
        var tmp = assignedStudents.concat(item);
        if (!assignedStudents.includes(item.user.email)) {
          console.log("email", item.user.email);
          let response = await axios({
            method: "PATCH",
            url: `http://localhost:8080/supervisor/addEmailToStudentList?id=${user.id}&studentEmail=${item.user.email}`,
            headers: {
              Authorization: sessionStorage.getItem("jwt"),
            },
            responseType: "json",
          }).catch((res) => console.log("res", res));
        }
        setAssignedStudents(tmp);
        setJustDropped(true);
        setTimeout(() => {
          setJustDropped(false);
        }, 1000);
      }
    },
  }));

  //TODO: Use backdrop click to close the dialog
  return (
    <>
      <Card
        key={index}
        ref={drop}
        role={"Dustbin"}
        style={{ backgroundColor: isOver ? "red" : "white" }}
        sx={{
          alignItem: "center",
          justifyContent: "center",
          m: 2,
        }}
      >
        {justDropped && <PersonAddIcon sx={{ m: 2 }} />}
        <Box sx={{ textAlign: "center" }}>
          <Avatar sx={{ mx: "auto", my: 2 }}></Avatar>
          <Typography>{user.email}</Typography>
          <Typography>
            {user.firstName}, {user.lastName}
          </Typography>
          <CardActions>
            <Tooltip title="Voir les étudiants assignés">
              <IconButton
                sx={{ margin: "auto" }}
                onClick={() => {
                  setOpen(true);
                }}
              >
                <People color="primary" />
              </IconButton>
            </Tooltip>
          </CardActions>
        </Box>
      </Card>
      <Dialog open={open}>
        <DialogContent sx={{ p: 0 }}>
          <List>
            {assignedStudents.map((student, key) => {
              return (
                <ListItem>
                  <Person />
                  <ListItemText sx={{ ml: 5 }}>
                    {student.user.lastName}, {student.user.firstName}
                  </ListItemText>
                  <ListItemButton sx={{ ml: 5, p: 0 }}>
                    <DeleteOutline sx={{ color: "red" }} />
                  </ListItemButton>
                </ListItem>
              );
            })}
          </List>
        </DialogContent>
      </Dialog>
    </>
  );
};

export default DroppableUserCard;
