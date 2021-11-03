import React from "react";
import { Box, Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView"

function ReportDialog({ open, toggleDialog, reportUrl, setReportUrl }) {

    const handleClose = (_, reason) => {
        if (reason === "backdropClick") {
            toggleDialog("reportDialog", false);
            setReportUrl('');
        }
    };

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogContent>
                <Box sx={{ height: 20, width: 25, bgcolor: "blue" }}>{reportUrl}</Box>
                {/* <PdfView pdfUrl={"http://localhost:8080/report/" + reportUrl}></PdfView> */}
            </DialogContent>
        </Dialog>
    )
}

export default ReportDialog

