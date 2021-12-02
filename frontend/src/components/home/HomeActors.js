import { Grid } from "@mui/material";
import React from "react";
import HomeCard from "./HomeCard";
import { motion } from "framer-motion";
const HomeActors = () => {
  const roles = ["Moniteur", "Superviseur", "Étudiant"];
  const descriptions = [
    "Responsable d'entreprise",
    "Accompagnateur du stagiaire",
    "Stagiaire",
  ];
  const functionnalities = [
    [
      "Ajouter des offres de stage",
      "Voir la liste des postulations.",
      "Démarrer le processus de signature de contrat.",
      "Contacter un étudiant.",
      "Afficher/Télécharger le C.V. d'un étudiant.",
      "Évaluer un stagiaire.",
    ],
    [
      "Afficher vos élèves assignés.",
      "Voir l'état de la demande de stage d'un étudiant.",
      "Évaluer un stagiaire.",
    ],
    [
      "Afficher votre profil.",
      "Changer le statut de votre demande de stage.",
      "Téléverser un C.V.",
      "Afficher vos C.V. téléversés.",
      "Appliquer à une offre de stage.",
    ],
  ];
  const resource = ["downtown.jpg", "cegep.jpg", "etudiant.jpg"];

  const scrolled = {
    offscreen: {
      y: 300,
      opacity: 0,
    },
    onscreen: {
      y: 0,
      opacity: 1,
    },
  };
  return (
    <>
      <Grid sx={{ flexGrow: 1 }} container spacing={2}>
        <Grid item xs={12}>
          <Grid container justifyContent="center" spacing={5}>
            {roles.map((value, key) => (
              <Grid key={key} item xs={12} sm={12} md={6} lg={4} xl={4}>
                <motion.div
                  variants={scrolled}
                  initial="offscreen"
                  animate="onscreen"
                  transition={{
                    type: "tween",
                    duration: 1,
                    delay: (key + 1) * 0.2,
                  }}
                >
                  <HomeCard
                    keyProp={key}
                    role={value}
                    description={descriptions[key]}
                    image={resource[key]}
                    functionnalities={functionnalities[key]}
                  />
                </motion.div>
              </Grid>
            ))}
          </Grid>
        </Grid>
      </Grid>
    </>
  );
};

export default HomeActors;
