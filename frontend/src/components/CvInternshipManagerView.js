import React, { useState, useEffect, useContext } from "react";
import {
  Card,
  CardActions,
  Container,
  CardContent,
  Typography,
  Grid,
  Box,
  Button,
} from "@mui/material";
import { motion } from "framer-motion";
import { blueGrey } from "@mui/material/colors";
import CloudDownloadIcon from "@mui/icons-material/CloudDownload";
import CvValidationDialog from "./CvValidationDialog";

const CvInternshipManagerView = ({
  id,
  assetId,
  userEmail,
  filename,
  uploadDate,
  removeCv,
}) => {

  const download = () => {
    const url = ""; //TODO confirm url with Erwan
    window.open("https://projetintegreteam4.s3.amazonaws.com/" + assetId); //TODO --> need to change it for amazonURl
  };

  return (
    <>
      <Container>
        <Card
          variant="outlined"
          sx={{
            backgroundColor: `${blueGrey[100]}`,
            border: "3px solid white",
            borderRadius: 4,
            width: "50%",
            boxShadow: 3,
            margin: "auto",
            marginTop: "20px",
            marginBottom: "20px",
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
                style={{ fontWeight: "bold", textAlign: "start" }}
                variant="overline"
              >
                Email de l'étudiant : {userEmail}
              </Typography>
              <Typography
                style={{ fontWeight: "bold", textAlign: "start" }}
                variant="overline"
              >
                Nom du fichier : {filename}
              </Typography>
              <Typography
                style={{ fontWeight: "bold", textAlign: "start" }}
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
                color="primary"
                sx={{ mb: "6px" }}
                onClick={download}
              >
                TÉLÉCHARGER <CloudDownloadIcon></CloudDownloadIcon>
              </Button>
            </Grid>
          </CardActions>
        </Card>
      </Container>
    </>
  );
};

export default CvInternshipManagerView;
