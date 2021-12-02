import React from "react";
import {
  Card,
  CardContent,
  Typography,
  Grid,
  Button,
  Box,
  Avatar,
} from "@mui/material";
import { motion } from "framer-motion";
import CloudDownloadIcon from "@mui/icons-material/CloudDownload";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import CvValidationDialog from "./CvValidationDialog";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme } from "@mui/material/styles";

const CvInternshipManagerView = ({
  id,
  assetId,
  userEmail,
  filename,
  uploadDate,
  removeCv,
  setUrl,
  profileImage,
}) => {
  const download = () => {
    window.open("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

  const openCv = () => {
    setUrl("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

  const theme = useTheme();
  const matchesBreakpointMd = useMediaQuery(theme.breakpoints.down("md"));

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

  return (
    <>
      <motion.div
        variants={fadeIn}
        initial="hidden"
        animate="show"
        style={{ width: "100%" }}
      >
        <Grid container flexDirection="column">
          <Grid item xs={12}>
            <Card
              variant="outlined"
              sx={{
                backgroundColor: "rgba(135, 135, 135, 0.03)",
                borderRadius: "10px",
                border: "2px solid rgba(100, 100, 100, 0.4)",
                my: 2,
                boxShadow: "15px 15px 10px 0px rgba(0, 0, 0, 0.35);",
              }}
            >
              <CardContent>
                <Grid container spacing={0} direction="row">
                  <Grid
                    item
                    xl={3}
                    lg={3}
                    md={3}
                    sm={12}
                    xs={12}
                    sx={{ mb: matchesBreakpointMd ? 5 : 0 }}
                  >
                    <Box
                      alignSelf="center"
                      sx={{
                        mx: matchesBreakpointMd ? "auto" : "",
                        borderRadius: "50%",
                        width: "150px",
                        height: "150px",
                      }}
                    >
                      <Avatar
                        src={profileImage || ""}
                        sx={{
                          width: "150px",
                          height: "150px",
                        }}
                      ></Avatar>
                    </Box>
                  </Grid>
                  <Grid
                    item
                    container
                    alignSelf="center"
                    xl={6}
                    lg={6}
                    md={6}
                    sm={12}
                    xs={12}
                  >
                    <Grid item xs={12}>
                      <Typography
                        sx={{
                          fontWeight: "bold",
                          textAlign: "start",
                        }}
                        variant="overline"
                      >
                        Courriel de l'étudiant : {userEmail}
                      </Typography>
                    </Grid>
                    <Grid item xs={12}>
                      <Typography
                        sx={{ fontWeight: "bold", textAlign: "start" }}
                        variant="overline"
                      >
                        Nom du fichier : {filename}
                      </Typography>
                    </Grid>
                    <Grid item xs={12}>
                      <Typography
                        sx={{ fontWeight: "bold", textAlign: "start" }}
                        variant="overline"
                      >
                        Date de dépôt : {uploadDate}
                      </Typography>
                    </Grid>
                  </Grid>

                  <Grid
                    item
                    container
                    alignSelf="center"
                    xl={3}
                    lg={3}
                    md={3}
                    sm={12}
                    xs={12}
                  >
                    <Grid
                      container
                      spacing={0}
                      direction="column"
                      justify="center"
                    >
                      <Button
                        size="medium"
                        variant="contained"
                        sx={{
                          mt: "6px",
                          backgroundColor: "rgba(50, 51, 50, 0.8)",
                          ":hover": {
                            backgroundColor: "rgba(100, 101, 100, 0.8)",
                          },
                          width: "100%",
                        }}
                        onClick={openCv}
                      >
                        AFFICHER
                        <RemoveRedEyeIcon sx={{ ml: 1 }}></RemoveRedEyeIcon>
                      </Button>
                      <Button
                        size="medium"
                        variant="contained"
                        sx={{
                          mt: "6px",
                          backgroundColor: "rgba(50, 51, 50, 0.8)",
                          width: "100%",
                          ":hover": {
                            backgroundColor: "rgba(100, 101, 100, 0.8)",
                          },
                        }}
                        onClick={download}
                      >
                        TÉLÉCHARGER
                        <CloudDownloadIcon sx={{ ml: 1 }}></CloudDownloadIcon>
                      </Button>
                      <CvValidationDialog
                        id={id}
                        removeCv={removeCv}
                      ></CvValidationDialog>
                    </Grid>
                  </Grid>
                </Grid>
              </CardContent>
            </Card>
          </Grid>
        </Grid>
      </motion.div>
    </>
  );
};

export default CvInternshipManagerView;
