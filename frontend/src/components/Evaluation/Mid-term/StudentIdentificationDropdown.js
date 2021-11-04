import React, { useState } from "react";
import { studentIdentification } from "../EvaluationFields";
import {
  Grid,
  AccordionDetails,
  Accordion,
  AccordionSummary,
  Typography,
  TextField,
  Radio,
} from "@mui/material";

const StudentIdentificationDropdown = () => {
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

  return (
    <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
      <AccordionSummary>
        <Grid container flexDirection="row" textAlign="center">
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <Typography
              variant="subtitle2"
              sx={{ fontSize: "1.75em", lineHeight: "100%" }}
            >
              {studentIdentification.title}
            </Typography>
          </Grid>
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <Typography variant="caption">
              {studentIdentification.description}
            </Typography>
          </Grid>
        </Grid>
      </AccordionSummary>
      <AccordionDetails sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)" }}>
        <Grid container alignItems="center">
          {studentIdentification.fields.map((field, key) => {
            return (
              <>
                <Grid
                  item
                  xl={1.5}
                  lg={1.5}
                  md={1.5}
                  sm={12}
                  xs={12}
                  key={"label" + key}
                >
                  <Typography variant="caption">{field.label}</Typography>
                </Grid>
                <Grid
                  item
                  xl={10.5}
                  lg={10.5}
                  md={10.5}
                  sm={12}
                  xs={12}
                  key={"field" + key}
                >
                  <TextField
                    id={field.id}
                    value={form[field.id]}
                    type={field.type}
                    variant="standard"
                    onChange={handleFormChange}
                    sx={{ "& .MuiInput-input": { fontSize: "0.8em" } }}
                    fullWidth
                  ></TextField>
                </Grid>
              </>
            );
          })}
          <Grid item xl={1.5} lg={1.5} md={1.5} sm={12} xs={12}>
            <Typography sx={{ fontSize: "0.8em" }}>Stage</Typography>
          </Grid>
          <Grid item xs={12} sm={12} md={10.5} xl={10.5} lg={10.5}>
            <Typography
              alignSelf="center"
              sx={{ fontSize: "0.8em", display: "inline-block" }}
            >
              1
            </Typography>
            <Radio
              name="text#internshipNumber"
              value={1}
              checked={form.text.internshipNumber === "1"}
              onChange={handleFormChange}
              color="primary"
            ></Radio>
            <Typography
              alignSelf="center"
              sx={{ fontSize: "0.8em", display: "inline-block" }}
            >
              2
            </Typography>
            <Radio
              name="text#internshipNumber"
              value={2}
              checked={form.text.internshipNumber === "2"}
              onChange={handleFormChange}
              color="primary"
            ></Radio>
          </Grid>
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default StudentIdentificationDropdown;
