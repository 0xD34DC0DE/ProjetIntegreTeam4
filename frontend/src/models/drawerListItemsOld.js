import AddIcon from "@mui/icons-material/Add";
import RuleIcon from "@mui/icons-material/Rule";

/**
 * @index 0 Roles possibles
 * @index 1 Dialog to open
 * @index 2 Rendered Icon
 * @index 3 Rendered Text
 * @author Maxime Dupuis
 */
export const drawerListDialogs = [
  [
    "INTERNSHIP_MANAGER, MONITOR",
    "internshipOfferDialog",
    <AddIcon />,
    "Déposer des offres de stage",
  ],
  [
    "INTERNSHIP_MANAGER",
    "validateOffer",
    <RuleIcon />,
    "Valider des offres de stage",
  ],
];
