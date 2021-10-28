import {
  Dialog,
  DialogContent,
} from "@mui/material";
import React, { useState, useContext, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import PdfView from "./PdfView.js";

function SignContractMonitorDialog({ open, toggleDialog, pdfUrl }) {
  const [userInfo] = useContext(UserInfoContext);
  const [form, setForm] = useState({});

  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const resetForm = () => {
    setForm((form) => ({ ...form, subject: "", content: "" }));
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      toggleDialog("signContractMonitorDialog", false);
      resetForm();
    }
  };

  return (
    <>
      <Dialog open={open} onClose={handleClose}>
        <DialogContent>
          <PdfView pdfUrl={pdfUrl} />
        </DialogContent>
      </Dialog>
    </>
  );
}

export default SignContractMonitorDialog;
