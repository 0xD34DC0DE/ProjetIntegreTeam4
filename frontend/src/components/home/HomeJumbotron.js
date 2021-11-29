import { Container, Divider, Paper, Typography } from "@mui/material";
import { motion } from "framer-motion";
import React, { useState } from "react";
import "./HomeJumbotron.css";

const HomeJumbotron = ({
  title,
  description,
  backgroundColor,
  color,
  steps,
  imgUrl,
  icon,
}) => {
  const [scroll, setScroll] = useState(false);
  const scrolled = {
    offscreen: {
      y: 300,
    },
    onscreen: {
      y: "30%",
      transition: {
        type: "tween",
        duration: 0.4,
      },
    },
  };
  return (
    <Container
      maxWidth={false}
      disableGutters={true}
      onMouseEnter={() => setScroll(true)}
    >
      <Paper
        sx={{
          backgroundColor: backgroundColor,
          color: color,
          display: "flex",
          minHeight: "40vh",
        }}
      >
        <div className="jumbotronText">
          <Typography variant="h3" sx={{ px: 5, py: 5 }}>
            {title}
          </Typography>
          <Typography variant="body1" sx={{ p: 5 }}>
            {description}
          </Typography>
          <div className="steps">
            {steps &&
              steps.map((step, key) => {
                return <li key={key}>{step}</li>;
              })}
          </div>
        </div>
        <div className="jumbotronImage">
          {scroll && (
            <motion.div
              variants={scrolled}
              initial="offscreen"
              animate="onscreen"
              style={{ display: "flex", justifyContent: "center" }}
            >
              {icon}
            </motion.div>
          )}
        </div>
        <Divider />
      </Paper>
    </Container>
  );
};

export default HomeJumbotron;
