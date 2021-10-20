import { Container, Grid, Paper, Typography, Box } from '@mui/material'
import { maxWidth } from '@mui/system'
import React from 'react'
import OfferApplicationButton from './OfferApplicationButton'

function OfferView({ companyName, beginningDate, endingDate, limitDateToApply, minSalary, maxSalary, description, hasAlreadyApplied, id }) {
  return (
    <>
      <Container sx={{ maxWidth: 'md' }}>
        <Paper sx={{ mb: 3, pl: 2 }} elevation={3}>
          <Grid container sx={{pt: 2, pb: 2}} columns={13} spacing={2} rowSpacing={1}>

            <Grid item xs={11} sx={{ ml: 2 }}>
              <Typography type="title" variant="h4">{companyName}</Typography>
              <Typography sx={{ mt: 1 }} variant="body2">Début: {beginningDate}</Typography>
              <Typography sx={{ mt: 1 }} variant="body2"> Fin: {endingDate}</Typography>
              <Typography sx={{ mt: 1 }} variant="body2">Date limite d'application: {limitDateToApply}</Typography>
              <Typography sx={{ mt: 1 }} variant="body2">Taux horaire: {minSalary}$ - {maxSalary}$</Typography>
            </Grid>

            <Grid item xs={1}>
              <OfferApplicationButton disabled={hasAlreadyApplied} offerId={id}/>
            </Grid>

            <Grid item xs={12} sx={{mb: 2, mr:2, ml: 2}}>
              <Typography sx={{ mt: 1, mb: 1}} variant="h6">Description:</Typography>
              <Typography variant="body">{description}</Typography>
            </Grid>

          </Grid>
        </Paper>
      </Container>
    </>
  )
}

export default OfferView