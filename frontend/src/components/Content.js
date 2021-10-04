import { Typography } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

// Ici je n'ai pas déconstruit, ça provoque une erreur dans la console.
const Content = () => {
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  return (
    <Typography
      variant="h4"
      sx={{
        margin: "auto",
      }}
    >
      {userInfo.loggedIn ? (
        <>
          Hello {userInfo.role.toLowerCase()}, {userInfo.email}
        </>
      ) : (
        "Hello visitor."
      )}
    </Typography>
  );
};

export default Content;
