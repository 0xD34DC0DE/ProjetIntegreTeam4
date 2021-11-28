import React, { useState, useEffect, useContext } from "react";
import axios from "axios";
import { Card, Typography, Grid, Container } from "@mui/material";
import StarBorderPurple500Icon from "@mui/icons-material/StarBorderPurple500";
import { UserInfoContext } from "../../stores/UserInfoStore";
import TouchAppIcon from "@mui/icons-material/TouchApp";
import { motion } from "framer-motion";
import PeopleIcon from "@mui/icons-material/People";
import SelectInterviewDate from "./SelectInterviewDate";
import SelectStudentState from "./SelectStudentState";
import IconImage from "./IconImage";
import BasicInfo from "./BasicInfo";

import React from 'react'

const SupervisorDashBoard = () => {

    const [userInfo] = useContext(UserInfoContext);

    useEffect(() => {
        const getProfile = () => {
          axios({
            method: "GET",
            url: "http://localhost:8080/supervisor/getProfile",
            headers: {
              Authorization: userInfo.jwt,
            },
            responseType: "json",
          })
            .then((response) => {
              setProfile(response.data);
              console.log(response.data);
              setIsDisabled(
                response.data.studentState !== listState[3] ||
                  !response.data.hasValidCv
              );
            })
            .catch((error) => {
              console.error(error);
            });
        };
    
        getProfile();
      }, []);

    return (
        <>
            
        </>
    )
}

export default SupervisorDashBoard;
