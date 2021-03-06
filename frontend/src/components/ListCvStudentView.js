import InfoIcon from "@mui/icons-material/Info";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import {
  Box,
  Card,
  CardContent,
  Container,
  Grid,
  Tooltip,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import CvRejectionExplanationDialog from "./CvRejectionExplanationDialog";
import CVDialog from "./CVDialog";
import { DialogContext } from "../stores/DialogStore";
import { motion } from "framer-motion";

const ListCvStudentView = ({ cvSent }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [cvs, setCvs] = useState([]);
  const [url, setUrl] = useState("");
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const fadeIn = {
    hidden: { opacity: 0 },
    show: {
      opacity: [0, 1],
      transition: {
        delay: 0.1,
        staggerChildren: 0.5,
      },
    },
  };

  const labels = ["Fichier:", "Vu: ", "Téléversement: "];
  useEffect(() => {
    const getAllCvByUserEmail = async () => {
      var response = await axios({
        method: "GET",
        url: `http://localhost:8080/file/getAllCvByUserEmail/${userInfo.email}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then((response) => setCvs(response.data))
        .catch((error) => {
          console.log(error);
        });
    };

    getAllCvByUserEmail();
  }, [cvSent]);

  const isNotARenderedAttribute = (identifier) => {
    return !["isValid", "isSeen", "rejectionExplanation", "assetId"].includes(
      identifier
    );
  };

  const openCv = (assetId) => {
    setUrl("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

  return (
    <>
      {cvs && (
        <Container sx={{ mt: 5 }}>
          <Grid alignItems="center" container spacing={5}>
            {cvs
              .sort(function (cv1, cv2) {
                return cv1.isValid === true
                  ? -1
                  : Date.parse(cv1.uploadDate) - Date.parse(cv2.uploadDate);
              })
              .map((cv, key) => {
                return (
                  <Grid
                    item
                    flexGrow="1"
                    key={key}
                    xs={12}
                    sm={6}
                    md={4}
                    lg={3}
                    xl={3}
                  >
                    <motion.div
                      variants={fadeIn}
                      initial="hidden"
                      animate="show"
                    >
                      <Card
                        variant="outlined"
                        sx={{
                          backgroundColor: "rgba(155, 155, 155, 0.1)",
                          borderRadius: 4,
                          boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
                          mt: 2,
                          mb: 5,
                          textAlign: "right",
                          ":hover": {
                            backgroundColor: "rgba(200, 200, 200, 0.1)",
                            cursor: "pointer",
                            boxShadow:
                              "0px 0px 15px 1px rgba(125, 51, 235, 0.8)",
                          },
                        }}
                      >
                        {cv.isSeen === false && (
                          <Typography title="Le CV n'a pas encore été vérifié.">
                            <InfoIcon
                              sx={{
                                float: "right",
                                mr: 1,
                                mt: 1,
                                color: "lightgrey",
                                ":hover": {
                                  color: "darkgray",
                                  cursor: "pointer",
                                },
                              }}
                            />
                          </Typography>
                        )}
                        {cv.isSeen === true && cv.isValid === true && (
                          <Typography title="Le CV a été validé. Vous pouvez maintenant appliquer à des offres.">
                            <ThumbUpIcon
                              sx={{
                                float: "right",
                                mr: 1,
                                mt: 1,
                                color: "green",
                                ":hover": {
                                  color: "darkgreen",
                                  cursor: "pointer",
                                },
                              }}
                            />
                          </Typography>
                        )}
                        {cv.isSeen === true && cv.isValid === false && (
                          <Typography title="Le CV a été rejeté: cliquez ici pour voir la raison du rejet">
                            <ThumbDownIcon
                              onClick={() =>
                                dialogDispatch({
                                  type: "OPEN",
                                  dialogName: "cvRejectionExplanationDialog",
                                })
                              }
                              sx={{
                                float: "right",
                                mr: 1,
                                mt: 1,
                                color: "red",
                                ":hover": {
                                  color: "darkred",
                                },
                              }}
                            />
                            <CvRejectionExplanationDialog
                              rejectionExplanation={cv.rejectionExplanation}
                            />
                          </Typography>
                        )}
                        <Tooltip
                          title={
                            "Cliquez pour visualiser votre CV " + cv.filename
                          }
                        >
                          <Box
                            sx={{
                              textAlign: "center",
                            }}
                            onClick={() => {
                              dialogDispatch({
                                type: "OPEN",
                                dialogName: "cvDialog",
                              });
                              openCv(cv.assetId);
                            }}
                          >
                            <CardContent key={key}>
                              <Typography>Fichier: {cv.filename}</Typography>
                              <Typography>
                                Téléversé: {cv.uploadDate}
                              </Typography>
                            </CardContent>
                          </Box>
                        </Tooltip>
                      </Card>
                    </motion.div>
                  </Grid>
                );
              })}
          </Grid>
        </Container>
      )}
      <CVDialog cvUrl={url} setUrl={setUrl} />
    </>
  );
};

export default ListCvStudentView;
