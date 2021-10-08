import { Container, Typography } from "@mui/material";
import React from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

// Ici je n'ai pas déconstruit, ça provoque une erreur dans la console.
const Content = () => {
  const [userInfo, userInfoDispatch] = React.useContext(UserInfoContext);
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
      {userInfo.loggedIn ? (
        <Typography variant="h4">
          Hello {userInfo.role.toLowerCase()}, {userInfo.email}
        </Typography>
      ) : (
        "Hello visitor."
      )}
    </Container>
  );
};

export default Content;
