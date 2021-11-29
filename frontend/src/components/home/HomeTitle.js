import { Container, Grid, Paper, Typography } from "@mui/material";
import React from "react";
import HomeButtons from "./HomeButtons";
const HomeTitle = () => {
  return (
    <Container maxWidth={false} disableGutters={true}>
      <Paper
        sx={{
          backgroundColor: "#1F2020",
          textAlign: "center",
        }}
      >
        <Typography sx={{ p: 2 }} variant="h1">
          Bienvenu sur le site
        </Typography>
        <HomeButtons />
      </Paper>
    </Container>
  );
};

export default HomeTitle;
