import { Typography } from "@mui/material";
const StudentState = ({ studentState }) => {
  switch (studentState) {
    case "NO_INTERVIEW":
      return (
        <Typography sx={{ border: "2px white solid" }}>
          Nouvel étudiant
        </Typography>
      );
    case "WAITING_INTERVIEW":
      return (
        <Typography sx={{ border: "2px red solid" }}>
          En attente d'entrevue
        </Typography>
      );
    case "WAITING_FOR_RESPONSE":
      return (
        <Typography sx={{ border: "2px orange solid" }}>
          En attente de la réponse
        </Typography>
      );
    case "INTERNSHIP_FOUND":
      return (
        <Typography sx={{ border: "2px green solid" }}>Stage trouvé</Typography>
      );
    default:
      break;
  }
};

export default StudentState;
