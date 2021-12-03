import { Dialog, DialogContent, Grid } from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import { DialogContext } from "../../stores/DialogStore.js";
import { UserInfoContext } from "../../stores/UserInfoStore.js";
import PdfView from "../PdfView.js";
import SignContractForm from "./SignContractForm.js";

function SignContractDialog() {
  const [userInfo] = useContext(UserInfoContext);
  const [contractId, setContractId] = useState("");
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "signContractDialog",
      });
    }
  };

  const getPdfUrl = () => {
    return `http://localhost:8080/contract/byId/${dialog.signContractDialog.data.contractId}`;
  };

  useEffect(() => {
    if (!!dialog.signContractDialog.data) {
      setContractId(dialog.signContractDialog.data.contractId);
    }
    return () => {
      setContractId("");
    };
  }, [dialog.signContractDialog.visible]);

  return (
    <>
      {!!dialog.signContractDialog.data && (
        <Dialog
          open={dialog.signContractDialog.visible}
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
              {contractId !== "" && (
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
