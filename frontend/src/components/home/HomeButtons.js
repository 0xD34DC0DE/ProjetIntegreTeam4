import React from "react";
import { Grid, Button } from "@mui/material";

const HomeButtons = () => {
  return (
    <Grid container sx={{}}>
      <Grid item xs={12} sm={6} sx={{ p: 1 }}>
        <Button variant="contained" sx={{ width: "100%", boxShadow: 6 }}>
          Se Connecter
        </Button>
      </Grid>
      <Grid item xs={12} sm={6} sx={{ p: 1 }}>
        <Button variant="contained" sx={{ width: "100%", boxShadow: 6 }}>
          S'enregistrer
        </Button>
      </Grid>
    </Grid>
  );
};

export default HomeButtons;
