import { Button } from '@mui/material'
import React, { useContext, useState, useEffect } from 'react'
import { UserInfoContext } from '../stores/UserInfoStore';
import axios from "axios";

function OfferApplicationButton({disabled, offerId}) {
  const [userInfo] = useContext(UserInfoContext);
  const [isWaitingResponse, setIsWaitingResponse] = useState(false);
  const [isDisabled, setIsDisabled] = useState(disabled);

  useEffect(() => {
    if (userInfo.loggedIn && isWaitingResponse) {
      axios({
        method: "PATCH",
        baseURL: "http://localhost:8080",
        url: `/internshipOffer/apply/${offerId}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json"
      })
        .then(() => {
          setIsWaitingResponse(false);
          setIsDisabled(true);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [userInfo, isWaitingResponse])

  const apply = () => {
    setIsWaitingResponse(true);
  };

  return (
    <>
      <Button disabled={isDisabled} variant="contained" sx={{justifyContent:'flex-end'}} onClick={() => apply()}>Appliquer</Button>
    </>
  )
}

export default OfferApplicationButton
