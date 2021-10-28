import React, { useState, useContext } from "react";
import { pdfjs } from "react-pdf";
import { Document, Page } from 'react-pdf/dist/esm/entry.webpack';
import { UserInfoContext } from "../stores/UserInfoStore";

pdfjs.GlobalWorkerOptions.workerSrc = "pdf.worker.min.js";

function PdfView({ pdfUrl }) {
  const [numPages, setNumPages] = useState(null);
  const [pageNumber, setPageNumber] = useState(1);
  const [userInfo] = useContext(UserInfoContext);

  function onDocumentLoadSuccess({ numPages }) {
    setNumPages(numPages);
  }

  return (
    <>
      <div>
        <Document
          file={{ url: pdfUrl, httpHeaders: { 'Authorization': userInfo.jwt } }}
          onLoadSuccess={onDocumentLoadSuccess}
        >
          <Page pageNumber={pageNumber} />
        </Document>
        <p>
          Page {pageNumber} of {numPages}
        </p>
      </div>
    </>
  );
}

export default PdfView;
