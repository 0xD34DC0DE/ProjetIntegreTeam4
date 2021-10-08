import {
  Button,
  Dialog,
  DialogContent,
  Tooltip,
  Typography,
  Container,
  Paper,
} from "@mui/material";
import React from "react";
import Tag from "@mui/icons-material/Tag";
import { CancelOutlined, CheckCircleOutline } from "@mui/icons-material";
import { listLabels } from "./InternshipOfferLabels";
import axios from "axios";

const InternshipOfferDescriptionDialog = ({
  dialogVisible,
  toggleDialogs,
  offer,
  removeInternshipOffer,
}) => {
  const [token, setToken] = React.useState(sessionStorage.getItem("jwt"));

  const handleClose = (_, reason) => {
    console.log("reason", reason);
    if (reason === "backdropClick")
      toggleDialogs("internshipOfferDialogValidation", false);
    console.log("dialogVisible", dialogVisible);
  };

  const validateInternshipOffer = async (id) => {
    await axios({
      method: "PATCH",
      url: `http://localhost:8080/internshipOffer/validateInternshipOffer?id=${id}`,
      headers: {
        Authorization: token,
      },
      responseType: "json",
    });
    toggleDialogs("internshipOfferDialogValidation", false);
    removeInternshipOffer(offer);
  };

  const refuseInternshipOffer = async (id) => {
    await axios({
      method: "PATCH",
      url: `http://localhost:8080/internshipOffer/refuseInternshipOffer?id=${id}`,
      headers: {
        Authorization: token,
      },
      responseType: "json",
    });
    toggleDialogs("internshipOfferDialogValidation", false);
    removeInternshipOffer(offer);
  };

  return (
    <Dialog open={dialogVisible} onClose={handleClose}>
      <DialogContent
        sx={{
          p: 0,
          pt: "5%",
          overflow: "auto",
          textAlign: "center",
        }}
      >
        <Typography variant="h6" fontSize="2rem" sx={{ m: "2%" }}>
          Détails
        </Typography>
        {offer && (
          <>
            <Tooltip title={`ID: ${offer.id}`} followCursor={true}>
              <Tag
                sx={{ position: "absolute", top: 0, right: 5, p: "5%" }}
                color="primary"
              />
            </Tooltip>
            {Object.keys(offer).map((offerKey, key) => {
              return (
                <>
                  {!["id", "listEmailInterestedStudents", "validated"].includes(
                    offerKey
                  ) && (
                    <Container key={key} sx={{ textAlign: "left" }}>
                      <Typography
                        variant="overline"
                        gutterBottom={true}
                        align="justify"
                      >
                        {listLabels[key]}
                      </Typography>
                      <Typography
                        variant="subtitle2"
                        sx={{ display: "inline-block", m: "1%" }}
                        align="justify"
                      >
                        {Object.values(offer)[key]}
                        {offerKey.includes("Salary") && "$"}
                      </Typography>
                    </Container>
                  )}
                </>
              );
            })}
          </>
        )}
        <Container
          sx={{
            margin: "auto",
            position: "sticky",
            display: "block",
            left: 0,
            bottom: 0,
          }}
        >
          <Paper>
            <Tooltip title="Valider" arrow={true}>
              <Button>
                <CheckCircleOutline
                  color="primary"
                  fontSize="large"
                  onClick={() => validateInternshipOffer(offer.id)}
                />
              </Button>
            </Tooltip>
            <Tooltip title="Refuser" arrow={true}>
              <Button>
                <CancelOutlined
                  sx={{ color: "red" }}
                  fontSize="large"
                  onClick={() => refuseInternshipOffer(offer.id)}
                />
              </Button>
            </Tooltip>
          </Paper>
        </Container>
      </DialogContent>
    </Dialog>
  );
};

export default InternshipOfferDescriptionDialog;
