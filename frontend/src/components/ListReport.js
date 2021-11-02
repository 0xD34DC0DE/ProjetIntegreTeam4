import { UserInfoContext } from "../stores/UserInfoStore";
// import axios from "axios";
import { Grid, Typography } from "@mui/material";
import React, { useState, useContext } from "react";
import Report from "./Report"

const ListReport = ({ role, visible }) => {
    const [reports, setReports] = useState([
        "Offres de stages non validées",
        "Offres de stages validées",
        "Étudiants inscrits",
        "Étudiants sans CV soumis",
        "Étudiants sans CV validé",
        "Étudiants sans convocation d'entrevue",
        "Étudiants en attente d'entrevue",
        "Étudiants en attente de réponse",
        "Étudiants avec stage trouvé",
        "Étudiants pas évalué par leur moniteur",
        "Étudiants dont le superviseur n'a pas évalué l'entreprise",
    ]);
    const [userInfo] = useContext(UserInfoContext);

    return (
        <>
            {visible && (
                <>
                    <Typography variant="h4" sx={{ color: "white", ml: 2, mt: 2 }}>
                        Rapports
                    </Typography>
                    <Grid
                        sx={{ py: "1vh", mt: "10%", display: "flex" }}
                        container
                        spacing={{ xs: 2, md: 3 }}
                        columns={{ xs: 4, sm: 8, md: 12 }}
                    >
                        {reports.map((current, index) => (
                            <>
                                <Grid item xs={6} sm={4} md={4} lg={3} xl={2} key={index}>
                                    <Report title={current} />
                                </Grid>
                            </>
                        ))}
                    </Grid>
                </>
            )}
        </>
    );
};

export default ListReport;
