import {
  Button,
  DialogContentText,
  Grid,
  TextField,
  Typography,
} from "@mui/material";
import axios from "axios";
import jwt_decode from "jwt-decode";
import React, { useState } from "react";

const Login = ({ userInformations, setUserInformations }) => {
  const [errorMessage, setErrorMessage] = useState();

  const logUserIn = (evt) => {
    // Prevents the form from being submitted
    evt.nativeEvent.preventDefault();

    // Get the form fields
    const formElements = evt.nativeEvent.target.elements;
    const email = formElements.email.value;
    const password = formElements.password.value;

    // TODO : use application context to get the service url
    axios({
      method: "POST",
      baseURL: "http://localhost:8080",
      url: "/user/login",
      data: JSON.stringify({ email: email, password: password }),
      headers: {
        "content-type": "application/json",
      },
    })
      .then(function (response) {
        sessionStorage.setItem("jwt", `Bearer ${response.data}`);

        const decodedJWT = jwt_decode(response.data);
        setUserInformations({
          email: email,
          role: decodedJWT.role,
          loggedIn: true,
        });
        setErrorMessage();
      })
      .catch(function (error) {
        let errorMessage =
          "Votre connexion a échoué. L’identifiant ou le mot de passe que vous avez entré n’est pas valide. Réessayez.";
        setErrorMessage(errorMessage);
      });
  };

  const deleteLocalUserInfo = () => {
    sessionStorage.setItem("jwt", "");
    setUserInformations({
      email: "",
      role: "",
      loggedIn: false,
    });
  };

  if (!userInformations.loggedIn) {
    return (
      <div>
        <Grid
          container
          spacing={10}
          direction="column"
          alignItems="center"
          justifyContent="center"
          style={{ minHeight: "100vh" }}
        >
          <Typography variant="h4" align="center" style={{ minHeight: "5vh" }}>
            Connexion
          </Typography>
          <DialogContentText style={{ color: "red" }}>
            {errorMessage}
          </DialogContentText>
          <form noValidate autoComplete="off" onSubmit={logUserIn}>
            <TextField
              margin="normal"
              required
              fullWidth
              id="email"
              label="Adresse courriel"
              name="email"
              autoComplete="email"
              autoFocus
            />
            <TextField
              margin="normal"
              required
              fullWidth
              name="password"
              label="Mot de passe"
              type="password"
              id="password"
              autoComplete="current-password"
            />
            <Button
              type="submit"
              variant="contained"
              color="primary"
              style={{ marginTop: "10px" }}
            >
              Envoyer
            </Button>
          </form>
        </Grid>
      </div>
    );
  } else {
    return (
      <div>
        <Grid
          container
          spacing={10}
          direction="column"
          alignItems="center"
          justifyContent="center"
          style={{ minHeight: "100vh" }}
        >
          <Typography variant="h4" align="center">
            Connexion
          </Typography>
          <DialogContentText>
            On dirait que vous êtes déjà connecté, voulez-vous vous déconnecter
            ?
          </DialogContentText>
          <Button
            variant="contained"
            color="primary"
            onClick={deleteLocalUserInfo}
            style={{ marginTop: "5vh" }}
          >
            Se Déconnecter
          </Button>
        </Grid>
      </div>
    );
  }
};

export default Login;
