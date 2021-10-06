import React, { useState, useEffect, useContext } from 'react'
import { CircularProgress, Stack, Typography, Box, Tab, Tabs } from '@mui/material'
import OfferView from './OfferView';
import axios from "axios";
import { UserInfoContext } from '../stores/UserInfoStore';

function OfferViews() {
  const [userInfo] = useContext(UserInfoContext)
  const [internshipOffers, setInternshipOffers] = useState(null)
  const [currentTab, setCurrentTab] = useState(0);

  useEffect(() => {
    if (userInfo.loggedIn) {
      setInternshipOffers(null);
      getInternshipOffers();
    }
  }, [userInfo]);

  useEffect(() => {
    if (userInfo.loggedIn) {
      setInternshipOffers(null);
      getInternshipOffers();
    }
  }, [currentTab]);

  const handleTabChange = (event, selectedTab) => {
    setCurrentTab(selectedTab);
  };

  const getEndpoint = (tabIndex) => {
    const email = (tabIndex === 1) ? userInfo.email : "";
    return `http://localhost:8080/internshipOffer/studentInternshipOffers/${email}`;
  }

  const getInternshipOffers = () => {
    setInternshipOffers(null);
    axios({
      method: "GET",
      url: getEndpoint(currentTab),
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    })
      .then((response) => {
        setInternshipOffers(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <>
      <Box sx={{ borderBottom: 1, borderColor: 'divider', display: 'flex' }}>
        <Tabs sx={{ width: '100%' }} value={currentTab} onChange={handleTabChange} aria-label="Affichage des offres" centered>
          <Tab label="Offres générales" index={0} sx={{ width: '50%' }} minWidth={100} />
          <Tab label="Offres exclusives" index={1} sx={{ width: '50%' }} minWidth={100} />
        </Tabs>
      </Box>
      <Stack mt={3} alignItems="center" justifyContent="center">
        {
          (internshipOffers == null) ?
            <CircularProgress sx={{ "marginTop": 10 }} />
            :
            (internshipOffers.length === 0) ?
              <Typography>Aucune offre disponible pour l'instant.</Typography>
              :
              internshipOffers.map((offer, i) => <OfferView key={i} {...offer} />)
        }
      </Stack>
    </>
  )
}

export default OfferViews
