import { Grid, Typography } from "@mui/material";
import React, { useContext, useRef, useState } from "react";
import CompanyAppreciationDropdown from "./CompanyAppreciationDropdown";
import CompanyInterestDropdown from "./CompanyInterestDropdown";
import StudentContactDetailsDropdown from "./StudentContactDetailsDropdown";
import StudentEvaluationDropdown from "./StudentEvaluationDropdown";
import { endEvaluation } from "../EvaluationFields";
import SubmitEvaluationButton from "../SubmitEvaluationButton";
import { motion } from "framer-motion";
import { UserInfoContext } from "../../../stores/UserInfoStore";
import axios from "axios";
import CheckCircleOutlineOutlinedIcon from "@mui/icons-material/CheckCircleOutlineOutlined";
import CancelOutlinedIcon from "@mui/icons-material/CancelOutlined";

const StudentEvaluationForm = ({ visible }) => {
  // TODO: Find a better way then useRef
  const evaluationForm = useRef({
    text: {},
    categorical: {},
    rating: {},
    expectation: {},
  });
  const [evaluationSent, setEvaluationSent] = useState(false);
  const [userInfo] = useContext(UserInfoContext);
  const [errorMessage, setErrorMessage] = useState("");

  const studentContactDetailsRef = useRef(null);
  const companyAppreciationRef = useRef(null);
  const companyInterestRef = useRef(null);
  const evaluationRefs = [
    useRef(null),
    useRef(null),
    useRef(null),
    useRef(null),
  ];

  const handleSubmit = async () => {
    await studentContactDetailsRef.current.getForm();
    await companyAppreciationRef.current.getForm();
    await companyInterestRef.current.getForm();
    await evaluationRefs.forEach((ref) => ref.current.getForm());

    await axios({
      method: "POST",
      url: "http://localhost:8080/evaluation",
      data: evaluationForm.current,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    }).catch((error) => {
      setErrorMessage("Une erreur est survenue, veuillez réessayer.");
      console.error(error);
    });

    setEvaluationSent(true);
    resetForm();
  };

  const resetForm = () => {
    setTimeout(() => {
      setEvaluationSent(false);
      setErrorMessage("");
      evaluationForm.current = {
        text: {},
        categorical: {},
        rating: {},
        expectation: {},
      };
    }, 5000);
  };

  const mergeForms = (form) => {
    evaluationForm.current = {
      categorical: {
        ...evaluationForm.current.categorical,
        ...form.categorical,
      },
      text: { ...evaluationForm.current.text, ...form.text },
      rating: { ...evaluationForm.current.rating, ...form.rating },
      expectation: {
        ...evaluationForm.current.expectation,
        ...form.expectation,
      },
    };
  };

  const dropdowns = [
    <StudentContactDetailsDropdown
      mergeForms={mergeForms}
      ref={studentContactDetailsRef}
    />,
    ...endEvaluation.map((section, key) => {
      return (
        <StudentEvaluationDropdown
          mergeForms={mergeForms}
          section={section}
          ref={evaluationRefs[key]}
          key={key}
        />
      );
    }),
    <CompanyAppreciationDropdown
      mergeForms={mergeForms}
      ref={companyAppreciationRef}
    />,
    <CompanyInterestDropdown
      mergeForms={mergeForms}
      ref={companyInterestRef}
    />,
  ];

  return (
    <>
      {evaluationSent && visible ? (
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

            <SubmitEvaluationButton onClick={handleSubmit} delay={2} />
          </Grid>
        )
      )}
    </>
  );
};

export default StudentEvaluationForm;
