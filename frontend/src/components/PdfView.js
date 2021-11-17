import React, { useState, useContext, useEffect, useRef } from "react";
import { pdfjs } from "react-pdf";
import { Document, Page } from "react-pdf/dist/esm/entry.webpack";
import { UserInfoContext } from "../stores/UserInfoStore";
import {
  Pagination,
  Container,
  Grid,
  Skeleton,
  Box,
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
      {
        axios({
          method: "GET",
          url: pdfUrl,
          headers: {
            Authorization: userInfo.jwt,
            Accept: "application/pdf",
            "Access-Control-Allow-Origin": "*",
            'Access-Control-Allow-Methods':'GET',
          },
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
      }
    };

    getPdf();
  }, []);

  return (
    <>
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
    </>
  );
}

export default PdfView;
