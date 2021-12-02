import { Container, Grid, Paper, Typography } from "@mui/material";
import React from "react";
import useMediaQuery from "@mui/material/useMediaQuery";
import { useTheme } from "@mui/material/styles";

const OfferView = ({
  title,
  companyName,
  beginningDate,
  endingDate,
  limitDateToApply,
  minSalary,
  maxSalary,
  description,
  hasAlreadyApplied,
  id,
  buttons,
}) => {
  const theme = useTheme();
  const matchesBreakpointLg = useMediaQuery(theme.breakpoints.down("lg"));

  return (
    <Container key={id} sx={{ maxWidth: "md" }}>
      <Paper
        sx={{
          mb: 5,
          pl: 2,
          pr: 4,
          backgroundColor: "rgba(135, 135, 135, 0.05)",
          borderRadius: "10px",
          boxShadow: "15px 15px 10px 0px rgba(0,0,0,0.35);",
        }}
        elevation={5}
      >
        <Grid container sx={{ pt: 2, pb: 2 }} rowSpacing={1}>
          <Grid item xl={10} lg={10} md={12} sm={12} xs={12}>
            <Typography type="title" variant="h4">
              {companyName}
            </Typography>
            <Typography
              type="title"
              variant="subtitle2"
              mb={2}
              fontStyle="italic"
              sx={{ color: "rgba(250, 250, 250, 0.2)" }}
            >
              {title}
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
          <Grid
            item
            xl={2}
            lg={2}
            md={3}
            sm={6}
            xs={12}
            textAlign={matchesBreakpointLg ? "start" : "end"}
            mt={matchesBreakpointLg ? 1 : 0}
          >
            {buttons &&
              buttons.map((button) => {
                return button;
              })}
          </Grid>
        </Grid>
        <Grid container alignContent="center">
          <Grid item xs={12} sx={{ mb: 2 }}>
            <Typography sx={{ mt: 1, mb: 1 }} variant="h6">
              Description:
            </Typography>
            <Typography variant="body">{description}</Typography>
          </Grid>
        </Grid>
      </Paper>
    </Container>
  );
};

export default OfferView;
