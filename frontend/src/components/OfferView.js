import { Container, Typography } from '@mui/material'
import React from 'react'

function OfferView({ companyName, beginningDate, endingDate, limitDateToApply, minSalary, maxSalary, description }) {
  return (
    <>
      <Container>
        <Typography>Compagnie: {companyName}</Typography>
        <Typography>Début: {beginningDate}</Typography>
        <Typography>Fin: {endingDate}</Typography>
        <Typography>Date limite d'application: {limitDateToApply}</Typography>
        <Typography>Taux horaire: {minSalary}$-{maxSalary}</Typography>
        <Typography>Description:</Typography>
        <Typography>{description}</Typography>
      </Container>
    </>
  )
}

export default OfferView
