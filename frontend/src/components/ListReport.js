import { Grid } from "@mui/material";
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
      <Grid sx={{mt:4}} container justifyContent="center">
        {reports.map((current, index) => (
          <Grid
            item
            key={index}
            lg={6}
            xl={4}
            md={12}
            sm={12}
            xs={12}
            textAlign="center"
          >
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
