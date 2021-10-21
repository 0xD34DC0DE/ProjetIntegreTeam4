import {
  List,
  ListItem,
  ListItemButton,
  Tooltip,
  Typography,
  Paper,
} from "@mui/material";
import axios from "axios";
import React from "react";
import { useHistory } from "react-router";
import { UserInfoContext } from "../stores/UserInfoStore";
import InternshipOfferDialog from "./InternshipOfferDialog";
import { listLabels } from "./InternshipOfferLabels";

const isKeyIncluded = (offerKey) => {
  return ![
    "id",
    "listEmailInterestedStudents",
    "companyName",
    "description",
    "validated",
  ].includes(offerKey);
};

const InternshipOfferValidation = ({
  internshipOfferDialogVisible,
  toggleDialogs,
}) => {
  const [unvalidatedOffers, setUnvalidatedOffers] = React.useState([]);
  const [companies, setCompanies] = React.useState([]);
  const history = useHistory();
  const [userInfo] = React.useContext(UserInfoContext);
  const [token, setToken] = React.useState(sessionStorage.getItem("jwt"));
  const [selectedOffer, setSelectedOffer] = React.useState(null);
  React.useEffect(() => {
    const getUnvalidatedInternshipOffers = async () => {
      let response = await axios({
        method: "GET",
        url: "http://localhost:8080/internshipOffer/getNotYetValidatedInternshipOffers",
        headers: {
          Authorization: token,
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

  React.useEffect(() => {
    const goBackToHome = () => {
      if (!userInfo.loggedIn) {
        history.push("/");
        console.log("time", new Date(performance.now()));
      }
    };

    goBackToHome();
  });

  return (
    <>
      <List
        sx={{
          width: "100vw",
          mt: 10,
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
                my: "5vh",
                px: "1vw",
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
                  mx: 2,
                  borderRadius: "15px",
                  backgroundColor: "rgb(34, 167, 240)",
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
                          ":hover": {
                            boxShadow: 6,
                          },
                        }}
                        onClick={() => {
                          toggleDialogs(
                            "internshipOfferDialogValidation",
                            true
                          );
                          setSelectedOffer(offer);
                        }}
                      >
                        {Object.keys(offer).map((offerKey, key) => {
                          return (
                            <>
                              {isKeyIncluded(offerKey) && (
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
      <InternshipOfferDialog
        dialogVisible={internshipOfferDialogVisible}
        toggleDialogs={toggleDialogs}
        offer={selectedOffer}
        unvalidatedOffers={unvalidatedOffers}
        setUnvalidatedOffers={setUnvalidatedOffers}
        removeInternshipOffer={removeInternshipOffer}
      />
    </>
  );
};

export default InternshipOfferValidation;
