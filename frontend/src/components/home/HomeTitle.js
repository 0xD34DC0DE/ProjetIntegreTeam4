import { Container, Paper, Typography } from "@mui/material";
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
        <Typography sx={{ pt: 2 }} variant="h1">
          OSER
        </Typography>
        <Typography sx={{ p: 1 }} variant="h3">
          Offre-Stage-Emplois-Rénové
        </Typography>
        <HomeButtons />
      </Paper>
    </Container>
  );
};

export default HomeTitle;
