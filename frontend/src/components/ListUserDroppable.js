import { Grid } from "@mui/material";
import axios from "axios";
import React, { useEffect, useState } from "react";
import { DndProvider } from "react-dnd";
import { HTML5Backend } from "react-dnd-html5-backend";
import DroppableUserCard from "./DroppableUserCard";

const ListUserDroppable = ({ role }) => {
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
    <DndProvider backend={HTML5Backend}>
      <Grid
        sx={{ py: "5vh", mt: "10%", display: "flex" }}
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
      >
        {users.map((user, index) => (
          <>
            <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
              <DroppableUserCard user={user} key={index} index={index} />
            </Grid>
          </>
        ))}
      </Grid>
    </DndProvider>
  );
};

export default ListUserDroppable;
