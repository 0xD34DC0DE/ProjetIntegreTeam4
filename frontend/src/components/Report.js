import React from "react";
import {
  Avatar,
  Box,
  Tooltip,
  Card,
  Typography,
  ButtonBase,
  Container,
} from "@mui/material";
import AssignmentIcon from "@mui/icons-material/Assignment";
import { keyframes } from "@mui/styled-engine";

const transition = keyframes`
  from {
    opacity: 0;
  }
  to {
    opacity: 1;
  }
`;

const Report = ({ title, url, setReportUrl }) => {
  return (
    <Container>
      <ButtonBase
        onClick={() => {
          setReportUrl(url);
        }}
        sx={{ width: "430px", height: "100%", mb: 5 }}
      >
        <Card
          role="Handle"
          sx={{
            backgroundColor: "rgba(135, 135, 135, 0.03)",
            width: "100%",
            height: "100%",
            border: "0.5px solid grey",
            boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
            borderRadius: "10px",
            py: 5,
            ":hover": {
              boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
              "#visualizeText": {
                opacity: 0.8,
              },
            },
          }}
        >
          <Avatar sx={{ mx: "auto", mb: 5, px: 2, py: 2 }}>
            <AssignmentIcon />
          </Avatar>
          <Typography variant="subtitle2" sx={{ fontSize: "1.2em" }}>
            {title}
          </Typography>
          <Typography
            variant="subtitle2"
            id="visualizeText"
            sx={{
              mt: 2,
              color: "rgba(255, 255, 255, 0.6)",
              opacity: 0,
              fontSize: "0.9em",
              lineHeight: "0",
              height: 0,
              mt: 2,
              transition: "opacity 0.5s ease-in-out",
            }}
          >
            APPUYEZ POUR VISUALISER
          </Typography>{" "}
        </Card>
      </ButtonBase>
    </Container>
  );
};

export default Report;
