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
import { useHistory } from "react-router";
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
  const history = useHistory();
  const [userInfo] = React.useContext(UserInfoContext);
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

    const goBackToHome = () => {
      if (!userInfo.loggedIn) {
        history.push("/");
      }
    };
    goBackToHome();
    getUnvalidatedInternshipOffers();
  }, [userInfo]);

  return (
    <>
      {unvalidatedOffers.length > 0 && (
        <Table>
          <TableHead sx={{ textAlign: "center" }}>
            {Object.keys(emptyOffer).map((offerKey, key) => {
              return (
                <TableCell key={key} sx={{ borderBottom: 0 }}>
                  {offerKey}
                </TableCell>
              );
            })}
          </TableHead>
          {unvalidatedOffers.map((offer, key) => {
            return (
              <>
                <TableBody key={key} sx={{ textAlign: "center" }}>
                  {Object.values(offer).map((offerValue, key) => {
                    return <TableCell key={key}>{offerValue}</TableCell>;
                  })}
                </TableBody>
              </>
            );
          })}
        </Table>
      )}
    </>
  );
}

export default InternshipOfferValidation;
