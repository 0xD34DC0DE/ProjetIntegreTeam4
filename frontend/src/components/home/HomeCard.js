import { Card, Grid, Typography } from "@mui/material";
import { motion } from "framer-motion";
import PropTypes from "prop-types";
import React, { useState } from "react";

const HomeCard = ({ keyProp, role, description, functionnalities, image }) => {
  const [showDescription, setShowDescription] = useState(false);
  const mouseEnter = {
    offscreen: {
      y: 300,
    },
    onscreen: {
      y: 10,
      transition: {
        type: "tween",
        duration: 0.5,
      },
    },
  };

  return (
    <Card
      onMouseOver={() => setShowDescription(true)}
      onMouseLeave={() => setShowDescription(false)}
      sx={{
        m: 2,
        height: "75vh",
        backgroundRepeat: "no-repeat",
        backgroundSize: "cover",
        backgroundImage: `url(${image})`,
      }}
    >
      <Grid
        container
        key={keyProp}
        sx={{
          color: "white",
          height: "100%",
          width: "100%",
          backgroundColor: "#1F2020",
          opacity: 0.8,
          textAlign: "center",
          justifyContent: "center",
          alignItems: "center",
          ":hover": {
            opacity: 0.9,
          },
        }}
      >
        <Grid item xs={12}>
          <Typography variant="h4">{role}</Typography>
          <Typography variant="h6">{description}</Typography>
        </Grid>
        {showDescription && (
          <motion.div
            variants={mouseEnter}
            initial="offscreen"
            animate="onscreen"
          >
            <Grid container>
              <Typography variant="h6" sx={{ m: 1 }}>
                Ce que vous pouvez faire:
              </Typography>
              {functionnalities &&
                functionnalities.map((job) => {
                  return (
                    <Grid item sx={{ p: 2, textAlign: "left" }} xs={12}>
                      <Typography>{job}</Typography>
                    </Grid>
                  );
                })}
            </Grid>
          </motion.div>
        )}
      </Grid>
    </Card>
  );
};

HomeCard.propTypes = {
  functionnalities: PropTypes.array,
};

export default HomeCard;
