import React, { useState, useEffect, useContext } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import axios from "axios";

const CvValidationDialog = ({ id }) => {
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const validateCv = (valid) => {
    console.log(valid);
    {
      axios({
        method: "PATCH",
        url: "http://localhost:8080/fileMetaData/validateCv",
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        params: {
          id: id,
          isValid: valid,
        },
        responseType: "json",
      })
        .then((response) => {
          console.log(response);
          handleClose();
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  const handleClose = () => {
    setOpen(false);
  };

  return (
    <>
      <Button variant="outlined" onClick={handleClickOpen}>
        Open alert dialog
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {"Validation Du CV Étudiant(e)"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Chère gestionnaire de stage, est-ce que le cv de cette étudiant(e)
            est valide?
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={() => validateCv(true)}>Oui</Button>
          <Button onClick={() => validateCv(false)}>Non</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default CvValidationDialog;
