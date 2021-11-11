import { Snackbar, SnackbarContent, Typography, Paper } from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";

const SeenInvalidCvStudentView = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [cv, setCv] = useState();
  useEffect(() => {
    const getSeenInvalidCv = async () => {
      var response = await axios({
        method: "GET",
        url: `http://localhost:8080/file/getSeenInvalidCv/${userInfo.email}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      }).catch((error) => {
        console.log(error);
      });

      console.log("LOG TEST");
      console.log("res", response.data);
      if (response) setCv(response.data);
    };

    console.log("LOG TEST 2");
    getSeenInvalidCv();
  }, []);

  return (
    <>
      {cv && (
        <Paper sx={{ backgroundColor: "black" }}>
          {cv.cvRejectionExplanation}
        </Paper>
      )}
    </>
  );
};

export default SeenInvalidCvStudentView;
