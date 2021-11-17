import React, { useRef } from "react";
import { Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView"

function CVDialog({ open, toggleDialog, cvUrl, setUrl}) {

    let dialogRef = useRef(null);

    const handleClose = (_, reason) => {
        if (reason === "backdropClick") {
            toggleDialog("cvDialog", false);
            setUrl('');
        }
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogContent ref={dialogRef}>
                {dialogRef && <PdfView pdfUrl={cvUrl} dialogRef={dialogRef} />}
                {/* {dialogRef && <iframe src={cvUrl}></iframe>} */}
            </DialogContent>
        </Dialog>
    )
}

export default CVDialog
