import React, { useState } from "react";
import {
  Grid,
  AccordionDetails,
  Accordion,
  AccordionSummary,
  Typography,
  Radio,
  TextField,
} from "@mui/material";
import { companyObservation } from "../EvaluationFields";

const CompanyObservationDropdown = () => {
  const [form, setForm] = useState({});
  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
    console.log(form);
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
              Observations générales
            </Typography>
          </Grid>
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12}>
            <Typography variant="caption">
              Vos observations du stagiaire à ce jour
            </Typography>
          </Grid>
        </Grid>
      </AccordionSummary>
      <AccordionDetails sx={{ backgroundColor: "rgba(35, 35, 35, 0.6)" }}>
        <Grid container alignItems="center">
          {companyObservation.map((observation, key) => {
            return (
              <>
                <Grid item xl={3} lg={4} md={4} xs={4} sm={4} key={key}>
                  <Typography variant="caption">{observation.label}</Typography>
                </Grid>
                <Grid
                  item
                  xl={9}
                  lg={8}
                  md={8}
                  xs={8}
                  sm={8}
                  textAlign="center"
                >
                  {observation.options.map((option, key2) => {
                    return (
                      <>
                        <Typography variant="caption" ml={5}>
                          {option}
                        </Typography>
                        <Radio
                          name={observation.id}
                          onChange={handleFormChange}
                          value={option}
                          checked={form[observation.id] === option}
                          mr={5}
                        ></Radio>
                      </>
                    );
                  })}
                </Grid>
              </>
            );
          })}
          <Grid item xl={12} lg={12} md={12} sm={12} xs={12} mt={1}>
            {Array.from(Array(3), () => {
              return (
                <>
                  <Typography variant="caption">
                    De
                    <TextField
                      variant="standard"
                      sx={{
                        "& .MuiInput-input": { fontSize: "0.8em" },
                        ml: 2,
                        mr: 2,
                      }}
                    ></TextField>
                    à
                    <TextField
                      variant="standard"
                      sx={{
                        "& .MuiInput-input": { fontSize: "0.8em" },
                        ml: 2,
                      }}
                    ></TextField>
                  </Typography>
                  <br />
                </>
              );
            })}
          </Grid>
          <Grid item xl={4} lg={4} md={4} xs={4} sm={4} ml={5} mt={5}>
            <TextField
              id="signature"
              onChange={handleFormChange}
              helperText="Signature de l'enseignant responsable du stagiaire"
              variant="standard"
              fullWidth
            ></TextField>
          </Grid>
          <Grid
            item
            xl={4}
            lg={4}
            md={4}
            xs={4}
            sm={4}
            ml={"auto"}
            mr={5}
            mt={5}
          >
            <TextField
              id="date"
              type="date"
              fullWidth
              onChange={handleFormChange}
              placeholder=""
              InputLabelProps={{
                sx: {
                  fontSize: "0.9em",
                },
                shrink: true,
              }}
              variant="standard"
              sx={{ "& .MuiInput-input": { fontSize: "0.8em" } }}
              helperText="Date"
            ></TextField>
          </Grid>
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default CompanyObservationDropdown;
