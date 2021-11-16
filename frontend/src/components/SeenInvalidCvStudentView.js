import { Snackbar, SnackbarContent, Typography, Paper } from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";
import { motion } from "framer-motion";

const SeenInvalidCvStudentView = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [rejectedCvs, setRejectedCvs] = useState([]);
  useEffect(() => {
    const getSeenInvalidCv = async () => {
      var response = await axios({
        method: "GET",
        url: `http://localhost:8080/file/getAllRejectedCvInfo/${userInfo.email}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      }).catch((error) => {
        console.log(error);
      });

      console.log("response", response.data);
      setRejectedCvs(response.data);
    };

    getSeenInvalidCv();
  }, []);

  return (
    <>
      {rejectedCvs && (
        <motion.div
          style={{ opacity: 0 }}
          animate={{ opacity: [0, 1] }}
          transition={{
            duration: 0.75,
            delay: 0.25,
          }}
        >
          <Paper
            sx={{
              minHeight: "125px",
              ml: 0,
              mr: 2,
              mt: 5,
              color: "white",
              backgroundColor: "rgba(100, 100, 100, 0.1)",
              boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
            }}
            align="center"
          >
            {rejectedCvs.map((cv) => {
              return Object.values(cv).map((value, key) => {
                console.log("rjcv", value);
                return <Typography key={key}>{value}</Typography>;
              });
            })}
          </Paper>
        </motion.div>
      )}
    </>
  );
};

export default SeenInvalidCvStudentView;
