import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const NameFormField = ({
  valid,
  step,
  onFieldChange,
  visibleStep,
  handleFieldKeyUp,
}) => {
  const [errorMessage] = useState({
    firstName: "",
    lastName: "",
  });
  const [field, setField] = useState({ firstName: "", lastName: "" });

  useEffect(() => {
    if (step !== visibleStep) return;
    const firstName = field.firstName.trim();
    const lastName = field.lastName.trim();

    if (firstName.length !== 0 && lastName.length !== 0) {
      valid(true);
    } else {
      valid(false);
    }
  }, [field.firstName, field.lastName, step, valid, visibleStep]);

  const handleFieldChange = (event) => {
    setField((fields) => ({
      ...fields,
      [event.target.id || event.target.name]: event.target.value,
    }));
    onFieldChange(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="firstName"
        dialogContentText="Entrez votre prénom et votre nom"
        label="Prénom"
        onChange={handleFieldChange}
        value={field.firstName}
        error={errorMessage.firstName}
        type="text"
        handleFieldKeyUp={handleFieldKeyUp}
        visible={step === visibleStep}
      />
      <TextFormField
        focus={false}
        id="lastName"
        dialogContentText=""
        label="Nom"
        onChange={handleFieldChange}
        value={field.lastName}
        error={errorMessage.lastName}
        type="text"
        handleFieldKeyUp={handleFieldKeyUp}
        visible={step === visibleStep}
      />
    </>
  );
};

export default NameFormField;
