import { Container, Grid, Paper, Typography, Box } from "@mui/material";
import { maxWidth } from "@mui/system";
import React from "react";
import OfferApplicationButton from "./OfferApplicationButton";

function OfferView({
  companyName,
  beginningDate,
  endingDate,
  limitDateToApply,
  minSalary,
  maxSalary,
  description,
  hasAlreadyApplied,
  id,
}) {
  return (
    <>
      <Container sx={{ maxWidth: "md" }}>
        <Paper
          sx={{
            mb: 5,
            pl: 2,
            backgroundColor: "rgba(135, 135, 135, 0.05)",
            borderRadius: "10px",
            boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
          }}
          elevation={5}
        >
          <Grid
            container
            sx={{ pt: 2, pb: 2, width: "96%", mx: "auto" }}
            rowSpacing={1}
            alignContent="center"
          >
            <Grid item xl={10.75} lg={10.25} md={9.8} sm={12} xs={12}>
              <Typography type="title" variant="h4">
                {companyName}
              </Typography>
              <Typography sx={{ mt: 1 }} variant="body2">
                Début: {beginningDate}
              </Typography>
              <Typography sx={{ mt: 1 }} variant="body2">
                Fin: {endingDate}
              </Typography>
              <Typography sx={{ mt: 1 }} variant="body2">
                Date limite d'application: {limitDateToApply}
              </Typography>
              <Typography sx={{ mt: 1 }} variant="body2">
                Taux horaire: {minSalary}$ - {maxSalary}$
              </Typography>
            </Grid>
            <Grid item xl={1.25} lg={1.75} md={2.2} sm={12} xs={12}>
              <OfferApplicationButton
                disabled={hasAlreadyApplied}
                offerId={id}
              />
            </Grid>
            <Grid container>
              <Grid item xs={12} xl={12} md={3} sm={12} xs={12} sx={{ mb: 2 }}>
                <Typography sx={{ mt: 1, mb: 1 }} variant="h6">
                  Description:
                </Typography>
                <Typography variant="body">{description}</Typography>
              </Grid>
            </Grid>
          </Grid>
        </Paper>
      </Container>
    </>
  );
}

export default OfferView;
