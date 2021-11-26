import { Grid, Button, Card } from "@mui/material";
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
                    backgroundColor: "rgba(135, 135, 135, 0.03)",
                    width: "95%",
                    height: "25%",
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
              </Grid>
              );
            })}
      </Grid>
    </Grid>
  );
};

export default Homepage;
