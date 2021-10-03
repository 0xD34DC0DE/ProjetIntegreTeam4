import React, { useState, useEffect, useContext } from "react";
import { Stack } from "@mui/material";
import axios from "axios";
import CvInternshipManagerView from "./CvInternshipManagerView";
import { UserInfoContext } from "../stores/UserInfoStore";

const ListCvInternshipManagerView = () => {
  const [cvs, setCvs] = useState([]);
  const noPage = 0;

  useEffect(() => {
    getCvs();
  }, []);

  const getCvs = () => {
    console.log(global.token);
    axios({
      method: "GET",
      url: `http://localhost:8080/fileMetaData/getListInvalidCvNotSeen/${noPage}`,
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

  return (
    <>
      <div style={{overflowY:"auto",overflowX:"auto"}}>
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
    </>
  );
};

export default ListCvInternshipManagerView;
