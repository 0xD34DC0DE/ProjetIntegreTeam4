import { Create, LocalHospital } from "@mui/icons-material";
import {
  Alert,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  Snackbar,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useEffect, useState, useCallback } from "react";
import { OFFER_FORM_VALUES } from "../models/TextFormFieldValues";
import TextFormField from "./TextFormField";
import { UserInfoContext } from "../stores/UserInfoStore";
import { DialogContext } from "../stores/DialogStore";

const OfferForm = () => {
  const emptyOffer = {
    title: "",
    limitDateToApply: "",
    beginningDate: "",
    endingDate: "",
    monitorEmail: "",
    companyName: "",
    minSalary: 0,
    maxSalary: 0,
    description: "",
  };

  const emptyError = {
    title: { isValid: true, message: "" },
    limitDateToApply: { isValid: true, message: "" },
    beginningDate: { isValid: true, message: "" },
    endingDate: { isValid: true, message: "" },
    monitorEmail: { isValid: true, message: "" },
    companyName: { isValid: true, message: "" },
    minSalary: { isValid: true, message: "" },
    maxSalary: { isValid: true, message: "" },
    description: { isValid: true, message: "" },
  };
  const [offer, setOffer] = useState(emptyOffer);
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [error, setError] = useState(emptyError);
  const [snackBarErrorMessage, setSnackBarErrorMessage] = useState("");
  const [snackBarSuccessMessage, setSnackBarSuccessMessage] = useState("");
  useEffect(() => {
    const fillMonitorEmail = () => {
      if (userInfo.role === "MONITOR" && offer.monitorEmail === "")
        setOffer({ ...offer, monitorEmail: userInfo.email });
    };

    const resetForm = () => {
      setOffer(emptyOffer);
      resetErrors();
    };

    resetForm();
    fillMonitorEmail();
  }, []);

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));

    resetErrors();
  };

  const validateForm = () => {
    let isValid = true;
    const errorEmpty = { isValid: false, message: "Veuillez remplir le champ" };

    let newState = emptyError;

    for (let field of Object.keys(error)) {
      if (offer[field] === "" || offer[field] === 0) {
        newState = { ...newState, [field]: errorEmpty };
        isValid = false;
      }
    }

    setError(newState);

    return (
      isValid &&
      validateDateEntries() &
        validateEmailEntries() &
        validateSalary(offer.minSalary, offer.maxSalary)
    );
  };

  const resetErrors = () => {
    setError(emptyError);
  };

  const validateDateEntries = () => {
    var dates = getDates();
    var validDateCount = 0;
    var previousDate = dates[0];

    const errorDate = {
      isValid: false,
      message: "Veuillez remplir les dates correctement.",
    };

    let newState = errorDate;
    Object.keys(offer).map((field, key) => {
      if (field.includes("Date")) {
        var currentDate = Object.values(offer)[key];
        if (compareDate(previousDate, currentDate)) {
          validDateCount++;
          previousDate = currentDate;
        }
      }
    });

    if (validDateCount !== dates.length - 1) {
      newState = {
        ...error,
        limitDateToApply: errorDate,
        beginningDate: errorDate,
        endingDate: errorDate,
      };
      setError(newState);
    }

    return validDateCount === dates.length - 1;
  };

  const getDates = () => {
    var dates = [];
    Object.values(offer).map((value, key) => {
      if (Object.keys(offer)[key].toLowerCase().includes("date")) {
        dates = [...dates, value];
      }
    });
    return dates;
  };

  function compareDate(date1, date2) {
    return new Date(date1).valueOf() < new Date(date2).valueOf();
  }

  const validateEmailEntries = () => {
    const emailError = {
      isValid: false,
      message: "Veuillez entrer une courriel valide.",
    };
    const emailRegexValidation =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var isValidEmail = false;

    Object.values(offer).map((value, key) => {
      if (Object.keys(offer)[key].toLowerCase().includes("email")) {
        isValidEmail = emailRegexValidation.test(value);
      }
    });

    if (isValidEmail == false) setError({ ...error, monitorEmail: emailError });

    return isValidEmail;
  };

  const validateSalary = (minSalary, maxSalary) => {
    const errorSalary = {
      isValid: false,
      message:
        "Le taux horaire minimum doit être plus petit ou égal au taux horaire maximum.",
    };
    const errorNoSalary = {
      isValid: false,
      message: "Le salaire doit être plus grand que 0",
    };

    if (minSalary <= 0) {
      setError({
        ...error,
        minSalary: errorNoSalary,
      });
    } else if (maxSalary <= 0) {
      setError({
        ...error,
        maxSalary: errorNoSalary,
      });
    }

    if (minSalary > maxSalary) {
      setError({ ...error, minSalary: errorSalary, maxSalary: errorSalary });
    }

    return minSalary > 0 && maxSalary > 0 && minSalary <= maxSalary;
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      dialogDispatch({ type: "CLOSE", dialogName: "internshipOfferDialog" });
  };

  const handleSnackBarClose = (_, reason) => {
    if (reason === "timeout") {
      setSnackBarErrorMessage("");
      setSnackBarSuccessMessage("");
    }
  };

  const saveInternshipOffer = () => {
    if (!validateForm()) {
      return;
    }
    axios({
      method: "POST",
      url: "http://localhost:8080/internshipOffer/addAnInternshipOffer",
      headers: {
        Authorization: userInfo.jwt,
      },
      data: offer,
      responseType: "json",
    })
      .then(() => {
        setOffer(emptyOffer);
        setSnackBarSuccessMessage("Votre offre a été envoyée avec succès.");
      })
      .catch((error) => {
        setOffer(emptyOffer);
        setSnackBarErrorMessage(
          "Une erreur est survenue. Le courriel du moniteur n'existe pas."
        );
        console.error(error);
      });
    dialogDispatch({ type: "CLOSE", dialogName: "internshipOfferDialog" });
  };

  return (
    <>
      <Dialog open={dialog.internshipOfferDialog.visible} onClose={handleClose}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Déposer une offre de stage <Create sx={{ mr: 3 }} />
        </Typography>
        <DialogContent sx={{ minWidth: 425 }}>
          {Object.keys(offer).map((offerKey, key) => {
            let currentField = OFFER_FORM_VALUES[key];
            return (
              <TextFormField
                key={key}
                id={offerKey}
                dialogContentText={currentField.contentText}
                onChange={handleFormChange}
                value={
                  offerKey === "monitorEmail" && userInfo.role === "MONITOR"
                    ? userInfo.email
                    : Object.values(offer)[key]
                }
                type={currentField.type}
                error={
                  !Object.values(error)[key].isValid
                    ? Object.values(error)[key].message
                    : ""
                }
                visible={true}
                readonly={
                  offerKey === "monitorEmail" && userInfo.role === "MONITOR"
                }
              />
            );
          })}
        </DialogContent>
        <DialogActions sx={{ mt: 0 }}>
          <Button size="small" onClick={saveInternshipOffer}>
            Envoyer
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={!!snackBarErrorMessage || !!snackBarSuccessMessage}
        onClose={handleSnackBarClose}
        autoHideDuration={4000}
      >
        {!!snackBarSuccessMessage ? (
          <Alert severity="success">{snackBarSuccessMessage}</Alert>
        ) : (
          <Alert severity="error">{snackBarErrorMessage}</Alert>
        )}
      </Snackbar>
    </>
  );
};

export default OfferForm;
