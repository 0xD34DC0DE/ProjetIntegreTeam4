import { Create } from "@mui/icons-material";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  FormGroup,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useState } from "react";
import { OFFER_FORM_VALUES } from "../models/TextFormFieldValues";
import TextFormField from "./TextFormField";

const OfferForm = ({ isMounted, toggleDialogs }) => {
  const emptyOffer = {
    limitDateToApply: new Date(),
    beginningDate: new Date(),
    endingDate: new Date(),
    emailOfMonitor: "",
    companyName: "",
    minSalary: 0,
    maxSalary: 0,
    description: "",
  };
  const [offer, setOffer] = useState(emptyOffer);
  const [isValid, setIsValid] = useState(false);
  const [token, setToken] = useState(sessionStorage.getItem("jwt"));

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));

    Object.values(offer).map((value, key, array) =>
      setIsValid(!array.includes("" || null))
    );
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      toggleDialogs("internshipOfferDialog", false);
  };

  const saveInternshipOffer = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/internshipOffer/addAnInternshipOffer",
      headers: {
        Authorization: token,
      },
      data: offer,
      responseType: "json",
    })
      .then(() => {
        setOffer(emptyOffer);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <>
      <Dialog open={isMounted} disable onClose={handleClose}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Déposer une offre de stage <Create sx={{ mx: 1 }} />
        </Typography>
        <DialogContent sx={{ minWidth: 425 }}>
          <FormGroup>
            {Object.keys(offer).map((offerKey, key) => {
              let currentField = OFFER_FORM_VALUES[key];
              return (
                <TextFormField
                  key={key}
                  id={offerKey}
                  dialogContentText={currentField.contentText}
                  onChange={handleFormChange}
                  value={Object.values(offer)[key]}
                  type={currentField.type}
                  error={""}
                  visible={true}
                />
              );
            })}
          </FormGroup>
        </DialogContent>
        <DialogActions sx={{ mt: 0 }}>
          <Button
            size="small"
            onClick={saveInternshipOffer}
            disabled={!isValid}
          >
            Envoyer
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default OfferForm;
