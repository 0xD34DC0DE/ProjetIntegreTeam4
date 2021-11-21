import React from "react";
import {
  Avatar,
  Box,
  Card,
  Typography,
  ButtonBase,
  Container,
} from "@mui/material";
import AssignmentIcon from "@mui/icons-material/Assignment";

const Report = ({ title, url, setReportUrl }) => {
  return (
    <Container>
      <ButtonBase
        onClick={() => {
          setReportUrl(url);
        }}
        sx={{ width: "100%" }}
      >
        <Card
          role="Handle"
          sx={{
            backgroundColor: "#1F2020",
            alignItem: "center",
            justifyContent: "center",
            width: "100%",
            height: "80px",
            pb: 3,
            pt: 2,
          }}
        >
          <Avatar sx={{ mx: "auto" }}>
            <AssignmentIcon />
          </Avatar>
          <Typography sx={{ mt: 2 }}>{title}</Typography>
        </Card>
      </ButtonBase>
    </Container>
  );
};

export default Report;
