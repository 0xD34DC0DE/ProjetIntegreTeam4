import React, { useState, useContext } from "react";
import axios from "axios";
import { Typography, TextField } from "@mui/material";
import { motion } from "framer-motion";
import { UserInfoContext } from "../../stores/UserInfoStore";
import PublishedWithChangesIcon from "@mui/icons-material/PublishedWithChanges";
import WarningIcon from "@mui/icons-material/Warning";
import ScheduleIcon from "@mui/icons-material/Schedule";

const SelectInterviewDate = ({
  hasInternship,
  profile,
  setProfile,
  fadeIn,
}) => {
  const currentDate = new Date();
  const [isDateValid, setIsDateValid] = useState(true);
  const [userInfo] = useContext(UserInfoContext);
  const [isInterviewDateUpdated, setIsInterviewDateUpdated] = useState(false);

  const showAndHideInterviewUpdateSuccessMsg = () => {
    setIsInterviewDateUpdated(true);

    setTimeout(() => {
      setIsInterviewDateUpdated(false);
    }, 3000);
  };

  const showAndHideInterviewDateErrorMsg = () => {
    setIsDateValid(false);
    setTimeout(() => {
      setIsDateValid(true);
    }, 3000);
  };

  const updateInterviewDate = (date) => {
    axios({
      method: "PATCH",
      url: "http://localhost:8080/student/updateInterviewDate/" + date,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then(() => {
        showAndHideInterviewUpdateSuccessMsg();
      })
      .catch((error) => {
        showAndHideInterviewDateErrorMsg();
        console.error(error);
      });
  };

  const handleChangeDate = ($event) => {
    const value = $event.target.value;
    const dateValues = value.split("-");

    if (
      (currentDate.getFullYear() === parseInt(dateValues[0]) &&
        parseInt(dateValues[1]) > currentDate.getMonth() + 1) ||
      (parseInt(dateValues[2], 10) >= currentDate.getDate() &&
        parseInt(dateValues[1]) === currentDate.getMonth() + 1 &&
        !hasInternship())
    ) {
      setProfile({ ...profile, closestInterviewDate: value });
      if (profile.studentState === "NO_INTERVIEW")
        setProfile({ ...profile, studentState: "WAITING_INTERVIEW" });
      updateInterviewDate(value);
      setIsDateValid(true);
    } else {
      showAndHideInterviewDateErrorMsg();
    }
  };

  return (
    <>
      {!hasInternship() && profile.hasValidCv ? (
        <TextField
          sx={{
            border: "1px white",
            boxShadow: 4,
            borderRadius: 2,
            display: "flex",
            m: 2,
          }}
          InputLabelProps={{
            shrink: true,
          }}
          label="Date d'entrevue la plus proche"
          value={profile.closestInterviewDate}
          onChange={handleChangeDate}
          type={"date"}
        />
      ) : (
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Typography
            variant="subtitle1"
            sx={{
              color: "gray",
              textAlign: "center",
              alignItems: "center",
              m: 2,
            }}
          >
            L'ajout d'entrevue n'est pas disponible pour l'instant
            <ScheduleIcon />
          </Typography>
        </motion.div>
      )}

      {isInterviewDateUpdated ? (
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Typography
            variant="subtitle1"
            sx={{ color: "green", textAlign: "center" }}
          >
            DATE D'ENTREVUE AJOUTÉE
            <PublishedWithChangesIcon />
          </Typography>
        </motion.div>
      ) : null}

      {!isDateValid ? (
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Typography
            variant="subtitle1"
            sx={{ color: "red", textAlign: "center" }}
          >
            DATE D'ENTREVUE INVALIDE
            <WarningIcon />
          </Typography>
        </motion.div>
      ) : null}
    </>
  );
};

export default SelectInterviewDate;
