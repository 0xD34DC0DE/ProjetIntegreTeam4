import { Box, Container, Tab, Tabs } from "@mui/material";
import React, { useState } from "react";
import ExclusiveInternshipOffers from "./ExclusiveInternshipOffers";
import InternshipOfferValidation from "./InternshipOfferValidation";
import SemesterSelect from "./SemesterSelect";

const InternshipManagerOfferViews = () => {
  const [semesterFullName, setSemesterFullName] = useState("");
  const [currentTab, setCurrentTab] = useState(0);

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
          <Tab label="Offres à valider" index={0} sx={{ width: "50%" }} />
          <Tab
            label="Gérer les offres exclusives"
            index={1}
            sx={{ width: "50%" }}
          />
        </Tabs>
      </Box>
      {currentTab === 0 ? (
        <InternshipOfferValidation semesterFullName={semesterFullName} />
      ) : (
        <ExclusiveInternshipOffers semesterFullName={semesterFullName} />
      )}
    </Container>
  );
};

export default InternshipManagerOfferViews;
