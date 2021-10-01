import AddIcon from "@mui/icons-material/Add";
import RuleIcon from "@mui/icons-material/Rule";

export const drawerListDialogs = [
  {
    roles: ["INTERNSHIP_MANAGER", "MONITOR"],
    name: "internshipOfferDialog",
    icon: <AddIcon />,
    text: "Déposer des offres de stage",
  },
  {
    roles: ["INTERNSHIP_MANAGER"],
    name: "internshipOfferDialog",
    icon: <RuleIcon />,
    text: "Valider des offres de stage",
  },
];
