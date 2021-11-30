import { CircularProgress, Stack, Typography } from "@mui/material";
import axios from "axios";
import { motion } from "framer-motion";
import React, { useContext, useEffect, useState } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import AssignedExclusiveOfferDialog from "./AssignedExclusiveOfferDialog";
import OfferExclusivitySwitch from "./OfferExclusivitySwitch";
import OfferView from "./OfferView";

const ExclusiveInternshipOffers = ({ semesterFullName }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [internshipOffers, setInternshipOffers] = useState(null);
  const [lastExclusiveOffer, setLastExclusiveOffer] = useState("");

  useEffect(() => {
    const getUnvalidatedInternshipOffers = async () => {
      if (semesterFullName === "") return;
      let response = await axios({
        method: "GET",
        url: `http://localhost:8080/internshipOffer/getAllValidatedOffers/${semesterFullName}`,
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      setInternshipOffers(response.data);
    };

    getUnvalidatedInternshipOffers();
  }, [lastExclusiveOffer, semesterFullName]);

  return (
    <>
      <Stack mt={5} alignItems="center" justifyContent="center">
        {internshipOffers == null ? (
          <CircularProgress sx={{ mt: 10, mb: 5 }} />
        ) : internshipOffers.length === 0 ? (
          <Typography color={"text.primary"}>
            Aucune offre disponible pour l'instant.
          </Typography>
        ) : (
          internshipOffers.map((offer, i) => {
            return (
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
                    <OfferExclusivitySwitch
                      offer={offer}
                      setLastExclusiveOffer={setLastExclusiveOffer}
                    />,
                  ]}
                />
              </motion.div>
            );
          })
        )}
      </Stack>
      <AssignedExclusiveOfferDialog offer={lastExclusiveOffer} />
    </>
  );
};

export default ExclusiveInternshipOffers;
