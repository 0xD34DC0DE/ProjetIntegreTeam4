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
import PasswordFormField from "./PasswordFormField";
import axios from "axios";
import AccountFormField from "./AccountFormField";
import CompanyNameFormField from "./CompanyNameFormField";

const Register = ({ open, toggleDialog }) => {
  const [step, setStep] = useState(0);
  const [stepCount, setStepCount] = useState(5);
  const [formValid, setFormValid] = useState(false);
  const [form, setForm] = useState({
    email: "",
    password: "",
    confirmPassword: "",
    companyName: "",
    phoneNumber: "",
    firstName: "",
    lastName: "",
    accountType: "",
  });

  const nextStep = () => {
    if (step === stepCount - 1) register();
    else setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    if (step === 0) {
      toggleDialog("registerDialog", false);
      return;
    }
    setStep((lastStep) => (lastStep -= 1));
    setFormValid(true);
  };

  const register = () => {
    axios({
      method: "POST",
      url:
        "http://localhost:8080/" + form.accountType.toLowerCase() + "/register",
      data: {
        email: form.email,
        password: form.password,
        phoneNumber: form.phoneNumber,
        firstName: form.firstName,
        companyName: form.companyName,
        lastName: form.lastName,
      },
      responseType: "json",
    })
      .then(() => {
        toggleDialog("registerDialog", false);
        setStep(0);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") toggleDialog("registerDialog", false);
  };

  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const handleAccountTypeChange = (event) => {
    setForm((form) => ({
      ...form,
      accountType: event.target.value,
    }));
    changeStepCount(event.target.value);
  };

  const changeStepCount = (accountType) => {
    if (accountType === "student") setStepCount(5);
    else if (accountType === "monitor") setStepCount(6);
  };

  const displayFormFields = () => {
    return (
      <>
        <AccountFormField
          valid={setFormValid}
          step={step}
          visibleStep={0}
          onFieldChange={handleAccountTypeChange}
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
        <PasswordFormField
          valid={setFormValid}
          step={step}
          visibleStep={4}
          onFieldChange={handleFormChange}
        />
        {/* Special form field for each individual role */}
        {form.accountType === "monitor" && (
          <CompanyNameFormField
            valid={setFormValid}
            step={step}
            visibleStep={5}
            onFieldChange={handleFormChange}
          />
        )}
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
