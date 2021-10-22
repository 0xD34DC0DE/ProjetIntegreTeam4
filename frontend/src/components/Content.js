import { Container, Typography } from "@mui/material";
import React from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import AsssignedStudentSupervisorView from "./AsssignedStudentSupervisorView";
import ListUserDraggable from "./ListUserDraggable";
import ListUserDroppable from "./ListUserDroppable";

const Content = () => {
  const [userInfo] = React.useContext(UserInfoContext);
  return (
    <>
      <Container
        sx={{
          display: "flex-row",
          justifyContent: "center",
          alignItems: "center",
          width: "100%",
          mt: "10%",
          mx: "auto",
          textAlign: "center",
        }}
      >
        {!userInfo.loggedIn ? (
          <Typography variant="h3">Bienvenue visiteur.</Typography>
        ) : (
          <Typography variant="h3">
            Bienvenue {userInfo.email.toLowerCase()}
          </Typography>
        )}
        <AsssignedStudentSupervisorView />
      </Container>
    </>
  );
};

export default Content;
