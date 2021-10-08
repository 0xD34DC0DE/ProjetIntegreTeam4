import AddIcon from "@mui/icons-material/Add";
import RuleIcon from "@mui/icons-material/Rule";
import FileCopyIcon from "@mui/icons-material/FileCopy";
import FileUploadIcon from "@mui/icons-material/FileUpload";
import SearchIcon from '@mui/icons-material/Search';

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
    url: "/validerOffreStage",
    icon: <RuleIcon />,
    text: "Valider des offres de stage",
  },
  {
    roles: ["INTERNSHIP_MANAGER"],
    url: "/validerCV",
    icon: <FileCopyIcon />,
    text: "Valider les C.V.",
  },
  {
    roles: ["STUDENT"],
    url: "/televerserCV",
    icon: <FileUploadIcon />,
    text: "Téléverser le C.V.",
  },
  {
    roles: ["STUDENT"],
    url: "/offres",
    icon: <SearchIcon />,
    text: "Voir les offres",
  },
  
];
