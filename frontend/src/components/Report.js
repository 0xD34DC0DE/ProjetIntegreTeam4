import React from "react";
import { Avatar, Box, Card, Typography } from "@mui/material";
const Report = ({ title }) => {
    return (
        <Card
            role="Handle"
            sx={{
                backgroundColor: "#1F2020",
                alignItem: "center",
                justifyContent: "center",
                p: 2,
                mx: 2,
            }}
        >
            <Box sx={{ textAlign: "center" }}>
                <Avatar sx={{ mx: "auto", my: 2 }}></Avatar>
                <Typography>
                    {title}
                </Typography>
            </Box>
        </Card>
    );
}

export default Report;
