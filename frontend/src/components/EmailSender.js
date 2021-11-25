import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  TextField,
  Typography,
} from "@mui/material";
import {
  KeyboardArrowRight,
  KeyboardArrowLeft,
  EmailOutlined,
} from "@mui/icons-material";
import axios from "axios";
import React, { useState, useContext, useEffect } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { DialogContext } from "../stores/DialogStore";

const EmailSender = ({ receiver }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [form, setForm] = useState({});
  const [dialog, dialogDispatch] = useContext(DialogContext);

  useEffect(() => {
    setForm({
      sender: userInfo.email,
      receiver: receiver,
      subject: "",
      content: "",
    });
  }, [receiver, userInfo]);

  const sendEmail = () => {
    const formData = new FormData();

    formData.append("receiver", form.receiver);
    formData.append("subject", form.subject);
    formData.append("content", form.content);

    axios({
      method: "POST",
      baseURL: "http://localhost:8080",
      url: "/emailsender",
      data: formData,
      headers: {
        Authorization: userInfo.jwt,
        "content-type": "application/json",
      },
    })
      .then(() => {
        resetForm();
      })
      .catch((error) => {
        console.error(error);
      });
    dialogDispatch({
      type: "CLOSE",
      dialogName: "emailSenderDialog",
    });
  };

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
      dialogDispatch({
        type: "CLOSE",
        dialogName: "emailSenderDialog",
      });
      resetForm();
    }
  };

  const handleFieldKeyUp = (event) => {
    if (event.code === "Enter") sendEmail();
  };

  return (
    <>
      <Dialog open={dialog.emailSenderDialog.visible} onClose={handleClose}>
        <DialogContent>
          <Typography
            variant="h4"
            align="left"
            sx={{ minHeight: "5vh", p: 0, m: 0, mb: 2 }}
          >
            Contactez l'étudiant <EmailOutlined sx={{ ml: 2 }} />
          </Typography>
          <TextField
            margin="normal"
            required
            fullWidth
            id="sender"
            label="Expéditeur-trice"
            name="sender"
            onChange={handleFormChange}
            value={form.sender}
            autoComplete="sender"
            variant="standard"
            autoFocus
            onKeyUp={handleFieldKeyUp}
            sx={{ p: 0, m: 0, mb: 2 }}
            inputProps={{
              readOnly: true,
            }}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="receiver"
            label="Destinataire"
            name="receiver"
            onChange={handleFormChange}
            value={form.receiver}
            autoComplete="receiver"
            variant="standard"
            autoFocus
            onKeyUp={handleFieldKeyUp}
            sx={{ p: 0, m: 0, mb: 2 }}
            inputProps={{
              readOnly: true,
            }}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            id="subject"
            label="Sujet"
            name="subject"
            onChange={handleFormChange}
            value={form.subject}
            autoComplete="subject"
            variant="standard"
            autoFocus
            onKeyUp={handleFieldKeyUp}
            sx={{ p: 0, m: 0, mb: 2 }}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="content"
            variant="outlined"
            onChange={handleFormChange}
            label="Contenu"
            sx={{ p: 0, m: 0 }}
            type="content"
            value={form.content}
            id="content"
            multiline
            rows={10}
            rowsmax={4}
          />
        </DialogContent>
        <DialogActions>
          <Button
            type="submit"
            variant="text"
            sx={{ justifySelf: "flex-start", mr: 20, flexGrow: "1" }}
            color="primary"
            onClick={() => {
              dialogDispatch({
                type: "CLOSE",
                dialogName: "emailSenderDialog",
              });
            }}
          >
            <KeyboardArrowLeft />
            Quitter
          </Button>
          <Button
            type="submit"
            variant="text"
            color="primary"
            onClick={sendEmail}
            sx={{ justifySelf: "flex-end", ml: 20, flexGrow: "1" }}
          >
            Envoyer
            <KeyboardArrowRight />
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default EmailSender;
