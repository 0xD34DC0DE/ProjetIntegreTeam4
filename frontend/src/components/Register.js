import { Dialog, DialogContent, DialogContentText, DialogActions, TextField, Typography, MobileStepper, FormControl } from "@mui/material";
import { Button } from "@mui/material";
import { KeyboardArrowRight, KeyboardArrowLeft, Create } from "@mui/icons-material";
import React, { useState } from "react";
import FormField from "./FormField";

const Home = () => {
    const [step, setStep] = useState(0)
    const [form, setForm] = useState({
        email: "",
        password: "",
        confirm_password: "",
        first_name: "",
        last_name: "",
        phone_number: ""
    })

    const stepCount = 6

    const nextStep = () => {
        setStep((lastStep) => lastStep + 1)
    }

    const prevStep = () => {
        setStep((lastStep) => lastStep - 1)
    }

    const handleFormChange = (event) => {
        setForm(lastForm => ({
            ...lastForm, 
            [event.target.id]: event.target.value
        }))
    }

    const displayFormField = () => {
        if(step === 0) {
            return (
                <FormField id="email" dialogContentText="Enter your e-mail" label="E-mail" onChange={handleFormChange} value={form.email} type="email"/>
            );
        }else if(step === 1) {
            return (
                <React.Fragment>
                    <FormField id="first_name" dialogContentText="Enter your first name and your last name" label="First name" onChange={handleFormChange} value={form.first_name} type="text"/>
                    <FormField id="last_name" dialogContentText="" label="Last name" onChange={handleFormChange} value={form.last_name} type="text"/>
                </React.Fragment>
            );
        }else if(step === 2) {
            return (
                <FormField id="phone_number" dialogContentText="Enter your phone number" label="Phone number" onChange={handleFormChange} value={form.phone_number} type="tel"/>
            );
        }else if(step === 3) {
            return (
                <React.Fragment>
                    <FormField id="password" dialogContentText="Enter your phone number" label="Password" onChange={handleFormChange} value={form.password} type="password"/>
                    <FormField id="confirm_password" dialogContentText="" label="Confirm password" onChange={handleFormChange} value={form.confirm_password} type="password"/>
                </React.Fragment>
            )
        }
    }

    return (
        <>
            <Dialog open={true}>
                <Typography
                    variant="h4"
                    sx={{ml: 3, mt: 3}}
                >
                    Register
                    <Create sx={{ml: 1}}/>
                </Typography>
                <DialogContent sx={{minWidth: 375}}>
                {displayFormField()}
                </DialogContent>
                <DialogActions sx={{mt: 0}}>
                    <MobileStepper
                            variant="dots"
                            steps={6}
                            position="static"
                            activeStep={step}
                            sx={{flexGrow: 1}}
                            nextButton={
                                <Button size="small" onClick={nextStep} disabled={step === stepCount - 1}>
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
                        >
                        </MobileStepper>
                </DialogActions>
            </Dialog>
        </>
    );
};

export default Home;
