export const sections = [
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
        id: "fastUnderstanding",
      },
      { label: "Maintenir un rythme de travail soutenu", id: "keepsRhythm" },
      { label: "Établir ses priorités", id: "establishesPriorities" },
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
        id: "paysAttentionToDetails",
      },
      {
        label: "Vérifier son travail, s'assurer que rien n'a été oublié",
        id: "verifiesWork",
      },
      {
        label: "Rechercher ses occasions de se perfectionner",
        id: "isPerfectionist",
      },
      {
        label: "Faire une bonne analyse des problèmes rencontrés",
        id: "goodProblemSolving",
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
        id: "isApprochable",
      },
      {
        label: "Contribuer activement au travail d'équipe",
        id: "contributesToTeamwork",
      },
      {
        label: "S'adapter facilement à la culture de l'entreprise",
        id: "adaptsToCompany",
      },
      {
        label: "Accepter les critiques constructives",
        id: "acceptsCriticism",
      },
      { label: "Être respectueux envers les gens", id: "isRespectful" },
      {
        label:
          "Faire preuve d'écoute active en essayant de comprendre le point de vue de l'autre",
        id: "listensToOthers",
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
        id: "showsMotivation",
      },
      { label: "Exprimer clairement ses idées", id: "expressesClearly" },
      { label: "Faire preuve d'initiative", id: "takesInitiative" },
      { label: "Travailler de façon sécuritaire", id: "worksSafely" },
      {
        label:
          "Démontrer un bon sens des responsabilités ne réquérant qu'un minimum de supervision",
        id: "showsAutonomy",
      },
      { label: "Être ponctuel et assidu à son travail", id: "isPunctual" },
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

export const appreciation = {
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
    { label: "Stage", id: "internshipNumber" },
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

export const generalObservation = {
  title: "Observations générales",
  fields: [],
};
