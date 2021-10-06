import React, { useState, useEffect, useContext } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";
import ApprovalIcon from "@mui/icons-material/Approval";
import axios from "axios";

const CvValidationDialog = ({ id, removeCv }) => {
  const [open, setOpen] = useState(false);

  const validateCv = (valid) => {
    {
      setOpen(true);
      axios({
        method: "PATCH",
        url: "http://localhost:8080/fileMetaData/validateCv",
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        params: {
          "id": id,
          "isValid": valid,
        },
        responseType: "json",
      })
        .then(() => {
          removeCv(id);
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
      <Button
        size="medium"
        variant="contained"
        color="success"
        sx={{ mb: "6px" }}
        onClick={validateCv}
      >
        VALIDER <ApprovalIcon></ApprovalIcon>
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
