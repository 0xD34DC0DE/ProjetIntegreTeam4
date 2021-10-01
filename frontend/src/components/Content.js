import { Container, Typography } from "@mui/material";
import React, { useContext } from "react";
import { styled, useTheme } from "@mui/material/styles";
import { UserInfoContext } from "../stores/UserInfoStore";

const drawerWidth = 25;
const Main = styled("main", { shouldForwardProp: (prop) => prop })(
  ({ theme, open }) => ({
    flexGrow: 1,
    padding: 0,
    transition: theme.transitions.create("margin", {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    marginLeft: 0,
    ...(open && {
      transition: theme.transitions.create("margin", {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
      marginLeft: `${drawerWidth}rem`,
    }),
  })
);

// Ici je n'ai pas déconstruit, ça provoque une erreur dans la console.
const Content = (props) => {
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

  return (
    <Main open={props.open}>
      <Typography
        variant="h4"
        sx={{
          margin: "auto",
        }}
      >
        {userInfo.loggedIn ? (
          <>
            Hello {userInfo.role.toLowerCase()}, {userInfo.email}{" "}
          </>
        ) : (
          "Hello visitor."
        )}
      </Typography>
    </Main>
  );
};

export default Content;
