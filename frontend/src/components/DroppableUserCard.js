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

const DroppableUserCard = ({ user, index, fetchStudents }) => {
  const [open, setOpen] = useState(false);
  const [justDropped, setJustDropped] = useState(false);
  const [assignedStudents, setAssignedStudents] = useState([]);
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
        })
          .then(() => fetchStudents())
          .catch(console.error);

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

  return (
    <>
      <Card
        key={index}
        ref={drop}
        role={"Dustbin"}
        sx={{
          border: "0.5px solid rgba(255, 255, 255, 0.2)",
          boxShadow: "3px 3px 15px 5px rgba(0, 0, 0, 0.5)",
          backgroundColor: isOver
            ? "rgba(135, 135, 135, 0.1)"
            : "rgba(135, 135, 135, 0.03)",
          alignItem: "center",
          borderRadius: "5px",
          justifyContent: "center",
          mx: 2,
          ":hover": {
            border: "0.5px solid rgba(255, 255, 255, 0)",
            boxShadow: "0px 0px 15px 1px rgba(125, 51, 235, 0.8) !important",
          },
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
          <Avatar
            sx={{
              mx: "auto",
              my: 2,
              clear: "right",
              width: "75px",
              height: "75px",
            }}
          ></Avatar>
          <Typography>{user.email}</Typography>
          <Typography>
            {user.firstName}, {user.lastName}
          </Typography>
          <CardActions>
            <Tooltip title="Voir les étudiants assignés">
              <motion.div
                style={{ margin: "0 auto 0 auto" }}
                whileHover={{ scale: [1, 1, 1, 1.5, 1, 1] }}
                animate={{}}
              >
                <IconButton
                  sx={{
                    margin: "auto",
                    backgroundColor: "rgba(125, 51, 235, 0.8)",

                    ":hover": {
                      backgroundColor: "#5d1f94",
                    },
                  }}
                  onClick={() => {
                    setOpen(true);
                  }}
                >
                  <People sx={{ color: "white" }} />
                </IconButton>
              </motion.div>
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
