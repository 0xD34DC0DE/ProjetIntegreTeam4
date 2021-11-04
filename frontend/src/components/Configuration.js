import {
  AccountCircleOutlined,
  PeopleAltOutlined,
  HomeOutlined,
  FindInPageOutlined,
  SearchOutlined,
  FileUploadOutlined,
  FactCheckOutlined,
  AddCircleOutlineOutlined,
  CheckOutlined,
  SupervisedUserCircleOutlined,
  FormatListBulletedOutlined,
} from "@mui/icons-material";

export const sidebarList = [
  {
    id: 0,
    label: "Acceuil",
    role: ["MONITOR", "INTERNSHIP_MANAGER", "STUDENT"],
    icon: <HomeOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Accueil",
  },
  {
    id: 1,
    label: "Valider les CV des étudiants",
    role: ["INTERNSHIP_MANAGER"],
    icon: <FactCheckOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Liste de CV en attente de validation",
  },
  {
    id: 2,
    label: "Ajouter une offre de stage",
    role: ["INTERNSHIP_MANAGER", "MONITOR"],
    icon: <AddCircleOutlineOutlined sx={{ mr: 1 }} />,
    isDialog: true,
    dialogName: "internshipOfferDialog",
    description: "",
  },
  {
    id: 3,
    label: "Valider les offres de stage",
    role: ["INTERNSHIP_MANAGER"],
    icon: <CheckOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Liste d'offres de stage en attente de validation",
  },
  {
    id: 4,
    label: "Voir les offres de stage",
    role: ["STUDENT"],
    icon: <SearchOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Liste d'offres de stage disponible",
  },
  {
    id: 5,
    label: "Téléverser son CV",
    role: ["STUDENT"],
    icon: <FileUploadOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description:
      "Téléverser votre CV en le glissant ou en cliquant sur la boite ci-dessous",
  },
  {
    id: 6,
    label: "Liste des postulations",
    role: ["MONITOR"],
    isDialog: false,
    icon: <FormatListBulletedOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant postulés à l'une de vos offres",
  },
  {
    id: 10,
    label: "Élèves assigné(e)s",
    role: ["SUPERVISOR"],
    isDialog: false,
    icon: <PeopleAltOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant été assigné",
  },
  {
    id: 11,
    label: "Assignation superviseurs",
    role: ["INTERNSHIP_MANAGER"],
    icon: <SupervisedUserCircleOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Assignations des superviseurs aux élèves",
  },
  {
    id: 12,
    label: "Évaluer un stagiaire",
    role: ["MONITOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à la fin de son contrat",
  },
  {
    id: 13,
    label: "Évaluer un stagiaire (mi-mandat)",
    role: ["SUPERVISOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à son mi-mandat",
  },
];

export const topbarMenuList = [
  {
    id: 7,
    label: "Profile",
    icon: <AccountCircleOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Profile",
  },
  {
    id: 8,
    label: "Espace Personnelle",
    icon: <FindInPageOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Espace personnelle",
  },
  {
    id: 9,
    label: "Paramètres",
    icon: <FindInPageOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Paramètres",
  },
];

export const roles = {
  STUDENT: "Étudiant",
  INTERNSHIP_MANAGER: "Gestionnaire de stage",
  MONITOR: "Moniteur",
  SUPERVISOR: "Superviseur",
};
