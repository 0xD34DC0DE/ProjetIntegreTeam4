import { Typography } from "@mui/material";
import React from "react";
import StickyFooter from "./Footer";
import SideBar from "./SideBar";
import TopBar from "./TopBar";

const Home = () => {
  return (
    <>
      <Typography
        variant="h1"
        component="h1"
        sx={{
          clear: "right",
        }}
      >
        Home
      </Typography>
    </>
  );
};

export default Home;
