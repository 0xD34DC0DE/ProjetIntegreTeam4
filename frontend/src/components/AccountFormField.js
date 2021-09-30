import React, { useState, useEffect } from "react";
import SelectFormField from "./SelectFormField";

const AccountFormField = ({ onFieldChange, step, valid, visibleStep }) => {
  const [accountType, setAccountType] = useState("");

  const accountTypes = [
    {
      type: "Étudiant",
      value: "student",
    },
    { type: "Moniteur", value: "monitor" },
  ];

  useEffect(() => {
    if (step !== visibleStep) return;

    if (accountType !== undefined) valid(true);
    else valid(false);
  }, [accountType, step, visibleStep]);

  const handleAccountTypeChange = (event) => {
    setAccountType(event.target.value);
    onFieldChange(event);
  };

  return (
    <>
      <SelectFormField
        focus={true}
        id="accountType"
        name="accountType"
        dialogContentText="Choisissez le type de compte"
        label="Type de compte"
        labelId="accountType"
        onChange={handleAccountTypeChange}
        value={accountType}
        error={""}
        items={accountTypes}
        visible={step === visibleStep}
      />
    </>
  );
};

export default AccountFormField;
