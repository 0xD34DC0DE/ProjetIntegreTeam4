import * as React from "react";
import {
  Card,
  CardActions,
  CardContent,
  Typography,
  Grid,
  Box,
  Button,
} from "@mui/material";
import { blueGrey } from "@mui/material/colors";
import CloudDownloadIcon from "@mui/icons-material/CloudDownload";
import ApprovalIcon from "@mui/icons-material/Approval";

const CvInternshipManagerView = ({
  assetId,
  userEmail,
  fileName,
  uploadDate,
}) => {

  const download = () => {
    const url = ""; //TODO confirm url with Erwan
    window.open("https://www.youtube.com"); //TODO --> need to change it for amazonURl
  };

  return (
    <>
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
              Nom du fichier : {fileName}
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
            <Button
              size="medium"
              variant="contained"
              color="success"
              sx={{ mb: "6px" }}
              onClick={download}
            >
              VALIDER <ApprovalIcon></ApprovalIcon>
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
    </>
  );
};

export default CvInternshipManagerView;
