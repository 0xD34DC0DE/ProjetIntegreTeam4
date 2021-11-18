import React, { useRef } from "react";
import { Dialog, DialogContent } from "@mui/material";
import PdfView from "./PdfView";

function ReportDialog({
  open,
  toggleDialog,
  reportUrl,
  semesterFullName,
  setReportUrl,
}) {
  let dialogRef = useRef(null);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      toggleDialog("reportDialog", false);
      setReportUrl("");
    }
  };

  const semesterDependentUrl = [
    "/generateAllNonValidatedOffersReport",
    "/generateAllValidatedOffersReport",
    "/generateStudentsNotEvaluatedReport",
    "/generateStudentsWithSupervisorWithNoCompanyEvaluation"
  ];

  if (semesterDependentUrl.includes(reportUrl)) {
    reportUrl =
      "http://localhost:8080/report" + reportUrl + "/" + semesterFullName;
  } else {
    reportUrl = "http://localhost:8080/report" + reportUrl;
  }

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogContent ref={dialogRef}>
        {dialogRef && <PdfView pdfUrl={reportUrl} dialogRef={dialogRef} />}
      </DialogContent>
    </Dialog>
  );
}

export default ReportDialog;
