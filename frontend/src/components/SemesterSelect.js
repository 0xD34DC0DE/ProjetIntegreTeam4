import React, { useState, useEffect, useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { Select, MenuItem } from "@mui/material";
import axios from "axios";

const SemesterSelect = ({ setSelectedSemester }) => {
  const [userInfo] = useContext(UserInfoContext);
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
          console.log(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    };

    getAllSemesterFullName();
  }, []);

  const formatSemesterFullName = (data) => {
    setSemesters(data);
    semesters.semestersFullNames.map((fullName) =>
      fullName.split("-").join("")
    );
    semesters.currentSemesterFullName = semesters.currentSemesterFullName
      .split("-")
      .join("");
    console.log(semesters);
  };

  const handleChange = ($event) => {
    setSelectedSemester($event.target.value);
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
        value={"FALL 2021"}
        label="Session"
        onChange={handleChange}
      >
        {semesters.semestersFullNames.map((value, key) => (
            <MenuItem key={key} value={value.split("-").join(" ")} sx={{ color: "white" }}>
            {value.split("-").join(" ")}
          </MenuItem>
        ))}
      </Select>
    </>
  );
};

export default SemesterSelect;
