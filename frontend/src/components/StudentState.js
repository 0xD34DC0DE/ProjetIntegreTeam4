import { Typography } from "@mui/material";
const StudentState = ({ studentState }) => {
  switch (studentState) {
    case "WAITING_FOR_RESPONSE":
      return (
        <Typography sx={{ border: "2px orange solid" }}>
          En attente de la réponse
        </Typography>
      );
    case "WAITING_INTERVIEW":
      return (
        <Typography sx={{ border: "2px red solid" }}>
          En attente d'entrevue
        </Typography>
      );
    case "INTERNSHIP_FOUND":
      return (
        <Typography sx={{ border: "2px green solid" }}>Stage trouvé</Typography>
      );
    case "REGISTERED":
      return (
        <Typography sx={{ border: "2px white solid" }}>
          Nouvel étudiant
        </Typography>
      );
    default:
      break;
  }
};

export default StudentState;
