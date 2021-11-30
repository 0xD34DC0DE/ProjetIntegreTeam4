import { Button, Typography } from "@mui/material";
import React, { useContext, useState, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import axios from "axios";

const OfferApplicationButton = ({ disabled, offerId }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [isDisabled, setIsDisabled] = useState(disabled);
  const [isExclusive, setIsExclusive] = useState(false);
  useEffect(() => {
    if (userInfo.loggedIn && !isExclusive) {
      axios({
        method: "PATCH",
        url: `http://localhost:8080/internshipOffer/changeInternshipOfferExclusivity`,
        params: {
          id: offerId,
          isExclusive: true,
        },
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      })
        .then(() => {
          setIsDisabled(true);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [userInfo, isExclusive]);

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
      onClick={() => setIsExclusive(true)}
    >
      <Typography variant="subtitle2">Rendre exclusive</Typography>
    </Button>
  );
};

export default OfferApplicationButton;
