import { Dialog, DialogContent, DialogActions, Typography, MobileStepper } from "@mui/material";
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

    const stepCount = 4

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
        return (
            <React.Fragment>
                {step === 0 ? <FormField id="email" dialogContentText="Enter your e-mail" label="E-mail" onChange={handleFormChange} value={form.email} type="email"/> : null}
                {step === 1 ? 
                    <React.Fragment>
                        <FormField id="first_name" dialogContentText="Enter your first name and your last name" label="First name" onChange={handleFormChange} value={form.first_name} type="text"/>
                        <FormField id="last_name" dialogContentText="" label="Last name" onChange={handleFormChange} value={form.last_name} type="text"/>
                    </React.Fragment>
                : null }
                {step === 2 ? <FormField id="phone_number" dialogContentText="Enter your phone number" label="Phone number" onChange={handleFormChange} value={form.phone_number} type="tel"/> : null}
                {step === 3 ?
                    <React.Fragment>
                        <FormField id="password" dialogContentText="Enter your phone number" label="Password" onChange={handleFormChange} value={form.password} type="password"/>
                        <FormField id="confirm_password" dialogContentText="" label="Confirm password" onChange={handleFormChange} value={form.confirm_password} type="password"/>
                    </React.Fragment>
                : null}
            </React.Fragment>
        )
    }

    return (
        <>
            <Dialog open={true}>
                <Typography variant="h4" sx={{ml: 3, mt: 3}}>Register
                <Create sx={{ml: 1}}/>
                </Typography>
                <DialogContent sx={{minWidth: 375}}>
                {displayFormField()}
                </DialogContent>
                <DialogActions sx={{mt: 0}}>
                    <MobileStepper
                            variant="dots"
                            steps={stepCount}
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
