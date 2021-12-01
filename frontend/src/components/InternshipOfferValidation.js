import { CircularProgress, Stack, Typography } from "@mui/material";
import axios from "axios";
import { motion } from "framer-motion";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import OfferValidationButton from "./OfferValidationButton";
import OfferView from "./OfferView";

const InternshipOfferValidation = ({ semesterFullName }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [internshipOffers, setInternshipOffers] = useState(null);
  const [lastRemovedOfferId, setLastRemovedOfferId] = useState("");

  useEffect(() => {
    if (semesterFullName === "") return;
    const getUnvalidatedInternshipOffers = async () => {
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/internshipOffer/getNotYetValidatedInternshipOffers/${semesterFullName}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setInternshipOffers(response.data);
    };

    getUnvalidatedInternshipOffers();
  }, [lastRemovedOfferId, semesterFullName]);

  return (
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
                <OfferValidationButton
                  offerId={offer.id}
                  isValid={true}
                  setLastRemovedOfferId={setLastRemovedOfferId}
                />,
                <OfferValidationButton
                  offerId={offer.id}
                  isValid={false}
                  setLastRemovedOfferId={setLastRemovedOfferId}
                />,
              ]}
            />
          </motion.div>
        ))
      )}
    </Stack>
  );
};

export default InternshipOfferValidation;
