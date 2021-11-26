import React, { useState } from "react";
import axios from "axios";
import {
  Typography,
  Select,
  MenuItem,
} from "@mui/material";
import { motion } from "framer-motion";
import PublishedWithChangesIcon from "@mui/icons-material/PublishedWithChanges";

const SelectStudentState = ({
  profile,
  setProfile,
  isDisabled,
  setIsDisabled,
  jwt,
  fadeIn,
  listState,
  listStateFrench,
}) => {
  const [isStatusUpdated, setIsStatusUpdated] = useState(false);

  const showAndHideStatusUpdateSuccessMsg = () => {
    setTimeout(() => {
      setIsStatusUpdated(false);
    }, 3000);
  };

  const updateStudentStatus = () => {
    axios({
      method: "PATCH",
      url: "http://localhost:8080/student/updateStudentState",
      headers: {
        Authorization: jwt,
      },
      responseType: "json",
    })
      .then(() => {
        showAndHideStatusUpdateSuccessMsg();
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleChange = ($event) => {
    setProfile({ ...profile, studentState: $event.target.value });
    updateStudentStatus();
    setIsDisabled(true);
  };

  return (
    <>
      <Select
        sx={{
          margin: "auto",
          border: "1px white",
          display: "flex",
          justifyContent: "center",
          boxShadow: 3,
          textAlign: "center",
          m: 1,
        }}
        labelId="demo-simple-select-label"
        id="demo-simple-select"
        value={profile.studentState}
        label="Age"
        onChange={handleChange}
        disabled={isDisabled}
      >
        {listState.map((value, key) => (
          <MenuItem
            key={key}
            value={value}
            disabled={value !== listState[2]}
            sx={{ color: "white" }}
          >
            {listStateFrench[listState.indexOf(value)]}
          </MenuItem>
        ))}
      </Select>
      {isStatusUpdated ? (
        <motion.div variants={fadeIn} initial="hidden" animate="show">
          <Typography
            variant="subtitle1"
            sx={{ color: "green", textAlign: "center", m: 1 }}
          >
            {"STATUT MODIFIÉE"}
            <PublishedWithChangesIcon />
          </Typography>
        </motion.div>
      ) : null}
    </>
  );
};

export default SelectStudentState;
