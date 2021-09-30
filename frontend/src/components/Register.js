import {
  Dialog,
  DialogContent,
  DialogActions,
  Typography,
  MobileStepper,
} from "@mui/material";
import { Button } from "@mui/material";
import {
  KeyboardArrowRight,
  KeyboardArrowLeft,
  Create,
} from "@mui/icons-material";
import React, { useState } from "react";
import EmailFormField from "./EmailFormField";
import NameFormField from "./NameFormField";
import PhoneNumberFormField from "./PhoneNumberFormField";
import RegistrationNumberFormField from "./RegistrationNumberFormField";
import PasswordFormField from "./PasswordFormField";
import axios from "axios";
import AccountFormField from "./AccountFormField";

const Register = ({ open, toggleDialogs }) => {
  const [step, setStep] = useState(0);
  const [formValid, setFormValid] = useState(false);
  const [form, setForm] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    registrationNumber: "",
    phoneNumber: "",
    firstName: "",
    lastName: "",
    accountType: "",
  });

  const stepCount = 6;

  const nextStep = () => {
    if (step === stepCount - 1) register();
    else setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    if (step === 0) {
      toggleDialogs("registerDialog", false);
      return;
    }
    setStep((lastStep) => (lastStep -= 1));
    setFormValid(true);
  };

  const register = () => {
    // Student and monitor uses the same model fields for now, it will change in the future

    axios({
      method: "POST",
      url:
        "http://localhost:8080/" + form.accountType.toLowerCase() + "/register",
      data: {
        email: form.email,
        password: form.password,
        phoneNumber: form.phoneNumber,
        firstName: form.firstName,
        registrationNumber: form.registrationNumber,
        lastName: form.lastName,
      },
      responseType: "json",
    })
      .then(() => {
        toggleDialogs("registerDialog", false);
        setStep(0);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") toggleDialogs("registerDialog", false);
  };

  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const displayFormFields = () => {
    return (
      <>
        <AccountFormField
          valid={setFormValid}
          step={step}
          visibleStep={0}
          onFieldChange={handleFormChange}
        />
        <EmailFormField
          valid={setFormValid}
          step={step}
          visibleStep={1}
          onFieldChange={handleFormChange}
        />
        <NameFormField
          valid={setFormValid}
          step={step}
          visibleStep={2}
          onFieldChange={handleFormChange}
        />
        <PhoneNumberFormField
          valid={setFormValid}
          step={step}
          visibleStep={3}
          onFieldChange={handleFormChange}
        />
        <RegistrationNumberFormField
          valid={setFormValid}
          step={step}
          visibleStep={4}
          onFieldChange={handleFormChange}
        />
        <PasswordFormField
          valid={setFormValid}
          step={step}
          visibleStep={5}
          onFieldChange={handleFormChange}
        />
      </>
    );
  };

  return (
    <>
      <Dialog open={open} onClose={handleClose}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Enregistrement <Create sx={{ ml: 1 }} />
        </Typography>
        <DialogContent sx={{ minWidth: "380px" }}>
          {displayFormFields()}
        </DialogContent>
        <DialogActions sx={{ mt: 0 }}>
          <MobileStepper
            variant="dots"
            steps={stepCount}
            position="static"
            activeStep={step}
            sx={{ flexGrow: 1 }}
            nextButton={
              <Button size="small" onClick={nextStep} disabled={!formValid}>
                {step === stepCount - 1 ? "Envoyer" : "Suivant"}
                <KeyboardArrowRight />
              </Button>
            }
            backButton={
              <Button size="small" onClick={prevStep}>
                <KeyboardArrowLeft />
                {step === 0 ? "Quitter" : "Retour"}
              </Button>
            }
          />
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Register;
