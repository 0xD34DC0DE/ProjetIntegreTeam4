import React, { useState } from "react";
import {
  Box,
  Grid,
  Typography,
  Paper,
  Button,
  Card,
  CardMedia,
} from "@mui/material";
import { motion } from "framer-motion";
import HomeCard from "./HomeCard";

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
      "Voir la liste des postulations",
      "Démarrer le processus de signature de contrat",
      "Contacter un étudiant",
      "Télécharger/Afficher le C.V. d'un étudiant",
      "Évaluer le stagiaire",
    ],
    [
      "Afficher ses élèves assignés",
      "Voir l'état de la demande de stage d'un étudiant",
      "Évaluer un stagiaire",
    ],
    [
      "Afficher votre profil",
      "Changer le statut de votre demande de stage",
      "Téléverser un C.V.",
      "Afficher ses C.V. téléversés",
      "Appliquer à une offre de stage",
    ],
  ];
  const resource = ["downtown.jpg", "cegep.jpg", "etudiant.jpg"];

  return (
    <>
      <Grid sx={{ flexGrow: 1 }} container spacing={2}>
        <Grid item xs={12}>
          <Grid container justifyContent="center" spacing={5}>
            {roles.map((value, key) => (
              <Grid key={key} item xs={12} sm={12} md={6} lg={4} xl={4}>
                <HomeCard
                  role={value}
                  description={descriptions[key]}
                  image={resource[key]}
                  functionnalities={functionnalities[key]}
                />
              </Grid>
            ))}
          </Grid>
        </Grid>
      </Grid>
    </>
  );
};

export default HomeActors;
