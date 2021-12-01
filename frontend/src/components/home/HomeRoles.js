import { Grid, Button, Typography } from "@mui/material";
import React, { useContext } from "react";
import { UserInfoContext } from "../../stores/UserInfoStore";
import { sidebarList } from "../Configuration";
import { DialogContext } from "../../stores/DialogStore";
import { SelectionContext } from "../../stores/SelectionStore";
import { Box } from "@mui/system";

const HomeRoles = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [selection, selectionDispatch] = useContext(SelectionContext);

  return (
    <Grid container flexDirection="column">
      <Grid sx={{ mt: 10 }} container justifyContent="center">
        {sidebarList
          .filter((item) => item.role.includes(userInfo.role) && item.id !== 0)
          .map((item, key) => {
            return (
              <Grid item key={item.id} lg={8} xl={8} md={8} sm={10} xs={10}>
                <Button
                  key={item.id}
                  sx={{
                    color: "text.primary",
                    fontSize: "1.5em",
                    backgroundColor:
                      key % 2 == 0
                        ? "rgba(125, 51, 235, 0.8)"
                        : "rgba(50, 50, 50, 0.5)",
                    width: "100%",
                    height: "25%",

                    boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
                    borderRadius: "10px",
                    py: 5,
                    mb: 1,
                    ":hover": {
                      backgroundColor:
                        key % 2 == 0
                          ? "rgba(125, 51, 235, 0.8)"
                          : "rgba(50, 50, 50, 0.5)",
                      boxShadow:
                        key % 2 == 0
                          ? "0px 0px 15px 2px rgba(95, 21, 205, 0.9)"
                          : "0px 0px 15px 2px rgba(125, 51, 235, 0.8)",
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
                  <Typography
                    variant="h6"
                    sx={{
                      fontSize: "1.3em",
                      textShadow: "2px 2px rgba(0, 0, 0, 0.5)",
                    }}
                  >
                    {item.label}
                    <Box sx={{ display: "inline-block", ml: 2 }}>
                      {item.icon}
                    </Box>
                  </Typography>
                </Button>
                {item.explication.map((explication, key) => {
                  return (
                    <Typography
                      key={key}
                      sx={{
                        color: "white",
                        width: "100%",
                      }}
                      variant="subtitle2"
                    >
                      {explication}
                    </Typography>
                  );
                })}
              </Grid>
            );
          })}
      </Grid>
    </Grid>
  );
};

export default HomeRoles;
