import { Grid } from "@mui/material";
import React from "react";
import StudentAppreciationForm from "./StudentAppreciationForm";
import StudentContactDetailsForm from "./StudentContactDetailsForm";
import { sections } from "./StudentEvaluationFields";
import StudentGenericForm from "./StudentGenericForm";
import StudentInterestForm from "./StudentInterestForm";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={1}>
        <StudentContactDetailsForm />
      </Grid>
      {sections.map((section, key) => {
        return <StudentGenericForm section={section} key={key} />;
      })}
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentAppreciationForm />
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <StudentInterestForm />
      </Grid>
    </Grid>
  );
};

export default StudentEvaluationForm;
