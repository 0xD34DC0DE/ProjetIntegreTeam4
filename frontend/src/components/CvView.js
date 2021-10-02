import * as React from "react";
import Box from "@mui/material/Box";
import {
  Card,
  CardActions,
  CardContent,
  Typography,
  Grid,
  Button,
} from "@mui/material";
import { blueGrey } from "@mui/material/colors";
import SimCardDownloadIcon from "@mui/icons-material/SimCardDownload";

/*
    TODO
        1) add a condition based on role of current user connected it will return necessary fields
            ->FileMetaDataInternshipManagerViewDto
            ->FileMetaDataStudentViewDto

*/

{
  /*
     <Container>
        <CardContent>
          <Typography>Email de l'élève: {userEmail}</Typography>
          <Typography>Nom du fichier : {fileName}</Typography>
          <Typography>Date de dépôt: {creationDate}</Typography>
        </CardContent>
      </Container>
 */
}

const CvView = ({ assetId, userEmail, fileName, creationDate }) => {
  const download = (userEmail, assetId) => {};

  const card = (
    <>
      <Card
        variant="outlined"
        sx={{ backgroundColor: `${blueGrey[100]}`, borderRadius: 4 , marginLeft:"50%"}}
      >
        <CardContent>
          <Grid
            container
            spacing={0}
            direction="column"
            alignItems="start"
            justify="start"
          >
            <Typography>Email de l'étudiant : {userEmail}</Typography>
            <Typography>Nom du fichier : {fileName}</Typography>
            <Typography>Date de dépôt : {creationDate}</Typography>
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
            <Button size="medium" variant="contained" color="primary">
              DOWNLOAD<SimCardDownloadIcon></SimCardDownloadIcon>
            </Button>
          </Grid>
        </CardActions>
      </Card>
    </>
  );

  return <>{card}</>;
};

export default CvView;
