import React, { useContext, useState } from "react";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
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
    axios({
      method: "PATCH",
      url: "http://localhost:8080/file/validateCv",
      headers: {
        Authorization: userInfo.jwt,
        "Content-Type": "text/plain",
      },
      params: {
        id: id,
        isValid: valid,
      },
      data: rejectionExplanation,
      responseType: "json",
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
        sx={{
          mt: "6px",
          backgroundColor: "rgba(125, 51, 235, 0.8)",
          width: "100%",
          ":hover": {
            backgroundColor: "rgba(85, 11, 185, 0.8)",
          },
        }}
        onClick={handleOpen}
      >
        VALIDER <ApprovalIcon sx={{ ml: 1 }} />
      </Button>
      <Dialog
        open={open}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          Validation du CV de l'étudiant(e)
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            Voulez-vous accepter ou rejeter le CV de cet(te) étudiant(e)?
          </DialogContentText>
          {isRejecting && (
            <Dialog open={isRejecting} onClose={handleClose}>
              <DialogContent>
                <DialogTitle sx={{ pt: 0 }}>Raison du rejet du CV</DialogTitle>
                {(rejectionExplanation === "" ||
                  rejectionExplanation === null) && (
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
                      rejectionExplanation !== "" ||
                      rejectionExplanation !== null
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
                    rejectionExplanation !== null &&
                    rejectionExplanation !== ""
                  ) {
                    validateCv(false, rejectionExplanation);
                  } else {
                    setErrorMessage(
                      "La raison du rejet du CV doit être inscrite"
                    );
                  }
                }}
              >
                Rejeter le CV
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
