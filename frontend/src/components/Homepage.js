import { Grid, Button } from "@mui/material";
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
                    ":hover": { backgroundColor: "rgba(100, 100, 100, 0.2)" },
                    justifyContent: "flex-start",
                    ml: 2,
                    mr: 2,
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
