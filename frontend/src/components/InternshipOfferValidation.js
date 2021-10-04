import {
  List,
  ListItemText,
  ListItem,
  Divider,
  ListItemButton,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
} from "@mui/material";
import { fontWeight } from "@mui/system";
import axios from "axios";
import React from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
const emptyOffer = {
  limitDateToApply: new Date(),
  beginningDate: new Date(),
  endingDate: new Date(),
  emailOfMonitor: "",
  companyName: "",
  minSalary: 0,
  maxSalary: 0,
  description: "",
};
function InternshipOfferValidation() {
  const [unvalidatedOffers, setUnvalidatedOffers] = React.useState([]);
  const [userInformation] = React.useContext(UserInfoContext);
  const [token, setToken] = React.useState(sessionStorage.getItem("jwt"));
  React.useEffect(() => {
    const getUnvalidatedInternshipOffers = async () => {
      let response = await axios({
        method: "GET",
        url: "http://localhost:8080/internshipOffer/unvalidatedOffers",
        headers: {
          Authorization: token,
        },
        responseType: "json",
      });
      console.log("res data", response.data);
      setUnvalidatedOffers(response.data);
    };
    getUnvalidatedInternshipOffers();
  }, []);
  return (
    <>
      {unvalidatedOffers.length > 0 && (
        <Table>
          <TableHead>
            {Object.keys(emptyOffer).map((offerKey, key) => {
              return (
                <TableCell sx={{ font: "bold" }} key={key}>
                  {offerKey}
                </TableCell>
              );
            })}
          </TableHead>
          {unvalidatedOffers.map((offer, key) => {
            return (
              <>
                <TableBody key={key}>
                  <TableCell>
                    {Object.values(offer).map((offerKey, key) => {
                      return <ListItemText key={key}>{offerKey}</ListItemText>;
                    })}
                  </TableCell>
                </TableBody>
                <Divider />
              </>
            );
          })}
        </Table>
      )}
    </>
  );
}

export default InternshipOfferValidation;
