import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const registrationNumberRegexValidation = /^[0-9]*$/;

const RegistrationNumberFormField = ({ valid, onFieldChange, step }) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [registrationNumber, setRegistrationNumber] = useState("");

  useEffect(() => {
    if (step !== 3) return;

    if (registrationNumber.length === 0) {
      setErrorMessage("");
      valid(false);
    } else if (registrationNumberRegexValidation.test(registrationNumber)) {
      setErrorMessage("");
      valid(true);
    } else {
      setErrorMessage("Le numéro de matricule est invalide");
      valid(false);
    }
  }, [registrationNumber, step, valid]);

  const handleRegistrationNumberChange = (event) => {
    setRegistrationNumber(event.target.value);
    onFieldChange(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="registrationNumber"
        dialogContentText="Entrez votre numéro de matricule"
        label="No. matricule"
        onChange={handleRegistrationNumberChange}
        value={registrationNumber}
        error={errorMessage}
        type="text"
        visible={step === 3}
      />
    </>
  );
};

export default RegistrationNumberFormField;
