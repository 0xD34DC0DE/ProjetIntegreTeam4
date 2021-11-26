import React from "react";
import {
  Card,
  CardActions,
  CardContent,
  Typography,
  Grid,
  Button,
} from "@mui/material";
import { motion } from "framer-motion";
import CloudDownloadIcon from "@mui/icons-material/CloudDownload";
import RemoveRedEyeIcon from "@mui/icons-material/RemoveRedEye";
import CvValidationDialog from "./CvValidationDialog";

const CvInternshipManagerView = ({
  id,
  assetId,
  userEmail,
  filename,
  uploadDate,
  removeCv,
  setUrl,
}) => {
  const download = () => {
    window.open("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

  const openCv = () => {
    setUrl("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
  };

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
      <Grid container flexDirection="column">
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Grid item xs={12}>
            <Card
              variant="outlined"
              sx={{
                backgroundColor: "rgba(135, 135, 135, 0.05)",
                borderRadius: "10px",
                border: "0.5px solid grey",
                display:"flex",
                ml: "auto",
                mr: "auto",
                mt: 2,
                mb: 5,
                p:4,
                ":hover": {
                  boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
                }
              }}
            >
              <CardContent>
                <Grid
                  container
                  spacing={0}
                  direction="column"
                  alignItems="start"
                  justify="start"
                >
                  <Typography
                    sx={{ fontWeight: "bold", textAlign: "start" }}
                    variant="overline"
                  >
                    Email de l'étudiant : {userEmail}
                  </Typography>
                  <Typography
                    sx={{ fontWeight: "bold", textAlign: "start" }}
                    variant="overline"
                  >
                    Nom du fichier : {filename}
                  </Typography>
                  <Typography
                    sx={{ fontWeight: "bold", textAlign: "start" }}
                    variant="overline"
                  >
                    Date de dépôt : {uploadDate}
                  </Typography>
                </Grid>
              </CardContent>
              <CardActions>
                <Grid
                  container
                  spacing={0}
                  direction="column"
                  alignItems="center"
                  justify="center"
                >
                  <CvValidationDialog
                    id={id}
                    removeCv={removeCv}
                  ></CvValidationDialog>
                  <Button
                    size="medium"
                    variant="contained"
                    color="info"
                    sx={{ mb: "6px" }}
                    onClick={openCv}
                  >
                    AFFICHER <RemoveRedEyeIcon></RemoveRedEyeIcon>
                  </Button>
                  <Button
                    size="medium"
                    variant="contained"
                    color="primary"
                    sx={{ mb: "6px" }}
                    onClick={download}
                  >
                    TÉLÉCHARGER <CloudDownloadIcon></CloudDownloadIcon>
                  </Button>
                </Grid>
              </CardActions>
            </Card>
          </Grid>
        </motion.div>
      </Grid>
    </>
  );
};

export default CvInternshipManagerView;
