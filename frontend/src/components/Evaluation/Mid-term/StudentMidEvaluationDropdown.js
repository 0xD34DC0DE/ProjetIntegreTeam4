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

const ordinalNumbers = ["Premier", "Deuxième", "Troisième"];

const StudentMidEvaluationDropdown = ({ mergeForms }, ref) => {
  const [form, setForm] = useState({
    text: {},
    categorical: {},
    rating: {},
    expectation: {},
  });
  const handleFormChange = (event) => {
    const target = (
      event.target.id ? event.target.id : event.target.name
    ).split("#");
    const id = target[1];
    const type = target[0];
    setForm((form) => ({
      ...form,
      [type]: {
        ...form[type],
        [id]: event.target.value,
      },
    }));
  };

  const getFieldValue = (taskId) => {
    return form[taskId.split("#")[0]][taskId.split("#")[1]];
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
      {ordinalNumbers.map((value, key) => {
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

  const hasSubtasks = (taskId) => {
    return taskId === "rating#internIntegrationMesures"
      ? integration
      : taskId === "rating#interestingSalary"
      ? salaryHour
      : "";
  };

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
                        {hasSubtasks(task.id)}
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
                              checked={getFieldValue(task.id) === rating.value}
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
