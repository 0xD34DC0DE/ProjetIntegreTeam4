import {
  Dialog,
  DialogContent,
  DialogContentText,
  Typography,
  Divider,
  Snackbar,
  Alert,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { motion } from "framer-motion";

const StudentInternshipDetailsDialog = ({
  open,
  resetOpenedStudentEmail,
  openedStudentEmail,
  toggleDialog,
}) => {
  const [internship, setInternship] = useState(null);
  const [userInfo] = useContext(UserInfoContext);
  const [exists, setExists] = useState(false);
  const internshipLabels = [
    "Courriel du moniteur",
    "Courriel de l'étudiant",
    "Courriel du gestionnaire de stage",
    "Date de début du stage",
    "Date de fin du stage",
  ];

  useEffect(() => {
    const existsByStudentEmail = async () => {
      var exists = await axios({
        method: "GET",
        url: `http://localhost:8080/internship/exists/${openedStudentEmail}`,
        headers: {
          Authorization: userInfo.jwt,
        },
      });
      setExists(exists.data);
      return exists.data;
    };

    const getInternshipByEmail = async () => {
      if ((await existsByStudentEmail()) === true) {
        var response = await axios({
          method: "GET",
          url: `http://localhost:8080/internship/${openedStudentEmail}`,
          headers: {
            Authorization: userInfo.jwt,
          },
        });
        setInternship(response.data);
      }
    };
    if (openedStudentEmail) {
      getInternshipByEmail();
    }
  }, [openedStudentEmail]);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick" || reason === "timeout") {
      resetOpenedStudentEmail();
      setInternship(null);
      toggleDialog("internshipDetailsDialog", false);
    }
    console.log("reason", reason);
  };

  return (
    <>
      {exists ? (
        <Dialog open={open} onClose={handleClose}>
          <DialogContent>
            {internship &&
              Object.keys(internship).map((identifier, key) => {
                return (
                  <Divider key={key}>
                    <Typography variant="h6" sx={{ m: 2, textAlign: "center" }}>
                      {internshipLabels[key]}
                    </Typography>
                    <DialogContentText>
                      {Object.values(internship)[key]}
                    </DialogContentText>
                  </Divider>
                );
              })}
          </DialogContent>
        </Dialog>
      ) : (
        <Snackbar open={open} autoHideDuration={2000} onClose={handleClose}>
          <Alert severity="warning" sx={{ width: "100%" }}>
            {openedStudentEmail} n'a pas de stage d'attribué
          </Alert>
        </Snackbar>
      )}
    </>
  );
};

export default StudentInternshipDetailsDialog;
