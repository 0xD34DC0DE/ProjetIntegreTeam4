import { Container, Typography } from "@mui/material";
import React from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import StudentDashBoard from "./StudentDashboard";

const Content = () => {
  const [userInfo] = React.useContext(UserInfoContext);
  return (
    <>
      <Container
        sx={{
          display: "flex",
          justifyContent: "center",
          alignItems: "center",
          mt: "10%",
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
      </Container>
      {userInfo.role == "STUDENT" ? (
        <div>
          <StudentDashBoard></StudentDashBoard>
        </div>
      ) : null}
    </>
  );
};

export default Content;
