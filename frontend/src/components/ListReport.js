import {
  Grid,
  Typography,
  FormControl,
  InputLabel,
  Select,
  MenuItem,
  Box,
} from "@mui/material";
import React, {
    useState,
    useEffect
} from "react";
import Report from "./Report"
import ReportDialog from "./ReportDialog";
import SemesterSelect from "./SemesterSelect";

const ListReport = ({ open, toggleDialog, visible }) => {
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

  useEffect(() => {
    if (reportUrl !== "") {
      toggleDialog("reportDialog", true);
    }
  }, [reportUrl]);

  const updateSemesterFullName = (fullName) => {
    setSemesterFullName(fullName);
    console.log(fullName);
  };

  return (
    //TODO: fix 'The Menu component doesn't accept a Fragment as a child."
    <>
      {visible && (
        <Box>
          <SemesterSelect updateSemesterFullName={updateSemesterFullName} />

          <Typography
            variant="h4"
            sx={{ color: "white", ml: 2, mt: 2, display: "inline-block" }}
          >
            Rapports
          </Typography>
          <Grid
            sx={{ py: "1vh", mt: "10%", display: "flex" }}
            container
            spacing={{ xs: 2, md: 3 }}
            columns={{ xs: 4, sm: 8, md: 12 }}
          >
            {reports.map((current, index) => (
              <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
                <Report
                  title={current.title}
                  url={current.url}
                  setReportUrl={setReportUrl}
                />
              </Grid>
            ))}
          </Grid>
          <ReportDialog
            open={open}
            toggleDialog={toggleDialog}
            reportUrl={reportUrl}
            semesterFullName={semesterFullName}
            setReportUrl={setReportUrl}
          />
        </Box>
      )}
    </>
  );
};

export default ListReport;
