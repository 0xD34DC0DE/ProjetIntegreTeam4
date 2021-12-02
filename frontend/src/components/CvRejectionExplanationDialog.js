import {
  DialogContentText,
  DialogTitle,
  Dialog,
  DialogContent,
} from "@mui/material";
import React, { useContext } from "react";
import { DialogContext } from "../stores/DialogStore";

const CvRejectionExplanationDialog = ({ rejectionExplanation }) => {
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      dialogDispatch({
        type: "CLOSE",
        dialogName: "cvRejectionExplanationDialog",
      });
  };
  return (
    <Dialog
      onClose={handleClose}
      open={dialog.cvRejectionExplanationDialog.visible}
    >
      <DialogTitle>Raison du rejet du CV</DialogTitle>
      <DialogContent>
        <DialogContentText>{rejectionExplanation}</DialogContentText>
      </DialogContent>
    </Dialog>
  );
};

export default CvRejectionExplanationDialog;
