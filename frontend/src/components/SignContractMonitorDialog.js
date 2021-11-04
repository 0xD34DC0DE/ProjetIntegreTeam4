import {
  Dialog,
  DialogContent,
  Button,
  Grid,
  Checkbox,
  Typography,
  TextField,
  Box,
  FormControlLabel,
} from "@mui/material";
import React, { useState, useContext, useEffect, useRef } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import PdfView from "./PdfView.js";

import SignContractMonitorForm from "./SignContractMonitorForm";

function SignContractMonitorDialog({ open, toggleDialog, pdfUrl, params }) {
  const [userInfo] = useContext(UserInfoContext);


  let dialogRef = useRef(null);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      toggleDialog("signContractMonitorDialog", false);
      //resetForm();
    }
  };


  function useTraceUpdate(props) {
    const prev = useRef(props);
    useEffect(() => {
      const changedProps = Object.entries(props).reduce((ps, [k, v]) => {
        if (prev.current[k] !== v) {
          ps[k] = [prev.current[k], v];
        }
        return ps;
      }, {});
      if (Object.keys(changedProps).length > 0) {
        console.log("Changed props:", changedProps);
      }
      prev.current = props;
    });
  }
  
  useTraceUpdate({ pdfUrl: pdfUrl, params: params, ref: dialogRef });

  return (
    <>
      <Dialog open={open} onClose={handleClose} fullWidth>
        {open && (
          <DialogContent>
            <Grid
              container
              direction="column"
              justifyContent={{ sm: "left", md: "center" }}
              alignContent={{ sm: "left", md: "center" }}
              rowSpacing={2}
              sx={{ mb: 4 }}
            >
              {dialogRef && (
                <>
                  <Grid item xs={12}>
                    <PdfView pdfUrl={pdfUrl} params={params} />
                  </Grid>

                  <Grid
                    item
                    xs={5}
                    sx={{ mt: 5 }}
                    alignItems="left"
                    textAlign="left"
                  >
                    <SignContractMonitorForm/>
                  </Grid>


                </>
              )}
            </Grid>
          </DialogContent>
        )}
      </Dialog>
    </>
  );
}

export default SignContractMonitorDialog;
