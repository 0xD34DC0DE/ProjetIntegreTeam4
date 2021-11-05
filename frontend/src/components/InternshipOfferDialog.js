import { CancelOutlined, CheckCircleOutline } from "@mui/icons-material";
import {
  Button,
  Container,
  Dialog,
  DialogContent,
  Paper,
  Tooltip,
  Typography,
} from "@mui/material";
import axios from "axios";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { listLabels } from "./InternshipOfferLabels";

const InternshipOfferDescriptionDialog = ({
  open,
  toggleDialog,
  offer,
  removeInternshipOffer,
}) => {
  const [userInfo] = useContext(UserInfoContext);

  const handleClose = (_, reason) => {
    if (reason === "backdropClick")
      toggleDialog("internshipOfferDialogValidation", false);
  };

  const validateInternshipOffer = async (id, valid) => {
    await axios({
      method: "PATCH",
      url: "http://localhost:8080/internshipOffer/validateInternshipOffer",
      headers: {
        Authorization: userInfo.jwt,
      },
      params: {
        id: id,
        isValid: valid,
      },
      responseType: "json",
    });
    toggleDialog("internshipOfferDialogValidation", false);
    removeInternshipOffer(offer);
  };

  return (
    <Dialog open={open} onClose={handleClose}>
      <DialogContent
        sx={{
          p: 0,
          pt: "5%",
          overflow: "auto",
          textAlign: "center",
        }}
      >
        <Typography variant="h6" fontSize="2.5rem" sx={{ m: "2%" }}>
          Détails
        </Typography>
        {offer && (
          <>
            {Object.keys(offer).map((identifier, key) => {
              return (
                <>
                  {identifier !== "id" && (
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
                        {identifier.includes("Salary") && "$"}
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
          <Paper
            sx={{ backgroundColor: "rgba(50, 50, 50, 0.2)", mb: 2, mt: 1 }}
          >
            <Tooltip title="Valider" arrow={true}>
              <Button>
                <CheckCircleOutline
                  color="primary"
                  fontSize="large"
                  sx={{ color: "green" }}
                  onClick={() => validateInternshipOffer(offer.id, true)}
                />
              </Button>
            </Tooltip>
            <Tooltip title="Refuser" arrow={true}>
              <Button>
                <CancelOutlined
                  sx={{ color: "red" }}
                  fontSize="large"
                  onClick={() => validateInternshipOffer(offer.id, false)}
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
