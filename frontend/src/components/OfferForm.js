import { Create } from "@mui/icons-material";
import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  FormGroup,
  Typography,
} from "@mui/material";
import axios from "axios";
import React from "react";
import { OFFER_FORM_VALUES } from "../modals/TextFormFieldValues";
import TextFormField from "./TextFormField";

function OfferForm() {
  const emptyOffer = {
    limitDateToApply: new Date(),
    beginningDate: new Date(),
    endingDate: new Date(),
    emailOfMonitor: "",
    companyName: "",
    minSalary: 0,
    maxSalary: 0,
    description: "",
  };
  const [offer, setOffer] = React.useState(emptyOffer);
  const [isValid, setIsValid] = React.useState(false);
  const [errorMessage, setErrorMessage] = React.useState("");
  const [errorIndexes, setErrorIndexes] = React.useState([]);
  const [currentEvent, setCurrentEvent] = React.useState(null);
  const [token, setToken] = React.useState(
    "eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiSU5URVJOU0hJUF9NQU5BR0VSIiwic3ViIjoiZnJhbmNvaXNMYWNvdXJzaWVyZUBnbWFpbC5jb20iLCJpYXQiOjE2MzI0NDI0MzQsImV4cCI6MTYzMjQ3MTIzNH0.Cc8QW-_DV-zq6pnplPhCI9Re_iDrosZd2s6-SQ_wjBpIKB1ARk7Zsz05d8PLC9Ynm2aFsRzc4toN5CPMXAaKDg"
  );

  // Validation à faire
  // TODO : limitDateToApply < beginningDate
  // TODO : beginningDate < endingDate
  // TODO : emailOfMonitor needs to have an email format
  // TODO : minSalary < maxSalary
  //

  React.useEffect(() => {
    const removeErrorIndex = (value) => {
      for (var i = 0; i < errorIndexes.length; i++) {
        if (errorIndexes[i] === value) {
          var tmpErrorIndex = errorIndexes.splice(i, 1);
          setErrorIndexes(tmpErrorIndex);
          console.log("errorIndex", errorIndexes);
        }
      }
    };
    const validateOfferChange = () => {
      let offerKeys = Object.keys(offer);
      let offerValues = Object.values(offer);
      switch (currentEvent != null && currentEvent.target.id) {
        case offerKeys[1]:
          setErrorMessage(
            "La date limite pour appliquer ne peut venir après la date du début du stage."
          );
          setErrorIndexes(errorIndexes.concat(1));
          break;
        case offerKeys[2]:
          setErrorMessage(
            "La date du début du stage ne peut venir après la date de fin du stage."
          );
          setErrorIndexes(errorIndexes.concat(2));
          break;
        case offerKeys[3]: // Courriel du Moniteur
          var err = emailValidation(offerValues[3]);
          if (err) {
            setErrorMessage(err);
            if (!errorIndexes.includes(3))
              setErrorIndexes(errorIndexes.concat(3));
          } else {
            removeErrorIndex(3);
            setErrorMessage("");
          }
          break;
        case offerKeys[4]:
          break;
        case offerKeys[5]:
          if (offerValues[5] < 0) {
            setErrorMessage("Le salaire minimum ne peut être négatif");
            if (!errorIndexes.includes(5))
              setErrorIndexes(errorIndexes.concat(5));
          } else {
            removeErrorIndex(5);
            setErrorMessage("");
          }
          break;
        case offerKeys[6]:
          if (offerValues[6] < 0) {
            setErrorMessage("Le salaire maximum ne peut être négatif");
            if (!errorIndexes.includes(6))
              setErrorIndexes(errorIndexes.concat(6));
          } else {
            removeErrorIndex(6);
            setErrorMessage("");
          }
          break;
        case offerKeys[7]:
          break;
        default:
          break;
      }
    };
    validateOfferChange();
  }, [offer, currentEvent, errorIndexes]);

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));

    setCurrentEvent(event);

    Object.values(offer).map((value, key, array) =>
      setIsValid(!array.includes("" || null))
    );
  };

  const emailValidation = (email) => {
    if (
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/.test(
        email
      )
    ) {
      return null;
    }
    return "Entrer une adresse courriel valide";
  };

  // CHANGE TOKEN AND LOGIN ON POSTMAN BEFORE TESTING
  const saveInternshipOffer = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/internshipOffer/addAnInternshipOffer",
      headers: {
        Authorization: `Bearer ${token}`,
      },
      data: offer,
      responseType: "json",
    })
      .then((response) => {
        setOffer(emptyOffer);
        console.log(response);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  // SHOULD : Lorsque moniteur remplir le champ courriel par défaut
  return (
    <>
      <Dialog open={true}>
        <Typography variant="h4" sx={{ ml: 3, mt: 3 }}>
          Déposer une offre de stage <Create sx={{ mx: 1 }} />
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
                  visible={true}
                  label={
                    errorMessage && errorIndexes.includes(key)
                      ? errorMessage
                      : null
                  }
                  error={
                    errorMessage && errorIndexes.includes(key) ? true : false
                  }
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
}

export default OfferForm;
