import React, { useState, useEffect } from "react";
import { TablePagination, Container } from "@mui/material";
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
        url: "http://localhost:8080/file/countAllInvalidCvNotSeen/",
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
        url: `http://localhost:8080/file/getListInvalidCvNotSeen/${newPage}`,
        headers: {
          Authorization: sessionStorage.getItem("jwt"),
        },
        responseType: "json",
      })
        .then((response) => {
          setCvs(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getNbrCvs();

    getCvs(noPage);
  }, [noPage]);

  const handleChangePage = (event, newPage, size) => {
    setNoPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const removeCv = (id) => {
    setCvs(cvs.filter((cv) => cv.id !== id));
    setNbrCvs(nbrCvs - 1);
  };

  return (
    <>
      <Container
        sx={{
          overflow: "auto",
          height: "900px",
          mt: "10vh",
          mb: "10vh",
        }}
      >
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
      </Container>
      <Container sx={{ marginBottom: "50px" }}>
        <TablePagination
          disabled
          component="div"
          sx={{ boxShadow: 5 }}
          count={nbrCvs}
          page={noPage}
          onPageChange={handleChangePage}
          rowsPerPage={rowsPerPage}
          onRowsPerPageChange={handleChangeRowsPerPage}
        />
      </Container>
    </>
  );
};

export default ListCvInternshipManagerView;
