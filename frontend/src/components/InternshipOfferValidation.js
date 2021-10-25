import {
  List,
  ListItem,
  ListItemButton,
  Tooltip,
  Typography,
  Paper,
  Grid,
} from "@mui/material";
import axios from "axios";
import React, { useState, useEffect, useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import InternshipOfferDialog from "./InternshipOfferDialog";
import { listLabels } from "./InternshipOfferLabels";

const InternshipOfferValidation = ({
  dialogVisibility,
  toggleDialog,
  visible,
}) => {
  const [unvalidatedOffers, setUnvalidatedOffers] = useState([]);
  const [companies, setCompanies] = useState([]);
  const [userInfo] = useContext(UserInfoContext);

  const [selectedOffer, setSelectedOffer] = useState(null);
  useEffect(() => {
    const getUnvalidatedInternshipOffers = async () => {
      let response = await axios({
        method: "GET",
        url: "http://localhost:8080/internshipOffer/getNotYetValidatedInternshipOffers",
        headers: {
          Authorization: userInfo.jwt,
        },
        responseType: "json",
      });
      var companiesName = [
        ...new Set(Array.from(response.data, ({ companyName }) => companyName)),
      ];
      setCompanies(companiesName);
      setUnvalidatedOffers(response.data);
    };
    getUnvalidatedInternshipOffers();
  }, []);

  const removeInternshipOffer = (offer) => {
    var index = unvalidatedOffers.indexOf(offer);
    unvalidatedOffers.splice(index, 1);
    setUnvalidatedOffers(unvalidatedOffers);
    setCompanies([
      ...new Set(
        Array.from(unvalidatedOffers, ({ companyName }) => companyName)
      ),
    ]);
  };

  return (
    <>
      {visible && (
        <Grid container>
          <List
            sx={{
              width: "100vw",
              pt: 0,
            }}
          >
            {companies.map((name, key) => {
              return (
                <Paper
                  key={key}
                  className={name}
                  elevation={15}
                  sx={{
                    mx: "5vw",
                    mb: "5vh",
                    px: "1vw",
                    backgroundColor: "rgba(135, 135, 135, 0.05)",
                    py: "1vw",
                    overflow: "auto",
                    borderRadius: "10px",
                  }}
                >
                  <Typography
                    key={key}
                    variant="h4"
                    sx={{
                      display: "inline-block",
                      p: "1rem",
                      color: "white",
                      pt: 2,
                      borderRadius: "15px",
                      backgroundColor: "rgba(0, 0, 0, 0.2)",
                      mb: 2,
                    }}
                  >
                    {name}
                  </Typography>
                  {unvalidatedOffers.map((offer, key) => {
                    if (name === offer.companyName) {
                      return (
                        <Tooltip
                          key={key}
                          title="Voir les détails"
                          placement="top"
                          followCursor={true}
                        >
                          <ListItemButton
                            key={key}
                            sx={{
                              margin: "auto",
                              boxShadow: "5px 5px 5px rgba(0, 0, 0, 0.5)",
                              backgroundColor: "rgba(0, 0, 0, 0.1)",
                              ":hover": {
                                boxShadow: 5,
                                backgroundColor: "rgba(50, 50, 50, 0.2)",
                              },
                              my: 1,
                            }}
                            onClick={() => {
                              toggleDialog(
                                "internshipOfferDialogValidation",
                                true
                              );
                              setSelectedOffer(offer);
                            }}
                          >
                            {Object.keys(offer).map((offerKey, key) => {
                              return (
                                <>
                                  {![
                                    "id",
                                    "listEmailInterestedStudents",
                                    "companyName",
                                    "description",
                                    "validated",
                                  ].includes(offerKey) && (
                                    <Tooltip
                                      key={key}
                                      title={listLabels[key]}
                                      sx={{
                                        alignItems: "center",
                                        justifyContent: "center",
                                      }}
                                    >
                                      <ListItem key={key}>
                                        {Object.values(offer)[key]}
                                        {offerKey.includes("Salary") && "$"}
                                      </ListItem>
                                    </Tooltip>
                                  )}
                                </>
                              );
                            })}
                          </ListItemButton>
                        </Tooltip>
                      );
                    }
                  })}
                </Paper>
              );
            })}
          </List>
        </Grid>
      )}
      <InternshipOfferDialog
        open={dialogVisibility.internshipOfferDialogValidation}
        toggleDialog={toggleDialog}
        offer={selectedOffer}
        unvalidatedOffers={unvalidatedOffers}
        setUnvalidatedOffers={setUnvalidatedOffers}
        removeInternshipOffer={removeInternshipOffer}
      />
    </>
  );
};

export default InternshipOfferValidation;
