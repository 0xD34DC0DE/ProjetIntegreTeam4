import { Search } from "@mui/icons-material";
import {
  Alert,
  Avatar,
  Button,
  Dialog,
  DialogContent,
  FormControl,
  Grid,
  List,
  ListItem,
  ListItemButton,
  ListItemText,
  Snackbar,
  Tooltip,
  Typography,
} from "@mui/material";
import { Box } from "@mui/system";
import axios from "axios";
import { motion } from "framer-motion";
import React, { useContext, useEffect, useState } from "react";
import { DialogContext } from "../stores/DialogStore";
import { UserInfoContext } from "../stores/UserInfoStore";

const AssignedExclusiveOfferDialog = ({ offer }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [searchText, setSearchText] = useState("");
  const [users, setUsers] = useState([]);
  const [selectedUsersEmail, setSelectedUsersEmail] = useState([]);
  const [userUpdatedCount, setUserUpdatedCount] = useState(-1);
  const [usersProfileImage, setUsersProfileImage] = useState([]);

  useEffect(() => {
    const getAllStudentNotContainingExclusiveOffer = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/student/getAllStudentNotContainingExclusiveOffer/${offer.id}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setUsers(response.data);
    };
    getAllStudentNotContainingExclusiveOffer();
  }, []);

  const addExclusiveOfferToStudents = (emails) => {
    axios({
      method: "PATCH",
      url: `http://localhost:8080/internshipOffer/addExclusiveOfferToStudents/${offer.id}`,
      data: emails,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then(() => {
        setUserUpdatedCount(selectedUsersEmail.length);
        dialogDispatch({
          type: "CLOSE",
          dialogName: "exclusiveInternshipOfferDialog",
        });
        clear();
      })
      .catch((err) => {
        console.log(err);
      });
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "exclusiveInternshipOfferDialog",
      });
    }
    clear();
  };

  const handleSnackBarClose = (_, reason) => {
    if (reason === "timeout") {
      setUserUpdatedCount(-1);
    }
  };

  const clear = () => {
    setSelectedUsersEmail([]);
    setSearchText("");
  };

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
        setUsersProfileImage(images);
      })
      .catch((error) => console.error(error));
  };

  useEffect(() => {
    if (users.length === 0) return;
    const uploadersEmails = users.map((user) => {
      return user.email;
    });
    fetchProfileImages(uploadersEmails);
  }, [users]);

  return (
    <>
      <Dialog
        maxWidth={false}
        open={dialog.exclusiveOfferDialog.visible}
        sx={{
          minWidth: "50vw",
        }}
        onClose={handleClose}
      >
        <DialogContent
          sx={{
            minWidth: "50vw",
            minHeight: "50vh",
            maxHeight: "50vh",
          }}
        >
          <Box
            sx={{
              textAlign: "center",
            }}
          >
            <Box sx={{ float: "right" }}>
              <Button
                variant="contained"
                sx={{
                  backgroundColor: "rgba(125, 51, 235, 0.8)",
                  color: "rgba(255, 255, 255, 0.8) !important",
                  ":hover": {
                    backgroundColor: "rgba(95, 21, 195, 0.8)",
                  },
                }}
                onClick={() => {
                  addExclusiveOfferToStudents(selectedUsersEmail);
                }}
              >
                Assigner
              </Button>
            </Box>
            <Typography variant="subtitle2" sx={{ fontSize: "2.3em" }}>
              {offer.companyName} | {offer.title}
            </Typography>

            <FormControl sx={{ width: "100%", mt: 2 }}>
              <Grid container>
                <Grid item xs={1}>
                  <Search />
                </Grid>
                <Grid item xs={11}>
                  <Tooltip title="Chercher un élève par son nom ou son prénom">
                    <input
                      style={{
                        width: "100%",
                        color: "white",
                        backgroundColor: "rgba(50, 50, 50, 0.)",
                        border: "1px solid rgba(255, 255, 255, 0.1)",
                        fontSize: "0.9em",
                        height: "30px",
                        "::focus": {
                          boxShadow: "0px 0px 15px 2px red !important",
                        },
                        ":placeholder": {
                          color: "white !important",
                        },
                      }}
                      type="search"
                      onChange={(e) => {
                        setSearchText(e.target.value);
                      }}
                      placeholder="Ex: Travis Scott"
                    />
                  </Tooltip>
                </Grid>
              </Grid>
            </FormControl>
          </Box>
          <List sx={{ mt: 2, maxWidth: "100%" }}>
            {users
              .filter(
                (user) =>
                  user.firstName
                    .toLowerCase()
                    .includes(searchText.toLowerCase()) ||
                  user.lastName.toLowerCase().includes(searchText.toLowerCase())
              )
              .map((user, key) => {
                return (
                  <motion.div
                    key={key}
                    animate={{ opacity: [0, 1] }}
                    transition={{
                      duration: 0.5,
                      delay: (key + 1) * 0.2,
                    }}
                  >
                    <ListItem sx={{ width: "100%", mr: 5 }}>
                      <ListItemButton
                        onClick={() => {
                          if (!selectedUsersEmail.includes(user.email))
                            setSelectedUsersEmail([
                              ...selectedUsersEmail,
                              user.email,
                            ]);
                          else {
                            var tmpSelectedUsersEmail = selectedUsersEmail;
                            tmpSelectedUsersEmail.splice(
                              tmpSelectedUsersEmail.indexOf(user.email),
                              1
                            );
                            setSelectedUsersEmail([...tmpSelectedUsersEmail]);
                          }
                        }}
                        sx={{
                          boxShadow: selectedUsersEmail.includes(user.email)
                            ? "0 0 8px rgba(125, 51, 235, 0.8)"
                            : "0 0 8px white",
                        }}
                      >
                        <Grid container>
                          <Grid item xs={1}>
                            <Avatar
                              sx={{ width: "50px", height: "50px" }}
                              src={
                                usersProfileImage
                                  .filter(
                                    (data) => data.uploaderEmail === user.email
                                  )
                                  .map((data) => {
                                    return data.image;
                                  })[0]
                              }
                            ></Avatar>
                          </Grid>
                          <Grid item xs={11} sx={{ px: 2 }} alignSelf="center">
                            <ListItemText>
                              {user.firstName} {user.lastName}
                            </ListItemText>
                          </Grid>
                        </Grid>
                      </ListItemButton>
                    </ListItem>
                  </motion.div>
                );
              })}
          </List>
        </DialogContent>
      </Dialog>
      <Snackbar
        open={userUpdatedCount >= 0}
        autoHideDuration={2000}
        onClose={handleSnackBarClose}
      >
        <Alert severity={userUpdatedCount > 0 ? "success" : "warning"}>
          {userUpdatedCount} étudiants ont été ajoutés à l'offre{" "}
          {offer.companyName} | {offer.title}
        </Alert>
      </Snackbar>
    </>
  );
};

export default AssignedExclusiveOfferDialog;
