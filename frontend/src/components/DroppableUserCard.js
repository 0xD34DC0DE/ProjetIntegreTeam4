import {
  People,
  PersonAddAltRounded,
  PersonAddRounded,
  PersonAddSharp,
  PersonAddTwoTone,
} from "@mui/icons-material";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  Icon,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";
import axios from "axios";
import { motion } from "framer-motion";
import { useState } from "react";
import { useDrop } from "react-dnd";
import AssignedStudentsDialog from "./AssignedStudentsDialog";

const fadeIn = {
  hidden: { transform: "translateY(20px)", opacity: 0 },
  show: {
    transform: 0,
    opacity: 1,
    transition: {
      staggerChildren: 1,
    },
  },
};

const DroppableUserCard = ({ user, index }) => {
  const [open, setOpen] = useState(false);
  const [justDropped, setJustDropped] = useState(false);
  const [assignedStudents, setAssignedStudents] = useState([]);
  const [droppedItem, setDroppedItem] = useState();
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
        await axios({
          method: "PATCH",
          url: `http://localhost:8080/supervisor/addEmailToStudentList?id=${user.id}&studentEmail=${item.user.email}`,
          headers: {
            Authorization: sessionStorage.getItem("jwt"),
          },
          responseType: "json",
        }).catch((res) => console.log("res", res));

        setJustDropped(true);
        setTimeout(() => {
          setJustDropped(false);
        }, 1000);
      }
    },
  }));

  const handleStudentAssignment = (data) => {
    setAssignedStudents(data);
  };

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
        {justDropped && (
          <motion.div variants={fadeIn} initial="hidden" animate="show">
            <PersonAddIcon
              sx={{ position: "static", float: "right" }}
              color="success"
            />
          </motion.div>
        )}
        <Box sx={{ textAlign: "center" }}>
          <Avatar sx={{ mx: "auto", my: 2, clear: "right" }}></Avatar>
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

      {open && (
        <AssignedStudentsDialog
          open={open}
          user={user}
          handleStudentAssignment={handleStudentAssignment}
          assignedStudents={assignedStudents}
        />
      )}
    </>
  );
};

export default DroppableUserCard;
