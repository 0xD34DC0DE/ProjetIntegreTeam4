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
import React, { useState, useContext } from "react";
import EmailFormField from "./EmailFormField";
import NameFormField from "./NameFormField";
import PhoneNumberFormField from "./PhoneNumberFormField";
import PasswordFormField from "./PasswordFormField";
import axios from "axios";
import AccountFormField from "./AccountFormField";
import CompanyNameFormField from "./CompanyNameFormField";
import { DialogContext } from "../stores/DialogStore";

const Register = () => {
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
    profileImageId: "",
  });
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const nextStep = () => {
    if (step === stepCount - 1) register();
    else setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    if (step === 0) {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "registerDialog",
      });
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
        profileImageId: "",
      },
      responseType: "json",
    })
      .then(() => {
        dialogDispatch({
          type: "CLOSE",
          dialogName: "registerDialog",
        });
        setStep(0);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      dialogDispatch({
        type: "CLOSE",
        dialogName: "registerDialog",
      });
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

  const handleFieldKeyUp = (event) => {
    if (event.code === "Enter" && formValid) nextStep();
  };

  const displayFormFields = () => {
    return (
      <>
        <AccountFormField
          valid={setFormValid}
          step={step}
          visibleStep={0}
          handleFieldKeyUp={handleFieldKeyUp}
          onFieldChange={handleAccountTypeChange}
        />
        <EmailFormField
          valid={setFormValid}
          step={step}
          visibleStep={1}
          handleFieldKeyUp={handleFieldKeyUp}
          onFieldChange={handleFormChange}
        />
        <NameFormField
          valid={setFormValid}
          step={step}
          visibleStep={2}
          handleFieldKeyUp={handleFieldKeyUp}
          onFieldChange={handleFormChange}
        />
        <PhoneNumberFormField
          valid={setFormValid}
          step={step}
          visibleStep={3}
          handleFieldKeyUp={handleFieldKeyUp}
          onFieldChange={handleFormChange}
        />
        <PasswordFormField
          valid={setFormValid}
          step={step}
          visibleStep={4}
          handleFieldKeyUp={handleFieldKeyUp}
          onFieldChange={handleFormChange}
        />
        {form.accountType === "monitor" && (
          <CompanyNameFormField
            valid={setFormValid}
            step={step}
            handleFieldKeyUp={handleFieldKeyUp}
            visibleStep={5}
            onFieldChange={handleFormChange}
          />
        )}
      </>
    );
  };

  return (
    <>
      <Dialog open={dialog.registerDialog.visible} onClose={handleClose}>
        <Typography
          variant="h2"
          align="left"
          sx={{
            p: 0,
            m: 0,
            minWidth: "600px",
            lineHeight: 1.5,
            fontSize: "2.3em",
            ml: 3,
            mt: 2,
          }}
        >
          Enregistrement
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
              <Button
                size="small"
                onClick={nextStep}
                disabled={!formValid}
                sx={{
                  textAlign: "center",
                  ":hover": {
                    backgroundColor: "rgba(125, 51, 235, 1) !important",
                  },
                  mr: 1,
                }}
              >
                <Typography
                  variant="subtitle2"
                  sx={{ verticalAlign: "middle", pl: 1 }}
                >
                  {step === stepCount - 1 ? "Envoyer" : "Suivant"}
                </Typography>
                <KeyboardArrowRight />
              </Button>
            }
            backButton={
              <Button
                size="small"
                onClick={prevStep}
                sx={{
                  ":hover": {
                    backgroundColor: "rgba(125, 51, 235, 1) !important",
                  },
                  ml: 1,
                }}
              >
                <KeyboardArrowLeft />
                <Typography
                  variant="subtitle2"
                  sx={{
                    verticalAlign: "middle",
                    textAlign: "center",
                    pr: 1,
                  }}
                >
                  {step === 0 ? "Quitter" : "Retour"}
                </Typography>
              </Button>
            }
          />
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Register;
