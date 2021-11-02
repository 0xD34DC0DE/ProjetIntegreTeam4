import { TextField, Typography } from "@mui/material";

export const endEvaluation = [
  {
    section: 1,
    title: "Productivité",
    description: "Capacité d'optimiser son rendement au travail",
    tasks: [
      {
        label: "Planifier et organiser son travail de façon efficace",
        id: "organizeEfficiently",
      },
      {
        label: "Comprendre rapidement les directives relatives à son travail",
        id: "understandFast",
      },
      { label: "Maintenir un rythme de travail soutenu", id: "keepRhythm" },
      { label: "Établir ses priorités", id: "establishPriorities" },
      { label: "Respecter ses échéanciers", id: "respectDeadlines" },
    ],
    comment: { label: "Commentaires :", id: "productivityComment" },
  },
  {
    section: 2,
    title: "Qualité du travail",
    description:
      "Capacité de s'acquitter des tâches sous sa responsabilité en s'imposant personnellement des normes de qualité",
    tasks: [
      {
        label: "Respecter les mandats qui lui on été confiés",
        id: "respectMandates",
      },
      {
        label: "Porter attention aux détails dans la réalisation de ses tâches",
        id: "payAttentionDetails",
      },
      {
        label: "Vérifier son travail, s'assurer que rien n'a été oublié",
        id: "verifyWork",
      },
      {
        label: "Rechercher ses occasions de se perfectionner",
        id: "perfectionate",
      },
      {
        label: "Faire une bonne analyse des problèmes rencontrés",
        id: "problemSolving",
      },
    ],
    comment: { label: "Commentaires :", id: "workQualityComment" },
  },
  {
    section: 3,
    title: "Qualités des relations interpersonnelles",
    description:
      "Capacité d'établir des interrelations harmonieuses dans son milieu de travail",
    tasks: [
      {
        label: "Établir facilement des contacts avec les gens",
        id: "approchable",
      },
      {
        label: "Contribuer activement au travail d'équipe",
        id: "contributeTeamwork",
      },
      {
        label: "S'adapter facilement à la culture de l'entreprise",
        id: "adaptCompany",
      },
      {
        label: "Accepter les critiques constructives",
        id: "acceptCriticism",
      },
      { label: "Être respectueux envers les gens", id: "respectful" },
      {
        label:
          "Faire preuve d'écoute active en essayant de comprendre le point de vue de l'autre",
        id: "listenOthers",
      },
    ],
    comment: {
      label: "Commentaires :",
      id: "interpersonalRelationshipsComment",
    },
  },
  {
    section: 4,
    title: "Habiletés personnelles",
    description:
      "Capacité de faire preuve d'attitudes ou de comportements matures et responsables",
    tasks: [
      {
        label: "Démontrer de l'intérêt et de la motivation au travail",
        id: "showMotivation",
      },
      { label: "Exprimer clairement ses idées", id: "expressClearly" },
      { label: "Faire preuve d'initiative", id: "takeInitiative" },
      { label: "Travailler de façon sécuritaire", id: "workSafely" },
      {
        label:
          "Démontrer un bon sens des responsabilités ne réquérant qu'un minimum de supervision",
        id: "showAutonomy",
      },
      { label: "Être ponctuel et assidu à son travail", id: "punctual" },
    ],
    comment: { label: "Commentaires :", id: "personalSkillsComment" },
  },
];

export const ratings = [
  { value: 5, label: "Totalement en accord" },
  { value: 4, label: "Plutôt en accord" },
  { value: 3, label: "Plutôt en désaccord" },
  {
    value: 2,
    label: "Totalement en désaccord",
  },
  { value: 1, label: "N/A" },
];

export const contactDetails = [
  { label: "Nom de l'élève", id: "studentFullName" },
  { label: "Programme d'études", id: "studyProgram" },
  { label: "Nom de l'entreprise", id: "companyName" },
  { label: "Nom du superviseur", id: "supervisorFullName" },
  { label: "Fonction", id: "fonction" },
  { label: "Téléphone", id: "phoneNumber" },
];

export const companyAppreciation = {
  id: "studentAppreciation",
  ratings: [
    {
      label: "Les habiletés démontrées dépassent de beaucoup les attentes",
      value: 5,
    },
    { label: "Les habiletés démontrées dépasent les attentes", value: 4 },
    {
      label: "Les habiletés démontrées répondent pleinement aux attentes",
      value: 3,
    },
    {
      label: "Les habiletés démontrées répondent partiellement aux attentes",
      value: 2,
    },
    {
      label: "Les habiletés démontrées ne répondent pas aux attentes",
      value: 1,
    },
  ],
};

export const studentIdentification = {
  title: "Identification du stagiare",
  description: "Information du stagiaire évalué",
  fields: [
    { label: "Nom du stagiaire", id: "studentName" },
    { label: "Date du stage", id: "internshipDate" },
  ],
};

export const companyIdentification = {
  title: "Identification de l'entreprise",
  description: "Information sur la compagnie offrant le stage",
  fields: [
    { label: "Nom de l'entreprise", id: "companyName" },
    { label: "Contact", id: "contact" },
    { label: "Adresse", id: "address" },
    { label: "Ville", id: "city" },
    { label: "Numéro de téléphone", id: "pĥoneNumber" },
    { label: "Télécopieur", id: "faxMachine" },
    { label: "Code postal", id: "zipCode" },
  ],
};

export const midTermEvaluation = {
  title: "Évaluation",
  description: "Évaluation du stagiaire mi-mandat",
  tasks: [
    {
      label:
        "Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l'entente de stage",
      id: "taskRespectAgreement",
    },
    {
      label:
        "Des mesures d'accueil facilitent l'intégration du nouveau stagiaire.",
      id: "internIntegrationMesures",
    },
    {
      label:
        "Le temps réel consacré à l'encadrement du stagiaire est suffisant",
      id: "internSupervisionTimeAllocated",
      precision: (
        <>
          <Typography variant="caption">
            Préciser le nombre d'heures/semaine :
          </Typography>
          <br />
          {["Premier", "Deuxième", "Troisième"].map((value, key) => {
            return (
              <>
                <Typography variant="caption">{value} mois: </Typography>
                <TextField
                  variant="standard"
                  sx={{
                    "& .MuiInput-input": {
                      fontSize: "0.8em",
                      textAlign: "center",
                    },
                  }}
                ></TextField>
              </>
            );
          })}
        </>
      ),
    },
    {
      label:
        "L'environnement de travail respecte les normes d'hygiène et de sécurité au travail",
      id: "workEnvironnement",
    },
    { label: "Le climat de travail est agréable", id: "pleasantWorkClimate" },
    {
      label: "Le milieu de stage est accessible par transport en commun",
      id: "publicTransportAccessible",
    },
    {
      label: "Le salaire offert est intéressant pour le stagiaire",
      id: "interestingSalary",
      precision: (
        <>
          <br />
          <Typography variant="caption">
            Précisez :
            <TextField
              variant="standard"
              sx={{
                "& .MuiInput-input": { fontSize: "0.8em", textAlign: "center" },
              }}
            ></TextField>
            /l'heure
          </Typography>
        </>
      ),
    },
    {
      label:
        "La communication avec le superviseur de stage facilite le déroulement du stage",
      id: "supervisorCommunicationEase",
    },
    {
      label: "L'équipment fourni est adéquat pour réaliser les tâches confiées",
      id: "adequateEquipement",
    },
    {
      label: "Le volume de travail est acceptable",
      id: "workVolumeAcceptable",
    },
  ],
  comment: { label: "Commentaire :", id: "evaluationComment" },
};

export const companyObservation = [
  {
    label: "Ce milieu est à privilégier pour le :",
    id: "internshipPrivilege",
    options: ["Premier stage", "Deuxième stage"],
  },
  {
    label: "Ce milieu est ouvert à accueillir :",
    id: "hiringInterns",
    options: ["Un stagiaire", "Deux stagière", "Plus de trois stagiaires"],
  },
  {
    label:
      "Ce milieu désire accueillir le même stagiaire pour un prochain stage :",
    id: "interestRehire",
    options: ["Oui", "Non"],
  },
  {
    label: "Ce milieu offre des quarts de travail variables :",
    id: "variableWorkShifts",
    options: ["Oui", "Non"],
  },
];
