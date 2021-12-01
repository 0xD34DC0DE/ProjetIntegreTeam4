import React, { useState, useEffect, useContext } from "react";
import { TablePagination, Container, Grid } from "@mui/material";
import axios from "axios";
import CvInternshipManagerView from "./CvInternshipManagerView";
import { motion } from "framer-motion";
import { UserInfoContext } from "../stores/UserInfoStore";
import CVDialog from "./CVDialog";
import { DialogContext } from "../stores/DialogStore";

const ListCvInternshipManagerView = () => {
  const [cvs, setCvs] = useState([]);
  const [nbrCvs, setNbrCvs] = useState(0);
  const [noPage, setNoPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(10);
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [url, setUrl] = useState("");
  const [studentsProfileImage, setStudentsProfileImage] = useState([]);
  const fadeIn = {
    hidden: { opacity: 0 },
    show: {
      opacity: [0, 1],
      transition: {
        delay: 0.5,
      },
    },
  };

  const fetchProfileImages = (uploadersEmails) => {
    axios({
      method: "POST",
      url: "http://localhost:8080/profileImage/emails",
      headers: {
        Authorization: userInfo.jwt,
      },
      data: uploadersEmails,
      responseType: "json",
    })
      .then(async (response) => {
        const images = response.data.map((data) => {
          return {
            uploaderEmail: data.uploaderEmail,
            image: "data:image/jpg;base64," + data.image,
          };
        });
        setStudentsProfileImage(images);
      })
      .catch((error) => console.error(error));
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

  useEffect(() => {
    if (url !== "") {
      dialogDispatch({
        type: "OPEN",
        dialogName: "cvDialog",
      });
    }
  }, [url]);

  useEffect(() => {
    if (cvs.length === 0) return;
    const uploadersEmails = cvs.map((cv) => {
      return cv.userEmail;
    });
    fetchProfileImages(uploadersEmails);
  }, [cvs]);

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

  return (
    <>
      <Grid
        container
        flexDirection="column"
        sx={{
          overflow: "auto",
          mt: 5,
        }}
      >
        <Grid item lg={9} xl={7} md={11} sm={11} xs={11} sx={{ mx: "auto" }}>
          {cvs.map((cv, key) => (
            <CvInternshipManagerView
              key={key}
              profileImage={
                studentsProfileImage
                  .filter((data) => data.uploaderEmail === cv.userEmail)
                  .map((data) => {
                    return data.image;
                  })[0]
              }
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
              backgroundColor: "rgba(100, 100, 100, 0.1)",
              boxShadow: "10px 10px 10px 0px rgba(0,0,0,0.35)",
              border: "1px solid rgba(100, 100, 100, 0.4)",
            }}
            count={nbrCvs}
            page={noPage}
            onPageChange={handleChangePage}
            rowsPerPage={rowsPerPage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />
        </motion.div>
      </Container>
      <CVDialog cvUrl={url} setUrl={setUrl} />
    </>
  );
};

export default ListCvInternshipManagerView;
