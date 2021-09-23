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
    limitDateToApply: "",
    beginningDate: "",
    endingDate: "",
    emailOfMonitor: "",
    companyName: "",
    description: "",
  };
  const [offer, setOffer] = React.useState(emptyOffer);
  const [isValid, setIsValid] = React.useState(false);

  const handleFormChange = (event) => {
    setOffer((previousForm) => ({
      ...previousForm,
      [event.target.id || event.target.name]: event.target.value,
    }));

    Object.values(offer).map((value, key, array) =>
      setIsValid(!array.includes("" || null))
    );
  };

  // CHANGE TOKEN AND LOGIN ON POSTMAN BEFORE TESTING
  const saveInternshipOffer = () => {
    axios({
      method: "POST",
      url: "http://localhost:8080/internshipOffer/addAnInternshipOffer",
      headers: {
        Authorization:
          "Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlIjoiSU5URVJOU0hJUF9NQU5BR0VSIiwic3ViIjoiZnJhbmNvaXNMYWNvdXJzaWVyZUBnbWFpbC5jb20iLCJpYXQiOjE2MzIzNjY4MzIsImV4cCI6MTYzMjM5NTYzMn0.3BJ6pGi_OTdq-W8LT9roTao-YfXptyvEWJR1tARuNju1k6X8232cdQEQQS-PnVU2LVm9UJnhGKE0fT3SSTa7WA",
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
