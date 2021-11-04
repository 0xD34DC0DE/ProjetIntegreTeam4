export const endEvaluation = [
  {
    section: 1,
    title: "Productivité",
    description: "Capacité d'optimiser son rendement au travail",
    tasks: [
      {
        label: "Planifier et organiser son travail de façon efficace",
        id: "rating#organizeEfficiently",
      },
      {
        label: "Comprendre rapidement les directives relatives à son travail",
        id: "rating#understandFast",
      },
      {
        label: "Maintenir un rythme de travail soutenu",
        id: "rating#keepRhythm",
      },
      { label: "Établir ses priorités", id: "rating#establishPriorities" },
      { label: "Respecter ses échéanciers", id: "rating#respectDeadlines" },
    ],
    comment: { label: "Commentaires :", id: "text#productivityComment" },
  },
  {
    section: 2,
    title: "Qualité du travail",
    description:
      "Capacité de s'acquitter des tâches sous sa responsabilité en s'imposant personnellement des normes de qualité",
    tasks: [
      {
        label: "Respecter les mandats qui lui on été confiés",
        id: "rating#respectMandates",
      },
      {
        label: "Porter attention aux détails dans la réalisation de ses tâches",
        id: "rating#payAttentionDetails",
      },
      {
        label: "Vérifier son travail, s'assurer que rien n'a été oublié",
        id: "rating#verifyWork",
      },
      {
        label: "Rechercher ses occasions de se perfectionner",
        id: "rating#perfectionate",
      },
      {
        label: "Faire une bonne analyse des problèmes rencontrés",
        id: "rating#problemSolving",
      },
    ],
    comment: { label: "Commentaires :", id: "text#workQualityComment" },
  },
  {
    section: 3,
    title: "Qualités des relations interpersonnelles",
    description:
      "Capacité d'établir des interrelations harmonieuses dans son milieu de travail",
    tasks: [
      {
        label: "Établir facilement des contacts avec les gens",
        id: "rating#approchable",
      },
      {
        label: "Contribuer activement au travail d'équipe",
        id: "rating#contributeTeamwork",
      },
      {
        label: "S'adapter facilement à la culture de l'entreprise",
        id: "rating#adaptCompany",
      },
      {
        label: "Accepter les critiques constructives",
        id: "rating#acceptCriticism",
      },
      { label: "Être respectueux envers les gens", id: "rating#respectful" },
      {
        label:
          "Faire preuve d'écoute active en essayant de comprendre le point de vue de l'autre",
        id: "rating#listenOthers",
      },
    ],
    comment: {
      label: "Commentaires :",
      id: "text#interpersonalRelationshipsComment",
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
        id: "rating#showMotivation",
      },
      { label: "Exprimer clairement ses idées", id: "rating#expressClearly" },
      { label: "Faire preuve d'initiative", id: "rating#takeInitiative" },
      { label: "Travailler de façon sécuritaire", id: "rating#workSafely" },
      {
        label:
          "Démontrer un bon sens des responsabilités ne réquérant qu'un minimum de supervision",
        id: "rating#showAutonomy",
      },
      { label: "Être ponctuel et assidu à son travail", id: "rating#punctual" },
    ],
    comment: { label: "Commentaires :", id: "text#personalSkillsComment" },
  },
];

export const ratings = [
  { value: "TOTALLY_AGREE", label: "Totalement en accord" },
  { value: "SOMEWHAT_AGREE", label: "Plutôt en accord" },
  { value: "SOMEWHAT_DISAGREE", label: "Plutôt en désaccord" },
  {
    value: "TOTALLY_DISAGREE",
    label: "Totalement en désaccord",
  },
  { value: "NA", label: "N/A" },
];

export const contactDetails = [
  { label: "Nom de l'élève", id: "text#studentFullName" },
  { label: "Programme d'études", id: "text#studyProgram" },
  { label: "Nom de l'entreprise", id: "text#companyName" },
  { label: "Nom du superviseur", id: "text#supervisorFullName" },
  { label: "Fonction", id: "text#fonction" },
  { label: "Téléphone", id: "text#phoneNumber" },
];

export const companyAppreciation = {
  id: "expectation#studentAppreciation",
  ratings: [
    {
      label: "Les habiletés démontrées dépassent de beaucoup les attentes",
      value: "FAR_EXCEEDS",
    },
    {
      label: "Les habiletés démontrées dépassent les attentes",
      value: "EXCEEDS",
    },
    {
      label: "Les habiletés démontrées répondent pleinement aux attentes",
      value: "FULLY_MEETS",
    },
    {
      label: "Les habiletés démontrées répondent partiellement aux attentes",
      value: "PARTLY_MEETS",
    },
    {
      label: "Les habiletés démontrées ne répondent pas aux attentes",
      value: "DOES_NOT_MEET",
    },
  ],
};

export const studentIdentification = {
  title: "Identification du stagiare",
  description: "Information du stagiaire évalué",
  fields: [
    { label: "Nom du stagiaire", id: "text#studentName", type: "text" },
    { label: "Date du stage", id: "text#internshipDate", type: "date" },
  ],
};

export const companyIdentification = {
  title: "Identification de l'entreprise",
  description: "Information sur la compagnie offrant le stage",
  fields: [
    { label: "Nom de l'entreprise", id: "text#companyName" },
    { label: "Contact", id: "text#contact" },
    { label: "Adresse", id: "text#address" },
    { label: "Ville", id: "text#city" },
    { label: "Numéro de téléphone", id: "text#pĥoneNumber" },
    { label: "Télécopieur", id: "text#faxMachine" },
    { label: "Code postal", id: "text#zipCode" },
  ],
};

export const midTermEvaluation = {
  title: "Évaluation",
  description: "Évaluation du stagiaire mi-mandat",
  tasks: [
    {
      label:
        "Les tâches confiées au stagiaire sont conformes aux tâches annoncées dans l'entente de stage",
      id: "rating#taskRespectAgreement",
    },
    {
      label:
        "Des mesures d'accueil facilitent l'intégration du nouveau stagiaire.",
      id: "rating#internIntegrationMesures",
    },
    {
      label:
        "Le temps réel consacré à l'encadrement du stagiaire est suffisant",
      id: "rating#internSupervisionTimeAllocated",
    },
    {
      label:
        "L'environnement de travail respecte les normes d'hygiène et de sécurité au travail",
      id: "rating#workEnvironnement",
    },
    {
      label: "Le climat de travail est agréable",
      id: "rating#pleasantWorkClimate",
    },
    {
      label: "Le milieu de stage est accessible par transport en commun",
      id: "rating#publicTransportAccessible",
    },
    {
      label: "Le salaire offert est intéressant pour le stagiaire",
      id: "rating#interestingSalary",
    },
    {
      label:
        "La communication avec le superviseur de stage facilite le déroulement du stage",
      id: "rating#supervisorCommunicationEase",
    },
    {
      label: "L'équipment fourni est adéquat pour réaliser les tâches confiées",
      id: "rating#adequateEquipement",
    },
    {
      label: "Le volume de travail est acceptable",
      id: "rating#workVolumeAcceptable",
    },
  ],
  comment: { label: "Commentaire :", id: "text#evaluationComment" },
};

export const companyObservation = [
  {
    label: "Ce milieu est à privilégier pour le :",
    id: "text#privilegeInternship",
    options: ["Premier stage", "Deuxième stage"],
  },
  {
    label: "Ce milieu est ouvert à accueillir :",
    id: "text#hiringInterns",
    options: ["Un stagiaire", "Deux stagière", "Plus de trois stagiaires"],
  },
  {
    label:
      "Ce milieu désire accueillir le même stagiaire pour un prochain stage :",
    id: "categorical#interestRehire",
    options: ["Oui", "Non"],
  },
  {
    label: "Ce milieu offre des quarts de travail variables :",
    id: "categorical#variableWorkShifts",
    options: ["Oui", "Non"],
  },
];
