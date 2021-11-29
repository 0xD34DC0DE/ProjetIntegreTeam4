import { Box, Typography } from "@mui/material";
import React, { useContext, useEffect } from "react";
import ListCvInternshipManagerView from "./ListCvInternshipManagerView";
import ContentTitle from "./ContentTitle";
import { UserInfoContext } from "../stores/UserInfoStore";
import Register from "./Register";
import Login from "./Login";
import UploadCV from "./UploadCV";
import ListStudentApplying from "./ListStudentApplying";
import { roles } from "./Configuration";
import OfferForm from "./OfferForm";
import OfferViews from "./OfferViews";
import StudentDashBoard from "./Dashboard/StudentDashboard";
import InternshipOfferValidation from "./InternshipOfferValidation";
import ListUserDroppable from "./ListUserDroppable";
import ListUserDraggable from "./ListUserDraggable";
import AssignedStudentSupervisorView from "./AssignedStudentSupervisorView";
import StudentEvaluationForm from "./Evaluation/End/StudentEvaluationForm";
import StudentEvaluationMidForm from "./Evaluation/Mid-term/StudentEvaluationMidForm";
import ListReport from "./ListReport";
import SignContractDialog from "./contracts/SignContractDialog";
import { SelectionContext } from "../stores/SelectionStore";
import HomeActors from "./home/HomeActors";
import Home from "./home/Home";

const Content = ({ isSidebarOpen }) => {
  const [selection] = useContext(SelectionContext);
  const [userInfo] = useContext(UserInfoContext);

  const displayComponents = (role) => {
    switch (role) {
      case "STUDENT":
        return (
          <>
            {selection.id === 7 && <StudentDashBoard />}
            {selection.id === 5 && <UploadCV />}
            {selection.id === 4 && <OfferViews />}
          </>
        );
      case "INTERNSHIP_MANAGER":
        return (
          <>
            {selection.id === 1 && <ListCvInternshipManagerView />}
            {selection.id === 3 && <InternshipOfferValidation />}
            {selection.id === 11 && <ListUserDroppable role="SUPERVISOR" />}
            {selection.id === 11 && <ListUserDraggable role="STUDENT" />}
            {selection.id === 14 && <ListReport />}
          </>
        );
      case "SUPERVISOR":
        return (
          <>
            {selection.id === 10 && <AssignedStudentSupervisorView />}
            {selection.id === 13 && <StudentEvaluationMidForm />}
          </>
        );
      case "MONITOR":
        return (
          <>
            {selection.id === 6 && <ListStudentApplying />}
            {selection.id === 12 && <StudentEvaluationForm />}
          </>
        );
      default:
        break;
    }
  };

  return (
    <Box
      sx={{
        backgroundColor: "primary.main",
        flexGrow: 1,
        m: 0,
        p: 0,
        minHeight: "calc(100vh - 45px)",
        Height: "100%",
        width: "100%",
      }}
    >
      {!userInfo.loggedIn && <Home />}
      {userInfo.loggedIn && selection.id !== undefined && (
        <Box
          sx={{ transition: "margin 300ms ease", ml: isSidebarOpen ? 36 : 0 }}
        >
          <ContentTitle
            key={selection.id}
            role={roles[userInfo.role]}
            description={selection.description}
          />
          {displayComponents(userInfo.role)}
        </Box>
      )}
      <Register></Register>
      <Login></Login>
      <OfferForm />
      <SignContractDialog />
    </Box>
  );
};

export default Content;
