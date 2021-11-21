import { Grid, Typography, Box, Container } from "@mui/material";
import React, { useState, useEffect, useContext } from "react";
import { DialogContext } from "../stores/DialogStore";
import Report from "./Report";
import ReportDialog from "./ReportDialog";
import SemesterSelect from "./SemesterSelect";

const ListReport = () => {
  const [reports] = useState([
    {
      title: "Offres de stages non validées",
      url: "/generateAllNonValidatedOffersReport",
    },
    {
      title: "Offres de stages validées",
      url: "/generateAllValidatedOffersReport",
    },
    { title: "Étudiants inscrits", url: "/generateAllStudentsReport" },
    { title: "Étudiants sans CV soumis", url: "/generateStudentsNoCvReport" },
    {
      title: "Étudiants sans CV validé",
      url: "/generateStudentsUnvalidatedCvReport",
    },
    {
      title: "Étudiants sans convocation d'entrevue",
      url: "/generateStudentsNoInternshipReport",
    },
    {
      title: "Étudiants en attente d'entrevue",
      url: "/generateStudentsWaitingInterviewReport",
    },
    {
      title: "Étudiants en attente de réponse",
      url: "/generateStudentsWaitingInterviewResponseReport",
    },
    {
      title: "Étudiants avec stage trouvé",
      url: "/generateStudentsWithInternshipReport",
    },
    {
      title: "Étudiants pas évalué par leur moniteur",
      url: "/generateStudentsNotEvaluatedReport",
    },
    {
      title: "Étudiants dont le superviseur n'a pas évalué l'entreprise",
      url: "/generateStudentsWithSupervisorWithNoCompanyEvaluation",
    },
  ]);

  const [reportUrl, setReportUrl] = useState("");
  const [semesterFullName, setSemesterFullName] = useState("");
  const [dialog, dialogDispatch] = useContext(DialogContext);

  useEffect(() => {
    if (reportUrl !== "") {
      dialogDispatch({
        type: "OPEN",
        dialogName: "reportDialog",
      });
    }
  }, [reportUrl]);

  const updateSemesterFullName = (fullName) => {
    setSemesterFullName(fullName);
  };

  return (
    <Grid container flexDirection="column">
      <Grid item>
        <SemesterSelect updateSemesterFullName={updateSemesterFullName} />
      </Grid>
      <Grid item>
        <Typography
          variant="subtitle2"
          sx={{
            color: "white",
            ml: 3,
            mb: 2,
            fontSize: "2.5em",
            lineHeight: "1",
          }}
        >
          Rapports
        </Typography>
      </Grid>
      <Grid
        container
        spacing={{ xs: 2, md: 3 }}
        columns={{ xs: 4, sm: 8, md: 12 }}
        justifyContent="center"
      >
        {reports.map((current, index) => (
          <Grid item xs={6} sm={6} md={8} lg={4} xl={2} key={index}>
            <Report
              title={current.title}
              url={current.url}
              setReportUrl={setReportUrl}
            />
          </Grid>
        ))}
      </Grid>
      <ReportDialog
        reportUrl={reportUrl}
        semesterFullName={semesterFullName}
        setReportUrl={setReportUrl}
      />
    </Grid>
  );
};

export default ListReport;
