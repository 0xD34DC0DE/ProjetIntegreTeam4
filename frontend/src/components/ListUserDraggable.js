import {
  Avatar,
  Button,
  Card,
  CardActionArea,
  CardContent,
  CardHeader,
  CardMedia,
  Grid,
  Typography,
  Box,
  Container,
} from "@mui/material";
import { UserInfoContext } from "../stores/UserInfoStore";
import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import UserCard from "./DraggableUserCard";
import DroppableUserCard from "./DroppableUserCard";
import { DndContext, DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";

const ListUserDraggable = ({ role, isDragging, visible }) => {
  const [users, setUsers] = useState([]);

  useEffect(() => {
    const getAllUsersByRole = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/user/getAll?role=${role}`,
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        responseType: "json",
      });
      setUsers(response.data);
    };
    getAllUsersByRole();
  }, []);
  return (
    <>
      {visible && (
        <DndProvider backend={HTML5Backend}>
          <Typography variant="h4" sx={{ color: "white", ml: 2 }}>
            Étudiants
          </Typography>
          <Grid
            sx={{ py: "5vh", mt: "10%", display: "flex" }}
            container
            spacing={{ xs: 2, md: 3 }}
            columns={{ xs: 4, sm: 8, md: 12 }}
          >
            {users.map((user, index) => (
              <>
                <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
                  <UserCard isDragging={isDragging} user={user} />
                </Grid>
              </>
            ))}
          </Grid>
        </DndProvider>
      )}
    </>
  );
};

export default ListUserDraggable;
