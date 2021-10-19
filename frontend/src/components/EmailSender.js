import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    TextField,
    Typography,
  } from "@mui/material";
  import { KeyboardArrowRight, KeyboardArrowLeft } from "@mui/icons-material";
  import axios from "axios";
  import React, { useState, useContext } from "react";
  import { UserInfoContext } from "../stores/UserInfoStore";
  
  const EmailSender = ({ open, toggleDialogs, receiver }) => {
    const [errorMessage, setErrorMessage] = useState();
    const [form, setForm] = useState({
      email: "",
      password: "",
    });
    const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  
    const sendEmail = () => {
        axios({
            method: "POST",
            baseURL: "http://localhost:8080",
            url: "/emailsender",
            data: formData,
            headers: {
                Authorization: sessionStorage.getItem("jwt"),
                "content-type": "application/json"
            }
        })
          .then((response) => {
            resetForm();
            setErrorMessage();
            toggleDialogs("loginDialog", false);
          })
          .catch((error) => {
            let errorMessage = error;
            setErrorMessage(errorMessage);
            console.error(error);
          });
    }

    const handleFormChange = (event) => {
      setForm((form) => ({
        ...form,
        [event.target.id || event.target.name]: event.target.value,
      }));
    };
  
    const resetForm = () => {
      setForm({ sender: "", receiver: "", subject: "", content: "" });
      setErrorMessage("");
    };
  
    const handleClose = (_, reason) => {
      if (reason === "backdropClick") {
        toggleDialogs("emailSenderDialog", false);
        resetForm();
      }
    };
  
    const handleFieldKeyUp = (event) => {
      if (event.code === "Enter") logUserIn();
    };
  
    if (!userInfo.loggedIn) {
      return (
        <Dialog open={open} onClose={handleClose}>
          <DialogContent>
            <Typography
              variant="h4"
              align="left"
              sx={{ minHeight: "5vh", p: 0, m: 0 }}
            >
              Envoie Courriel
            </Typography>
            <DialogContentText sx={{ color: "red", p: 0, m: 0 }}>
              {errorMessage}
            </DialogContentText>
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
              sx={{ p: 0, m: 0 }}
              variant="outlined"
              defaultValue={userInfo.email.toLowerCase()}
              InputProps={{
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
              sx={{ p: 0, m: 0 }}
              variant="outlined"
              defaultValue={receiver.toLowerCase()}
              InputProps={{
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
              sx={{ p: 0, m: 0 }}
              variant="outlined"
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="content"
              variant="standard"
              onChange={handleFormChange}
              label="Contenu"
              sx={{ p: 0, m: 0 }}
              type="content"
              value={form.content}
              id="content"
              sx={{ p: 0, m: 0 }}
              multiline
              rows={10}
              rowsMax={4}
              variant="outlined"
            />
          </DialogContent>
          <DialogActions sx={{}}>
            <Button
              type="submit"
              variant="text"
              sx={{ justifySelf: "flex-start", mr: 20, flexGrow: "1" }}
              color="primary"
              onClick={() => {
                toggleDialogs("emailSenderDialog", false);
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
      );
    } else {
      return <></>;
    }
  };
  
  export default EmailSender;
  