import React from "react";
import { Grid, Button } from "@mui/material";
import { motion } from "framer-motion";
const HomeButtons = () => {
  return (
    <Grid container>
      {["Se Connecter", "S'enregistrer"].map((text, key) => {
        return (
          <Grid item xs={12} sm={6} sx={{ p: 1 }}>
            <motion.div
              animate={{ opacity: [0, 1] }}
              transition={{
                duration: 1,
                delay: (key + 1) * 0.5,
              }}
            >
              <Button
                variant="contained"
                sx={{
                  width: "100%",
                  boxShadow: 6,
                  backgroundColor: "rgba(125, 51, 235, 0.8)",
                }}
              >
                {text}
              </Button>
            </motion.div>
          </Grid>
        );
      })}
    </Grid>
  );
};

export default HomeButtons;
