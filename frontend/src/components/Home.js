import { Typography } from "@mui/material";
import React from "react";
import StickyFooter from "./Footer";
import SideBar from "./SideBar";
import TopBar from "./TopBar";

const Home = () => {
  const [open, setOpen] = React.useState(true);

  return (
    <>
      <TopBar setOpen={setOpen} open={open} />
      <SideBar setOpen={setOpen} open={open} />
      <Typography variant="h1">Home</Typography>
      <StickyFooter />
    </>
  );
};

export default Home;
