import { Stack } from '@mui/material'
import React, { useState, useEffect, useContext } from 'react'
import OfferView from './OfferView';
import axios from "axios";
import { UserInfoContext } from '../stores/UserInfoStore';

function OfferViews() {

  const [userInfo] = useContext(UserInfoContext)
  const [intershipOffers, setInternshipOffers] = useState([])

  useEffect(() => {
    if (userInfo.loggedIn) {
      getInternshipOffers();
    }
  }, [userInfo])

  const getInternshipOffers = () => {
    axios({
      method: "GET",
      url: `http://localhost:8080/internshipOffer/studentInternshipOffers/${userInfo.email}`,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then((response) => {
        setInternshipOffers(response.data);
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };


  return (
    <>
      <Stack>
        {
          intershipOffers.map((offer, index) =>
            <OfferView key={index}
              companyName={offer.companyName}
              beginningDate={offer.beginningDate}
              endingDate={offer.endingDate}
              limitDateToApply={offer.limitDateToApply}
              minSalary={offer.minSalary}
              maxSalary={offer.maxSalary}
              description={offer.description} />
          )
        }
      </Stack>
    </>
  )
}

export default OfferViews
