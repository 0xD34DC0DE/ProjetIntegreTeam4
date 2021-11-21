import { Dialog, DialogContent, Grid } from "@mui/material";
import React, { useContext } from "react";
import PdfView from "../PdfView.js";
import CreateContractMonitorForm from "./CreateContractMonitorForm";
import { DialogContext } from "../../stores/DialogStore";

function CreateContractMonitorDialog({ pdfUrl, params }) {
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "signContractMonitorDialog",
      });
    }
  };

  return (
    <>
      <Dialog
        open={dialog.signContractMonitorDialog.visible}
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
