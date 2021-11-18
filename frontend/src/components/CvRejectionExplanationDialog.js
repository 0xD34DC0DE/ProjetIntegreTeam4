import {
  DialogContentText,
  DialogTitle,
  Dialog,
  DialogContent,
} from "@mui/material";
import React from "react";

const CvRejectionExplanationDialog = ({
  rejectionExplanation,
  open,
  toggleDialog,
}) => {
  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      toggleDialog("cvRejectionExplanationDialog", false);
  };
  return (
    <Dialog onClose={handleClose} open={open}>
      <DialogTitle>Raison du rejet du C.V.</DialogTitle>
      <DialogContent>
        <DialogContentText>{rejectionExplanation}</DialogContentText>
      </DialogContent>
    </Dialog>
  );
};

export default CvRejectionExplanationDialog;
