import React from "react";
import { ListItemIcon, ListItemAvatar } from "@mui/material";
import DashboardCustomizeOutlinedIcon from "@mui/icons-material/DashboardCustomizeOutlined";
import FileCopyIcon from "@mui/icons-material/FileCopy";

const CvView = ({id,userEmail,fileName,creationDate}) => {
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
