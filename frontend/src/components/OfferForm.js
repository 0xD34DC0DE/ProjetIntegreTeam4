import { Create, LocalHospital } from "@mui/icons-material";
import {
  Alert,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  FormGroup,
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
  const [offer, setOffer] = useState(emptyOffer);
  const [isValid, setIsValid] = useState(false);
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [errorMessage, setErrorMessage] = useState("");
  const [snackBarErrorMessage, setSnackBarErrorMessage] = useState("");
  const [currentFieldKey, setCurrentFieldKey] = useState();
  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  useEffect(() => {
    const fillMonitorEmail = () => {
      if (userInfo.role === "MONITOR" && offer.monitorEmail === "")
        setOffer({ ...offer, monitorEmail: userInfo.email });
    };
    fillMonitorEmail();

    setIsValid(
      validateIsNotNull() &
        validateDateEntries() &
        validateSalary(offer.minSalary, offer.maxSalary) &
        validateEmailEntries()
    );
  }, [offer]);

  const validateIsNotNull = () => {
    let nbValid = 0;
    Object.values(offer).map((value) => {
      if (!!value) nbValid++;
    });

    return nbValid === Object.values(offer).length;
  };

  const validateDateEntries = () => {
    var dates = getDates();
    var previousDate = dates[0];
    var validDateCount = 0;

    for (let i = 1; i < dates.length; i++) {
      if (compareDate(previousDate, dates[i])) {
        validDateCount++;
        previousDate = dates[i];
      }
    }

    return validDateCount === dates.length - 1;
  };

  const validateEmailEntries = () => {
    const emailRegexValidation =
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    var isValidEmail;
    Object.values(offer).map((value, key) => {
      if (Object.keys(offer)[key].toLowerCase().includes("email")) {
        isValidEmail = emailRegexValidation.test(value);
      }
    });
    if (isValidEmail) {
      setErrorMessage("");
      return isValidEmail;
    }
    setErrorMessage("Le format du courriel n'est pas valide.");
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

  const validateSalary = (minSalary, maxSalary) => {
    return minSalary < maxSalary;
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      dialogDispatch({ type: "CLOSE", dialogName: "internshipOfferDialog" });
  };

  const handleSnackBarClose = (_, reason) => {
    if (reason === "timeout") setSnackBarErrorMessage("");
  };

  const saveInternshipOffer = () => {
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
                handleFieldKeyUp={() => setCurrentFieldKey(key)}
                value={
                  offerKey === "monitorEmail" && userInfo.role === "MONITOR"
                    ? userInfo.email
                    : Object.values(offer)[key]
                }
                type={currentField.type}
                error={currentFieldKey === key ? errorMessage : ""}
                visible={true}
                readonly={
                  offerKey === "monitorEmail" && userInfo.role === "MONITOR"
                }
              />
            );
          })}
        </DialogContent>
        <DialogActions sx={{ mt: 0 }}>
          <Button
            size="small"
            onClick={saveInternshipOffer}
            disabled={!isValid}
          >
            Envoyer
          </Button>
        </DialogActions>
      </Dialog>
      <Snackbar
        open={!!snackBarErrorMessage}
        onClose={handleSnackBarClose}
        autoHideDuration={2000}
      >
        <Alert severity="error">{snackBarErrorMessage}</Alert>
      </Snackbar>
    </>
  );
};

export default OfferForm;
