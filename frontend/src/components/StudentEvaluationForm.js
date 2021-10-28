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
  TextField,
  RadioGroup,
} from "@mui/material";
import React from "react";
import {
  appreciationFields,
  fields,
  internFields,
  ratingFields,
} from "./StudentEvaluationFields";

const StudentEvaluationForm = () => {
  return (
    <Grid container px={5} pb={3}>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={3}>
        <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
          <AccordionSummary>
            <Grid container flexDirection="row" textAlign="center">
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography
                  variant="subtitle2"
                  sx={{ fontSize: "1.75em", lineHeight: "100%" }}
                >
                  Évaluation de l'étudiant
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="caption">Coordonnées des pairs</Typography>
              </Grid>
            </Grid>
          </AccordionSummary>
          <AccordionDetails sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)" }}>
            <Grid container>
              {internFields.map((field, key) => {
                return (
                  <>
                    <Grid item xl={1.5} lg={1.5} md={1.5} sm={1.5} xs={1.5}>
                      <Typography variant="caption">{field}</Typography>
                    </Grid>
                    <Grid
                      item
                      xl={10.5}
                      lg={10.5}
                      md={10.5}
                      sm={10.5}
                      xs={10.5}
                    >
                      <TextField
                        variant="standard"
                        sx={{ "& .MuiInput-input": { fontSize: "0.8em" } }}
                        fullWidth
                      ></TextField>
                    </Grid>
                  </>
                );
              })}
            </Grid>
          </AccordionDetails>
        </Accordion>
      </Grid>
      {fields.map((section, key) => {
        return (
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
            <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
              <AccordionSummary>
                <Grid container flexDirection="row" textAlign="center">
                  <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                    <Typography
                      variant="subtitle2"
                      sx={{ fontSize: "1.75em", lineHeight: "100%" }}
                    >
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
                      {ratingFields.map((rating, key3) => {
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
                            {ratingFields.map((task, key3) => {
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
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
          <AccordionSummary>
            <Grid container flexDirection="row" textAlign="center">
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography
                  variant="subtitle2"
                  sx={{ fontSize: "1.75em", lineHeight: "100%" }}
                >
                  Appréciation
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="caption">
                  Appréciation globale du stagiaire
                </Typography>
              </Grid>
            </Grid>
          </AccordionSummary>
          <AccordionDetails
            sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)", pb: 1 }}
          >
            <Grid container textAlign="center" alignItems="center">
              {appreciationFields.map((field, key) => {
                return (
                  <>
                    <Grid item xl={6} lg={6} md={6} sm={6} xs={6}>
                      <Typography variant="caption">{field} </Typography>
                    </Grid>
                    <Grid item xl={6} lg={6} md={6} sm={6} xs={6}>
                      <Radio value={key} name={"radio" + key}></Radio>
                    </Grid>
                  </>
                );
              })}
            </Grid>
            <Grid container>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography
                  variant="caption"
                  sx={{
                    display: "inline-block",
                    fontSize: "0.8em",
                    ml: 1,
                    mt: 3,
                  }}
                >
                  Précisez votre appréciation
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <TextField
                  fullWidth
                  multiline
                  variant="outlined"
                  rows={3}
                  margin="dense"
                  size="small"
                ></TextField>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography
                  variant="caption"
                  sx={{
                    display: "inline-block",
                    fontSize: "0.8em",
                    ml: 1,
                    mt: 3,
                  }}
                >
                  Cette évaluation a été discutée avec le stagiaire:
                </Typography>
                <RadioGroup row sx={{ display: "inline-block" }}>
                  <Radio value={1}></Radio>
                  <Typography variant="caption">Oui</Typography>
                  <Radio value={2}></Radio>
                  <Typography variant="caption">Non</Typography>
                </RadioGroup>
              </Grid>
            </Grid>
          </AccordionDetails>
        </Accordion>
      </Grid>
      <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={5}>
        <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
          <AccordionSummary>
            <Grid container flexDirection="row" textAlign="center">
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography
                  variant="subtitle2"
                  sx={{ fontSize: "1.75em", lineHeight: "100%" }}
                >
                  Intérêt
                </Typography>
              </Grid>
              <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                <Typography variant="caption">
                  Intérêt à la réembauche du stagiaire
                </Typography>
              </Grid>
            </Grid>
          </AccordionSummary>
          <AccordionDetails
            sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)", pb: 1 }}
          >
            <Grid container textAlign="center" flexDirection="column">
              <Grid item textAlign="center">
                <Typography>
                  L'entreprise aimerait accueillir cet élève pour son prochain
                  stage
                </Typography>
              </Grid>

              <Grid item textAlign="center">
                <RadioGroup row sx={{ display: "inline-block" }}>
                  <Typography variant="caption">Oui</Typography>
                  <Radio value={1}></Radio>
                  <Typography variant="caption">Non</Typography>
                  <Radio value={2}></Radio>
                  <Typography variant="caption">Peut-être</Typography>
                  <Radio value={3}></Radio>
                </RadioGroup>
              </Grid>
              <Grid item textAlign="left" alignSelf="flex-start">
                <Typography
                  variant="caption"
                  sx={{ display: "inline-block", fontSize: "0.8em", ml: 1 }}
                >
                  La formation technique du stagiaire était-elle suffisante pour
                  accomplir le mandat de stage?
                </Typography>
              </Grid>
              <Grid item>
                <TextField
                  fullWidth
                  multiline
                  variant="outlined"
                  rows={3}
                  margin="dense"
                  size="small"
                ></TextField>
              </Grid>
            </Grid>
          </AccordionDetails>
        </Accordion>
      </Grid>
    </Grid>
  );
};

export default StudentEvaluationForm;
