import React, { forwardRef, useImperativeHandle, useState } from "react";
import {
  Accordion,
  Grid,
  Typography,
  TextField,
  AccordionSummary,
  AccordionDetails,
  Radio,
} from "@mui/material";
import { midTermEvaluation, ratings } from "../EvaluationFields";

const StudentMidEvaluationDropdown = ({ mergeForms }, ref) => {
  const [form, setForm] = useState({ text: {}, categorical: {}, rating: {} });
  const handleFormChange = (event) => {
    const inputType = (
      event.target.id ? event.target.id : event.target.name
    ).split("#");
    setForm((form) => ({
      ...form,
      [inputType[0]]: {
        ...form[inputType[0]],
        [inputType[1]]: event.target.value,
      },
    }));
  };

  useImperativeHandle(ref, () => ({
    getForm: () => {
      mergeForms(form);
    },
  }));

  const integration = (
    <>
      <br />
      <Typography variant="caption">
        Préciser le nombre d'heures/semaine :
      </Typography>
      <br />
      {["Premier", "Deuxième", "Troisième"].map((value, key) => {
        return (
          <>
            <Typography variant="caption">{value} mois: </Typography>
            <TextField
              id={"text#timeSpentWeeklyMonth" + (key + 1)}
              variant="standard"
              onChange={handleFormChange}
              sx={{
                "& .MuiInput-input": {
                  fontSize: "0.8em",
                  textAlign: "center",
                },
              }}
            ></TextField>
          </>
        );
      })}
    </>
  );

  const salaryHour = (
    <>
      <br />
      <Typography variant="caption">
        Précisez :
        <TextField
          id="text#salaryHour"
          variant="standard"
          onChange={handleFormChange}
          sx={{
            "& .MuiInput-input": { fontSize: "0.8em", textAlign: "center" },
          }}
        ></TextField>
        /l'heure
      </Typography>
    </>
  );

  return (
    <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
      <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
        <AccordionSummary>
          <Grid container flexDirection="row" textAlign="center">
            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
              <Typography
                variant="subtitle2"
                sx={{ fontSize: "1.75em", lineHeight: "100%" }}
              >
                {midTermEvaluation.title}
              </Typography>
            </Grid>
            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
              <Typography variant="caption">
                {midTermEvaluation.description}
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
                      key={key3}
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
                {midTermEvaluation.tasks.map((task, key2) => {
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
                        key={key2}
                      >
                        <Typography variant="caption">
                          {key2 + 1}) {task.label}
                        </Typography>
                        {task.id === "rating#internIntegrationMesures"
                          ? integration
                          : task.id === "rating#interestingSalary"
                          ? salaryHour
                          : ""}
                      </Grid>
                      {ratings.map((rating, key3) => {
                        return (
                          <Grid
                            item
                            textAlign="center"
                            xl={1.2}
                            lg={1.2}
                            md={1.2}
                            sm={1.2}
                            xs={1.2}
                            key={key3}
                          >
                            <Radio
                              value={rating.value}
                              name={task.id}
                              onChange={handleFormChange}
                              color="primary"
                              checked={
                                form[task.id.split("#")[0]][
                                  task.id.split("#")[1]
                                ] === rating.value
                              }
                            ></Radio>
                          </Grid>
                        );
                      })}
                    </>
                  );
                })}
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
                    {midTermEvaluation.comment.label}
                  </Typography>
                </Grid>
                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
                  <TextField
                    fullWidth
                    multiline
                    type="text"
                    id={midTermEvaluation.comment.id}
                    onChange={handleFormChange}
                    inputProps={{
                      sx: {
                        fontSize: "0.8em",
                      },
                    }}
                    variant="outlined"
                    rows={3}
                    margin="dense"
                    size="small"
                  ></TextField>
                </Grid>
              </Grid>
            </Grid>
          </Grid>
        </AccordionDetails>
      </Accordion>
    </Grid>
  );
};

export default forwardRef(StudentMidEvaluationDropdown);
