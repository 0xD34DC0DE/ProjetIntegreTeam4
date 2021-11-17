import React, { useState, useContext, useEffect } from "react";
import { pdfjs } from "react-pdf";
import { UserInfoContext } from "../stores/UserInfoStore";
import {
  Pagination,
  Grid,
  Typography,
} from "@mui/material";
import axios from "axios";
import PdfContainer from "./PdfContainer";

pdfjs.GlobalWorkerOptions.workerSrc = "pdf.worker.min.js";

function PdfView({ pdfUrl, params }) {
  const [numPages, setNumPages] = useState(1);
  const [pageNumber, setPageNumber] = useState(1);
  const [userInfo] = useContext(UserInfoContext);
  const [pdfData, setPdfData] = useState();
  const [error, setError] = useState();

  const handlePageChange = (_, selectedPage) => {
    setPageNumber(selectedPage);
  };

  useEffect(() => {
    const getPdf = () => {
      var config;
        if (pdfUrl.indexOf('amazon') === -1) {
          config = {
            Accept: "application/pdf",
            Authorization: userInfo.jwt,
            "Access-Control-Allow-Origin": "*",
            'Access-Control-Allow-Methods':'GET',
          }
        } else {
          config = {
            Accept: "application/pdf",
            "Access-Control-Allow-Origin": "*",
            'Access-Control-Allow-Methods':'GET',
          }
        }
      
      axios({
        method: "GET",
        url: pdfUrl,
        headers: config,
        params: { ...params },
        responseType: "arraybuffer",
      })
        .then((response) => {
          setPdfData(response.data);
        })
        .catch((error) => {
          setError(error);
          console.error(error);
        });
      
    };

    getPdf();
  }, []);

  return (
      <Grid
        item
        container
        direction="column"
        justifyContent={"center"}
        alignContent={"center"}
        alignItems={"center"}
      >
        <Grid item>
          <PdfContainer
            pdfData={pdfData}
            pageNumber={pageNumber}
            setPageCount={setNumPages}
          />
          {error && <Typography> Erreur lors du chargement du PDF</Typography>}
        </Grid>

        <Grid item textAlign="center">
          <Pagination
            sx={{ mt: 1 }}
            count={numPages}
            page={pageNumber}
            onChange={handlePageChange}
          />
        </Grid>
      </Grid>
  );
}

export default PdfView;
