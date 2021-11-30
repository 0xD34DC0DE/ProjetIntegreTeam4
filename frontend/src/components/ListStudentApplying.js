import {
  Grid,
  List,
  ListItemAvatar,
  ListItemText,
  Paper,
  Typography,
  Avatar,
  Divider,
  Tooltip,
  Button,
} from "@mui/material";
import React, { useContext, useEffect, useState } from "react";
import FileDownloadOutlinedIcon from "@mui/icons-material/FileDownloadOutlined";
import RemoveRedEyeOutlinedIcon from "@mui/icons-material/RemoveRedEyeOutlined";
import HowToRegIcon from "@mui/icons-material/HowToReg";
import { motion } from "framer-motion";
import axios from "axios";
import MailOutlineIcon from "@mui/icons-material/MailOutline";
import EmailSender from "./EmailSender";
import { UserInfoContext } from "../stores/UserInfoStore";
import CreateContractMonitorDialog from "./contracts/CreateContractMonitorDialog";
import SemesterSelect from "./SemesterSelect";
import CVDialog from "./CVDialog";
import { DialogContext } from "../stores/DialogStore";

const ListStudentApplying = () => {
  const [offers, setOffers] = useState([]);
  const [receiver, setReceiver] = useState("");
  const [userInfo] = useContext(UserInfoContext);
  const [semesterFullName, setSemesterFullName] = useState("");
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [studentsProfileImage, setStudentsProfileImage] = useState([]);

  const fetchData = () => {
    axios({
      method: "GET",
      url: "http://localhost:8080/internshipOffer/interestedStudents",
      params: {
        monitorEmail: userInfo.email,
        semesterFullName: semesterFullName,
      },
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then((response) => {
        setOffers(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const updateSemesterFullName = (fullName) => {
    setSemesterFullName(fullName);
  };

  useEffect(() => {}, [offers]);

  const getStudentInitials = (fullName) => {
    let initials = "";
    fullName.split(" ").map((char) => (initials += char.charAt(0)));
    return initials;
  };

  useEffect(() => {
    if (semesterFullName === "") return;
    fetchData();
  }, [semesterFullName]);

  const [url, setUrl] = useState("");

  useEffect(() => {
    if (url !== "") {
      dialogDispatch({
        type: "OPEN",
        dialogName: "cvDialog",
      });
    }
  }, [url]);

  const showCv = (email, medium) => {
    var assetId = "";
    axios({
      method: "GET",
      baseURL: "http://localhost:8080",
      url: "/file/getLatestCv/" + email,
      headers: {
        Authorization: userInfo.jwt,
      },
    })
      .then((response) => {
        assetId = response.data;
        if (medium) {
          setUrl("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
        } else {
          window.open("https://projetintegreteam4.s3.amazonaws.com/" + assetId);
        }
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const getStudentProfilePicture = (student) => {
    const profileImageObject = studentsProfileImage.find(
      (data) => data.uploaderEmail === student.email
    );

    return profileImageObject !== undefined ? profileImageObject.image : "";
  };

  useEffect(async () => {
    if (offers === []) return;
    const interestedStudentEmails = await offers.flatMap((offer) =>
      offer.interestedStudentList.map((student) => student.email)
    );
    fetchProfileImages(interestedStudentEmails);
  }, [offers]);

  const fetchProfileImages = (uploadersEmails) => {
    axios({
      method: "POST",
      url: "http://localhost:8080/profileImage/emails",
      headers: {
        Authorization: userInfo.jwt,
      },
      data: uploadersEmails,
      responseType: "json",
    })
      .then(async (response) => {
        const images = response.data.map((data) => {
          return {
            uploaderEmail: data.uploaderEmail,
            image: "data:image/jpg;base64," + data.image,
          };
        });
        setStudentsProfileImage(images);
      })
      .catch((error) => console.error(error));
  };

  return (
    <Grid
      container
      flexDirection="row"
      mt={2}
      gap="20px"
      justifyContent="center"
      alignItems="center"
    >
      <SemesterSelect updateSemesterFullName={updateSemesterFullName} />

      {offers
        .filter((offer) => offer.interestedStudentList.length !== 0)
        .map((offer, key) => {
          return (
            <React.Fragment key={key}>
              <Grid container flexDirection="column">
                <motion.div
                  animate={{ opacity: [0, 1] }}
                  transition={{
                    duration: 0.2,
                    delay: (key + 1) * 0.2,
                  }}
                >
                  <Grid item xl={6} lg={8} md={10} xs={12} sm={12} px={3}>
                    <Paper
                      elevation={5}
                      sx={{
                        backgroundColor: "rgba(255, 255, 255, 0.01)",
                        px: 2,
                        py: 2,
                      }}
                    >
                      <motion.div
                        animate={{ opacity: [0, 1] }}
                        transition={{
                          duration: 0.3,
                          delay: (key + 1) * 0.3,
                        }}
                      >
                        <Typography
                          variant="subtitle2"
                          color="text.primary"
                          sx={{
                            fontSize: "2.2em",
                            lineHeight: "1.2",
                            color: "rgba(255, 255, 255, 0.7)",
                          }}
                        >
                          {offer.companyName}
                        </Typography>
                        <Typography
                          variant="subtitle2"
                          sx={{
                            color: "rgba(255, 255, 255, 0.5)",
                            fontStyle: "italic",
                          }}
                        >
                          {offer.title}
                        </Typography>
                        <Typography variant="caption" color="text.primary">
                          Description du poste
                        </Typography>
                        <Typography
                          variant="caption"
                          sx={{
                            wordBreak: "break-all",
                            color: "rgba(150, 150, 150, 255)",
                            fontStyle: "italic",
                          }}
                        >
                          : {offer.description}
                        </Typography>
                      </motion.div>
                    </Paper>
                  </Grid>
                </motion.div>
              </Grid>
              {offer.interestedStudentList.map((student, key2) => {
                return (
                  <Grid item key={key2} xl={3} lg={3} md={4} sm={8} xs={8}>
                    <motion.div
                      animate={{ opacity: [0, 1] }}
                      transition={{
                        duration: 0.4,
                        delay: (key + 1) * 0.4,
                      }}
                    >
                      <Paper
                        elevation={5}
                        sx={{
                          backgroundColor: "rgba(100, 100, 100, 0.1)",
                          boxShadow: "5px 5px 10px 1px rgba(0, 0, 0, 0.6)",
                        }}
                      >
                        <List sx={{ pb: 0 }}>
                          <Grid container flexDirection={"column"} my={1}>
                            <Grid
                              item
                              sx={{ pt: 1 }}
                              xs={12}
                              alignSelf="center"
                            >
                              <ListItemAvatar>
                                <Avatar
                                  src={getStudentProfilePicture(student)}
                                  sx={{
                                    width: 75,
                                    height: 75,
                                    backgroundColor: "rgba(255, 255, 255, 0.5)",
                                    color: "rgba(255, 255, 255, 0.5)",
                                  }}
                                >
                                  {getStudentInitials(
                                    student.firstName
                                      .concat(" ")
                                      .concat(student.lastName)
                                  )}
                                </Avatar>
                              </ListItemAvatar>
                            </Grid>
                            <Grid item xs={12}>
                              <ListItemText
                                sx={{
                                  textAlign: "center",
                                  whiteSpace: "wrap",
                                  overflow: "hidden",
                                  textOverflow: "ellipsis",
                                }}
                              >
                                <Typography sx={{ mt: 1 }} variant="subtitle1">
                                  {student.firstName} {student.lastName}
                                </Typography>
                                <Typography variant="caption">
                                  {student.email}
                                </Typography>
                                <br />
                                <Typography variant="caption">
                                  No. teléphone: {student.phoneNumber}
                                </Typography>
                              </ListItemText>
                            </Grid>
                            <Grid item xs={12} mt={1}>
                              <Divider
                                sx={{
                                  backgroundColor: "rgba(255, 255, 255, 0.2)",
                                }}
                              />
                            </Grid>
                            <Grid
                              item
                              xs={12}
                              sx={{
                                backgroundColor: "rgba(0, 0, 0, 0.2)",
                                py: 1,
                              }}
                            >
                              <Grid container flexDirection={"row"}>
                                <Grid item xs={3} textAlign="center">
                                  <Tooltip title="Télécharger le CV">
                                    <Button
                                      sx={{
                                        p: 0,
                                        m: 0,
                                        py: 0.5,
                                        ":hover": {
                                          backgroundColor:
                                            "rgba(125, 51, 235, 0.8)",
                                        },
                                      }}
                                      onClick={() => {
                                        showCv(student.email, false);
                                      }}
                                    >
                                      <FileDownloadOutlinedIcon
                                        sx={{ color: "white" }}
                                      />
                                    </Button>
                                  </Tooltip>
                                </Grid>
                                <Grid item xs={3} textAlign="center">
                                  <Tooltip title="Répondre à la demande">
                                    <Button
                                      sx={{
                                        p: 0,
                                        m: 0,
                                        py: 0.5,
                                        ":hover": {
                                          backgroundColor:
                                            "rgba(125, 51, 235, 0.8)",
                                        },
                                      }}
                                      onClick={() => {
                                        setReceiver(student.email);
                                        dialogDispatch({
                                          type: "OPEN",
                                          dialogName: "emailSenderDialog",
                                        });
                                      }}
                                    >
                                      <MailOutlineIcon
                                        sx={{ color: "white" }}
                                      />
                                    </Button>
                                  </Tooltip>
                                </Grid>
                                <Grid item xs={3} textAlign="center">
                                  <Tooltip title="Visualiser le CV">
                                    <Button
                                      sx={{
                                        p: 0,
                                        m: 0,
                                        py: 0.5,
                                        ":hover": {
                                          backgroundColor:
                                            "rgba(125, 51, 235, 0.8)",
                                        },
                                      }}
                                      onClick={() => {
                                        showCv(student.email, true);
                                      }}
                                    >
                                      <RemoveRedEyeOutlinedIcon
                                        sx={{ color: "white" }}
                                      />
                                    </Button>
                                  </Tooltip>
                                </Grid>
                                <Grid item xs={3} textAlign="center">
                                  <Tooltip title="Démarer la signature de contrat">
                                    <Button
                                      sx={{
                                        p: 0,
                                        m: 0,
                                        py: 0.5,
                                        ":hover": {
                                          backgroundColor:
                                            "rgba(125, 51, 235, 0.8)",
                                        },
                                      }}
                                      onClick={() => {
                                        setReceiver({
                                          internshipOfferId: offer.id,
                                          studentEmail: student.email,
                                        });
                                        dialogDispatch({
                                          type: "OPEN",
                                          dialogName:
                                            "signContractMonitorDialog",
                                        });
                                      }}
                                    >
                                      <HowToRegIcon sx={{ color: "white" }} />
                                    </Button>
                                  </Tooltip>
                                </Grid>
                              </Grid>
                            </Grid>
                          </Grid>
                        </List>
                      </Paper>
                    </motion.div>
                  </Grid>
                );
              })}
            </React.Fragment>
          );
        })}
      <EmailSender receiver={receiver} />
      <CreateContractMonitorDialog
        pdfUrl={`http://localhost:8080/contract`}
        params={{
          internshipOfferId: receiver.internshipOfferId,
          studentEmail: receiver.studentEmail,
        }}
      />
      <CVDialog cvUrl={url} setUrl={setUrl} />
    </Grid>
  );
};

export default ListStudentApplying;
