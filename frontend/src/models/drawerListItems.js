import AddIcon from "@mui/icons-material/Add";
import RuleIcon from "@mui/icons-material/Rule";
import FileCopyIcon from "@mui/icons-material/FileCopy";

export const drawerListDialogs = [
  {
    roles: ["INTERNSHIP_MANAGER", "MONITOR"],
    name: "internshipOfferDialog",
    icon: <AddIcon />,
    text: "Déposer des offres de stage",
  },
];

export const drawerListRoutes = [
  {
    roles: ["INTERNSHIP_MANAGER"],
    url: "/internshipOfferValidation",
    icon: <RuleIcon />,
    text: "Valider des offres de stage",
  },
  {
    roles: ["INTERNSHIP_MANAGER"],
    url: "/cvValidation",
    icon: <FileCopyIcon />,
    text: "Valider les C.V.",
  },
];
