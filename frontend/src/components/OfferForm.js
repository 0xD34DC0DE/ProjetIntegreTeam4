import { Create, LocalHospital } from "@mui/icons-material";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  FormGroup,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext, useState } from "react";
import { OFFER_FORM_VALUES } from "../models/TextFormFieldValues";
import TextFormField from "./TextFormField";
import { UserInfoContext } from "../stores/UserInfoStore";
import { DialogContext } from "../stores/DialogStore";

const OfferForm = () => {
  const emptyOffer = {
    title: "",
    limitDateToApply: new Date(),
    beginningDate: new Date(),
    endingDate: new Date(),
    emailOfMonitor: "",
    companyName: "",
    minSalary: 0,
    maxSalary: 0,
    description: "",
  };
  const [offer, setOffer] = useState(emptyOffer);
  const [isValid, setIsValid] = useState(false);
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));
    setIsValid(validateDateEntries());
    validateEmailEntries();
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

  //TODO: Not Done
  const validateEmailEntries = () => {
    var validRegex =
      /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/;
    Object.values(offer).map((value, key) => {
      if (Object.keys(offer)[key].toLowerCase().includes("email")) {
        console.log(Object.keys(offer)[key].toLowerCase());
      }
    });
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

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      dialogDispatch({ type: "CLOSE", dialogName: "internshipOfferDialog" });
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
          <FormGroup>
            {Object.keys(offer).map((offerKey, key) => {
              let currentField = OFFER_FORM_VALUES[key];
              return (
                <TextFormField
                  key={key}
                  id={offerKey}
                  dialogContentText={currentField.contentText}
                  onChange={handleFormChange}
                  value={Object.values(offer)[key]}
                  type={currentField.type}
                  error={""}
                  visible={true}
                />
              );
            })}
          </FormGroup>
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
    </>
  );
};

export default OfferForm;
