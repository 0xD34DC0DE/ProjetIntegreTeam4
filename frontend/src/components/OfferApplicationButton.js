import { Button, Typography } from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";

function OfferApplicationButton({ disabled, offerId }) {
  const [userInfo] = useContext(UserInfoContext);
  const [isWaitingResponse, setIsWaitingResponse] = useState(false);
  const [isDisabled, setIsDisabled] = useState(disabled);

  useEffect(() => {
    if (userInfo.loggedIn && isWaitingResponse) {
      axios({
        method: "PATCH",
        url: `http://localhost:8080/internshipOffer/apply/${offerId}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then(() => {
          setIsWaitingResponse(false);
          setIsDisabled(true);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [userInfo, isWaitingResponse]);

  const apply = () => {
    setIsWaitingResponse(true);
  };

  return (
    <Button
      disabled={isDisabled}
      variant="contained"
      sx={{
        textTransform: "uppercase",
        backgroundColor: "rgba(100, 100, 100, 0.2)",
        ":hover": {
          backgroundColor: "rgba(100, 100, 100, 0.5)",
        },
        width: "100%",
      }}
      onClick={() => apply()}
    >
      <Typography variant="subtitle2">Appliquer</Typography>
    </Button>
  );
}

export default OfferApplicationButton;
