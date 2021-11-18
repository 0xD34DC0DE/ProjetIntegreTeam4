import { Button, Checkbox, FormControlLabel, Grid, Typography } from "@mui/material";
import { grey } from "@mui/material/colors";
import axios from "axios";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../../stores/UserInfoStore";

function SignContractForm({ contractId }) {
  const [hasAccepted, setHasAccepted] = useState(false);
  const [userInfo] = useContext(UserInfoContext);
  const [alreadySigned, setAlreadySigned] = useState(null);

  const handleChange = (event) => {
    setHasAccepted(event.target.checked);
  };

  const onSubmit = () => {
    if (hasAccepted) {
      axios({
        method: "POST",
        baseURL: "http://localhost:8080",
        url: "/contract/sign",
        data: {
          contractId: contractId,
          internshipOfferId: null,
          studentEmail: null

        },
        headers: {
          Authorization: userInfo.jwt,
        },
      })
        .then(() => {
          setAlreadySigned(true);
          console.log("sign success");
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
      url: "/contract/signedByContractId",
      params: {
        contractId: contractId,
        userEmail: userInfo.email,
      },
      headers: {
        Authorization: userInfo.jwt,
      },
    })
      .then((response) => {
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
      {alreadySigned === true && (
        <Grid item alignItems="center" textAlign="center" sx={{ mt: 3 }}>
          <Typography color="green">Contrat signé!</Typography>
        </Grid>
      )}
      {alreadySigned != null && alreadySigned === false && (
        <>
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
}

export default SignContractForm;
