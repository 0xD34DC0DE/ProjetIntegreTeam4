import React from "react";
import { pdfjs } from "react-pdf";
import { Document, Page } from "react-pdf/dist/esm/entry.webpack";

pdfjs.GlobalWorkerOptions.workerSrc = "pdf.worker.min.js";

function PdfContainer({ pdfData, pageNumber, setPageCount }) {
  function onDocumentLoadSuccess({ numPages: nextNumPages }) {
    setPageCount(nextNumPages);
  }

  return (
    <div>
      <div
        sx={{
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          margin: "10px 0",
          padding: "10px",
        }}
      >
        <div
          sx={{
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
          }}
        >
          {pdfData && (
            <Document
              sx={{
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
              }}
              file={{
                data: pdfData,
              }}
              onLoadSuccess={onDocumentLoadSuccess}
            >
              <Page
                sx={{
                  maxWidth: 'calc(~"100% - 6em")',
                  boxShadow: "0 0 8px rgba(0, 0, 0, .5)",
                  margin: "1em",
                }}
                pageNumber={pageNumber}
              />
            </Document>
          )}
        </div>
      </div>
    </div>
  );
}

export default PdfContainer;
