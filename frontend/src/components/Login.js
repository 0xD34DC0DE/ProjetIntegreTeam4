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
import { DialogContext } from "../stores/DialogStore";
import { v4 as uuidv4 } from "uuid";

const Login = () => {
  const [errorMessage, setErrorMessage] = useState();
  const [form, setForm] = useState({
    email: "",
    password: "",
  });
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);

  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const logUserIn = () => {
    axios({
      method: "POST",
      baseURL: "http://localhost:8080",
      url: "/user/login?" + uuidv4(),
      data: JSON.stringify({ email: form.email, password: form.password }),
      headers: {
        "Content-Type": "application/json",
      },
      responseType: "json",
    })
      .then((response) => {
        userInfoDispatch({ type: "LOGIN", payload: { token: response.data } });
        resetForm();
        setErrorMessage();
        dialogDispatch({
          type: "CLOSE",
          dialogName: "loginDialog",
        });
      })
      .catch((error) => {
        let errorMessage =
          "L’identifiant ou le mot de passe que vous avez fourni n’est pas valide";
        setErrorMessage(errorMessage);
        console.error(error);
      });
  };

  const resetForm = () => {
    setForm({ email: "", password: "" });
    setErrorMessage("");
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") {
      dialogDispatch({
        type: "CLOSE",
        dialogName: "loginDialog",
      });
      resetForm();
    }
  };

  const handleFieldKeyUp = (event) => {
    if (event.code === "Enter") logUserIn();
  };

  if (!userInfo.loggedIn) {
    return (
      <Dialog open={dialog.loginDialog.visible} onClose={handleClose}>
        <DialogContent>
          <Typography
            variant="h4"
            align="left"
            sx={{ minHeight: "6vh", p: 0, m: 0, minWidth: "600px" }}
          >
            Connexion
          </Typography>
          <DialogContentText style={{ color: "rgba(220, 50, 50)", p: 0, m: 0 }}>
            {errorMessage}
          </DialogContentText>
          <TextField
            margin="normal"
            required
            fullWidth
            id="email"
            label="Adresse courriel"
            name="email"
            onChange={handleFormChange}
            value={form.email}
            autoComplete="email"
            variant="standard"
            autoFocus
            onKeyUp={handleFieldKeyUp}
            sx={{ p: 0, m: 0 }}
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            variant="standard"
            onChange={handleFormChange}
            label="Mot de passe"
            sx={{ p: 0, m: 0 }}
            type="password"
            value={form.password}
            id="password"
            onKeyUp={handleFieldKeyUp}
            autoComplete="current-password"
          />
        </DialogContent>
        <DialogActions>
          <Button
            type="submit"
            variant="text"
            sx={{
              justifySelf: "flex-start",
              mr: 20,
              flexGrow: "1",
              ":hover": {
                color: "rgba(125, 51, 235, 1) !important",
              },
            }}
            color="primary"
            onClick={() => {
              dialogDispatch({
                type: "CLOSE",
                dialogName: "loginDialog",
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
            onClick={logUserIn}
            sx={{
              justifySelf: "flex-end",
              ml: 20,
              flexGrow: "1",
              ":hover": {
                color: "rgba(125, 51, 235, 1) !important",
              },
            }}
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

export default Login;
