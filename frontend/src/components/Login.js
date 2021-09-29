import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  Grid,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import jwt_decode from "jwt-decode";
import React, { useState } from "react";

const Login = ({
  open,
  userInformation,
  setUserInformation,
  toggleDialogs,
}) => {
  const [errorMessage, setErrorMessage] = useState();
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

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
        sessionStorage.setItem("jwt", `Bearer ${response.data}`);

        const decodedJWT = jwt_decode(response.data);
        setUserInformation({
          email: form.email,
          role: decodedJWT.role,
          loggedIn: true,
        });
        resetForm();
        setErrorMessage();
        toggleDialogs("loginDialog", false);
      })
      .catch((error) => {
        let errorMessage =
          "Votre connexion a échoué. L’identifiant ou le mot de passe que vous avez entré n’est pas valide. Réessayez.";
        setErrorMessage(errorMessage);
        console.error(error);
      });
  };

  const resetForm = () => {
    setForm({ email: "", password: "" });
  };

  const handleClose = (_, reason) => {
    if (reason === "backdropClick") toggleDialogs("loginDialog", false);
  };

  if (!userInformation.loggedIn) {
    return (
      <Dialog open={open} onClose={handleClose}>
        <DialogContent>
          <Typography variant="h4" align="center" sx={{ minHeight: "5vh" }}>
            Connexion
          </Typography>
          <DialogContentText sx={{ color: "red" }}>
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
          />
          <TextField
            margin="normal"
            required
            fullWidth
            name="password"
            variant="standard"
            onChange={handleFormChange}
            label="Mot de passe"
            type="password"
            value={form.password}
            id="password"
            autoComplete="current-password"
          />
        </DialogContent>
        <DialogActions>
          <Button
            type="submit"
            variant="contained"
            color="primary"
            sx={{ m: "0 auto", mt: "10px", display: "flex" }}
            onClick={logUserIn}
          >
            Envoyer
          </Button>
        </DialogActions>
      </Dialog>
    );
  } else {
    return <></>;
  }
};

export default Login;
