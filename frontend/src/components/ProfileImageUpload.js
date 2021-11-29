import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";
import {
  Grid,
  Typography,
  Paper,
  Avatar,
  Button,
  CircularProgress,
} from "@mui/material";
import CameraAltOutlinedIcon from "@mui/icons-material/CameraAltOutlined";
import { Box } from "@mui/system";
import { motion } from "framer-motion";
import CheckCircleOutlineOutlinedIcon from "@mui/icons-material/CheckCircleOutlineOutlined";
import ErrorOutlineOutlinedIcon from "@mui/icons-material/ErrorOutlineOutlined";

const ProfileImageUpload = () => {
  const [image, setImage] = useState(undefined);
  const [uploadImage, setUploadImage] = useState(undefined);
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  const uploadImageMaxSize = 1024 * 1024 * 4;
  const [errorMessage, setErrorMessage] = useState("");
  const [uploadImageSrc, setUploadImageSrc] = useState("");
  const [uploadingState, setUploadingState] = useState("NOT_UPLOADED");
  const fileNameRegexValidation = /[^A-Za-z0-9]+/;
  const allowedImageTypes = ["jpeg", "jpg", "png"];

  const uploadProfileImage = () => {
    const formData = new FormData();

    formData.append("image", uploadImage);
    formData.append("uploaderEmail", userInfo.email);

    axios({
      method: "POST",
      baseURL: "http://localhost:8080",
      url: "/profileImage/upload",
      data: formData,
      headers: {
        Authorization: userInfo.jwt,
      },
    })
      .then((response) => {
        userInfoDispatch({
          type: "UPDATE_PROFILE_IMAGE",
          payload: {
            profileImage: arrayBufferToBlobUrl(uploadImage, uploadImage.type),
          },
        });
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const arrayBufferToBlobUrl = (arrayBuffer, headers) => {
    const imageType = headers["Content-Type"];
    const blob = new Blob([arrayBuffer], { type: imageType });
    return window.URL.createObjectURL(blob);
  };

  const validateUploadImage = (file) => {
    const size = file.size;
    const type = file.type.split("/")[1];
    const fileName = file.name.substring(0, file.name.lastIndexOf("."));

    if (size > uploadImageMaxSize)
      setErrorMessage(
        "L'image est trop volumineuse, les images doivent être moins que 4MB"
      );
    else if (fileNameRegexValidation.test(fileName))
      setErrorMessage(
        "Le nom du fichier ne doit contenir que des lettres et des chiffres"
      );
    else if (!allowedImageTypes.includes(type))
      setErrorMessage("Le fichier doit être de type jpg, jpeg ou png");
    else {
      setErrorMessage("");
      setUploadImage(file);
    }
  };

  useEffect(() => {
    if (uploadingState === "UPLOADING")
      setTimeout(() => {
        setUploadingState("UPLOADED");
      }, 5000);
  }, [uploadingState]);

  useEffect(() => {
    if (errorMessage === "") return;
    console.log(errorMessage);
  }, [errorMessage]);

  useEffect(() => {
    if (uploadImage === undefined) return;
    getUploadFilePreview();
  }, [uploadImage]);

  const handleUploadImageInput = (event) => {
    const file = event.target.files[0];
    if (file !== undefined) validateUploadImage(file);
  };

  const getUploadFilePreview = async () => {
    let fileReader = new FileReader();
    let url = fileReader.readAsDataURL(uploadImage);
    fileReader.onloadend = () => {
      setUploadImageSrc(fileReader.result);
    };
  };

  const handleFileDrop = (event) => {
    event.preventDefault();
    validateUploadImage(event.dataTransfer.files[0]);
  };

  return (
    <motion.div
      animate={{ opacity: [0, 1] }}
      transition={{
        duration: 1,
      }}
      style={{ opacity: 0 }}
    >
      <div
        style={{ width: "100%", height: "100%" }}
        onDrop={handleFileDrop}
        onDragOver={(event) => {
          event.preventDefault();
        }}
      >
        <Grid container justifyContent="center" mt={5} pb={3}>
          <Grid item xs={8} alignSelf="center" textAlign="center">
            <Paper
              sx={{
                backgroundColor: "rgba(22, 22, 22, 0.04)",
                py: 2,
                px: 5,
                border: "1px solid rgba(255, 255, 255, 0.5)",
                boxShadow: "0px 0px 10px 3px rgba(0, 0, 0, 0.5)",
              }}
            >
              <Grid item xs={12}>
                <Typography
                  variant="subtitle2"
                  sx={{
                    fontSize: "1.8em",
                    display: "inline-block",
                    textShadow: "3px 3px black",
                    lineHeight: 1,
                  }}
                >
                  Modifier votre image de profile
                </Typography>
              </Grid>
              <Grid item xs={12}>
                <Typography
                  variant="caption"
                  sx={{
                    textShadow: "2px 2px black",
                  }}
                >
                  Glisser votre image ou appuyez sur le boutton ci-dessous
                </Typography>
              </Grid>
              <Grid
                container
                mt={uploadingState === "UPLOADED" ? 0 : 5}
                alignItems="center"
              >
                {uploadingState !== "UPLOADED" && (
                  <Grid item xs={12}>
                    <Box
                      sx={{
                        boxShadow: "0px 0px 10px 2px rgba(255, 255, 255, 0.2)",
                        width: "200px",
                        height: "200px",
                        mx: "auto",
                        borderRadius: "50%",
                      }}
                    >
                      {uploadImageSrc !== "" && errorMessage === "" && (
                        <motion.div
                          animate={{ opacity: [0, 1] }}
                          transition={{
                            duration: 1,
                          }}
                          style={{ opacity: 0, width: "100%", height: "100%" }}
                        >
                          <Avatar
                            src={uploadImageSrc}
                            sx={{ width: "100%", height: "100%", opacity: 1 }}
                          ></Avatar>
                        </motion.div>
                      )}
                    </Box>
                  </Grid>
                )}
                <Grid
                  item
                  xs={12}
                  textAlign="center"
                  mt={uploadingState === "UPLOADED" ? 0 : 5}
                >
                  {uploadingState == "NOT_UPLOADED" ? (
                    <>
                      <Grid item xs={12}>
                        <Button
                          variant="contained"
                          component="label"
                          id="uploadImageDragnDrop"
                          onDragOver={(event) => {
                            event.preventDefault();
                          }}
                          sx={{
                            ":hover": {
                              backgroundColor:
                                "rgba(105, 105, 105, 0.1) !important",
                            },
                            width: "180px",
                            backgroundColor:
                              "rgba(255, 255, 255, 0.1) !important",
                          }}
                        >
                          <Typography variant="subtitle2">
                            Parcourir vos fichiers...
                          </Typography>
                          <input
                            id="uploadImageInput"
                            type="file"
                            onChange={handleUploadImageInput}
                            hidden
                          />
                        </Button>
                      </Grid>
                      <Grid item xs={12} mt={1}>
                        <Button
                          disabled={
                            errorMessage !== "" || uploadImageSrc === ""
                          }
                          variant="contained"
                          onClick={() => {
                            setUploadingState("UPLOADING");
                            uploadProfileImage();
                          }}
                          sx={{
                            ":hover": {
                              backgroundColor:
                                "rgba(105, 105, 105, 0.1) !important",
                            },
                            width: "180px",
                            backgroundColor:
                              "rgba(255, 255, 255, 0.1) !important",
                          }}
                        >
                          <Typography variant="subtitle2">
                            Téléverser l'image
                          </Typography>
                        </Button>
                        <Grid item mt={2} xs={12}>
                          <Typography
                            variant="subtitle2"
                            sx={{
                              color: "rgba(255, 85, 85, 1)",
                              textShadow: "2px 2px black",
                              textAlign: "center",
                            }}
                          >
                            {errorMessage}
                          </Typography>
                        </Grid>
                      </Grid>
                    </>
                  ) : (
                    <>
                      {uploadingState === "UPLOADING" ? (
                        <Grid item textAlign="center">
                          <CircularProgress sx={{ color: "white" }} />
                        </Grid>
                      ) : uploadingState !== "ERROR" ? (
                        <motion.div
                          animate={{ opacity: [0, 1] }}
                          transition={{
                            duration: 1,
                          }}
                          style={{ opacity: 0 }}
                        >
                          <Grid item textAlign="center">
                            <Typography
                              variant="h2"
                              sx={{
                                fontSize: "1.1em",
                                color: "rgba(255, 255, 255, 0.9)",
                                textShadow: "2px 2px black",
                              }}
                              my={2}
                            >
                              Image téléversée avec succès!
                              <CheckCircleOutlineOutlinedIcon
                                sx={{ ml: 2, color: "rgba(50, 200, 50, 1)" }}
                              />
                            </Typography>
                          </Grid>
                        </motion.div>
                      ) : (
                        <motion.div
                          animate={{ opacity: [0, 1] }}
                          transition={{
                            duration: 1,
                          }}
                          style={{ opacity: 0 }}
                        >
                          <Grid item textAlign="center">
                            <Typography
                              variant="body2"
                              sx={{
                                fontSize: "1.1em",
                                color: "rgba(255, 255, 255, 0.9)",
                                textShadow: "2px 2px black",
                              }}
                              my={2}
                            >
                              Une erreur est survenue, veuillez réessayer.
                              <ErrorOutlineOutlinedIcon
                                sx={{ ml: 2, color: "rgba(200, 50, 50, 1)" }}
                              />
                            </Typography>
                          </Grid>
                          <Grid item textAlign="center">
                            <Button
                              variant="contained"
                              onClick={() => {
                                setUploadingState("NOT_UPLOADED");
                                setUploadImageSrc("");
                                setUploadImage(undefined);
                                setImage(undefined);
                              }}
                              sx={{
                                ":hover": {
                                  backgroundColor:
                                    "rgba(105, 105, 105, 0.1) !important",
                                },
                                width: "180px",
                                backgroundColor:
                                  "rgba(255, 255, 255, 0.1) !important",
                              }}
                            >
                              <Typography variant="subtitle2">
                                Réessayer
                              </Typography>
                            </Button>
                          </Grid>
                        </motion.div>
                      )}
                    </>
                  )}
                </Grid>
              </Grid>
            </Paper>
          </Grid>
        </Grid>
      </div>
    </motion.div>
  );
};

export default ProfileImageUpload;
