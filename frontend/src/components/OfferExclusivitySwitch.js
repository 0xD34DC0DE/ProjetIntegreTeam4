import { Button, Switch, Typography, Box } from "@mui/material";
import axios from "axios";
import React, { useContext, useState } from "react";
import { DialogContext } from "../stores/DialogStore";
import { UserInfoContext } from "../stores/UserInfoStore";

const OfferExclusivitySwitch = ({ offer, setLastExclusiveOffer }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [checked, setChecked] = useState(offer.isExclusive);
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const changeInternshipOfferExclusivity = async (isExclusive) => {
    var res = await axios({
      method: "PATCH",
      url: `http://localhost:8080/internshipOffer/changeInternshipOfferExclusivity`,
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        id: offer.id,
        isExclusive: isExclusive,
      },
      responseType: "json",
    }).catch((error) => {
      console.error(error);
    });
    console.log("Res", res);
  };

  return (
    <Button
      variant="contained"
      sx={{
        boxShadow: "5px 5px 2px 2px rgba(0, 0, 0, 0.5)",
        textTransform: "uppercase",
        backgroundColor: checked
          ? "rgba(100, 100, 100, 0.2)"
          : "rgba(125, 51, 235, 0.8)",
        ":hover": {
          backgroundColor: checked
            ? "rgba(100, 100, 100, 0.5)"
            : "rgba(125, 51, 235, 1)",
          boxShadow: "5px 5px 2px 2px rgba(0, 0, 0, 0.5)",
        },
        width: "100%",
      }}
      checked={checked}
      onClick={(e) => {
        if (!checked) {
          dialogDispatch({
            type: "OPEN",
            dialogName: "exclusiveOfferDialog",
          });
          setLastExclusiveOffer(offer);
        }

        setChecked(!checked);
        changeInternshipOfferExclusivity(!e.target.checked);
      }}
    >
      {checked ? "Exclusive" : "Rendre exclusive"}
    </Button>
  );
};

export default OfferExclusivitySwitch;
