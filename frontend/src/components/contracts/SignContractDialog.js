import { Dialog, DialogContent, Grid } from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../../stores/UserInfoStore.js";
import PdfView from "../PdfView.js";
import SignContractForm from "./SignContractForm.js";

function SignContractDialog({ open, toggleDialog, dialogData }) {
  const [userInfo] = useContext(UserInfoContext);
  const [contractId, setContractId] = useState("");

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      toggleDialog("signContractDialog", false);
    }
  };

  const getPdfUrl = () => {
    return `http://localhost:8080/contract/byId/${dialogData.contractId}`;
  };

  useEffect(() => {
    if(!!dialogData) {
      setContractId(dialogData.contractId);
    }
    return () => {
      setContractId("");
    };
  }, [open]);

  return (
    <>
      {!!dialogData && (
        <Dialog open={open} onClose={handleClose} fullWidth>
          <DialogContent>
            <Grid
              container
              direction="column"
              justifyContent="center"
              alignContent="center"
              rowSpacing={2}
            >
              {contractId != "" && (
                <>
                  <Grid item xs={12}>
                    <PdfView pdfUrl={getPdfUrl()} params={{}} />
                  </Grid>

                  <Grid item>
                    <SignContractForm contractId={contractId} />
                  </Grid>
                </>
              )}
            </Grid>
          </DialogContent>
        </Dialog>
      )}
    </>
  );
}

export default SignContractDialog;
