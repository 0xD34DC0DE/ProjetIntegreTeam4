import React, { useState, useEffect, useContext } from "react";
import { Button } from "@mui/material";
import axios from "axios";
import CvInternshipManagerView from "./CvInternshipManagerView";
import ExpandMoreIcon from "@mui/icons-material/ExpandMore";

const ListCvInternshipManagerView = () => {
  const [noPage, setNoPage] = useState(0);
  const [cvs, setCvs] = useState([]);
  const [hasMoreElement, setHasMoreElement] = useState(true);

  useEffect(() => {
    getCvs();
  }, []);

  const getCvs = () => {
    axios({
      method: "GET",
      url: `http://localhost:8080/fileMetaData/getListInvalidCvNotSeen/${noPage}`,
      headers: {
        Authorization: sessionStorage.getItem("jwt"),
      },
      responseType: "json",
    })
      .then((response) => {
        if (response.data.length > 0) {
          setCvs(cvs.concat(response.data));
          setNoPage(noPage + 1);
          console.log(noPage);
          console.log(response.data);
        } else {
          setHasMoreElement(false);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const loadMore = () => {
    getCvs();
  };

  return (
    <>
      <div style={{ overflow: "auto" }}>
        {cvs.map((cv, key) => (
          <CvInternshipManagerView
            key={key}
            assetId={cv.assetId}
            userEmail={cv.userEmail}
            fileName={cv.fileName}
            uploadDate={cv.uploadDate}
          />
        ))}
      </div>

      <div>
        {hasMoreElement ? (
          <Button
            size="medium"
            variant="contained"
            color="primary"
            sx={{ mb: "100px", mt: "50px" }}
            onClick={loadMore}
          >
            LOAD MORE <ExpandMoreIcon></ExpandMoreIcon>
          </Button>
        ) : null}
      </div>
    </>
  );
};

export default ListCvInternshipManagerView;
