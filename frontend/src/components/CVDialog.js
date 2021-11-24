import React, { useContext, useRef } from "react";
import { Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView";
import { DialogContext } from "../stores/DialogStore";

function CVDialog({ cvUrl, setUrl }) {
  let dialogRef = useRef(null);
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "cvDialog",
      });
      setUrl("");
    }
  };

  return (
    <Dialog open={dialog.cvDialog.visible} onClose={handleClose}>
      <DialogContent ref={dialogRef}>
        {dialogRef && <PdfView pdfUrl={cvUrl} dialogRef={dialogRef} />}
      </DialogContent>
    </Dialog>
  );
}

export default CVDialog;
