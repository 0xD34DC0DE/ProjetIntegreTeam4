import {
  Accordion,
  AccordionActions,
  AccordionDetails,
  AccordionSummary,
  Paper,
  Grid,
  Button,
  Typography,
  Radio,
} from "@mui/material";
import React from "react";
import { fields, ratings } from "./StudentEvaluationFields";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
        <Paper>
          <p>asd</p>
        </Paper>
      </Grid>
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
                sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)", pb: 1 }}
              >
                <Grid container>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Grid container flexDirection="row" textAlign="center">
                      <Grid item xl={6} lg={6} md={6} sm={6} xs={6}></Grid>
                      {ratings.map((rating, key3) => {
                        return (
                          <Grid
                            item
                            xl={1.2}
                            lg={1.2}
                            md={1.2}
                            sm={1.2}
                            xs={1.2}
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
                    </Grid>
                  </Grid>
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Grid container flexDirection="row">
                      {section.tasks.map((task, key2) => {
                        return (
                          <>
                            <Grid
                              item
                              xl={6}
                              lg={6}
                              md={6}
                              sm={6}
                              xs={6}
                              alignSelf="center"
                            >
                              <Typography variant="caption">
                                {key2 + 1}) {task}
                              </Typography>
                            </Grid>

                            {ratings.map((task, key3) => {
                              return (
                                <Grid
                                  item
                                  textAlign="center"
                                  xl={1.2}
                                  lg={1.2}
                                  md={1.2}
                                  sm={1.2}
                                  xs={1.2}
                                >
                                  <Radio
                                    value={key3}
                                    name={"radio" + key2 + "_" + key3}
                                  ></Radio>
                                </Grid>
                              );
                            })}
                          </>
                        );
                      })}
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
