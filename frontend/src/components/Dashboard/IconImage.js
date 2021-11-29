import React from "react";
import { Avatar, Typography, Grid } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import BlockIcon from "@mui/icons-material/Block";

const IconImage = ({ profile, role }) => {
  return (
    <>
      <Grid item justifyContent="center">
        <Avatar
          sx={{
            width: 200,
            height: 200,
            border: "1px solid white",
            boxShadow: 6,
          }}
        >
          {profile.firstName.charAt(0)}
        </Avatar>
        {role === "STUDENT" && (
          <div>
            {profile.hasValidCv ? (
              <Typography
                sx={{ color: "green", textAlign: "center", m: 1 }}
                variant="subtitle1"
                component="div"
                gutterBottom
              >
                Vous avez un CV est valide
                <CheckCircleIcon />
              </Typography>
            ) : (
              <Typography
                sx={{ color: "red", textAlign: "center", m: 1 }}
                variant="subtitle1"
                component="div"
                gutterBottom
              >
                Vous n'avez aucun CV valide
                <BlockIcon />
              </Typography>
            )}
          </div>
        )}
      </Grid>
    </>
  );
};

export default IconImage;
