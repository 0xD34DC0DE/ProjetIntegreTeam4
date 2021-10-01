import { Stack } from '@mui/material'
import React, { useState, useEffect, useContext } from 'react'
import OfferView from './OfferView';
import axios from "axios";
import { UserInfoContext } from '../stores/UserInfoStore';

function OfferViews() {

  const [userInfo] = useContext(UserInfoContext)
  const [intershipOffers, setInternshipOffers] = useState([])

  useEffect(() => {
    getInternshipOffers();
  }, [])

  const getInternshipOffers = () => {
    axios({
      method: "GET",
      url: `http://localhost:8080/internshipOffers/${userInfo.email}`,
      headers: {
        Authorization: global.token,
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
            <OfferView key={index} companyName={offer.companyName} />
          )
        }
      </Stack>
    </>
  )
}

export default OfferViews
