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
import { DialogContext } from "../stores/DialogStore";

const StudentInternshipDetailsDialog = ({
  resetOpenedStudentEmail,
  openedStudentEmail,
}) => {
  const [internship, setInternship] = useState(null);
  const [userInfo] = useContext(UserInfoContext);
  const [exists, setExists] = useState(null);
  const internshipLabels = [
    "Courriel du moniteur",
    "Courriel de l'étudiant",
    "Courriel du gestionnaire de stage",
    "Date de début du stage",
    "Date de fin du stage",
  ];
  const [dialog, dialogDispatch] = useContext(DialogContext);

  useEffect(() => {
    const existsByStudentEmail = async () => {
      var exists = await axios({
        method: "GET",
        url: `http://localhost:8080/internship/exists/${openedStudentEmail}`,
        headers: {
          Authorization: userInfo.jwt,
        },
      });
      console.log(openedStudentEmail);
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
      setExists(null);
      dialogDispatch({
        type: "CLOSE",
        dialogName: "internshipDetailsDialog",
      });
    }
  };

  return (
    <>
      {exists ? (
        <Dialog
          open={dialog.internshipDetailsDialog.visible}
          onClose={handleClose}
        >
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
        <Snackbar
          open={exists == false}
          autoHideDuration={2000}
          onClose={handleClose}
        >
          <Alert severity="warning" sx={{ width: "100%" }}>
            {openedStudentEmail} n'a pas de stage d'attribué
          </Alert>
        </Snackbar>
      )}
    </>
  );
};

export default StudentInternshipDetailsDialog;
