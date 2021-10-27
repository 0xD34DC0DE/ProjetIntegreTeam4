import {
  Accordion,
  AccordionActions,
  AccordionDetails,
  AccordionSummary,
  Grid,
  Button,
  Typography,
  Radio,
} from "@mui/material";
import React from "react";
import { fields, ratings } from "./StudentEvaluationFields";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5}>
      {fields.map((section, key) => {
        return (
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
            <Accordion>
              <AccordionSummary>
                <Grid container flexDirection="row" textAlign="center">
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Typography variant="subtitle2" sx={{ fontSize: "1.5em" }}>
                      {section.title}
                    </Typography>
                  </Grid>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Typography variant="caption">
                      {section.description}
                    </Typography>
                  </Grid>
                </Grid>
              </AccordionSummary>
              <AccordionDetails
                sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)" }}
              >
                <Grid container>
                  <Grid item xl={4} lg={4} md={4} sm={4} xs={4} mt={2}>
                    <Grid container flexDirection="column">
                      {section.tasks.map((task, key2) => {
                        return (
                          <Grid
                            item
                            xl={12}
                            lg={12}
                            md={12}
                            sm={12}
                            xs={12}
                            mt={3}
                          >
                            <Typography variant="caption">
                              {key2 + 1}) {task}
                            </Typography>
                          </Grid>
                        );
                      })}
                    </Grid>
                  </Grid>
                  <Grid
                    item
                    xl={8}
                    lg={8}
                    md={8}
                    sm={8}
                    xs={8}
                    sx={{ textAlign: "center" }}
                  >
                    <Grid container>
                      {ratings.map((rating, key3) => {
                        return (
                          <Grid
                            item
                            xl={2.4}
                            lg={2.4}
                            md={2.4}
                            sm={2.4}
                            xs={2.4}
                          >
                            <Typography
                              variant="caption"
                              sx={{
                                color: "white",
                                width: "100%",
                              }}
                            >
                              {rating.label}
                            </Typography>
                          </Grid>
                        );
                      })}

                      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={2}>
                        <Grid container flexDirection="row">
                          {ratings.map((task, key3) => {
                            return section.tasks.map((_, key4) => {
                              return (
                                <Grid
                                  item
                                  xl={2.4}
                                  lg={2.4}
                                  md={2.4}
                                  sm={2.4}
                                  xs={2.4}
                                  mb={0.25}
                                >
                                  <Radio value={key3} name={"radio" + key3} />
                                </Grid>
                              );
                            });
                          })}
                        </Grid>
                      </Grid>
                    </Grid>
                  </Grid>
                </Grid>
              </AccordionDetails>
            </Accordion>
          </Grid>
        );
      })}
    </Grid>
  );
};

export default StudentEvaluationForm;
