import React, { forwardRef, useImperativeHandle, useState } from "react";
import { companyIdentification } from "../EvaluationFields";
import {
  Grid,
  AccordionDetails,
  Accordion,
  AccordionSummary,
  Typography,
  TextField,
} from "@mui/material";

const CompanyIdentificationDropdown = ({ mergeForms }, ref) => {
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

  useImperativeHandle(ref, () => ({
    getForm: () => {
      mergeForms(form);
    },
  }));

  return (
    <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
      <AccordionSummary>
        <Grid container flexDirection="row" textAlign="center">
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <Typography
              variant="subtitle2"
              sx={{ fontSize: "1.75em", lineHeight: "100%" }}
            >
              {companyIdentification.title}
            </Typography>
          </Grid>
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <Typography variant="caption">
              {companyIdentification.description}
            </Typography>
          </Grid>
        </Grid>
      </AccordionSummary>
      <AccordionDetails sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)" }}>
        <Grid container alignItems="center">
          {companyIdentification.fields.map((field, key) => {
            return (
              <React.Fragment key={key}>
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
                    type="text"
                    variant="standard"
                    onChange={handleFormChange}
                    sx={{ "& .MuiInput-input": { fontSize: "0.8em" } }}
                    fullWidth
                  ></TextField>
                </Grid>
              </React.Fragment>
            );
          })}
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default forwardRef(CompanyIdentificationDropdown);
