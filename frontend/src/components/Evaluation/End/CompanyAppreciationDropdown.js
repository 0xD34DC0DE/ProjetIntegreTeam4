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
import { companyAppreciation } from "../EvaluationFields";

const CompanyAppreciationDropdown = ({ mergeForms }, ref) => {
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

  const getFieldValue = (id) => {
    return form[id.split("#")[0]][id.split("#")[1]];
  };

  return (
    <Accordion sx={{ boxShadow: "3px 3px 15px 2px rgba(0, 0, 0, 1)" }}>
      <AccordionSummary>
        <Grid container flexDirection="row" textAlign="center">
          <Grid item xs={12}>
            <Typography
              variant="subtitle2"
              sx={{ fontSize: "1.75em", lineHeight: "100%" }}
            >
              Appréciation
            </Typography>
          </Grid>
          <Grid item xs={12}>
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
          {companyAppreciation.ratings.map((field, key) => {
            return (
              <React.Fragment key={key}>
                <Grid item xs={6} key={"label" + key}>
                  <Typography variant="caption">{field.label} </Typography>
                </Grid>
                <Grid item xs={6} key={"radio" + key}>
                  <Radio
                    checked={
                      getFieldValue(companyAppreciation.id) === field.value
                    }
                    name={companyAppreciation.id}
                    value={field.value}
                    color="primary"
                    onChange={handleFormChange}
                  ></Radio>
                </Grid>
              </React.Fragment>
            );
          })}
        </Grid>
        <Grid container>
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
              Précisez votre appréciation :
            </Typography>
          </Grid>
          <Grid item xs={12}>
            <TextField
              fullWidth
              multiline
              id="text#appreciationComment"
              type="text"
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
              Cette évaluation a été discutée avec le stagiaire:
            </Typography>
            <Radio
              id={"categorical#discussedEvaluation"}
              name={"categorical#discussedEvaluation"}
              color="primary"
              onChange={handleFormChange}
              checked={form.categorical.discussedEvaluation === "YES"}
              value={"YES"}
            ></Radio>
            <Typography variant="caption">Oui</Typography>
            <Radio
              id={"categorical#discussedEvaluation"}
              name={"categorical#discussedEvaluation"}
              color="primary"
              onChange={handleFormChange}
              checked={form.categorical.discussedEvaluation === "NO"}
              value={"NO"}
            ></Radio>
            <Typography variant="caption">Non</Typography>
          </Grid>
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default forwardRef(CompanyAppreciationDropdown);
