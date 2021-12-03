import React, { useContext } from "react";
import { Grid, Button } from "@mui/material";
import { motion } from "framer-motion";
import { DialogContext } from "../../stores/DialogStore";

const HomeButtons = () => {
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const buttonsInfo = [
    { dialog: "loginDialog", text: "Se connecter" },
    { dialog: "registerDialog", text: "S'enregistrer" },
  ];

  const openDialog = (dialog) => {
    dialogDispatch({
      type: "OPEN",
      dialogName: dialog,
    });
  };
  return (
    <Grid container>
      {buttonsInfo.map((buttonInfo, key) => {
        return (
          <Grid item xs={12} sm={6} sx={{ p: 1 }} key={key}>
            <motion.div
              animate={{ opacity: [0, 1] }}
              transition={{
                duration: 1,
                delay: (key + 1) * 0.5,
              }}
            >
              <Button
                onClick={() => openDialog(buttonInfo.dialog)}
                variant="contained"
                sx={{
                  width: "100%",
                  boxShadow: 6,
                  backgroundColor: "rgba(125, 51, 235, 0.8)",
                }}
              >
                {buttonInfo.text}
              </Button>
            </motion.div>
          </Grid>
        );
      })}
    </Grid>
  );
};

export default HomeButtons;
