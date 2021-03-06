import React from "react";
import { Grid, Typography, Button } from "@mui/material";
import { motion } from "framer-motion";
import UploadFileOutlinedIcon from "@mui/icons-material/UploadFileOutlined";

const SubmitEvaluationButton = ({ delay, onClick }) => {
  return (
    <Grid item xs={12} mt={5} textAlign="center">
      <motion.div
        animate={{ opacity: [0, 1] }}
        transition={{
          duration: 0.2,
          delay: delay,
        }}
      >
        <Button
          sx={{
            color: "white",
            backgroundColor: "rgba(70, 70, 70, 0.8)",
            boxShadow: "0px 0px 5px 3px rgba(0, 0, 0, 0.5)",
            ":hover": { backgroundColor: "rgba(50, 50, 50, 0.8)" },
            px: 15,
          }}
          onClick={onClick}
        >
          <Typography variant="subtitle2" sx={{ fontSize: "1.75em", mr: 2 }}>
            Envoyer
          </Typography>
          <UploadFileOutlinedIcon />
        </Button>
      </motion.div>
    </Grid>
  );
};

export default SubmitEvaluationButton;
