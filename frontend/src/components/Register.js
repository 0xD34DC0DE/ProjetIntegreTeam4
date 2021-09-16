import { Dialog, DialogContent, DialogContentText, DialogActions, TextField, Typography, MobileStepper, FormControl } from "@mui/material";
import { Button } from "@mui/material";
import { KeyboardArrowRight, KeyboardArrowLeft, Create } from "@mui/icons-material";
import React, { useState } from "react";

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
                <React.Fragment>
                    <DialogContentText>
                        Enter your email address and your password
                    </DialogContentText>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="email"
                            label="Email Address"
                            type="email"
                            fullWidth
                            variant="standard"
                            value={form.email}
                            onChange={handleFormChange}
                            sx={{pb: 0}}
                        />
                    </FormControl>
                </React.Fragment>
            );
        }else if(step === 1) {
            return (
                <React.Fragment>
                    <DialogContentText>
                        Enter your first name and last name
                    </DialogContentText>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="first_name"
                            label="First name"
                            type="text"
                            variant="standard"
                            value = {form.first_name}
                            onChange={handleFormChange}
                            sx={{flexGrow: 1}}
                        />
                    </FormControl>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="last_name"
                            label="Last name"
                            type="text"
                            variant="standard"
                            value = {form.last_name}
                            onChange={handleFormChange}
                            sx={{flexGrow: 1}}
                        />
                    </FormControl>
                </React.Fragment>
            );
        }else if(step === 2) {
            return (
                <React.Fragment>
                    <DialogContentText>
                        Enter your phone number
                    </DialogContentText>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="phone_number"
                            label="Phone number"
                            type="tel"
                            variant="standard"
                            sx={{flexGrow: 1}}
                            value = {form.phone_number}
                            onChange={handleFormChange}
                        />
                    </FormControl>
                </React.Fragment>
            );
        }else if(step === 3) {
            return (
                <React.Fragment>
                    <DialogContentText>
                        Enter your password
                    </DialogContentText>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="password"
                            label="Password"
                            type="password"
                            fullWidth
                            variant="standard"
                            value = {form.password}
                            onChange={handleFormChange}
                        />
                    </FormControl>
                    <FormControl sx={{width: "100%"}}>
                        <TextField
                            autoFocus
                            margin="dense"
                            id="confirm_password"
                            label="Confirm password"
                            type="password"
                            fullWidth
                            variant="standard"
                            value = {form.confirm_password}
                            onChange={handleFormChange}
                        />
                    </FormControl>
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
