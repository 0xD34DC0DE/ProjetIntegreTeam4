import React from "react";
import HomeActors from "./HomeActors";
import HomeJumbotron from "./HomeJumbotron";
import HomeTitle from "./HomeTitle";
import SearchIcon from "@mui/icons-material/Search";
import FastForwardIcon from "@mui/icons-material/FastForward";
import SchoolIcon from "@mui/icons-material/School";

const Home = () => {
  return (
    <>
      <HomeTitle />
      <HomeActors />
      <HomeJumbotron
        title={"À la recherche d'un stagiaire?"}
        description={
          "Notre outil vous connecte avec des élèves partout au Québec. Il est désormais facile de trouver un stagiaire."
        }
        steps={[
          "Enregistrez-vous.",
          "Connectez-vous.",
          "Ajouter une offre de stage.",
          "Contacter les appliquants dans l'application.",
        ]}
        backgroundColor="#1F2020"
        color="white"
        imgUrl={"loupe.png"}
        icon={<SearchIcon sx={{ fontSize: "15rem" }} />}
      />
      <HomeJumbotron
        title={"Technologie Reactive Supérieure"}
        description={
          "L'application est rapide et gère efficacement l'utilisation de la mémoire."
        }
        backgroundColor="white"
        color="rgba(125, 51, 235, 0.8)"
        icon={<FastForwardIcon sx={{ fontSize: "15rem" }} />}
      />
      <HomeJumbotron
        title={
          "Cherchez-vous à développer vos connaissances dans une entreprise?"
        }
        description={"Trouver un stage rapidement avec notre outil."}
        steps={[
          "Enregistrez-vous.",
          "Connectez-vous.",
          "Téléverser votre CV.",
          "Appliquer à une offre de stage concurrente.",
        ]}
        backgroundColor="#1F2020"
        color="white"
        icon={<SchoolIcon sx={{ fontSize: "15rem" }} />}
      />
    </>
  );
};

export default Home;
