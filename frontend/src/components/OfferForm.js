import React from "react";
import {
  Typography,
  Button,
  Dialog,
  DialogContent,
  MobileStepper,
  DialogActions,
} from "@mui/material";
import {
  KeyboardArrowRight,
  KeyboardArrowLeft,
  Create,
} from "@mui/icons-material";

import TextFormField from "./TextFormField";
import { OFFER_FORM_VALUES } from "../modals/TextFormFieldValues";
function OfferForm() {
  const [step, setStep] = React.useState(0);
  const [offer, setOffer] = React.useState({
    limitDateToApply: "",
    beginningDate: "",
    endingDate: "",
    emailOfMonitor: "",
    companyName: "",
    description: "",
  });

  const STEP_COUNT = Object.keys(offer).length;

  const nextStep = () => {
    setStep((previousStep) => (previousStep += 1));
  };

  const prevStep = () => {
    setStep((previousStep) => (previousStep -= 1));
  };

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const saveInternshipOffer = () => {
    Object.values(offer).map((value, key) => {
      if (value === null || value === "") console.log("isEmpty", key);
    });
  };

  return (
    <>
      <Dialog open={true}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Déposer une offre de stage <Create sx={{ mx: 1 }} />
        </Typography>
        <DialogContent sx={{ minWidth: 425 }}>
          {Object.keys(offer).map((value, key) => {
            let currentField = OFFER_FORM_VALUES[key];
            return (
              <TextFormField
                key={key}
                id={value}
                dialogContentText={currentField.contentText}
                onChange={handleFormChange}
                value={Object.values(offer)[key]}
                type={currentField.type}
                visible={step === key}
              />
            );
          })}
        </DialogContent>
        <DialogActions sx={{ mt: 0 }}>
          <MobileStepper
            variant="dots"
            steps={STEP_COUNT}
            position="static"
            activeStep={step}
            sx={{ flexGrow: 1 }}
            nextButton={
              <Button
                size="small"
                onClick={nextStep}
                disabled={step === STEP_COUNT - 1}
              >
                Next
                <KeyboardArrowRight />
              </Button>
            }
            backButton={
              <Button size="small" onClick={prevStep} disabled={step === 0}>
                <KeyboardArrowLeft />
                Back
              </Button>
            }
          ></MobileStepper>
          <Button
            size="small"
            onClick={saveInternshipOffer}
            disabled={step !== STEP_COUNT - 1}
          >
            Envoyer
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}

export default OfferForm;
