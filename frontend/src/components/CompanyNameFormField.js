import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const CompanyNameFormField = ({ valid, onFieldChange, step, visibleStep }) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [companyName, setCompanyName] = useState("");

  useEffect(() => {
    if (step !== visibleStep) return;

    if (companyName.length === 0) {
      setErrorMessage("");
      valid(false);
    } else {
      setErrorMessage("");
      valid(true);
    }
  }, [companyName, step, valid, visibleStep]);

  const handleCompanyNameChange = (event) => {
    setCompanyName(event.target.value);
    onFieldChange(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="companyName"
        dialogContentText="Entrez le nom de votre compagnie"
        label="Nom compagnie"
        onChange={handleCompanyNameChange}
        value={companyName}
        error={errorMessage}
        type="text"
        visible={step === visibleStep}
      />
    </>
  );
};

export default CompanyNameFormField;
