import { Typography } from "@mui/material";
import React from "react";
import FileUpload from "./FileUpload";

const Home = ({ userInformation }) => {
  return (
    <>
      <Typography variant="h4">
        {userInformation.loggedIn ? (
          <>
            {" "}
            Hello {userInformation.role.toLowerCase()}, {userInformation.email}{" "}
          </>
        ) : (
          "Hello visitor."
        )}
        <FileUpload />
      </Typography>
    </>
  );
};

export default Home;
