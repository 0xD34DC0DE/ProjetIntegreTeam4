import { Dialog, DialogContent, Grid } from "@mui/material";
import React from "react";
import PdfView from "../PdfView.js";
import CreateContractMonitorForm from "./CreateContractMonitorForm";

function CreateContractMonitorDialog({ open, toggleDialog, pdfUrl, params }) {

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      toggleDialog("signContractMonitorDialog", false);
    }
  };

  return (
    <>
      <Dialog open={open} onClose={handleClose} fullWidth>
        <DialogContent>
          <Grid
            container
            direction="column"
            justifyContent="center"
            alignContent="center"
            rowSpacing={2}
          >
            <Grid item xs={12}>
              <PdfView pdfUrl={pdfUrl} params={params} />
            </Grid>

            <Grid item>
              <CreateContractMonitorForm
                studentEmail={params.studentEmail}
                offerId={params.internshipOfferId}
              />
            </Grid>
          </Grid>
        </DialogContent>
      </Dialog>
    </>
  );
}

export default CreateContractMonitorDialog;
