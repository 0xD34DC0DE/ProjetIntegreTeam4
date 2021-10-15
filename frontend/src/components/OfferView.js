import { Container, Paper, Typography } from '@mui/material'
import React from 'react'

function OfferView({ companyName, beginningDate, endingDate, limitDateToApply, minSalary, maxSalary, description }) {
  return (
    <>
      <Container sx={{mb: 2}}>
        <Paper elevation={3} sx={{mb: 3, pl: 2}}>
          <Typography variant="h4">{companyName}</Typography>
          <Typography variant="body2">Début: {beginningDate}</Typography>
          <Typography variant="body2"> Fin: {endingDate}</Typography>
          <Typography variant="body2">Date limite d'application: {limitDateToApply}</Typography>
          <Typography variant="body2">Taux horaire: {minSalary}$ - {maxSalary}$</Typography>
          <Typography>Description:</Typography>
          <Typography variant="body">{description}</Typography>
        </Paper>
      </Container>
    </>
  )
}

export default OfferView