import InfoIcon from "@mui/icons-material/Info";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import {
  Box,
  Card,
  CardContent,
  Container,
  Grid,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import CvRejectionExplanationDialog from "./CvRejectionExplanationDialog";
import CVDialog from "./CVDialog";

const ListCvStudentView = ({ toggleDialog, dialogVisibility }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [cvs, setCvs] = useState([]);
  const [url, setUrl] = useState("");
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
  useEffect(() => {
    const getAllCvByUserEmail = async () => {
      var response = await axios({
        method: "GET",
        url: `http://localhost:8080/file/getAllCvByUserEmail/${userInfo.email}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      }).catch((error) => {
        console.log(error);
      });

      console.log("response", response.data);
      setCvs(response.data);
    };

    getAllCvByUserEmail();
  }, []);

  const isNotARenderedAttribute = (identifier) => {
    return !["isValid", "isSeen", "rejectionExplanation", "assetId"].includes(
      identifier
    );
  };

  const openCv = (assetId) => {
    console.log("assetId", assetId);
    setUrl("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

  return (
    <>
      {cvs && (
        <Container sx={{ mt: 5 }}>
          <Typography
            variant="h4"
            color="white"
            textAlign="center"
            sx={{ my: 5 }}
          >
            Vos C.V.
          </Typography>
          <Grid alignItems="center" container spacing={5}>
            {cvs
              .sort(function (cv1, cv2) {
                return cv1.isValid == true
                  ? -1
                  : Date.parse(cv1.uploadDate) - Date.parse(cv2.uploadDate);
              })
              .map((cv) => {
                return (
                  <Grid item flexGrow="1" xs={12} sm={6} md={4} lg={3} xl={3}>
                    <Card
                      variant="outlined"
                      sx={{
                        backgroundColor: "rgba(155, 155, 155, 0.1)",
                        borderRadius: 4,
                        boxShadow: "0px 0px 10px 1px rgba(255, 255, 255, 0.3)",
                        mt: 2,
                        mb: 5,
                        textAlign: "right",
                        ":hover": {
                          backgroundColor: "rgba(200, 200, 200, 0.1)",
                          cursor: "pointer",
                        },
                      }}
                    >
                      {cv.isSeen == false && (
                        <Typography title="Le C.V. n'a pas encore été vérifié.">
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
                      {cv.isSeen == true && cv.isValid == true && (
                        <Typography title="Le C.V. a été validé. Vous pouvez maintenant appliquer à des offres.">
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
                      {cv.isSeen == true && cv.isValid == false && (
                        <Typography title="Le C.V. a été rejeté: cliquez ici pour voir la raison du rejet">
                          <ThumbDownIcon
                            onClick={() =>
                              toggleDialog("cvRejectionExplanationDialog", true)
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
                            open={dialogVisibility.cvRejectionExplanationDialog}
                            toggleDialog={toggleDialog}
                            rejectionExplanation={cv.rejectionExplanation}
                          />
                        </Typography>
                      )}
                      <Box
                        sx={{
                          textAlign: "center",
                        }}
                        onClick={() => {
                          toggleDialog("cvDialog", true);
                          openCv(cv.assetId);
                        }}
                      >
                        {Object.keys(cv).map((identifier, key) => {
                          var value = Object.values(cv)[key];
                          if (value && isNotARenderedAttribute(identifier))
                            return <CardContent key={key}>{value}</CardContent>;
                        })}
                      </Box>
                    </Card>
                  </Grid>
                );
              })}
          </Grid>
        </Container>
      )}
      <CVDialog
        open={dialogVisibility.cvDialog}
        toggleDialog={toggleDialog}
        cvUrl={url}
        setUrl={setUrl}
      />
    </>
  );
};

export default ListCvStudentView;
