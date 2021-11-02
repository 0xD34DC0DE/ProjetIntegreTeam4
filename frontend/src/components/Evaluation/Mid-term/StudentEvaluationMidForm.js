import React from "react";
import { Grid } from "@mui/material";
import CompanyInterestDropdown from "../End/CompanyInterestDropdown";
import StudentIdentificationDropdown from "./StudentIdentificationDropdown";
import StudentMidEvaluationDropdown from "./StudentMidEvaluationDropdown";

const StudentEvaluationMidForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <CompanyInterestDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentIdentificationDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentMidEvaluationDropdown />
      </Grid>
    </Grid>
  );
};

export default StudentEvaluationMidForm;
