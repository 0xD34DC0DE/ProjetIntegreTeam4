import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core';
import axios from 'axios';

const useStyles = makeStyles((theme) => ({
}));

const Home = () => {
    const classes = useStyles();
    const [countries, setCountries] = useState([]);

    useEffect(() => {
        axios.get("https://restcountries.eu/rest/v2/all")
        .then((response) => {
            setCountries(response.data);
        });
    }, [])

    return (
        <div>
            <h1>Home</h1>
            <ul>
                {countries.map((country, index) => <li key={index}>{country.name}</li>)}
            </ul>
        </div>
    )
}

export default Home;