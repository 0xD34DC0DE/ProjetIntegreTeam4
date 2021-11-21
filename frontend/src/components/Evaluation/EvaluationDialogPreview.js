import { Dialog, DialogContent, Grid } from "@mui/material";
import React, { useContext } from "react";
import { DialogContext } from "../../stores/DialogStore.js";
import PdfView from "../PdfView.js";

function EvaluationDialogPreview({ evaluationId, setEvaluationId }) {
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const handleClose = (_, reason) => {
    dialogDispatch({
      type: "CLOSE",
      dialogName: "evaluationDialogPreview",
    });
    setEvaluationId("");
  };

  const getPdfUrl = () => {
    return `http://localhost:8080/evaluation/` + evaluationId;
  };

  return (
    <>
      {evaluationId && (
        <Dialog
          open={dialog.evaluationDialogPreview.visible}
          onClose={handleClose}
          fullWidth
        >
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
