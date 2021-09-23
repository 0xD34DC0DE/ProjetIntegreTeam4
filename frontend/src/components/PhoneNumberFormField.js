import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const phoneNumberRegexValidation =
  /^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;

const PhoneNumberFormField = ({ valid, value, step }) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  useEffect(() => {
    if (step !== 2) return;
    if (phoneNumberRegexValidation.test(phoneNumber)) {
      setErrorMessage("");
      valid(true);
    } else if (phoneNumber.length === 0) {
      setErrorMessage("");
      valid(false);
    } else {
      setErrorMessage("Numéro de téléphone invalide");
      valid(false);
    }
  }, [phoneNumber, step, valid]);

  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
    value(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="phoneNumber"
        dialogContentText="Entrez votre numéro de téléphone"
        label="No. téléphone"
        onChange={handlePhoneNumberChange}
        value={phoneNumber}
        error={errorMessage}
        type="tel"
        visible={step === 2}
      />
    </>
  );
};

export default PhoneNumberFormField;
