import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const phoneNumberRegexValidation =
  /^(\+\d{1,2}\s?)?1?\-?\.?\s?\(?\d{3}\)?[\s.-]?\d{3}[\s.-]?\d{4}$/;

const PhoneNumberFormField = ({
  valid,
  onFieldChange,
  step,
  visibleStep,
  handleFieldKeyUp,
}) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [phoneNumber, setPhoneNumber] = useState("");

  useEffect(() => {
    if (step !== visibleStep) return;
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
  }, [phoneNumber, step, visibleStep, valid]);

  const handlePhoneNumberChange = (event) => {
    setPhoneNumber(event.target.value);
    onFieldChange(event);
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
        handleFieldKeyUp={handleFieldKeyUp}
        type="tel"
        visible={step === visibleStep}
      />
    </>
  );
};

export default PhoneNumberFormField;
