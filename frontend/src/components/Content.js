import { Container, Typography } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import FileUpload from "./FileUpload";

const Content = () => {
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

  return (
    <Container sx={{ clear: "right", ml: "0" }}>
      <Typography variant="h4">
        {userInfo.loggedIn ? (
          <>
            Hello {userInfo.role.toLowerCase()}, {userInfo.email}{" "}
          </>
        ) : (
          "Hello visitor."
        )}
        <FileUpload />
      </Typography>
    </Container>
  );
};

export default Content;
