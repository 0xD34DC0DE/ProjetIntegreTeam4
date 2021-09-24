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

const Register = ({ open, setOpen }) => {
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
  });

  const stepCount = 5;

  const nextStep = () => {
    if (step === stepCount - 1) register();
    else setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    setStep((lastStep) => (lastStep -= 1));
    setFormValid(true);
  };

  const register = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/student/register",
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
        setOpen(false);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleClose = (event, reason) => {
    console.log("reason", reason);
    console.log("open", open);
    if (reason === "backdropClick") setOpen(false);
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
        <EmailFormField
          valid={setFormValid}
          step={step}
          onFieldChange={handleFormChange}
        />
        <NameFormField
          valid={setFormValid}
          step={step}
          onFieldChange={handleFormChange}
        />
        <PhoneNumberFormField
          valid={setFormValid}
          step={step}
          onFieldChange={handleFormChange}
        />
        <RegistrationNumberFormField
          valid={setFormValid}
          step={step}
          onFieldChange={handleFormChange}
        />
        <PasswordFormField
          valid={setFormValid}
          step={step}
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
        <DialogContent sx={{ minWidth: 425 }}>
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
              <Button size="small" onClick={prevStep} disabled={step === 0}>
                <KeyboardArrowLeft />
                Retour
              </Button>
            }
          />
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Register;
