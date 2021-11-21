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
import StudentDashBoard from "./StudentDashboard";
import InternshipOfferValidation from "./InternshipOfferValidation";
import ListUserDroppable from "./ListUserDroppable";
import ListUserDraggable from "./ListUserDraggable";
import AssignedStudentSupervisorView from "./AssignedStudentSupervisorView";
import StudentEvaluationForm from "./Evaluation/End/StudentEvaluationForm";
import StudentEvaluationMidForm from "./Evaluation/Mid-term/StudentEvaluationMidForm";
import ListReport from "./ListReport";
import SignContractDialog from "./contracts/SignContractDialog";

const Content = ({ isSidebarOpen, selection }) => {
  const [userInfo] = useContext(UserInfoContext);

  const roleCondtionnal = (role) => {
    switch (role) {
      case "STUDENT":
        return (
          <>
            <StudentDashBoard visible={selection.id === 7} />
            <UploadCV visible={selection.id === 5} />
            <OfferViews visible={selection.id === 4} />
          </>
        );
      case "INTERNSHIP_MANAGER":
        return (
          <>
            <ListCvInternshipManagerView visible={selection.id === 1} />
            <InternshipOfferValidation visible={selection.id === 3} />
            <ListUserDroppable
              role="SUPERVISOR"
              visible={selection.id === 11}
            />
            <ListUserDraggable role="STUDENT" visible={selection.id === 11} />
            <ListReport visible={selection.id === 14} />
          </>
        );
      case "SUPERVISOR":
        return (
          <>
            <AssignedStudentSupervisorView visible={selection.id === 10} />
            <StudentEvaluationMidForm visible={selection.id === 13} />
          </>
        );
      case "MONITOR":
        return (
          <>
            <ListStudentApplying visible={selection.id === 6} />
            <StudentEvaluationForm visible={selection.id === 12} />
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
      {!userInfo.loggedIn && (
        <Typography variant="h3" color="white" sx={{ pl: 2, pt: 2 }}>
          Bonjour visiteur.
        </Typography>
      )}
      {userInfo.loggedIn && selection.id !== undefined && (
        <Box
          sx={{ transition: "margin 300ms ease", ml: isSidebarOpen ? 36 : 0 }}
        >
          <ContentTitle
            key={selection.id}
            role={roles[userInfo.role]}
            description={selection.description}
          />
          {roleCondtionnal(userInfo.role)}
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
