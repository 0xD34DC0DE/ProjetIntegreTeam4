import { Typography } from "@mui/material";
import React from "react";

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
      </Typography>
    </>
  );
};

export default Home;
