import { Container, Typography } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import CvValidationDialog from "./CvValidationDialog";

const Content = () => {
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

  return (
    <Container sx={{ clear: "right", margin: "auto",textAlign: "center" }}>
      <Typography variant="h4">
        {userInfo.loggedIn ? (
          <>
            Hello {userInfo.role.toLowerCase()}, {userInfo.email}{" "}
          </>
        ) : (
          "Hello visitor."
        )}
      </Typography>
      <CvValidationDialog id={"61592af0c1198a3d5a45c96e"}></CvValidationDialog>
    </Container>
  );
};

export default Content;
