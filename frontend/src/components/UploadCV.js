import axios from "axios";
import React, { useState, useRef } from "react";
import { Button, Paper, Typography } from "@mui/material";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";

const UploadCV = () => {
  const [file, setFile] = useState();
  const [errorMessage, setErrorMessage] = useState("");
  const inputFileRef = useRef();
  const [cvSent, setCvSent] = useState(false);

  const validFileType = ["application/pdf"];

  const uploadCV = () => {
    const formData = new FormData();

    formData.append("mimeType", file.type);
    formData.append("type", "CV");
    formData.append("filename", file.name.split(".")[0]);
    formData.append("file", file);

    axios({
      method: "POST",
      baseURL: "http://localhost:8080",
      url: "/file",
      data: formData,
      headers: {
        Authorization: sessionStorage.getItem("jwt"),
      },
    })
      .then((response) => {
        if (response.status === 201) setCvSent(true);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const onDrop = (event) => {
    event.preventDefault();
    handleField(event.dataTransfer.files[0]);
  };

  const handleField = (file) => {
    if (file === undefined) return;
    if (validFileType.includes(file.type)) {
      setFile(file);
      setErrorMessage("");
    } else {
      setFile(undefined);
      setErrorMessage(
        "Fichier invalide. Seulement les fichiers PDF sont acceptés."
      );
    }
  };

  const handleFileChange = (event) => {
    if (event.target.files) handleField(event.target.files[0]);
  };

  const openFileExplorer = (event) => {
    if (event.target.localName !== "button") inputFileRef.current.click();
  };

  return (
    <>
      <Paper
        elevation={3}
        sx={{
          mt: 10,
          minHeight: "125px",
          ml: 0,
          mr: 0,
          display: "flex",
          alignItems: "center",
          cursor: "pointer",
        }}
        align="center"
        onClick={openFileExplorer}
      >
        <input
          id="file_input"
          type="file"
          onChange={handleFileChange}
          ref={inputFileRef}
          style={{ display: "none" }}
        />
        <div
          value={file}
          id="file_upload_box"
          onDrop={onDrop}
          onDragOver={(event) => {
            event.preventDefault();
          }}
          style={{
            flexGrow: 1,
            display: "flex",
            alignItems: "center",
            flexDirection: "column",
          }}
        >
          <Typography variant="h5">
            {!cvSent
              ? "Cliquer ou glisser votre CV dans la boite pour téléverser"
              : "CV téléversé avec succès!"}
            <PictureAsPdfIcon sx={{ ml: 2 }} />
          </Typography>
          <Typography variant="caption" color="error">
            {errorMessage !== "" ? errorMessage : ""}
          </Typography>
          {!cvSent && (
            <Typography variant="caption">
              {file === undefined
                ? "Aucune fichier spécifié"
                : `Fichier choisi: ${file.name}`}
            </Typography>
          )}
          {file !== undefined && !cvSent && (
            <Button onClick={uploadCV} variant="contained" color="success">
              Envoyer
            </Button>
          )}
        </div>
      </Paper>
    </>
  );
};

export default UploadCV;
