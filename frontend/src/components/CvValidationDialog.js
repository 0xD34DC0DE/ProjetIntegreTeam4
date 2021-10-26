import React, { useContext, useState } from "react";
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
import { UserInfoContext } from "../stores/UserInfoStore";

const CvValidationDialog = ({ id, removeCv }) => {
  const [open, setOpen] = useState(false);
  const [userInfo] = useContext(UserInfoContext);

  const handleOpen = () => {
    setOpen(true);
  };

  const validateCv = (valid) => {
    {
      axios({
        method: "PATCH",
        url: "http://localhost:8080/file/validateCv",
        headers: {
          Authorization: userInfo.jwt,
        },
        params: {
          id: id,
          isValid: valid,
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
        onClick={handleOpen}
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
          {"Validation du C.V. étudiant(e)"}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Chère gestionnaire de stage, est-ce que le C.V. de cet(te)
            étudiant(e) est valide?
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
