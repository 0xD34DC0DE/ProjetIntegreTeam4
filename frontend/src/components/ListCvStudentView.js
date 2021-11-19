import {
  Snackbar,
  SnackbarContent,
  Typography,
  Paper,
  Grid,
  Card,
  CardContent,
  List,
  ListItem,
  Container,
  Divider,
  Box,
  Popover,
} from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";
import { motion } from "framer-motion";
import InfoIcon from "@mui/icons-material/Info";
import ThumbUpIcon from "@mui/icons-material/ThumbUp";
import ThumbDownIcon from "@mui/icons-material/ThumbDown";
import { Document, Page } from "react-pdf/dist/esm/entry.webpack";
import CvRejectionExplanationDialog from "./CvRejectionExplanationDialog";

const ListCvStudentView = ({ toggleDialog, dialogVisibility }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [rejectedCvs, setRejectedCvs] = useState([]);
  const [selectedRejectionExplanation, setSelectedRejectionExplanation] =
    useState("");
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
      setRejectedCvs(response.data);
    };

    getAllCvByUserEmail();
  }, []);

  const isNotARenderedAttribute = (identifier) => {
    return !["isValid", "isSeen", "rejectionExplanation"].includes(identifier);
  };

  return (
    <>
      {rejectedCvs && (
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
            {rejectedCvs.map((cv) => {
              console.log("cv seen valid", cv);
              return (
                <Grid item flexGrow="1">
                  <Card
                    variant="outlined"
                    sx={{
                      backgroundColor: "rgba(155, 155, 155, 0.1)",
                      borderRadius: 4,
                      boxShadow: "0px 0px 10px 1px rgba(255, 255, 255, 0.3)",
                      mt: 2,
                      mb: 5,
                      textAlign: "right",
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
                      <Typography title="Le C.V. a été validé. Vous pouvez maintenant appliquer a des offres.">
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
                      <Typography title="Voir la raison du rejet">
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
                              cursor: "pointer",
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
                    <Box sx={{ textAlign: "center" }}>
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
    </>
  );
};

export default ListCvStudentView;
