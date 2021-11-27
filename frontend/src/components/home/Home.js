import React from "react";
import HomeActors from "./HomeActors";
import HomeButtons from "./HomeButtons";
import HomeCaroussel from "./HomeCaroussel";

const Home = () => {
  return (
    <>
      <HomeButtons />
      <HomeCaroussel />
      <HomeActors />
    </>
  );
};

export default Home;
