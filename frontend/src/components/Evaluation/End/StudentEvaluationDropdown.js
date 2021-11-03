import React, { useState } from "react";
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

const StudentEvaluationDropdown = ({ section, key }) => {
  const [form, setForm] = useState({});
  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

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
                {section.title}
              </Typography>
            </Grid>
            <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
              <Typography variant="caption">{section.description}</Typography>
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
                        key={key2}
                      >
                        <Typography variant="caption">
                          {key2 + 1}) {task.label}
                        </Typography>
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
                              id={task.id}
                              color="primary"
                              name={task.id}
                              onChange={handleFormChange}
                              checked={parseInt(form[task.id]) === rating.value}
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
                    {section.comment.label}
                  </Typography>
                </Grid>
                <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
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

export default StudentEvaluationDropdown;
