import { Dialog, DialogContent, DialogActions, Typography, MobileStepper } from "@mui/material";
import { Button } from "@mui/material";
import { KeyboardArrowRight, KeyboardArrowLeft, Create, NextWeekRounded } from "@mui/icons-material";
import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";
import SelectFormField from "./SelectFormField";
import axios from "axios"

const Home = () => {
    const [step, setStep] = useState(0)
    const [formValid, setFormValid] = useState(false)
    const [open, setOpen] = useState(true)

    const [form, setForm] = useState({
        email: "",
        password: "",
        confirm_password: "",
        first_name: "",
        last_name: "",
        phone_number: "",
        registration_number: "",
        account_type: 1
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

    const accountTypes = [{value: 1, type: "Étudiant"}, {value: 2, type: "Superviseur"}]

    const nextStep = () => {
        if(step === stepCount - 1)
            register()
        else
            setStep((lastStep) => lastStep+=1);
    }

    const prevStep = () => {
        setStep((lastStep) => lastStep-=1);
        setFormValid(true)
    }

    const handleFormChange = (event) => {
        setForm(lastForm => ({
            ...lastForm, 
            [event.target.id || event.target.name]: event.target.value
        }));
    }

    const updateErrorMessage = (field, message) => {
        setErrorMessages(errors => ({...errors, [field]: message}))
        message === "" ? setFormValid(true) : setFormValid(false)
    }

    useEffect(() => {
        switch(step){
            case 0: {
                // DB call to check if e-mail is not taken
                const re = /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                !re.test(form.email) ? updateErrorMessage("email", "Invalid e-mail") : updateErrorMessage("email", "")
            } 
            break;
            case 1:
                form.first_name.trim().length > 0 ? updateErrorMessage("first_name", "") : updateErrorMessage("first_name", "Name cannot be empty")   
                form.last_name.trim().length > 0 ? updateErrorMessage("last_name", "") : updateErrorMessage("last_name", "Last name cannot be empty")
            break;
            case 2: {
                const re = /(\+\d{1,3}\s?)?((\(\d{3}\)\s?)|(\d{3})(\s|-?))(\d{3}(\s|-?))(\d{4})(\s?(([E|e]xt[:|.|]?)|x|X)(\s?\d+))?/g
                !re.test(form.phone_number) ? updateErrorMessage("phone_number", "Invalid phone number") : updateErrorMessage("phone_number", "") 
            } break;
            case 3: {
                const re = /^[0-9]+$/
                re.test(form.registration_number) && form.registration_number.length > 0  ? updateErrorMessage("registration_number", "") : updateErrorMessage("registration_number", "Registration number cannot be empty or contain characters")
            } break;
            case 4:
                // Allow spaces?
                form.password.length > 0 ? updateErrorMessage("password", "") : updateErrorMessage("password", "Password cannot be empty")
                form.password === form.confirm_password ? updateErrorMessage("confirm_password", "") : updateErrorMessage("confirm_password", "Passwords do not match")
            break;
            default:
            break;
        }
    }, [step, form])

    const register = () => {
        axios({
            method: "POST",
            url: "http://localhost:8080/student/register",
            data: {
                email: form.email,
                password: form.password,
                phone_number: form.phone_number,
                first_name: form.first_name,
                registration_number: form.registration_number,
                last_name: form.last_name,
                account_type: form.account_type
            },
            responseType: "json"
        })
        .then((response) => {
            setOpen(false)
            console.log(response);
        })
        .catch((error) => {
            console.error(error);
        });
    }

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
                    error={errorMessages.email}
                    type="email"
                    visible={(step === 0)}
                />
                <React.Fragment>
                    <TextFormField
                        focus={true} 
                        id="first_name" 
                        dialogContentText="Enter your first name and your last name" 
                        label="First name" 
                        onChange={handleFormChange} 
                        value={form.first_name} 
                        error={errorMessages.first_name}
                        type="text"
                        visible={(step === 1)}
                    />
                    <TextFormField 
                        id="last_name" 
                        dialogContentText="" 
                        label="Last name" 
                        onChange={handleFormChange} 
                        value={form.last_name} 
                        error={errorMessages.last_name}
                        type="text"
                        visible={(step === 1)}
                    />
                </React.Fragment>
                <TextFormField 
                    focus={true} 
                    id="phone_number" 
                    dialogContentText="Enter your phone number" 
                    label="Phone number" 
                    onChange={handleFormChange} 
                    value={form.phone_number} 
                    error={errorMessages.phone_number}
                    type="tel"
                    visible={(step === 2)}
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
                    visible={(step === 3)}
                />
                <React.Fragment>
                    <TextFormField 
                        focus={true} 
                        id="password" 
                        dialogContentText="Enter your password and confirm it" 
                        label="Password" 
                        onChange={handleFormChange} 
                        error={errorMessages.password}
                        value={form.password} 
                        type="password"
                        visible={(step === 4)}
                    />
                    <TextFormField 
                        id="confirm_password" 
                        dialogContentText=""
                        label="Confirm password" 
                        onChange={handleFormChange} 
                        value={form.confirm_password} 
                        error={errorMessages.confirm_password}
                        type="password"
                        visible={(step === 4)}
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
                    visible={(step === 5)}
                />
            </React.Fragment>
        )
    }

    return (
        <>
            <Dialog open={open}>
                <Typography variant="h4" sx={{ml: 3, mt: 3}}>Register <Create sx={{ml: 1}}/></Typography>
                <DialogContent sx={{minWidth: 425}}>
                    {displayTextFormFields()}
                </DialogContent>
                <DialogActions sx={{mt: 0}}>
                    <MobileStepper
                        variant="dots"
                        steps={stepCount}
                        position="static"
                        activeStep={step}
                        sx={{flexGrow: 1}}
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
