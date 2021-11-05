import {
  Button,
  Grid,
  Checkbox,
  TextField,
  FormControlLabel,
  Typography,
} from "@mui/material";
import React, { useState, useEffect, useContext } from "react";
import { grey } from "@mui/material/colors";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";

const SignContractMonitorForm = ({ studentEmail, offerId }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [form, setForm] = useState({
    address: "",
    dailySchedule: "",
    hoursPerWeek: "",
    hourlyRate: "",
  });
  const [hasAccepted, setHasAccepted] = useState(false);
  const [success, setSuccess] = useState(false);
  const [alreadySigned, setAlreadySigned] = useState(null);

  const handleChange = (event) => {
    setHasAccepted(event.target.checked);
  };

  const handleFormChange = (event) => {
    setForm((form) => ({
      ...form,
      [event.target.id || event.target.name]: event.target.value,
    }));
  };

  const resetForm = () => {
    setForm(() => ({
      address: "",
      dailySchedule: "",
      hoursPerWeek: "",
      hourlyRate: "",
    }));
    setHasAccepted(false);
    setSuccess(false);
  };

  useEffect(() => {
    resetForm();
    return () => {
      resetForm();
    };
  }, []);

  const onSubmit = () => {
    if (hasAccepted) {
      axios({
        method: "POST",
        baseURL: "http://localhost:8080",
        url: "/contract/initiate",
        data: {
          ...form,
          studentEmail: studentEmail,
          internshipOfferId: offerId,
        },
        headers: {
          Authorization: userInfo.jwt,
        },
      })
        .then(() => {
          resetForm();
          setAlreadySigned(true);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  };

  useEffect(() => {
    axios({
      method: "GET",
      baseURL: "http://localhost:8080",
      url: "/contract/signed",
      params: {
        userEmail: userInfo.email,
        internshipOfferId: offerId,
      },
      headers: {
        Authorization: userInfo.jwt,
      },
    })
      .then((response) => {
        console.log(response.data);
        setAlreadySigned(response.data);
      })
      .catch((error) => {
        console.error(error);
      });

    return () => {
      setAlreadySigned(null);
    };
  }, []);

  return (
    <>
      {alreadySigned == true && (
        <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
          <Typography color="green">Contrat signé!</Typography>
        </Grid>
      )}

      {alreadySigned != null && alreadySigned == false && (
        <>
          <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
            <TextField
              id={"address"}
              value={form.address}
              label="Addresse du lieu de stage"
              type="text"
              variant="standard"
              onChange={handleFormChange}
              sx={{ "& .MuiInput-input": { fontSize: "0.8em" }, width: "70%" }}
            ></TextField>
          </Grid>

          <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
            <TextField
              id={"dailySchedule"}
              value={form.dailySchedule}
              label="Horaire journalier"
              type="text"
              variant="standard"
              onChange={handleFormChange}
              sx={{ "& .MuiInput-input": { fontSize: "0.8em" }, width: "70%" }}
            ></TextField>
          </Grid>

          <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
            <TextField
              id={"hoursPerWeek"}
              value={form.hoursPerWeek}
              label="Nombre d'heures par semaine"
              type="text"
              variant="standard"
              onChange={handleFormChange}
              sx={{ "& .MuiInput-input": { fontSize: "0.8em" }, width: "70%" }}
            ></TextField>
          </Grid>

          <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
            <TextField
              id={"hourlyRate"}
              value={form.hourlyRate}
              label="Salaire horaire"
              type="text"
              variant="standard"
              onChange={handleFormChange}
              sx={{ "& .MuiInput-input": { fontSize: "0.8em" }, width: "70%" }}
            ></TextField>
          </Grid>

          <Grid item sx={{ mt: 5 }} alignItems="center" textAlign="center">
            <FormControlLabel
              control={
                <Checkbox
                  checked={hasAccepted}
                  onChange={handleChange}
                  sx={{
                    color: grey[100],
                    "&.Mui-checked": {
                      color: grey[100],
                    },
                  }}
                />
              }
              label="J'accepte les conditions"
            />
          </Grid>

          <Grid item sx={{ mt: 3 }} alignItems="center" textAlign="center">
            <Button
              variant="contained"
              sx={{
                textTransform: "uppercase",
                backgroundColor: "rgba(100, 100, 100, 0.2)",
                ":hover": {
                  backgroundColor: "rgba(100, 100, 100, 0.5)",
                },
              }}
              disabled={!hasAccepted}
              onClick={onSubmit}
            >
              Signer
            </Button>
          </Grid>
        </>
      )}
    </>
  );
};

export default SignContractMonitorForm;
