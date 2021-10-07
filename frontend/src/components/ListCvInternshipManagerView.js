import React, { useState, useEffect } from "react";
import {TablePagination } from "@mui/material";
import axios from "axios";
import CvInternshipManagerView from "./CvInternshipManagerView";

const ListCvInternshipManagerView = () => {
  const [cvs, setCvs] = useState([]);
  const [nbrCvs, setNbrCvs] = useState(0);
  const [noPage, setNoPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);

  useEffect(() => {
    const getNbrCvs = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/fileMetaData/countAllInvalidCvNotSeen/",
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        responseType: "json",
      })
        .then((response) => {
          setNbrCvs(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    };
    
    const getCvs = (newPage) => {
      axios({
        method: "GET",
        url: `http://localhost:8080/fileMetaData/getListInvalidCvNotSeen/${newPage}`,
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        responseType: "json",
      })
        .then((response) => {
          setCvs(response.data);
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getNbrCvs();

    getCvs(noPage);

  },[noPage]);

  const handleChangePage = (event, newPage,size) => {
    console.log("nouvelle page => " + newPage);
    console.log(event);
    setNoPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    console.log("handleChangeRows => " + event.target.value);
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const removeCv = (id) => {
    setCvs(cvs.filter((cv) => cv.id !== id));
    setNbrCvs(nbrCvs - 1);
  };

  return (
    <>
      <div style={{ overflow: "auto", height: "800px", margin: "auto" }}>
        {cvs.map((cv, key) => (
          <CvInternshipManagerView
            key={key}
            id={cv.id}
            assetId={cv.assetId}
            userEmail={cv.userEmail}
            filename={cv.filename}
            uploadDate={cv.uploadDate}
            removeCv={removeCv}
          />
        ))}
      </div>
      <TablePagination
        disabled
        sx={{ border: "1px" }}
        component="div"
        count={nbrCvs}
        page={noPage}
        onPageChange={handleChangePage}
        rowsPerPage={rowsPerPage}
        onRowsPerPageChange={handleChangeRowsPerPage}
      />
    </>
  );
};

export default ListCvInternshipManagerView;
