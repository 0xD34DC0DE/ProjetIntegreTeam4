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

pdfjs.GlobalWorkerOptions.workerSrc = "pdf.worker.min.js";

function PdfView({ pdfUrl, params }) {
  const [numPages, setNumPages] = useState(null);
  const [pageNumber, setPageNumber] = useState(1);
  const [userInfo] = useContext(UserInfoContext);
  const [dialogHeight, setDialogHeight] = useState(700);
  const [pdfData, setPdfData] = useState();
  const [error, setError] = useState();

  const handlePageChange = (_, selectedPage) => {
    setPageNumber(selectedPage);
  };

  // useEffect(() => {
  //   if (dialogRef.current) {
  //     setDialogHeight(dialogRef.current.offsetHeight);
  //   }
  // }, []);

  const onDocumentLoadSuccess = (pdfProxy) => {
    setNumPages(pdfProxy.numPages);
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

  useTraceUpdate({ pdfUrl: pdfUrl, params: params });

  useEffect(() => {
    const getPdf = () => {
      {
        axios({
          method: "GET",
          url: pdfUrl,
          headers: {
            Authorization: userInfo.jwt,
            Accept: "application/pdf",
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
      <Container sx={{ mt: 3 }}>
        <Grid
          container
          direction="column"
          justifyContent={"center"}
          alignContent={"center"}
          alignItems={"center"}
        >
          <Grid item xs={12} sm={11} md={10} lg={8} textAlign="center">
            {pdfData && (
              <Box>
                <Document
                  file={{
                    data: pdfData,
                  }}
                  onLoadSuccess={onDocumentLoadSuccess}
                  loading={null}
                >
                  <Page
                    sx={{ pl: 4, pr: 4 }}
                    loading={null}
                    pageNumber={pageNumber}
                    width={window.innerWidth * 0.85}
                  />
                </Document>
              </Box>
            )}
            {error && (
              <Typography> Erreur lors du chargement du PDF</Typography>
            )}
          </Grid>

          <Grid item xs={12} textAlign="center">
            <Pagination
              sx={{ mt: 1 }}
              count={numPages}
              page={pageNumber}
              onChange={handlePageChange}
            />
          </Grid>
        </Grid>
      </Container>
    </>
  );
}

export default PdfView;
