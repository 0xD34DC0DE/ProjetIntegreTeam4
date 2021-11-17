import React from "react";
import { Avatar, Box, Card, Typography, ButtonBase } from "@mui/material";
import AssignmentIcon from '@mui/icons-material/Assignment';

const Report = ({ title, url, setReportUrl }) => {
    return (
        <ButtonBase
            onClick={event => {
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
                    p: 2,
                    mx: 2,
                    width: "100%",
                }}
            >
                <Box sx={{ textAlign: "center" }}>
                    <Avatar sx={{ mx: "auto", my: 2 }}>
                        <AssignmentIcon />
                    </Avatar>
                    <Typography>
                        {title}
                    </Typography>
                </Box>
            </Card>
        </ButtonBase>
    );
}

export default Report;
