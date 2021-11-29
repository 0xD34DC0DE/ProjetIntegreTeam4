import { Grid, Button, Typography, } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { sidebarList } from "./Configuration";
import { DialogContext } from "../stores/DialogStore";
import { SelectionContext } from "../stores/SelectionStore";


const Homepage = () => {

  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [selection, selectionDispatch] = useContext(SelectionContext);

  return ( 
    <Grid container flexDirection="column">
    <Grid sx={{mt:4}} container justifyContent="center">
    {sidebarList
            .filter((item) => item.role.includes(userInfo.role) && item.id !== 0)
            .map((item, key) => {
              return (  
                <Grid
                  item
                  key={item.id}
                  lg={12}
                  xl={12}
                  md={12}
                  sm={12}
                  xs={12}
                  marginX="25%"
                >
                <Button
                  key={item.id}
                  sx={{
                      color: "text.primary",
                      fontSize: "1.5em",
                      // backgroundColor: "rgba(135, 135, 135, 0.03)",
                      backgroundColor: "#702963",
                      width: "100%",
                      height: "25%",
                      border: "0.5px solid grey",
                      boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
                      borderRadius: "10px",
                      py: 5,
                      mb: 1,
                      ":hover": {
                          boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
                          backgroundColor: "#702963",
                          "#visualizeText": {
                              opacity: 0.8,
                          },
                      },
                  }}
                  onClick={() => {
                      if (item.isDialog)
                      dialogDispatch({
                          type: "OPEN",
                          dialogName: item.dialogName,
                      });
                      else selectionDispatch(item);
                  }}
                >
                {item.icon}
                {item.label}
                </Button>
                <Typography
                    variant="subtitle"
                    sx={{ 
                        fontSize: "1em",
                        color: "#CDCDCD",
                    }}
                >
                    {item.explication}
                </Typography>
              </Grid>
              );
            })}
      </Grid>
    </Grid>
  );
};

export default Homepage;
