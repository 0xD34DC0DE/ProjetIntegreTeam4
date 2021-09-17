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
import TextFormField from "./TextFormField";
import SelectFormField from "./SelectFormField";
import axios from "axios";

const Register = () => {
  const [step, setStep] = useState(0);

  const [form, setForm] = useState({
    email: "",
    password: "",
    confirm_password: "",
    first_name: "",
    last_name: "",
    phone_number: "",
    account_type: 1,
  });

  const stepCount = 5;

  const accountTypes = [
    { value: 1, type: "Étudiant" },
    { value: 2, type: "Superviseur" },
  ];

  const nextStep = () => {
    setStep((lastStep) => (lastStep += 1));
  };

  const prevStep = () => {
    setStep((lastStep) => (lastStep -= 1));
  };

  const handleFormChange = (event) => {
    setForm((lastForm) => ({
      ...lastForm,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const register = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/user/register",
      data: {
        email: form.email,
        password: form.password,
        phone_number: form.phone_number,
        first_name: form.first_name,
        last_name: form.last_name,
        account_type: form.account_type,
      },
      responseType: "json",
      withCredentials: true,
    })
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  const displayTextFormFields = () => {
    return (
      <React.Fragment>
        <TextFormField
          focus={true}
          id="email"
          dialogContentText="Enter your e-mail"
          label="E-mail"
          onChange={handleFormChange}
          value={form.email}
          type="email"
          visible={step === 0}
        />
        <React.Fragment>
          <TextFormField
            focus={true}
            id="first_name"
            dialogContentText="Enter your first name and your last name"
            label="First name"
            onChange={handleFormChange}
            value={form.first_name}
            type="text"
            visible={step === 1}
          />
          <TextFormField
            id="last_name"
            dialogContentText=""
            label="Last name"
            onChange={handleFormChange}
            value={form.last_name}
            type="text"
            visible={step === 1}
          />
        </React.Fragment>
        <TextFormField
          focus={true}
          id="phone_number"
          dialogContentText="Enter your phone number"
          label="Phone number"
          onChange={handleFormChange}
          value={form.phone_number}
          type="tel"
          visible={step === 2}
        />
        <React.Fragment>
          <TextFormField
            focus={true}
            id="password"
            dialogContentText="Enter your password and confirm it"
            label="Password"
            onChange={handleFormChange}
            value={form.password}
            type="password"
            visible={step === 3}
          />
          <TextFormField
            id="confirm_password"
            dialogContentText=""
            label="Confirm password"
            onChange={handleFormChange}
            value={form.confirm_password}
            type="password"
            visible={step === 3}
          />
        </React.Fragment>
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
          visible={step === 4}
        />
      </React.Fragment>
    );
  };

  return (
    <>
      <Dialog open={true}>
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
              <Button
                size="small"
                onClick={nextStep}
                disabled={step === stepCount - 1}
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
        </DialogActions>
      </Dialog>
    </>
  );
};

export default Register;
