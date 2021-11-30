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
import SemesterSelect from "./SemesterSelect";

const InternshipManagerOfferViews = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [semesterFullName, setSemesterFullName] = useState("");
  const [internshipOffers, setInternshipOffers] = useState(null);
  const [currentTab, setCurrentTab] = useState(0);

  useEffect(async () => {
    if (semesterFullName === "") return;
    if (userInfo.loggedIn) {
      const getAllValidatedOffers = async () => {
        const endpoint = `http://localhost:8080/internshipOffer/getAllValidatedOffers/${semesterFullName}`;

        var response = await axios({
          method: "GET",
          url: endpoint,
          headers: {
            Authorization: userInfo.jwt,
          },
          responseType: "json",
        }).catch((error) => {
          console.error(error);
        });

        setInternshipOffers(response.data);
      };

      getAllValidatedOffers();
    }
  }, [currentTab, userInfo, semesterFullName]);

  const handleTabChange = (event, selectedTab) => {
    setCurrentTab(selectedTab);
  };

  const updateSemesterFullName = (fullName) => {
    setSemesterFullName(fullName);
  };

  return (
    <Container maxWidth="lg" sx={{ mt: 0 }}>
      <SemesterSelect updateSemesterFullName={updateSemesterFullName} />
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
              <OfferView key={i} {...offer} />
            </motion.div>
          ))
        )}
      </Stack>
    </Container>
  );
};

export default InternshipManagerOfferViews;
