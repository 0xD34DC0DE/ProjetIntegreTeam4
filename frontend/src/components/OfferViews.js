import React, { useState, useEffect, useContext } from 'react'
import { CircularProgress, Stack, Typography, Box, Tab, Tabs, Pagination } from '@mui/material'
import OfferView from './OfferView';
import axios from "axios";
import { UserInfoContext } from '../stores/UserInfoStore';

function OfferViews() {
  const offersPerPage = 3;
  const [userInfo] = useContext(UserInfoContext)
  const [internshipOffers, setInternshipOffers] = useState(null)
  const [currentTab, setCurrentTab] = useState(0);
  const [pagingCount, setPagingCount] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    if (userInfo.loggedIn) {
      const getInternshipOffersPagingCount = () => {
        setPagingCount(1);
        const email = (currentTab === 1) ? "/" + userInfo.email : "";
        axios({
          method: "GET",
          url: `http://localhost:8080/internshipOffer/pageCount${email}?size=${offersPerPage}`,
          headers: {
            Authorization: userInfo.jwt,
          },
          responseType: "json",
        })
          .then((response) => {
            setPagingCount(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      }
      getInternshipOffersPagingCount();
    }
  }, [currentTab, currentPage, userInfo]);

  useEffect(() => {
    if (userInfo.loggedIn) {
      const getInternshipOffers = () => {
        setInternshipOffers(null);
        const email = (currentTab === 1) ? "/" + userInfo.email : "";
        const endpoint = `http://localhost:8080/internshipOffer/studentInternshipOffers${email}?page=${currentPage - 1}&size=${offersPerPage}`
        axios({
          method: "GET",
          url: endpoint,
          headers: {
            Authorization: userInfo.jwt,
          },
          responseType: "json",
        })
          .then((response) => {
            console.log(response);
            setInternshipOffers(response.data);
          })
          .catch((error) => {
            console.error(error);
          });
      };
      getInternshipOffers();
    }
  }, [currentTab, currentPage, userInfo]);

  const handleTabChange = (event, selectedTab) => {
    setCurrentTab(selectedTab);
  };

  const handlePageChange = (event, selectedPage) => {
    setCurrentPage(selectedPage);
  };

  return (
    <>
      <Box sx={{ borderBottom: 1, borderColor: 'divider', display: 'flex' }}>
        <Tabs sx={{ width: '100%' }} value={currentTab} onChange={handleTabChange} aria-label="Affichage des offres" centered>
          <Tab label="Offres générales" index={0} sx={{ width: '50%' }}/>
          <Tab label="Offres exclusives" index={1} sx={{ width: '50%' }} />
        </Tabs>
      </Box>
      <Stack mt={3} alignItems="center" justifyContent="center">
        {
          (internshipOffers == null) ?
            <CircularProgress sx={{ mt: 10, mb: 5 }} />
            :
            (internshipOffers.length === 0) ?
              <Typography>Aucune offre disponible pour l'instant.</Typography>
              :
              internshipOffers.map((offer, i) => <OfferView key={i} {...offer} />)
        }
        {
            internshipOffers != null && <Pagination sx={{mt: 5}} count={pagingCount} page={currentPage} onChange={handlePageChange} />
        }
      </Stack>
    </>
  )
}

export default OfferViews
