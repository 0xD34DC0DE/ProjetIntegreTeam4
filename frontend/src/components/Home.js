import { Typography } from "@mui/material";
import React from "react";
import SideBar from "./SideBar";
import TopBar from "./TopBar";

const Home = () => {
  return (
    <>
      <TopBar />
      <Typography variant="h1">Home</Typography>
      <SideBar />
    </>
  );
};

export default Home;
