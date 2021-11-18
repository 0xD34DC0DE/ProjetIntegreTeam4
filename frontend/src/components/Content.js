import { Box, Typography } from "@mui/material";
import React, { useContext } from "react";
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

const Content = ({
  isSidebarOpen,
  toggleDialog,
  dialogVisibility,
  selection,
}) => {
  const [userInfo] = useContext(UserInfoContext);

  //TODO use switch instead of repeated conditionnal rendering

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
          {userInfo.role === "INTERNSHIP_MANAGER" && (
            <ListCvInternshipManagerView 
              toggleDialog={toggleDialog}
              visible={selection.id === 1} 
              dialogVisibility={dialogVisibility}
            />
          )}
          {userInfo.role === "MONITOR" && (
            <ListStudentApplying
              visible={selection.id === 6}
              toggleDialog={toggleDialog}
              dialogVisibility={dialogVisibility}
            />
          )}
          {userInfo.role === "STUDENT" && (
            <UploadCV
              visible={selection.id === 5}
              toggleDialog={toggleDialog}
              dialogVisibility={dialogVisibility}
            />
          )}
          {userInfo.role === "STUDENT" && (
            <OfferViews visible={selection.id === 4} />
          )}
          {userInfo.role === "STUDENT" && (
            <StudentDashBoard
              visible={selection.id === 7 && userInfo.role === "STUDENT"}
            />
          )}
          {userInfo.role === "INTERNSHIP_MANAGER" && (
            <InternshipOfferValidation
              visible={selection.id === 3}
              toggleDialog={toggleDialog}
              dialogVisibility={dialogVisibility}
            />
          )}
          {userInfo.role === "SUPERVISOR" && (
            <AssignedStudentSupervisorView
              visible={selection.id === 10}
              toggleDialog={toggleDialog}
              dialogVisibility={dialogVisibility}
            />
          )}
          {userInfo.role === "INTERNSHIP_MANAGER" && (
            <ListUserDroppable
              role="SUPERVISOR"
              visible={selection.id === 11}
            />
          )}
          {userInfo.role === "INTERNSHIP_MANAGER" && (
            <ListUserDraggable role="STUDENT" visible={selection.id === 11} />
          )}
          {userInfo.role === "MONITOR" && (
            <StudentEvaluationForm visible={selection.id === 12} />
          )}
          {userInfo.role === "SUPERVISOR" && (
            <StudentEvaluationMidForm visible={selection.id === 13} />
          )}
          {userInfo.role === "INTERNSHIP_MANAGER" && (
            <ListReport
              toggleDialog={toggleDialog}
              visible={selection.id === 14}
              dialogVisibility={dialogVisibility}
            />
          )}
        </Box>
      )}
      <OfferForm
        toggleDialog={toggleDialog}
        open={dialogVisibility.internshipOfferDialog}
      />
      <Register
        open={dialogVisibility.registerDialog}
        toggleDialog={toggleDialog}
      ></Register>
      <Login
        open={dialogVisibility.loginDialog}
        toggleDialog={toggleDialog}
      ></Login>
    </Box>
  );
};

export default Content;
