import React, { useRef } from "react";
import { Box, Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView"

function ReportDialog({ open, toggleDialog, reportUrl, setReportUrl, sessionNumber }) {

    let dialogRef = useRef(null);

    const handleClose = (_, reason) => {
        if (reason === "backdropClick") {
            toggleDialog("reportDialog", false);
            setReportUrl('');
        }
    };

    if (reportUrl === "/generateAllNonValidatedOffersReport" ||
        reportUrl === "/generateAllValidatedOffersReport" ||
        reportUrl === "/generateStudentsNotEvaluatedReport" ||
        reportUrl === "/generateStudentsWithSupervisorWithNoCompanyEvaluation") {
        reportUrl = "http://localhost:8080/report" + reportUrl + "/" + sessionNumber;
    } else {
        reportUrl = "http://localhost:8080/report" + reportUrl;
    }

    return (
        <Dialog open={open} onClose={handleClose}>
            <DialogContent ref={dialogRef}>
                {dialogRef && <PdfView pdfUrl={reportUrl} dialogRef={dialogRef} />}
            </DialogContent>
        </Dialog>
    )
}

export default ReportDialog
