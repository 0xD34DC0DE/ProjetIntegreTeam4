import { Button, Typography } from "@mui/material";
import axios from "axios";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";

const OfferValidationButton = ({ offerId, isValid, setLastRemovedOfferId }) => {
  const [userInfo] = useContext(UserInfoContext);

  const validateInternshipOffer = () => {
    axios({
      method: "PATCH",
      url: `http://localhost:8080/internshipOffer/validateInternshipOffer`,
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        id: offerId,
        isValid: isValid,
      },
      responseType: "json",
    }).catch((error) => {
      console.error(error);
    });
  };

  return (
    <Button
      variant="contained"
      sx={{
        textTransform: "uppercase",
        backgroundColor: `${
          isValid ? "rgba(125, 51, 235, 0.8)" : "rgba(100, 100, 100, 0.2)"
        }`,
        ":hover": {
          backgroundColor: `${
            isValid ? "rgba(125, 51, 235, 1)" : "rgba(100, 100, 100, 0.5)"
          }`,
          boxShadow: "5px 5px 2px 2px rgba(0, 0, 0, 0.5)",
        },
        width: "100%",
        mt: 1,
        boxShadow: "5px 5px 2px 2px rgba(0, 0, 0, 0.5)",
      }}
      onClick={() => {
        validateInternshipOffer();
        setLastRemovedOfferId(offerId);
      }}
    >
      <Typography variant="subtitle2">
        {isValid ? "Accepter" : "Refuser"}
      </Typography>
    </Button>
  );
};

export default OfferValidationButton;
