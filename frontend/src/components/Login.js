import React from 'react';
import { makeStyles, TextField, Button, Typography, Grid } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
}));

const Login = () => {
    const classes = useStyles();

    return (
        <div>
            <Grid container spacing={0} direction="column" alignItems="center" justifyContent="center" style={{minHeight: '100vh'}}>
                <form noValidate autoComplete="off">
                    <Typography variant="h4" align="center">
                        Login
                    </Typography>
                    <TextField required id="matricule" label="Matricule" />
                    <br/>
                    <TextField required id="password" type="password" label="Mot de passe" />
                    <br/>
                    <Button variant="contained" color="primary" style={{marginTop: "10px"}}>
                        send
                    </Button>
                </form>
            </Grid>
        </div>
    )
}

export default Login;