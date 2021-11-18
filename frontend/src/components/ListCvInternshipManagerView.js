import React, { useState, useEffect, useContext } from "react";
import { TablePagination, Container, Grid } from "@mui/material";
import axios from "axios";
import CvInternshipManagerView from "./CvInternshipManagerView";
import { motion } from "framer-motion";
import { UserInfoContext } from "../stores/UserInfoStore";
import CVDialog from "./CVDialog";

const ListCvInternshipManagerView = ({ toggleDialog, visible, dialogVisibility }) => {
  const [cvs, setCvs] = useState([]);
  const [nbrCvs, setNbrCvs] = useState(0);
  const [noPage, setNoPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [userInfo] = useContext(UserInfoContext);

  const fadeIn = {
    hidden: { opacity: 0 },
    show: {
      opacity: [0, 1],
      transition: {
        delay: 0.5,
      },
    },
  };

  useEffect(() => {
    const getNbrCvs = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/file/countAllInvalidCvNotSeen/",
        headers: {
          Authorization: userInfo.jwt,
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
          Authorization: userInfo.jwt,
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

  const handleChangePage = (_, newPage) => {
    setNoPage(newPage);
  };

  const handleChangeRowsPerPage = (event) => {
    setRowsPerPage(parseInt(event.target.value, 10));
  };

  const removeCv = (id) => {
    setCvs(cvs.filter((cv) => cv.id !== id));
    setNbrCvs(nbrCvs - 1);
  };

  const [url, setUrl] = useState("");

  useEffect(() => {
    if (url !== "") {
        toggleDialog("cvDialog", true);
    }
  }, [url])

  return (
    <>
      {visible && (
        <>
          <Grid
            container
            flexDirection="column"
            sx={{
              overflow: "auto",
              mt: 5,
            }}
          >
            <Grid item lg={12} xl={12} md={12} sm={6} xs={6} alignSelf="center">
              {cvs.map((cv, key) => (
                <CvInternshipManagerView
                  key={key}
                  id={cv.id}
                  assetId={cv.assetId}
                  userEmail={cv.userEmail}
                  filename={cv.filename}
                  uploadDate={cv.uploadDate}
                  removeCv={removeCv}
                  setUrl={setUrl} 
                />
              ))}
            </Grid>
          </Grid>
          <Container sx={{ pb: 3, mt: 3 }}>
            <motion.div variants={fadeIn} initial="hidden" animate="show">
              <TablePagination
                disabled
                component="div"
                sx={{
                  boxShadow: "0px 0px 5px 1px rgba(255, 255, 255, 0.2)",
                  backgroundColor: "rgba(100, 100, 100, 0.1)",
                }}
                count={nbrCvs}
                page={noPage}
                onPageChange={handleChangePage}
                rowsPerPage={rowsPerPage}
                onRowsPerPageChange={handleChangeRowsPerPage}
              />
            </motion.div>
          </Container>
          <CVDialog open={dialogVisibility.cvDialog} toggleDialog={toggleDialog} cvUrl={url} setUrl={setUrl}/>
        </>
      )}
    </>
  );
};

export default ListCvInternshipManagerView;
