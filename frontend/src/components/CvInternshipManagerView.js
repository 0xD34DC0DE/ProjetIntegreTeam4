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
import { shadows } from "@mui/system";
import SimCardDownloadIcon from "@mui/icons-material/SimCardDownload";

/*
    TODO
        1) add a condition based on role of current user connected it will return necessary fields
            ->FileMetaDataInternshipManagerViewDto
            ->FileMetaDataStudentViewDto

*/

const CvInternshipManagerView = ({
  assetId,
  userEmail,
  fileName,
  uploadDate,
}) => {
  const download = () => {
    const url =
      "https://907028119533.signin.aws.amazon.com/console/" +
      userEmail +
      "/" +
      assetId; //TODO confirm url with Erwan
    window.open("https://www.youtube.com"); //TODO --> need to change it for amazonURl
  };
  
  const card = (
    <>
      <Card
        variant="outlined"
        sx={{
          backgroundColor: `${blueGrey[100]}`,
          border:"3px solid white",
          borderRadius: 4,
          width: "50%",
          boxShadow: 3,
          margin: "auto"
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
              style={{ textDecoration: "underline", fontWeight: "bold" }}
              variant="overline"
            >
              Email de l'étudiant : {userEmail}
            </Typography>
            <Typography
              style={{ textDecoration: "underline", fontWeight: "bold" }}
              variant="overline"
            >
              Nom du fichier : {fileName}
            </Typography>
            <Typography
              style={{ textDecoration: "underline", fontWeight: "bold" }}
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
              color="primary"
              sx={{ mb: "6px" }}
              onClick={download}
            >
              DOWNLOAD<SimCardDownloadIcon></SimCardDownloadIcon>
            </Button>
          </Grid>
        </CardActions>
      </Card>
    </>
  );

  return <>{card}</>;
};

export default CvInternshipManagerView;
