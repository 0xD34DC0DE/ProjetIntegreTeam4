import React from "react";
import { Typography, Grid, Divider } from "@mui/material";
import { motion } from "framer-motion";

const ContentTitle = ({ role, description }) => {
  return (
    <Grid
      container
      flexDirection={"column"}
      sx={{ backgroundColor: "rgba(14, 14, 16, 0.2)" }}
    >
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} ml={2}>
        <motion.div
          animate={{ opacity: [0, 1] }}
          transition={{
            duration: 0.5,
          }}
          style={{ opacity: 0 }}
        >
          <Typography
            variant="subtitle2"
            color="text.primary"
            fontSize="3em"
            sx={{
              pt: 2,
              lineHeight: "55px",
              whiteSpace: "wrap",
              overflow: "hidden",
              textOverflow: "ellipsis",
            }}
          >
            {role}
          </Typography>
          <Typography
            variant="subtitle2"
            color="text.primary"
            sx={{
              p: 0,
              m: 0,
              ml: 0.5,
              mb: 1,
              fontSize: "0.8em",
              color: "rgba(255, 255, 255, 0.5)",
            }}
          >
            {description}
          </Typography>
        </motion.div>
      </Grid>
      <Divider
        sx={{ mt: 1, backgroundColor: "rgba(255, 255, 255, 0.2)" }}
      ></Divider>
    </Grid>
  );
};

export default ContentTitle;
