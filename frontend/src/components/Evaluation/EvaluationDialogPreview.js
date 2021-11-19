import { Dialog, DialogContent, Grid } from "@mui/material";
import React from "react";
import PdfView from "../PdfView.js";

function EvaluationDialogPreview({
  open,
  toggleDialog,
  dialogData,
  evaluationId,
  setEvaluationId,
}) {
  const handleClose = (_, reason) => {
    toggleDialog("evaluationDialogPreview", false);
    setEvaluationId("");
  };

  const getPdfUrl = () => {
    return `http://localhost:8080/evaluation/` + evaluationId;
  };

  return (
    <>
      {evaluationId && (
        <Dialog open={open} onClose={handleClose} fullWidth>
          <DialogContent>
            <Grid
              container
              direction="column"
              justifyContent="center"
              alignContent="center"
              rowSpacing={2}
            >
              <>
                <Grid item xs={12}>
                  <PdfView pdfUrl={getPdfUrl()} params={{}} />
                </Grid>
              </>
            </Grid>
          </DialogContent>
        </Dialog>
      )}
    </>
  );
}

export default EvaluationDialogPreview;
