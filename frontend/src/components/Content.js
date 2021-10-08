import { Container, Typography } from "@mui/material";
import React from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

const Content = () => {
  const [userInfo ] = React.useContext(UserInfoContext);
  return (
    <Container
      sx={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        mt: "10%",
        textAlign: "center",
      }}
    >
      {userInfo.loggedIn &&
        <Typography variant="h3">
          Bienvenu {userInfo.role.toLowerCase()}
        </Typography>
      }
    </Container>
  );
};

export default Content;
