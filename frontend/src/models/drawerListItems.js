import OfferViews from "../components/OfferViews";

/**
 * [ListItemText, Icon, path]
 * Ex: <ListItemText>Accueil</ListItemText> --> The List Text Element
 * Ex: <Icon>home</Icon> --> To render the Google Font Icon home
 * Ex: history.push("home") --> To path to /home
 * @description Un tableau de tableaux qui contiennent
 * les chaînes de caractères requis pour afficher chaque item d'une list
 * @author Maxime Dupuis
 * */
export const drawerListItems = [
  [
    "INTERNSHIP_MANAGER",
    "Déposer des offres de stage",
    "note_add",
    "internshipOfferDialog",
  ],
  [
    "INTERNSHIP_MANAGER",
    "Valider des offres de stage",
    "rule",
    "validateOffer",
  ],
];
