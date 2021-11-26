import { Avatar, Box, Card, Typography } from "@mui/material";
import React from "react";
import { useDrag } from "react-dnd";
export default function UserCard({ user }) {
  const [{ isDragging }, drag] = useDrag(
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
      role="Handle"
      ref={drag}
      sx={{
        backgroundColor: "rgba(135, 135, 135, 0.05)",
        alignItem: "center",
        border: "0.5px solid grey",
        borderRadius: "10px",
        justifyContent: "center",
        p: 2,
        mx: 2,
        ":hover": {
          boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
        }
      }}
    >
      <Box sx={{ textAlign: "center" }}>
        <Avatar sx={{ mx: "auto", my: 2 }}></Avatar>
        <Typography>{user.email}</Typography>
        <Typography>
          {user.firstName}, {user.lastName}
        </Typography>
      </Box>
    </Card>
  );
}
