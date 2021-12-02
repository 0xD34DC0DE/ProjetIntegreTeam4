import { Dialog, DialogContent, Grid, Button } from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { DialogContext } from "../../stores/DialogStore.js";
import { UserInfoContext } from "../../stores/UserInfoStore";
import DownloadIcon from "@mui/icons-material/Download";
import PdfView from "../PdfView.js";
import axios from "axios";

function EvaluationDialogPreview({ evaluationId, setEvaluationId, mid }) {
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [userInfo] = useContext(UserInfoContext);
  const [pdfBlobUrl, setPdfBlobUrl] = useState("");

  const handleClose = () => {
    dialogDispatch({
      type: "CLOSE",
      dialogName: "evaluationDialogPreview",
    });
    setEvaluationId("");
  };

  const getPdfUrl = () => {
    return (
      `http://localhost:8080/evaluation/` +
      (mid === true ? "mid/" : "") +
      evaluationId
    );
  };

  const generateBlobUrl = () => {
    axios({
      method: "GET",
      headers: {
        Authorization: userInfo.jwt,
      },
      url: getPdfUrl(),
      responseType: "arraybuffer",
    }).then((response) => {
      const pdfBlob = new Blob([response.data], { type: "application/pdf" });
      setPdfBlobUrl(URL.createObjectURL(pdfBlob));
    });
  };

  const openPdfUrl = () => {
    const newWindow = window.open(pdfBlobUrl, "_blank", "noopener,noreferrer");
    if (newWindow) newWindow.opener = null;
  };

  useEffect(() => {
    if (pdfBlobUrl === "") return;
    openPdfUrl();
  }, [pdfBlobUrl]);

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
                <Grid item textAlign="center" xs={11}>
                  <Button
                    variant="contained"
                    sx={{
                      color: "white !important",
                      backgroundColor: "rgba(125, 51, 235, 0.8) !important",
                      ":hover": {
                        backgroundColor: "rgba(95, 21, 205, 0.6) !important",
                      },
                      fontSize: "0.9em",
                      boxShadow: "2px 2px 10px 2px rgba(0, 0, 0, 0.5)",
                    }}
                    onClick={() => {
                      generateBlobUrl();
                    }}
                  >
                    Télécharger
                    <DownloadIcon />
                  </Button>
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
