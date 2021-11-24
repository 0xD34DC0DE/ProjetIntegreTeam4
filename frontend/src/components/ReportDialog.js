import React, { useContext, useRef } from "react";
import { Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView";
import { DialogContext } from "../stores/DialogStore";

function ReportDialog({ reportUrl, semesterFullName, setReportUrl }) {
  const dialogRef = useRef(null);
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "reportDialog",
      });
      setReportUrl("");
    }
  };

  const semesterDependentUrls = [
    "/generateAllNonValidatedOffersReport",
    "/generateAllValidatedOffersReport",
    "/generateStudentsNotEvaluatedReport",
    "/generateStudentsWithSupervisorWithNoCompanyEvaluation",
  ];

  if (semesterDependentUrls.includes(reportUrl)) {
    reportUrl =
      "http://localhost:8080/report" + reportUrl + "/" + semesterFullName;
  } else {
    reportUrl = "http://localhost:8080/report" + reportUrl;
  }

  return (
    <Dialog open={dialog.reportDialog.visible} onClose={handleClose}>
      <DialogContent ref={dialogRef}>
        {dialogRef && <PdfView pdfUrl={reportUrl} dialogRef={dialogRef} />}
      </DialogContent>
    </Dialog>
  );
}

export default ReportDialog;
