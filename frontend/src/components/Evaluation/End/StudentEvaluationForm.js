import { Grid } from "@mui/material";
import React from "react";
import CompanyAppreciationDropdown from "./CompanyAppreciationDropdown";
import CompanyInterestDropdown from "./CompanyInterestDropdown";
import StudentContactDetailsDropdown from "./StudentContactDetailsDropdown";
import StudentEvaluationDropdown from "./StudentEvaluationDropdown";
import { endEvaluation } from "../EvaluationFields";
import SubmitEvaluationButton from "../SubmitEvaluationButton";
import { motion } from "framer-motion";

const dropdowns = [
  ...endEvaluation.map((section, key) => {
    return <StudentEvaluationDropdown section={section} key={key} />;
  }),
  <StudentContactDetailsDropdown />,
  <CompanyAppreciationDropdown />,
  <CompanyInterestDropdown />,
];

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5} pb={3}>
      {dropdowns.map((dropdown, key) => {
        return (
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5} key={key}>
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

      <SubmitEvaluationButton />
    </Grid>
  );
};

export default StudentEvaluationForm;
