import { Grid, Typography, Box, Container, Button } from "@mui/material";
import React, { useState, useEffect, useContext } from "react";
import { DialogContext } from "../stores/DialogStore";
import Report from "./Report";
import ReportDialog from "./ReportDialog";
import SemesterSelect from "./SemesterSelect";
import { UserInfoContext } from "../stores/UserInfoStore";
import { sidebarList } from "./Configuration";


const Homepage = () => {

  const [userInfo] = useContext(UserInfoContext);

  return (
    <Grid container flexDirection="column">
    <Grid sx={{mt:4}} container justifyContent="center">
    {sidebarList
            .filter((item) => item.role.includes(userInfo.role))
            .map((item, key) => {
              return (  
                <Grid
                item
                key={item.id}
                lg={6}
                xl={4}
                md={12}
                sm={12}
                xs={12}
                textAlign="center"
              >
                <Button
                  key={item.id}
                  sx={{
                    color: "text.primary",
                    ":hover": { backgroundColor: "rgba(100, 100, 100, 0.2)" },
                    justifyContent: "flex-start",
                    ml: 2,
                    mr: 2,
                  }}
                  onClick={() => {
                    console.log(item.id)
                  }}
                >
                  {item.icon}
                  {item.label}
                </Button>
              </Grid>
              );
            })}
      </Grid>
      {/* <Grid sx={{mt:4}} container justifyContent="center">
      {sidebarList
            .filter((item) => item.role.includes(userInfo.role))
            .map((item, key) => {
              return (
                <Button
                  key={item.id}
                  sx={{
                    color: "text.primary",
                    ":hover": { backgroundColor: "rgba(100, 100, 100, 0.2)" },
                    justifyContent: "flex-start",
                    ml: 2,
                    mr: 2,
                  }}
                  onClick={() => {
                    console.log(item.id)
                  }}
                >
                  {item.icon}
                  {item.label}
                </Button>
              );
            })}
      </Grid> */}
    </Grid>
  );
};

export default Homepage;
