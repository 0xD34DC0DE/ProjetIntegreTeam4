import React from "react";
import { Grid, Typography } from "@mui/material";
import CompanyIdentificationDropdown from "./CompanyIdentificationDropdown";
import StudentIdentificationDropdown from "./StudentIdentificationDropdown";
import StudentMidEvaluationDropdown from "./StudentMidEvaluationDropdown";
import CompanyObservationDropdown from "./CompanyObservationDropdown";

const StudentEvaluationMidForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <Typography
          variant="subtitle2"
          sx={{ fontSize: "2.5em", color: "white" }}
          textAlign="center"
        >
          Évaluation du milieu de stage
        </Typography>
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <CompanyIdentificationDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentIdentificationDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentMidEvaluationDropdown />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <CompanyObservationDropdown />
      </Grid>
    </Grid>
  );
};

export default StudentEvaluationMidForm;
