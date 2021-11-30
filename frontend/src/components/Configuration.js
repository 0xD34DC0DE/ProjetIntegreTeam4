import {
  AccountCircleOutlined,
  DashboardOutlined,
  SettingsOutlined,
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
  AssignmentSharp,
  ManageSearch,
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
    explication: (
      <Grid>
        <Typography>
          Visualisez le CV directement dans l'application.
        </Typography>
        <Typography>Téléchargez le CV de l'étudiant.</Typography>
        <Typography>
          Approuvez ou refusez le CV, en pouvant préciser la rasion du refus si
          tel est le cas.
        </Typography>
      </Grid>
    ),
  },
  {
    id: 2,
    label: "Ajouter une offre de stage",
    role: ["INTERNSHIP_MANAGER", "MONITOR"],
    icon: <AddCircleOutlineOutlined sx={{ mr: 1 }} />,
    isDialog: true,
    dialogName: "internshipOfferDialog",
    description: "",
    explication: (
      <Typography>
        Entrez les informations concernant l'offre que vous souhaitez ajouter.
      </Typography>
    ),
  },
  {
    id: 4,
    label: "Voir les offres de stage",
    role: ["STUDENT"],
    icon: <SearchOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Liste d'offres de stage disponible",
    explication: (
      <Grid>
        <Typography>
          Consultez les offres de stage disponible à tous, ainsi que les offres
          de stage exclusives.
        </Typography>
        <Typography>Appliquez aux offres après les avoir consulté.</Typography>
      </Grid>
    ),
  },
  {
    id: 5,
    label: "Vos C.V.",
    role: ["STUDENT"],
    icon: <FileUploadOutlined sx={{ mr: 1 }} />,
    isDialog: false,
    description:
      "Téléversez votre CV en le glissant ou en cliquant sur la boite ci-dessous",
    explication: (
      <Grid>
        <Typography>
          Téléversez votre CV et consultez tous ceux que vous avez déjà ajouté.
        </Typography>
        <Typography>
          Dans le cas d'un refus de votre CV, consultez sa raison pour pouvoir
          en déposez un autre en appliquant les commentaires suggérés.
        </Typography>
      </Grid>
    ),
  },
  {
    id: 6,
    label: "Liste des candidatures",
    role: ["MONITOR"],
    isDialog: false,
    icon: <FormatListBulletedOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant postulés à l'une de vos offres",
    explication: (
      <Grid>
        <Typography>
          Consultez la liste des élèves qui ont appliqués aux offres de stage
          que vous avez déposé.
        </Typography>
        <Typography>
          Pour chacun des élèves, vous pouvez télécharger leur CV ou le
          consulter dans l'application, contacter l'étudiant et démarrer le
          processus de signature de contrat.
        </Typography>
      </Grid>
    ),
  },
  {
    id: 10,
    label: "Élèves assigné(e)s",
    role: ["SUPERVISOR"],
    isDialog: false,
    icon: <PeopleAltOutlined sx={{ mr: 1 }} />,
    description: "Liste des étudiants ayant été assigné",
    explication: (
      <Grid>
        <Typography>
          Consultez la liste des élèves qui vous ont été assignés ainsi que le
          statut du processus de recherche de stage.
        </Typography>
        <Typography>
          Pour les élèves ayant trouvez un stage, consultez les informations
          concernant le stage de l'élève.
        </Typography>
      </Grid>
    ),
  },
  {
    id: 11,
    label: "Assigner les élèves aux superviseurs",
    role: ["INTERNSHIP_MANAGER"],
    icon: <SupervisedUserCircleOutlined fontSize="small" sx={{ mr: 1 }} />,
    description: "Assignations des superviseurs aux élèves",
    explication: (
      <Typography>
        Assignez les étudiants aux professeurs qui seront responsables de
        superviser l'étudiant durant son stage.
      </Typography>
    ),
  },
  {
    id: 12,
    label: "Évaluer un stagiaire",
    role: ["MONITOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à la fin de son contrat",
    explication: (
      <Typography>
        Remplissez le formulaire d'évaluation de l'étudiant voulu une fois sont
        stage terminé.
      </Typography>
    ),
  },
  {
    id: 13,
    label: "Évaluer un stagiaire (mi-mandat)",
    role: ["SUPERVISOR"],
    icon: <FindInPageOutlined sx={{ mr: 1 }} />,
    description: "Évaluation du stagiaire à son mi-mandat",
    explication: (
      <Typography>
        Remplissez le formulaire d'évaluation de l'étudiant voulu lorsque son
        stage est arrivé à la la moitié du mandat.
      </Typography>
    ),
  },
  {
    id: 14,
    label: "Consulter les rapports",
    role: ["INTERNSHIP_MANAGER"],
    icon: <AssignmentSharp fontSize="small" sx={{ mr: 1 }} />,
    description: "Consultation des rapports",
    explication: (
      <Typography>
        Consultez les rapports concernants divers informations sur l'état des
        offres de stage, des étudiants et des tout ce qui tourne autour.
      </Typography>
    ),
  },
  {
    id: 15,
    label: "Gérer les offres de stage",
    role: ["INTERNSHIP_MANAGER"],
    icon: <ManageSearch sx={{ mr: 1 }} />,
    isDialog: false,
    description: "Valider les offres de stage et les rendre exclusives",
    explication: (
      <Grid>
        <Typography>Valider les offres de stage des moniteurs.</Typography>
        <Typography>
          Assigner des élèves à des offres de stage exclusives
        </Typography>
      </Grid>
    ),
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
