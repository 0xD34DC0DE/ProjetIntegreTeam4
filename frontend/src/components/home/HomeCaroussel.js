import React, { useEffect, useState } from "react";
import { Container } from "@mui/material";
import { Box } from "@mui/system";

const slides = [
  {
    title: "title1",
    description: "desc1",
    imgUrl: "moniteur-edifice.jpg",
  },
  {
    title: "title2",
    description: "desc2",
    imgUrl: "cegep.jpg",
  },
  {
    title: "title3",
    description: "desc3",
    imgUrl: "etudiant.jpg",
  },
];

const HomeCaroussel = () => {
  let currentSlide = 0;
  const timeInMs = 10000;
  const [slide, setSlide] = useState(slides[currentSlide]);
  useEffect(() => {
    const setCurrentSlide = () => {
      setInterval(() => {
        if (currentSlide < slides.length - 1) ++currentSlide;
        else currentSlide = 0;
        setSlide(slides[currentSlide]);
      }, timeInMs);
    };
    setCurrentSlide();
  }, []);
  return (
    <Container
      disableGutters
      maxWidth={false}
      sx={{
        backgroundImage: `url(${slide.imgUrl})`,
        height: "50vh",
        backgroundRepeat: "round",
      }}
    >
      <Box sx={{ color: "white" }}>Title : {slide.title}</Box>
    </Container>
  );
};

export default HomeCaroussel;
