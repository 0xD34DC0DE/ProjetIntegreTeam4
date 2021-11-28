import axios from "axios";
import React, { useState, useRef, useContext } from "react";
import { Button, Paper, Typography, Grid } from "@mui/material";
import PictureAsPdfIcon from "@mui/icons-material/PictureAsPdf";
import CheckCircleOutlineIcon from "@mui/icons-material/CheckCircleOutline";
import { motion } from "framer-motion";
import { UserInfoContext } from "../stores/UserInfoStore";
import ListCvStudentView from "./ListCvStudentView";
const UploadCV = () => {
  const [file, setFile] = useState();
  const [errorMessage, setErrorMessage] = useState("");
  const inputFileRef = useRef();
  const [cvSent, setCvSent] = useState(false);
  const [userInfo] = useContext(UserInfoContext);

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
        Authorization: userInfo.jwt,
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
    if (cvSent) return;
    if (event.target.localName !== "button") inputFileRef.current.click();
  };

  return (
    <>
      <Grid container flexDirection={"column"}>
        <Grid container justifyContent="center">
          <Grid item xl={8} md={8} lg={8} xs={10} sm={10}>
            <motion.div
              style={{ opacity: 0 }}
              animate={{ opacity: [0, 1] }}
              transition={{
                duration: 0.75,
                delay: 0.25,
              }}
            >
              <Paper
                elevation={5}
                sx={{
                  minHeight: "125px",
                  ml: 0,
                  mr: 2,
                  mt: 5,
                  display: "flex",
                  alignItems: "center",
                  cursor: "pointer",
                  backgroundColor: "rgba(100, 100, 100, 0.1)",
                  boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
                  ":hover": {
                    boxShadow: "0px 0px 15px 1px rgba(255, 255, 255, 0.3)",
                  }
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
                    {!cvSent ? (
                      <PictureAsPdfIcon sx={{ ml: 3 }} />
                    ) : (
                      <CheckCircleOutlineIcon sx={{ ml: 3 }} color="success" />
                    )}
                  </Typography>
                  {errorMessage !== "" && (
                    <Typography
                      variant="subtitle2"
                      color="error"
                      sx={{ fontSize: "0.8em", mt: 1 }}
                    >
                      {errorMessage !== "" ? errorMessage : ""}
                    </Typography>
                  )}
                  {!cvSent && (
                    <Typography variant="subtitle2" sx={{ mt: 1 }}>
                      {file === undefined
                        ? "Aucun fichier spécifié"
                        : `Fichier choisi: ${file.name}`}
                    </Typography>
                  )}
                  {file !== undefined && !cvSent && (
                    <Button
                      onClick={uploadCV}
                      variant="contained"
                      color="success"
                      sx={{ mt: 1 }}
                    >
                      ENVOYER
                    </Button>
                  )}
                </div>
              </Paper>
            </motion.div>
          </Grid>
        </Grid>
      </Grid>
      <motion.div
        style={{ opacity: 0 }}
        animate={{ opacity: [0, 1] }}
        transition={{
          duration: 0.75,
          delay: 0.25,
        }}
      >
        <ListCvStudentView />
      </motion.div>
    </>
  );
};

export default UploadCV;
