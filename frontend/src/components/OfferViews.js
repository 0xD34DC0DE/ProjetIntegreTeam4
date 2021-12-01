import React, { useState, useEffect, useContext, useRef } from "react";
import {
  CircularProgress,
  Stack,
  Typography,
  Box,
  Tab,
  Tabs,
  Pagination,
  Container,
} from "@mui/material";
import OfferView from "./OfferView";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";
import { motion } from "framer-motion";
import OfferApplicationButton from "./OfferApplicationButton";

const OfferViews = () => {
  const offersPerPage = 3;
  const [userInfo] = useContext(UserInfoContext);
  const [internshipOffers, setInternshipOffers] = useState(null);
  const [currentTab, setCurrentTab] = useState(0);
  const [pagingCount, setPagingCount] = useState(1);
  const [currentPage, setCurrentPage] = useState(1);

  const CancelTokenOffers = axios.CancelToken;
  let cancelOffers = useRef(null);

  const CancelTokenPageCount = axios.CancelToken;
  let cancelPageCount = useRef(null);

  useEffect(() => {
    if (userInfo.loggedIn) {
      const getInternshipOffersPagingCount = () => {
        setPagingCount(1);

        const email = currentTab === 1 ? "/" + userInfo.email : "";

        if (cancelPageCount.current) {
          cancelPageCount.current();
        }
        axios({
          method: "GET",
          url: `http://localhost:8080/internshipOffer/pageCount${email}?size=${offersPerPage}`,
          headers: {
            Authorization: userInfo.jwt,
          },
          responseType: "json",
          cancelToken: new CancelTokenPageCount((c) => {
            cancelPageCount.current = c;
          }),
        })
          .then((response) => {
            setPagingCount(response.data);
          })
          .catch((error) => {
            if (!axios.isCancel(error)) {
              console.error(error);
            }
          });
      };
      getInternshipOffersPagingCount();
    }
  }, [currentTab, currentPage, userInfo, CancelTokenPageCount]);

  useEffect(() => {
    if (userInfo.loggedIn) {
      const getInternshipOffers = () => {
        setInternshipOffers(null);

        const email = currentTab === 1 ? "/" + userInfo.email : "";
        const endpoint = `http://localhost:8080/internshipOffer/studentInternshipOffers${email}?page=${
          currentPage - 1
        }&size=${offersPerPage}`;

        if (cancelOffers.current) {
          cancelOffers.current();
        }

        axios({
          method: "GET",
          url: endpoint,
          headers: {
            Authorization: userInfo.jwt,
          },
          responseType: "json",
          cancelToken: new CancelTokenOffers((c) => {
            cancelOffers.current = c;
          }),
        })
          .then((response) => {
            setInternshipOffers(response.data);
          })
          .catch((error) => {
            if (!axios.isCancel(error)) {
              console.error(error);
            }
          });
      };
      getInternshipOffers();
    }
  }, [currentTab, currentPage, userInfo, CancelTokenOffers]);

  const handleTabChange = (event, selectedTab) => {
    setCurrentPage(1);
    setCurrentTab(selectedTab);
  };

  const handlePageChange = (event, selectedPage) => {
    setCurrentPage(selectedPage);
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 0 }}>
      <Box
        sx={{
          borderBottom: 1,
          borderColor: "rgba(50, 50, 50, 1)",
          display: "flex",
        }}
      >
        <Tabs
          sx={{ width: "100%" }}
          value={currentTab}
          onChange={handleTabChange}
          aria-label="Affichage des offres"
          centered
        >
          <Tab label="Offres générales" index={0} sx={{ width: "50%" }} />
          <Tab label="Offres exclusives" index={1} sx={{ width: "50%" }} />
        </Tabs>
      </Box>
      <Stack mt={5} alignItems="center" justifyContent="center">
        {internshipOffers == null ? (
          <CircularProgress sx={{ mt: 10, mb: 5 }} />
        ) : internshipOffers.length === 0 ? (
          <Typography color={"text.primary"}>
            Aucune offre disponible pour l'instant.
          </Typography>
        ) : (
          internshipOffers.map((offer, i) => (
            <motion.div
              style={{ width: "100%", height: "100%", opacity: 0 }}
              animate={{ opacity: [0, 1] }}
              transition={{
                duration: 0.4,
                delay: (i + 1) * 0.2,
              }}
              key={i}
            >
              <OfferView
                key={i}
                {...offer}
                buttons={[
                  <OfferApplicationButton
                    disabled={offer.hasAlreadyApplied}
                    offerId={offer.id}
                  />,
                ]}
              />
            </motion.div>
          ))
        )}
        {internshipOffers != null && (
          <Pagination
            sx={{ mt: 5, mb: 5 }}
            count={pagingCount}
            page={currentPage}
            onChange={handlePageChange}
          />
        )}
      </Stack>
    </Container>
  );
};

export default OfferViews;
