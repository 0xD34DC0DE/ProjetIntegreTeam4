import {
  Accordion,
  AccordionActions,
  AccordionDetails,
  AccordionSummary,
  Grid,
  Button,
  Typography,
} from "@mui/material";
import React from "react";
import { fields } from "./StudentEvaluationFields";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5}>
      {fields.map((section, key) => {
        return (
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
            <Accordion>
              <AccordionSummary>
                <Typography
                  variant="subtitle2"
                  sx={{ fontSize: "1.25em" }}
                  mx="auto"
                >
                  {section.title}
                </Typography>
              </AccordionSummary>

              {section.tasks.map((task, key2) => {
                return (
                  <AccordionDetails>
                    <Typography variant="body2">{task}</Typography>
                  </AccordionDetails>
                );
              })}
            </Accordion>
          </Grid>
        );
      })}
    </Grid>
  );
};

export default StudentEvaluationForm;
