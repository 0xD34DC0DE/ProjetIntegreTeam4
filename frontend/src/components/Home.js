<<<<<<< HEAD
import React from 'react';

const Home = () => {
    return (
        <div>

        </div>
    )
}
=======
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
      <Typography
        variant="h1"
        component="h1"
        sx={{
          clear: "right",
        }}
      >
        Home
      </Typography>
      <StickyFooter />
    </>
  );
};
>>>>>>> d51002cce0abf4c38b8d730f5ed561a5690e28e4

export default Home;
