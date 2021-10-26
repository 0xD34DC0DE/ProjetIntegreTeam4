import { People } from "@mui/icons-material";
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import {
  Avatar,
  Box,
  Card,
  CardActions,
  IconButton,
  Tooltip,
  Typography,
} from "@mui/material";
import axios from "axios";
import { motion } from "framer-motion";
import { useContext, useState } from "react";
import { useDrop } from "react-dnd";
import { UserInfoContext } from "../stores/UserInfoStore";
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
  const [userInfo] = useContext(UserInfoContext);
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
            Authorization: userInfo.jwt,
          },
          responseType: "json",
        }).catch(console.error);

        setJustDropped(true);
        setTimeout(() => {
          setJustDropped(false);
        }, 1000);
      }
    },
  }));

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      setOpen(false);
    }
  };

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
        sx={{
          backgroundColor: isOver ? "#2F3030" : "#1F2020",
          alignItem: "center",
          justifyContent: "center",
          mx: 2,
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
                <People sx={{ color: "white" }} />
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
          handleClose={handleClose}
        />
      )}
    </>
  );
};

export default DroppableUserCard;
