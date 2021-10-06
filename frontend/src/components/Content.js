import { Container, Typography } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import ListCvInternshipManagerView from "./ListCvInternshipManagerView";

const Content = () => {
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

  return (
    <Container sx={{ clear: "right", margin: "auto", textAlign: "center" }}>
      <Typography variant="h4">
        {userInfo.loggedIn ? (
          <>
            Hello {userInfo.role.toLowerCase()}, {userInfo.email}{" "}
          </>
        ) : (
          "Hello visitor."
        )}
      </Typography>
        <ListCvInternshipManagerView style={{marginTop:"50px",marginBottom:"50px"}}></ListCvInternshipManagerView>
    </Container>
  );
};

export default Content;
