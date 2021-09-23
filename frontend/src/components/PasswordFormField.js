import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";

const PasswordFormField = ({ valid, value, step }) => {
  const [errorMessage, setErrorMessage] = useState({
    password: "",
    confirmPassword: "",
  });
  const [field, setField] = useState({ password: "", confirmPassword: "" });

  useEffect(() => {
    if (step !== 4) return;
    const password = field.password.trim();
    const confirmPassword = field.confirmPassword.trim();

    if (confirmPassword.length > 0 && confirmPassword === password) {
      setErrorMessage((errors) => ({
        ...errors,
        confirmPassword: "",
      }));
      valid(true);
    } else if (confirmPassword.length === 0) {
      setErrorMessage((errors) => ({ ...errors, confirmPassword: "" }));
      valid(false);
    } else {
      setErrorMessage((errors) => ({
        ...errors,
        confirmPassword: "Les mots de passe ne sont pas identique",
      }));
      valid(false);
    }
  }, [field.password, field.confirmPassword, step, valid]);

  const handleFieldChange = (event) => {
    setField((fields) => ({
      ...fields,
      [event.target.id || event.target.name]: event.target.value,
    }));
    value(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="password"
        dialogContentText="Entrez votre mot de passe et confirmez-le"
        label="Mot de passe"
        onChange={handleFieldChange}
        value={field.password}
        error={errorMessage.password}
        type="password"
        visible={step === 4}
      />
      <TextFormField
        focus={false}
        id="confirmPassword"
        dialogContentText=""
        label="Comfirmation du mot de passe"
        onChange={handleFieldChange}
        value={field.confirmPassword}
        error={errorMessage.confirmPassword}
        type="password"
        visible={step === 4}
      />
    </>
  );
};

export default PasswordFormField;
