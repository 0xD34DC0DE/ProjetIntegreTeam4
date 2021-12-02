import { Avatar, Box, Card, Typography } from "@mui/material";
import React from "react";
import { useDrag } from "react-dnd";
export default function UserCard({ user }) {
  const [{ isDragging }, drag, key] = useDrag(
    () => ({
      type: "UserCard",
      item: { user },
      collect: (monitor) => ({
        opacity: monitor.isDragging() ? 0.5 : 1,
      }),
    }),
    []
  );

  return (
    <Card
      key={key}
      role="Handle"
      ref={drag}
      sx={{
        backgroundColor: "rgba(135, 135, 135, 0.03)",
        border: "0.5px solid rgba(255, 255, 255, 0.2)",
        boxShadow: "3px 3px 15px 5px rgba(0, 0, 0, 0.5)",
        alignItem: "center",
        borderRadius: "5px",
        justifyContent: "center",
        p: 2,
        mx: 2,
        ":hover": {
          border: "0.5px solid rgba(255, 255, 255, 0)",
          boxShadow: "0px 0px 15px 1px rgba(125, 51, 235, 0.8) !important",
        },
      }}
    >
      <Box sx={{ textAlign: "center" }}>
        <Avatar
          sx={{ mx: "auto", my: 2, width: "75px", height: "75px" }}
        ></Avatar>
        <Typography>{user.email}</Typography>
        <Typography>
          {user.firstName}, {user.lastName}
        </Typography>
      </Box>
    </Card>
  );
}
