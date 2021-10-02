import React from "react";
import { Container, Typography } from "@mui/material";


/*
    TODO
        1) add a condition based on role of current user connected
            ->FileMetaDataInternshipManagerViewDto
            ->FileMetaDataStudentViewDto

*/
const CvView = ({ id, userEmail, fileName, creationDate }) => {
  return (
    <>
      <Container>
        <Typography>Email de l'élève: {userEmail}</Typography>
        <Typography>Nom du fichier : {fileName}</Typography>
        <Typography>Date de dépôt: {creationDate}</Typography>
      </Container>
    </>
  );
};

export default CvView;
