import React, { useState, useEffect, useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { Select, MenuItem, Container } from "@mui/material";
import axios from "axios";

const SemesterSelect = ({ updateSemesterFullName }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [selectedSemester, setSelectedSemester] = useState("");
  const [semesters, setSemesters] = useState({
    currentSemesterFullName: "",
    semestersFullNames: [],
  });

  useEffect(() => {
    const getAllSemesterFullName = () => {
      axios({
        method: "GET",
        url: "http://localhost:8080/semester/getAllSemesterFullName",
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then((response) => {
          setSemesters(response.data);
          /*

          */
          setSelectedSemester(response.data.currentSemesterFullName);
          updateSemesterFullName(response.data.currentSemesterFullName);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getAllSemesterFullName();
    console.log(selectedSemester);
  }, []);

  const handleChange = ($event) => {
    setSelectedSemester($event.target.value);
    updateSemesterFullName(
      formatSemesterFullNameForBackEnd($event.target.value)
    );
  };

  const formatSemesterFullNameForUi = (semesterFullName) => {
    return semesterFullName.split("-").join(" ");
  };

  const formatSemesterFullNameForBackEnd = (semesterFullName) => {
    return semesterFullName.split(" ").join("-");
  };

  return (
    <>
      <Container>
        <Select
          sx={{
            border: "1px white",
            display: "flex",
            justifyContent: "center",
            boxShadow: 3,
            textAlign: "center",
            display: "flex",
            m: 4,
          }}
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          value={formatSemesterFullNameForUi(selectedSemester)}
          label="Session"
          onChange={handleChange}
        >
          {semesters.semestersFullNames.map((value, key) => (
            <MenuItem
              key={key}
              value={formatSemesterFullNameForUi(value)}
              sx={{ color: "white" }}
            >
              {formatSemesterFullNameForUi(value)}
            </MenuItem>
          ))}
        </Select>
      </Container>
    </>
  );
};

export default SemesterSelect;
