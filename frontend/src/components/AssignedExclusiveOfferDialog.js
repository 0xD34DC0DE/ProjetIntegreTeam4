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
  const [userUpdatedCount, setUserUpdatedCount] = useState(0);

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
      setUserUpdatedCount(0);
    }
  };

  const clear = () => {
    setSelectedUsersEmail([]);
    setSearchText("");
  };

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
                  ":hover": { backgroundColor: "rgba(125, 51, 235, 0.8)" },
                }}
                onClick={() => {
                  addExclusiveOfferToStudents(selectedUsersEmail);
                }}
              >
                Assigner
              </Button>
            </Box>
            <Typography variant="h4">
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
                      style={{ width: "100%" }}
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
                    animate={{ opacity: [0, 1] }}
                    transition={{
                      duration: 0.5,
                      delay: (key + 1) * 0.2,
                    }}
                  >
                    <ListItem key={key} sx={{ width: "100%", mr: 5 }}>
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
                            <Avatar></Avatar>
                          </Grid>
                          <Grid item xs={11} sx={{ px: 2 }}>
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
        open={userUpdatedCount > 0}
        autoHideDuration={2000}
        onClose={handleSnackBarClose}
      >
        <Alert severity="success">
          {userUpdatedCount} étudiants ont été ajoutés à l'offre{" "}
          {offer.companyName} | {offer.title}
        </Alert>
      </Snackbar>
    </>
  );
};

export default AssignedExclusiveOfferDialog;
