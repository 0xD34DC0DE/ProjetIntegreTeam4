import React, { useContext, useRef, useState } from "react";
import { Grid, Typography } from "@mui/material";
import CompanyIdentificationDropdown from "./CompanyIdentificationDropdown";
import StudentIdentificationDropdown from "./StudentIdentificationDropdown";
import StudentMidEvaluationDropdown from "./StudentMidEvaluationDropdown";
import CompanyObservationDropdown from "./CompanyObservationDropdown";
import { motion } from "framer-motion";
import SubmitEvaluationButton from "../SubmitEvaluationButton";
import CheckCircleOutlineOutlinedIcon from "@mui/icons-material/CheckCircleOutlineOutlined";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";
import { UserInfoContext } from "../../../stores/UserInfoStore";

import axios from "axios";

const StudentEvaluationMidForm = ({ visible }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [errorMessage, setErrorMessage] = useState("");
  const [midEvaluationSent, setMidEvaluationSent] = useState(false);

  const midEvaluationForm = useRef({
    text: {},
    categorical: {},
    rating: {},
    expectation: {},
  });

  const companyIdentificationRef = useRef(null);
  const studentIdentificationRef = useRef(null);
  const studentMidEvaluationRef = useRef(null);
  const companyObservationRef = useRef(null);

  const mergeForms = (form) => {
    midEvaluationForm.current = {
      categorical: {
        ...midEvaluationForm.current.categorical,
        ...form.categorical,
      },
      text: { ...midEvaluationForm.current.text, ...form.text },
      rating: { ...midEvaluationForm.current.rating, ...form.rating },
      expectation: {
        ...midEvaluationForm.current.expectation,
        ...form.expectation,
      },
    };
  };

  const dropdowns = [
    <CompanyIdentificationDropdown
      ref={companyIdentificationRef}
      mergeForms={mergeForms}
    />,
    <StudentIdentificationDropdown
      ref={studentIdentificationRef}
      mergeForms={mergeForms}
    />,
    <StudentMidEvaluationDropdown
      ref={studentMidEvaluationRef}
      mergeForms={mergeForms}
    />,
    <CompanyObservationDropdown
      ref={companyObservationRef}
      mergeForms={mergeForms}
    />,
  ];

  const handleSubmit = async () => {
    await companyIdentificationRef.current.getForm();
    await studentIdentificationRef.current.getForm();
    await studentMidEvaluationRef.current.getForm();
    await companyObservationRef.current.getForm();

    console.log(midEvaluationForm.current);

    await axios({
      method: "POST",
      url: "http://localhost:8080/evaluation",
      data: midEvaluationForm.current,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    }).catch((error) => {
      setErrorMessage("Une erreur est survenue, veuillez réessayer.");
      console.error(error);
    });

    setMidEvaluationSent(true);
    resetForm();
  };

  const resetForm = () => {
    setTimeout(() => {
      setMidEvaluationSent(false);
      setErrorMessage("");
      midEvaluationForm.current = {
        text: {},
        categorical: {},
        rating: {},
        expectation: {},
      };
    }, 5000);
  };

  return (
    <>
      {midEvaluationSent && visible ? (
        <Grid container px={5} pb={3} justifyContent="center">
          <motion.div
            animate={{ opacity: [0, 1] }}
            transition={{
              duration: 0.5,
              delay: 0.5,
            }}
          >
            <Grid item textAlign="center">
              <Typography variant="h4" color="white" mt={5}>
                {errorMessage
                  ? errorMessage
                  : "L'évaluation du stagiaire a été envoyée avec succès!"}
                {errorMessage ? (
                  <CancelOutlinedIcon
                    fontSize="large"
                    sx={{
                      color: "rgba(255, 100, 100, 0.5)",
                      pl: 5,
                    }}
                  />
                ) : (
                  <CheckCircleOutlineOutlinedIcon
                    fontSize="large"
                    sx={{
                      color: "rgba(100, 255, 100, 0.5)",
                      pl: 5,
                    }}
                  />
                )}
              </Typography>
            </Grid>
          </motion.div>
        </Grid>
      ) : (
        visible && (
          <Grid container px={5} pb={3}>
            {dropdowns.map((dropdown, key) => {
              return (
                <Grid
                  item
                  xl={12}
                  lg={12}
                  md={12}
                  sm={12}
                  xs={12}
                  mt={5}
                  key={key}
                >
                  <motion.div
                    animate={{ opacity: [0, 1] }}
                    transition={{
                      duration: 0.2,
                      delay: (key + 1) * 0.2,
                    }}
                  >
                    {dropdown}
                  </motion.div>
                </Grid>
              );
            })}
            <SubmitEvaluationButton onClick={handleSubmit} delay={1} />
          </Grid>
        )
      )}
    </>
  );
};

export default StudentEvaluationMidForm;
