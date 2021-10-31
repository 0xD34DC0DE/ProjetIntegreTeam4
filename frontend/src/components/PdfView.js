import React, { useState, useContext, useEffect } from "react";
import { pdfjs } from "react-pdf";
import { Document, Page } from "react-pdf/dist/esm/entry.webpack";
import { UserInfoContext } from "../stores/UserInfoStore";
import {
  Pagination,
  Container,
  Grid,
  Skeleton,
} from "@mui/material";

pdfjs.GlobalWorkerOptions.workerSrc = "pdf.worker.min.js";

function PdfView({ pdfUrl, dialogRef }) {
  const [numPages, setNumPages] = useState(null);
  const [pageNumber, setPageNumber] = useState(1);
  const [userInfo] = useContext(UserInfoContext);
  const [dialogHeight, setDialogHeight] = useState(700);

  const handlePageChange = (_, selectedPage) => {
    setPageNumber(selectedPage);
  };

  useEffect(() => {
    if (dialogRef.current) {
      setDialogHeight(dialogRef.current.offsetHeight);
    }
  }, [dialogRef]);

  const onDocumentLoadSuccess = (pdfProxy) => {
    setNumPages(pdfProxy.numPages);
  };

  return (
    <>
      <Container>
          <Grid
            container
            direction="column"
            justifyContent={{xs: "left", md:"center"}}
            alignContent={{xs: "left", md:"center"}}
            alignItems={{xs: "left", md:"center"}}
          >
            <Grid item xs={12} sm={11} md={10} lg={8} textAlign="center"> 
              <Document
                file={{
                  url: pdfUrl,
                  httpHeaders: { Authorization: userInfo.jwt },
                }}
                onLoadSuccess={onDocumentLoadSuccess}
                loading={
                  <Skeleton variant="rectangular" width={"100%"} height={500} />
                }
              >
                <Page
                  md={2}
                  loading={null}
                  pageNumber={pageNumber}
                  height={dialogHeight}
                />
              </Document>
            </Grid>

            <Grid item xs={12} textAlign="center">
              <Pagination
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
