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
  NextWeekRounded,
} from "@mui/icons-material";
import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";
import SelectFormField from "./SelectFormField";
import axios from "axios";

const Home = () => {
  const [step, setStep] = useState(0);
  const [formValid, setFormValid] = useState(false);
  const [open, setOpen] = useState(true);

  const validationRegexes = {
    email:
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
    phoneNumber:
      /(\+\d{1,3}\s?)?((\(\d{3}\)\s?)|(\d{3})(\s|-?))(\d{3}(\s|-?))(\d{4})(\s?(([E|e]xt[:|.|]?)|x|X)(\s?\d+))?/g,
    registrationNumber: /^[0-9]+$/,
  };

  const [form, setForm] = useState({
    email: "",
    password: "",
    confirm_password: "",
    first_name: "",
    last_name: "",
    phone_number: "",
    registration_number: "",
    account_type: 1,
  });

  const [errorMessages, setErrorMessages] = useState({
    email: "",
    password: "",
    confirm_password: "",
    first_name: "",
    last_name: "",
    registration_number: "",
    phone_number: "",
  });

  const stepCount = 6;

  const accountTypes = [
    { value: 1, type: "Étudiant" },
    { value: 2, type: "Superviseur" },
  ];

  const nextStep = () => {
    if (step === stepCount - 1) register();
    else setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    setStep((lastStep) => (lastStep -= 1));
    setFormValid(true);
  };

  const handleFormChange = (event) => {
    setForm((lastForm) => ({
      ...lastForm,
      [event.target.id || event.target.name]: event.target.value,
    }));
    if (event.target.value === "") {
      setFormValid(false);
      setErrorMessages(event.target.id || event.target.name, "");
    }
  };

  const updateErrorMessage = (field, message) => {
    setErrorMessages((errors) => ({ ...errors, [field]: message }));
    message === "" ? setFormValid(true) : setFormValid(false);
  };

  // FIND AN ALTERNATIVE
  useEffect(() => {
    switch (step) {
      case 0:
        validateEmail();
        break;
      case 1:
        validateFirstName();
        validateLastName();
        break;
      case 2:
        validatePhoneNumber();
        break;
      case 3:
        validateRegistrationNumber();
        break;
      case 4:
        validatePassword();
        validateConfirmPassword();
        break;
      default:
        break;
    }
  }, [step, form]);

  const validateEmail = () => {
    if (validationRegexes.email.test(form.email)) {
      axios({
        method: "GET",
        url: "http://localhost:8080/user/email/" + form.email,
      })
        .then((response) => {
          response.data === true
            ? updateErrorMessage("email", "E-mail already taken")
            : clearErrorMessage("email");
        })
        .catch((error) => {
          console.error(error);
        });
    } else if (isFieldEmpty("email")) {
      clearErrorMessageAndInvalidate("email");
    } else {
      updateErrorMessage("email", "Invalid e-mail");
    }
  };

  const validateFirstName = () => {
    if (form.first_name.trim().length > 0) {
      clearErrorMessage("first_name");
    } else if (isFieldEmpty("first_name")) {
      clearErrorMessageAndInvalidate("first_name");
    } else {
      updateErrorMessage("first_name", "First name cannot be empty");
    }
  };

  const validateLastName = () => {
    if (form.last_name.trim().length > 0) {
      clearErrorMessage("last_name");
    } else if (isFieldEmpty("last_name")) {
      clearErrorMessageAndInvalidate("last_name");
    } else {
      updateErrorMessage("last_name", "Last name cannot be empty");
    }
  };

  const validatePhoneNumber = () => {
    if (validationRegexes.phoneNumber.test(form.phone_number)) {
      clearErrorMessage("phone_number");
    } else if (isFieldEmpty("phone_number")) {
      clearErrorMessageAndInvalidate("phone_number");
    } else {
      updateErrorMessage("phone_number", "Invalid phone number");
    }
  };

  const validateRegistrationNumber = () => {
    if (validationRegexes.registrationNumber.test(form.registration_number)) {
      clearErrorMessage("registration_number");
    } else if (isFieldEmpty("registration_number")) {
      clearErrorMessageAndInvalidate("registration_number");
    } else {
      updateErrorMessage(
        "registration_number",
        "Registration number is invalid"
      );
    }
  };

  const validatePassword = () => {
    if (form.password.length > 0) {
      clearErrorMessage("password");
    } else if (isFieldEmpty("password")) {
      clearErrorMessageAndInvalidate("password");
    }
  };

  const validateConfirmPassword = () => {
    if (
      form.confirm_password.length > 0 &&
      form.confirm_password !== form.password
    ) {
      updateErrorMessage("confirm_password", "Passwords are not identical");
    } else if (isFieldEmpty("confirm_password")) {
      clearErrorMessageAndInvalidate("confirm_password");
    } else {
      clearErrorMessage("confirm_password");
    }
  };

  const isFieldEmpty = (fieldName) => {
    return form[fieldName].trim().length === 0;
  };

  const register = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/student/register",
      data: {
        email: form.email,
        password: form.password,
        phoneNumber: form.phone_number,
        firstName: form.first_name,
        registrationNumber: form.registration_number,
        lastName: form.last_name,
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

  const clearErrorMessageAndInvalidate = (fieldName) => {
    clearErrorMessage(fieldName);
    setFormValid(false);
  };

  const clearErrorMessage = (fieldName) => {
    updateErrorMessage(fieldName, "");
  };

  const displayTextFormFields = () => {
    return (
      <>
        <TextFormField
          focus={true}
          id="email"
          dialogContentText="Enter your e-mail"
          label="E-mail"
          onChange={handleFormChange}
          value={form.email}
          error={errorMessages.email}
          type="email"
          visible={step === 0}
        />
        <>
          <TextFormField
            focus={true}
            id="first_name"
            dialogContentText="Enter your first name and your last name"
            label="First name"
            onChange={handleFormChange}
            value={form.first_name}
            error={errorMessages.first_name}
            type="text"
            visible={step === 1}
          />
          <TextFormField
            id="last_name"
            dialogContentText=""
            label="Last name"
            onChange={handleFormChange}
            value={form.last_name}
            error={errorMessages.last_name}
            type="text"
            visible={step === 1}
          />
        </>
        <TextFormField
          focus={true}
          id="phone_number"
          dialogContentText="Enter your phone number"
          label="Phone number"
          onChange={handleFormChange}
          value={form.phone_number}
          error={errorMessages.phone_number}
          type="tel"
          visible={step === 2}
        />
        <TextFormField
          focus={true}
          id="registration_number"
          dialogContentText="Enter your registration number"
          label="Registration number"
          onChange={handleFormChange}
          value={form.registration_number}
          error={errorMessages.registration_number}
          type="tel"
          visible={step === 3}
        />
        <>
          <TextFormField
            focus={true}
            id="password"
            dialogContentText="Enter your password and confirm it"
            label="Password"
            onChange={handleFormChange}
            error={errorMessages.password}
            value={form.password}
            type="password"
            visible={step === 4}
          />
          <TextFormField
            id="confirm_password"
            dialogContentText=""
            label="Confirm password"
            onChange={handleFormChange}
            value={form.confirm_password}
            error={errorMessages.confirm_password}
            type="password"
            visible={step === 4}
          />
        </>
        <SelectFormField
          focus={true}
          id="account_type"
          items={accountTypes}
          dialogContentText="Select your account type"
          label="Account type"
          name="account_type"
          onChange={handleFormChange}
          value={form.account_type}
          labelId="account_type_label"
          visible={step === 5}
        />
      </>
    );
  };

  return (
    <>
      <Dialog open={open}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Register <Create sx={{ ml: 1 }} />
        </Typography>
        <DialogContent sx={{ minWidth: 425 }}>
          {displayTextFormFields()}
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
                {step === stepCount - 1 ? "Send" : "Next"}
                <KeyboardArrowRight />
              </Button>
            }
            backButton={
              <Button size="small" onClick={prevStep} disabled={step === 0}>
                <KeyboardArrowLeft />
                Back
              </Button>
            }
          />
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Home;
