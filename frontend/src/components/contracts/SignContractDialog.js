import { Dialog, DialogContent, Grid, Typography } from "@mui/material";
import React, { useEffect, useContext, useState } from "react";
import { UserInfoContext } from "../../stores/UserInfoStore.js";
import PdfView from "../PdfView.js";
import axios from "axios";
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
    console.log("AAA");
    //return `http://localhost:8080/contract/${dialogData.contractId}`;
    return `http://localhost:8080/contract/byId/${contractId}`;
  };

  useEffect(() => {
    if (open) {
      axios({
        method: "GET",
        baseURL: "http://localhost:8080",
        url: "/contract/tmp",
        headers: {
          Authorization: userInfo.jwt,
        },
      })
        .then((response) => {
          setContractId(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [open]);

  return (
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
                <SignContractForm contractId={contractId}/>
              </Grid>
            </>
          )}
        </Grid>
      </DialogContent>
    </Dialog>
  );
}

export default SignContractDialog;
