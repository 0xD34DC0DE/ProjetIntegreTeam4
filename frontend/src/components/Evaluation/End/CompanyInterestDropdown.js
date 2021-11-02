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

const CompanyInterestDropdown = () => {
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
              L'entreprise aimerait accueillir cet élève pour son prochain stage
            </Typography>
          </Grid>

          <Grid item textAlign="center">
            <RadioGroup
              onChange={handleFormChange}
              name="interestedNextInternship"
              row
              sx={{ display: "inline-block" }}
            >
              <Typography variant="caption">Oui</Typography>
              <Radio value={"yes"}></Radio>
              <Typography variant="caption">Non</Typography>
              <Radio value={"no"}></Radio>
              <Typography variant="caption">Peut-être</Typography>
              <Radio value={"maybe"}></Radio>
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
              id="technicalTrainingComment"
              onChange={handleFormChange}
              fullWidth
              multiline
              type="text"
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
          <Grid container flexDirection="row" justifyContent="center" mt={1}>
            <Grid item xl={5.8} lg={5.7} md={5.6} sm={5.5} xs={5.2} mr={5}>
              <TextField
                id="monitorFullName"
                type="text"
                onChange={handleFormChange}
                fullWidth
                variant="standard"
                label="Nom"
                InputLabelProps={{
                  sx: {
                    fontSize: "0.9em",
                  },
                }}
              ></TextField>
            </Grid>
            <Grid item xl={5.8} lg={5.7} md={5.6} sm={5.5} xs={5.2}>
              <TextField
                id="fonction"
                type="text"
                onChange={handleFormChange}
                fullWidth
                variant="standard"
                label="Fonction"
                InputLabelProps={{
                  sx: {
                    fontSize: "0.9em",
                  },
                }}
              ></TextField>
            </Grid>
          </Grid>
          <Grid
            container
            flexDirection="row"
            justifyContent="center"
            mb={2}
            mt={1}
          >
            <Grid item xl={5.8} lg={5.7} md={5.6} sm={5.5} xs={5.2} mr={5}>
              <TextField
                id="signature"
                onChange={handleFormChange}
                fullWidth
                type="text"
                variant="standard"
                label="Signature"
                InputLabelProps={{
                  sx: {
                    fontSize: "0.9em",
                  },
                }}
              ></TextField>
            </Grid>
            <Grid item xl={5.8} lg={5.7} md={5.6} sm={5.5} xs={5.2}>
              <TextField
                id="date"
                type="date"
                onChange={handleFormChange}
                fullWidth
                placeholder=""
                sx={{ "& .MuiInput-input": { fontSize: "0.8em" } }}
                InputLabelProps={{
                  sx: {
                    fontSize: "0.9em",
                  },
                  shrink: true,
                }}
                variant="standard"
                label="Date"
              ></TextField>
            </Grid>
          </Grid>
        </Grid>
      </AccordionDetails>
    </Accordion>
  );
};

export default CompanyInterestDropdown;
