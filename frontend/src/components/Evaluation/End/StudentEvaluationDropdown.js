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
import { ratings } from "../EvaluationFields";

const StudentEvaluationDropdown = ({ section, keyRef, mergeForms }, ref) => {
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

  return (
    <Grid item xs={12} key={keyRef}>
      <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
        <AccordionSummary>
          <Grid container flexDirection="row" textAlign="center">
            <Grid item xs={12}>
              <Typography
                variant="subtitle2"
                sx={{ fontSize: "1.75em", lineHeight: "100%" }}
              >
                {section.title}
              </Typography>
            </Grid>
            <Grid item xs={12}>
              <Typography variant="caption">{section.description}</Typography>
            </Grid>
          </Grid>
        </AccordionSummary>
        <AccordionDetails
          sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)", pb: 1 }}
        >
          <Grid container>
            <Grid item xs={12}>
              <Grid container flexDirection="row" textAlign="center">
                <Grid item xs={6}></Grid>
                {ratings.map((rating, key3) => {
                  return (
                    <Grid item xs={1} key={key3}>
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
            <Grid item xs={12}>
              <Grid container flexDirection="row">
                {section.tasks.map((task, key2) => {
                  return (
                    <React.Fragment key={key2}>
                      <Grid item xs={6} alignSelf="center" key={key2}>
                        <Typography variant="caption">
                          {key2 + 1}) {task.label}
                        </Typography>
                      </Grid>
                      {ratings.map((rating, key3) => {
                        return (
                          <Grid item textAlign="center" xs={1} key={key3}>
                            <Radio
                              value={rating.value}
                              id={task.id}
                              color="primary"
                              name={task.id}
                              onChange={handleFormChange}
                              checked={getFieldValue(task.id) === rating.value}
                            ></Radio>
                          </Grid>
                        );
                      })}
                    </React.Fragment>
                  );
                })}
                <Grid item xs={12}>
                  <Typography
                    variant="caption"
                    sx={{
                      display: "inline-block",
                      fontSize: "0.8em",
                      ml: 1,
                      mt: 3,
                    }}
                  >
                    {section.comment.label}
                  </Typography>
                </Grid>
                <Grid item xs={12}>
                  <TextField
                    fullWidth
                    multiline
                    type="text"
                    id={section.comment.id}
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

export default forwardRef(StudentEvaluationDropdown);
