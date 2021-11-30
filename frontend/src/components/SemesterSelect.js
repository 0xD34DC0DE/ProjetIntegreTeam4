import React, { useState, useEffect, useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { Select, MenuItem, Container } from "@mui/material";
import axios from "axios";

const SemesterSelect = ({ updateSemesterFullName }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [selectedSemester, setSelectedSemester] = useState("");
  const [semesters, setSemesters] = useState([]);

  const semesterNamesForUi = {
    FALL: "AUTOMNE",
    WINTER: "HIVER",
    SUMMER: "ÉTÉ",
  };

  const semesterNamesForBackEnd = {
    AUTOMNE: "FALL",
    HIVER: "WINTER",
    ÉTÉ: "SUMMER",
  };

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
          setSemesters(
            response.data.semestersFullNames.map((semester) =>
              formatSemesterFullNameForUi(semester)
            )
          );
          setSelectedSemester(
            formatSemesterFullNameForUi(response.data.currentSemesterFullName)
          );
          updateSemesterFullName(response.data.currentSemesterFullName);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getAllSemesterFullName();
  }, []);

  const handleChange = ($event) => {
    const value = $event.target.value;
    setSelectedSemester(value);
    updateSemesterFullName(formatSemesterFullNameForBackEnd(value));
  };

  const formatSemesterFullNameForUi = (semesterFullName) => {
    const tmp = semesterFullName.split("-");

    return semesterNamesForUi[tmp[0]] + " " + tmp[1];
  };

  const formatSemesterFullNameForBackEnd = (semesterFullName) => {
    const tmp = semesterFullName.split(" ");
    return semesterNamesForBackEnd[tmp[0]] + "-" + tmp[1];
  };

  return (
    <>
      <Container>
        <Select
          sx={{
            border: "1px white",
            justifyContent: "center",
            textAlign: "center",
            display: "flex",
            mt: 5,
            mb: 2,
            boxShadow: "15px 15px 10px 0px rgba(0, 0, 0, 0.35);",
          }}
          labelId="semesterSelectLabel"
          id="semesterSelect"
          value={selectedSemester}
          label="Session"
          onChange={handleChange}
        >
          {semesters.map((value, key) => (
            <MenuItem key={key} value={value} sx={{ color: "white" }}>
              {value}
            </MenuItem>
          ))}
        </Select>
      </Container>
    </>
  );
};

export default SemesterSelect;
