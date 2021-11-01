import {
  Dialog,
  DialogContent,
  DialogContentText,
  Typography,
  Divider,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

const StudentInternshipDetailsDialog = ({
  openedStudentEmail,
  toggleDialog,
}) => {
  const [internship, setInternship] = useState(null);
  const [userInfo] = useContext(UserInfoContext);
  const internshipLabels = [
    "Courriel du moniteur",
    "Courriel de l'étudiant",
    "Courriel du gestionnaire de stage",
    "Date de début du stage",
    "Date de fin du stage",
  ];

  useEffect(() => {
    const getInternshipByEmail = async () => {
      var response = await axios({
        method: "GET",
        url: `http://localhost:8080/internship/${openedStudentEmail}`,
        headers: {
          Authorization: userInfo.jwt,
        },
      });
      setInternship(response.data);
    };

    if (openedStudentEmail) {
      getInternshipByEmail();
    }
  }, [openedStudentEmail]);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      toggleDialog("internshipDetailsDialog", false);
  };

  return (
    <Dialog open={openedStudentEmail != ""} onClose={handleClose}>
      <DialogContent>
        {internship ? (
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
          })
        ) : (
          <DialogContentText sx={{ m: 2, textAlign: "center" }}>
            Cet étudiant n'a pas de stage d'attribué
          </DialogContentText>
        )}
      </DialogContent>
    </Dialog>
  );
};

export default StudentInternshipDetailsDialog;
