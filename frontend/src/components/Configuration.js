import {
  AccountCircleOutlined,
  AddCircleOutlineOutlined,
  AssignmentSharp,
  DashboardOutlined,
  FactCheckOutlined,
  FileUploadOutlined,
  FindInPageOutlined,
  FormatListBulletedOutlined,
  HomeOutlined,
  ManageSearch,
  PeopleAltOutlined,
  SearchOutlined,
  SettingsOutlined,
  SupervisedUserCircleOutlined,
} from "@mui/icons-material";
import { Grid, Typography } from "@mui/material";

export const sidebarList = [
  {
    id: 0,
    label: "Accueil",
    role: ["MONITOR", "INTERNSHIP_MANAGER", "STUDENT", "SUPERVISOR"],
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
    explication: [
      "Visualiser le CV directement dans l'application",
      "Télécharger le CV de l'étudiant",
      "Approuver ou refuser le CV, en pouvant préciser la raison du refus si tel est le cas",
    ],
  },
  {
    id: 2,
    label: "Ajouter une offre de stage",
    role: ["INTERNSHIP_MANAGER", "MONITOR"],
    icon: <AddCircleOutlineOutlined sx={{ mr: 1 }} />,
    isDialog: true,
    dialogName: "internshipOfferDialog",
    description: "",
    explication: [
      "Entrer les informations concernant l'offre que vous souhaitez ajouter",
    ],
  },
  {
    id: 4,
    label: "Voir les offres de stage",
    role: ["STUDENT"],
    icon: <SearchOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Liste d'offres de stage disponible",
    explication: [
      "Consulter les offres de stage disponibles à tous, ainsi que les offres de stage qui vous sont exclusives",
      "Appliquer aux offres après avoir consulté leurs détails",
    ],
  },
  {
    id: 5,
    label: "Vos CV",
    role: ["STUDENT"],
    icon: <FileUploadOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description:
      "Téléverser votre CV en le glissant ou en cliquant sur la boîte ci-dessous",
    explication: [
      "Téléverser votre CV et consulter tous ceux que vous avez déjà ajouté",
      "Dans le cas d'un refus de votre CV, consulter sa raison pour pouvoir en déposer un autre en appliquant les commentaires suggérés",
    ],
  },
  {
    id: 6,
    label: "Liste des candidatures",
    role: ["MONITOR"],
    isDialog: false,
    icon: <FormatListBulletedOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant postulés à l'une de vos offres",
    explication: [
      "Consulter la liste des élèves qui ont appliquées aux offres de stage que vous avez déposé",
      "Pour chacun des élèves, vous pouvez télécharger leur CV ou le consulter dans l'application, contacter l'étudiant et démarrer le processus de signature de contrat",
    ],
  },
  {
    id: 10,
    label: "Élèves assigné(e)s",
    role: ["SUPERVISOR"],
    isDialog: false,
    icon: <PeopleAltOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant été assignés",
    explication: [
      "Consulter la liste des élèves qui vous ont été assignés ainsi que le statut du processus de recherche de stage",
      "Pour les élèves ayant trouvé un stage, consultez les informations concernant le stage de l'élève",
    ],
  },
  {
    id: 11,
    label: "Assigner les élèves aux superviseurs",
    role: ["INTERNSHIP_MANAGER"],
    icon: <SupervisedUserCircleOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Assignations des superviseurs aux élèves",
    explication: [
      "Assigner les étudiants aux professeurs qui seront responsables de superviser l'étudiant durant son stage",
    ],
  },
  {
    id: 12,
    label: "Évaluer un stagiaire",
    role: ["MONITOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à la fin de son mandat",
    explication: [
      "Remplir le formulaire d'évaluation de l'étudiant une fois son stage terminé",
    ],
  },
  {
    id: 13,
    label: "Évaluer un stagiaire (mi-mandat)",
    role: ["SUPERVISOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à son mi-mandat",
    explication: [
      "Remplir le formulaire d'évaluation de l'étudiant à la moitié du mandat",
    ],
  },
  {
    id: 14,
    label: "Consulter les rapports",
    role: ["INTERNSHIP_MANAGER"],
    icon: <AssignmentSharp fontSize="small" sx={{ mr: 1 }} />,
    description: "Consultation des rapports",
    explication: [
      "Consulter les rapports de l'état des offres de stage, des étudiants inscrits, etc",
    ],
  },
  {
    id: 15,
    label: "Gérer les offres de stage",
    role: ["INTERNSHIP_MANAGER"],
    icon: <ManageSearch sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Valider les offres de stage et les rendre exclusives",
    explication: [
      "Valider les offres de stage des moniteurs.",
      "Assigner des élèves à des offres de stage exclusives",
    ],
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
    icon: <DashboardOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Espace personnelle",
    disabled: true,
  },
  {
    id: 9,
    label: "Paramètres",
    icon: <SettingsOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Paramètres",
  },
];

export const roles = {
  STUDENT: "Étudiant",
  INTERNSHIP_MANAGER: "Gestionnaire de stage",
  MONITOR: "Moniteur",
  SUPERVISOR: "Superviseur",
};
