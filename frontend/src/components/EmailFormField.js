import React, { useState, useEffect } from "react";
import TextFormField from "./TextFormField";
import axios from "axios";

const emailRegexValidation =
  /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

const EmailFormField = ({ valid, onFieldChange, step }) => {
  const [errorMessage, setErrorMessage] = useState("");
  const [email, setEmail] = useState("");

  useEffect(() => {
    if (step !== 0) return;
    if (emailRegexValidation.test(email)) {
      axios({
        method: "GET",
        url: "http://localhost:8080/user/email/" + email,
      })
        .then((response) => {
          if (response.data === false) {
            setErrorMessage("");
            valid(true);
          } else {
            setErrorMessage("L'adresse courriel est déjà utilisée");
            valid(false);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    } else if (email.length === 0) {
      setErrorMessage("");
      valid(false);
    } else {
      setErrorMessage("L'adresse courriel est invalide");
      valid(false);
    }
  }, [email, step, valid]);

  const handleEmailChange = (event) => {
    setEmail(event.target.value);
    onFieldChange(event);
  };

  return (
    <>
      <TextFormField
        focus={true}
        id="email"
        dialogContentText="Entrez votre adresse courriel"
        label="Adresse courriel"
        onChange={handleEmailChange}
        value={email}
        error={errorMessage}
        type="email"
        visible={step === 0}
      />
    </>
  );
};

export default EmailFormField;
