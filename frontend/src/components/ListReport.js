import { UserInfoContext } from "../stores/UserInfoStore";
// import axios from "axios";
import {
    Grid,
    Typography,
    FormControl,
    InputLabel,
    Select,
    MenuItem,
    Box
} from "@mui/material";
import React, {
    useState,
    useContext,
    useEffect
} from "react";
import Report from "./Report"
import ReportDialog from "./ReportDialog";

const ListReport = ({ open, toggleDialog, visible }) => {
    const [reports, setReports] = useState([
        { title: "Offres de stages non validées", url: "/generateAllNonValidatedOffersReport" },
        { title: "Offres de stages validées", url: "/generateAllValidatedOffersReport" },
        { title: "Étudiants inscrits", url: "/generateAllStudentsReport" },
        { title: "Étudiants sans CV soumis", url: "/generateStudentsNoCvReport" },
        { title: "Étudiants sans CV validé", url: "/generateStudentsUnvalidatedCvReport" },
        { title: "Étudiants sans convocation d'entrevue", url: "/generateStudentsNoInternshipReport" },
        { title: "Étudiants en attente d'entrevue", url: "/generateStudentsWaitingInterviewReport" },
        { title: "Étudiants en attente de réponse", url: "/generateStudentsWaitingInterviewResponseReport" },
        { title: "Étudiants avec stage trouvé", url: "/generateStudentsWithInternshipReport" },
        { title: "Étudiants pas évalué par leur moniteur", url: "test" },
        { title: "Étudiants dont le superviseur n'a pas évalué l'entreprise", url: "test" },
    ]);
    const [userInfo] = useContext(UserInfoContext);

    const [reportUrl, setReportUrl] = useState("");

    const [session, setSession] = useState(321);

    useEffect(() => {
        if (reportUrl !== "") {
            toggleDialog("reportDialog", true);
        }
    }, [reportUrl])

    const handleChange = (evt) => {
        setSession(evt.target.value)
    }

    return (
        //TODO: fix 'The Menu component doesn't accept a Fragment as a child."
        <>
            {visible && (
                <Box>
                    <Typography variant="h4" sx={{ color: "white", ml: 2, mt: 2, display: "inline-block" }}>
                        Rapports
                    </Typography>
                    <FormControl sx={{ display: "inline-block", width: "150px", padding: "10px" }}>
                        <InputLabel id="sessionSelectInputLabel" sx={{ fontsize: "large", padding: "10px" }}>Session</InputLabel>
                        <Select
                            labelId="sessionSelect"
                            id="sessionSelect"
                            onChange={handleChange}
                            defaultValue={321}
                            sx={{ display: "inline-block", width: "150px" }}
                        >
                            <MenuItem key={119} value={119}>Hiver 2019</MenuItem>
                            <MenuItem key={219} value={219}>Été 2019</MenuItem>
                            <MenuItem key={319} value={319}>Automne 2019</MenuItem>
                            <MenuItem key={120} value={120}>Hiver 2020</MenuItem>
                            <MenuItem key={220} value={220}>Été 2020</MenuItem>
                            <MenuItem key={320} value={320}>Automne 2020</MenuItem>
                            <MenuItem key={121} value={121}>Hiver 2021</MenuItem>
                            <MenuItem key={221} value={221}>Été 2021</MenuItem>
                            <MenuItem key={321} value={321}>Automne 2021</MenuItem>
                            <MenuItem key={122} value={122}>Hiver 2022</MenuItem>
                            <MenuItem key={222} value={222}>Été 2022</MenuItem>
                            <MenuItem key={322} value={322}>Automne 2022</MenuItem>
                        </Select>
                    </FormControl>
                    <Grid
                        sx={{ py: "1vh", mt: "10%", display: "flex" }}
                        container
                        spacing={{ xs: 2, md: 3 }}
                        columns={{ xs: 4, sm: 8, md: 12 }}
                    >
                        {reports.map((current, index) => (
                            <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
                                <Report title={current.title} url={current.url} setReportUrl={setReportUrl} />
                            </Grid>
                        ))}
                    </Grid>
                    <ReportDialog open={open} toggleDialog={toggleDialog} reportUrl={reportUrl} setReportUrl={setReportUrl} sessionNumber={session} />
                </Box>
            )}
        </>
    );
};

export default ListReport;
