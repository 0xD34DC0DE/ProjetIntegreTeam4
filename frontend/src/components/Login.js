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

const Login = ({ open, toggleDialog }) => {
  const [errorMessage, setErrorMessage] = useState();
  const [form, setForm] = useState({
    email: "",
    password: "",
  });
  const [userInfo, userInfoDispatch] = useContext(UserInfoContext);

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
      url: "/user/login",
      data: JSON.stringify({ email: form.email, password: form.password }),
      headers: {
        "content-type": "application/json",
      },
    })
      .then((response) => {
        userInfoDispatch({ type: "LOGIN", payload: { token: response.data } });
        resetForm();
        setErrorMessage();
        toggleDialog("loginDialog", false);
      })
      .catch((error) => {
        let errorMessage =
          "L’identifiant ou le mot de passe que vous avez fourni n’est pas valide.";
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
      toggleDialog("loginDialog", false);
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
            Connexion
          </Typography>
          <DialogContentText style={{ color: "red", p: 0, m: 0 }}>
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
            sx={{ justifySelf: "flex-start", mr: 20, flexGrow: "1" }}
            color="primary"
            onClick={() => {
              toggleDialog("loginDialog", false);
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

export default Login;
