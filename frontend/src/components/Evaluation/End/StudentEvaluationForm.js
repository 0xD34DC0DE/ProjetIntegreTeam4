import { Grid, Typography } from "@mui/material";
import React from "react";
import CompanyAppreciationDropdown from "./CompanyAppreciationDropdown";
import CompanyInterestDropdown from "./CompanyInterestDropdown";
import StudentContactDetailsDropdown from "./StudentContactDetailsDropdown";
import StudentEvaluationDropdown from "./StudentEvaluationDropdown";
import { endEvaluation } from "../EvaluationFields";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentContactDetailsDropdown />
      </Grid>
      {endEvaluation.map((section, key) => {
        return <StudentEvaluationDropdown section={section} key={key} mt={5} />;
      })}
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <CompanyAppreciationDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <CompanyInterestDropdown />
      </Grid>
    </Grid>
  );
};

export default StudentEvaluationForm;
