import React, { useState } from "react";
import {
  Accordion,
  Grid,
  Typography,
  TextField,
  AccordionSummary,
  AccordionDetails,
  Radio,
  RadioGroup,
} from "@mui/material";
import { appreciation } from "./StudentEvaluationFields";

const StudentAppreciationForm = () => {
  const [form, setForm] = useState({});
  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
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
          {appreciation.ratings.map((field, key) => {
            return (
              <>
                <Grid
                  item
                  xl={6}
                  lg={6}
                  md={6}
                  sm={6}
                  xs={6}
                  key={"label" + key}
                >
                  <Typography variant="caption">{field.label} </Typography>
                </Grid>
                <Grid
                  item
                  xl={6}
                  lg={6}
                  md={6}
                  sm={6}
                  xs={6}
                  key={"radio" + key}
                >
                  <Radio
                    checked={parseInt(form[appreciation.id]) === field.value}
                    id={appreciation.id}
                    name={appreciation.id}
                    value={field.value}
                    onChange={handleFormChange}
                  ></Radio>
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
              Précisez votre appréciation :
            </Typography>
          </Grid>
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <TextField
              fullWidth
              multiline
              id="appreciationComment"
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
            <RadioGroup
              onChange={handleFormChange}
              name={"discussedEvaluation"}
              row
              sx={{ display: "inline-block" }}
            >
              <Radio value={"no"}></Radio>
              <Typography variant="caption">Oui</Typography>
              <Radio value={"yes"}></Radio>
              <Typography variant="caption">Non</Typography>
            </RadioGroup>
          </Grid>
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default StudentAppreciationForm;
