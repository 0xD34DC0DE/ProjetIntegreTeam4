import React, { useContext, useState } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Box,
  TextareaAutosize,
  Typography,
} from "@mui/material";
import ApprovalIcon from "@mui/icons-material/Approval";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";

const CvValidationDialog = ({ id, removeCv }) => {
  const [open, setOpen] = useState(false);
  const [isRejecting, setIsRejecting] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");
  const [userInfo] = useContext(UserInfoContext);

  const [rejectionExplanation, setRejectionExplanation] = useState("");

  const handleOpen = () => {
    setOpen(true);
  };

  const validateCv = (valid, rejectionExplanation) => {
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
        data: { rejectionExplanation },
      })
        .then(() => {
          setRejectionExplanation(null);
          setIsRejecting(false);
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
    setErrorMessage("");
    setRejectionExplanation("");
    setIsRejecting(false);
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
            Voulez-vous accepter ou rejeter le C.V. de cet(te) étudiant(e)?
          </DialogContentText>
          {isRejecting && (
            <Dialog open={isRejecting} onClose={handleClose}>
              <DialogContent>
                <DialogTitle sx={{ pt: 0 }}>
                  Raison du rejet du C.V.
                </DialogTitle>
                {(rejectionExplanation == "" ||
                  rejectionExplanation == null) && (
                  <Typography sx={{ color: "red" }}>{errorMessage}</Typography>
                )}
                <TextareaAutosize
                  placeholder="Expliquez à l'élève la raison du rejet de son C.V."
                  onChange={(e) => {
                    setRejectionExplanation(e.target.value);
                    setErrorMessage("");
                  }}
                  minRows={5}
                  style={{
                    font: "14px arial, sans-serif",
                    borderColor:
                      rejectionExplanation != "" || rejectionExplanation != null
                        ? "black"
                        : "red",
                    resize: "none",
                    display: "block",
                    width: "100%",
                    height: "100%",
                  }}
                />
              </DialogContent>
              <Button
                onClick={() => {
                  if (
                    rejectionExplanation != null &&
                    rejectionExplanation != ""
                  ) {
                    validateCv(false, rejectionExplanation);
                  } else {
                    setErrorMessage(
                      "La raison du rejet du C.V. doit être inscrite"
                    );
                  }
                }}
              >
                Rejeter le C.V.
              </Button>
            </Dialog>
          )}
        </DialogContent>
        <DialogActions>
          <Button onClick={() => validateCv(true)}>Accepter</Button>
          <Button onClick={() => setIsRejecting(true)}>Rejeter</Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default CvValidationDialog;
