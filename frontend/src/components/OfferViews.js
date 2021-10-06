import { Skeleton, Stack, Typography } from '@mui/material'
import Box from '@mui/material/Box'
import Tab from '@mui/material/Tab'
import Tabs from '@mui/material/Tabs'
import React, { useState, useEffect, useContext } from 'react'
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
    } else {
      console.log(userInfo);
    }
  }, []);

  useEffect(() => {
    if (userInfo.loggedIn) {
      console.log("Size effect");
      setInternshipOffers(null);
      getInternshipOffers();
    }
  }, [currentTab]);

  const handleTabChange = (event, selectedTab) => {
    setCurrentTab(selectedTab);
  };

  const getEndpoint = (tabIndex) => {
    if (tabIndex === 0) {
      console.log("Getting with no email");
      return "http://localhost:8080/internshipOffer/studentInternshipOffers/";
    } else if (tabIndex === 1) {
      console.log("Getting with email");
      return `http://localhost:8080/internshipOffer/studentInternshipOffers/${userInfo.email}`;
    }
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
      <Box sx={{ width: '100%' }}>
        <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
          <Tabs value={currentTab} onChange={handleTabChange} aria-label="Affichage des offres">
            <Tab label="Offres générales" index={0} />
            <Tab label="Offres exclusives" index={1} />
          </Tabs>
        </Box>
        <Stack mt={3}>
          {
            (internshipOffers == null) ?
              <Skeleton variant="rectangular" height={100} />
              :
              (internshipOffers.length === 0) ?
                <Typography>Aucune offre disponible pour l'instant.</Typography>
                :
                internshipOffers.map((offer, index) => <OfferView key={index} {...offer} />)
          }

        </Stack>
      </Box>
    </>
  )
}

export default OfferViews
