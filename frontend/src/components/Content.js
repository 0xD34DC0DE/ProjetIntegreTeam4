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
import AsssignedStudentSupervisorView from "./AsssignedStudentSupervisorView";

const Content = ({
  isSidebarOpen,
  toggleDialog,
  dialogVisibility,
  selection,
}) => {
  const [userInfo] = useContext(UserInfoContext);

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
      {userInfo.loggedIn && selection.id != undefined && (
        <Box
          sx={{ transition: "margin 300ms ease", ml: isSidebarOpen ? 36 : 0 }}
        >
          <ContentTitle
            key={selection.id}
            role={roles[userInfo.role]}
            description={selection.description}
          />
          <ListStudentApplying
            visible={selection.id == 6}
            toggleDialog={toggleDialog}
            dialogVisibility={dialogVisibility}
          />
          <UploadCV visible={selection.id == 5} />
          <ListCvInternshipManagerView visible={selection.id == 1} />
          <OfferViews visible={selection.id == 4} />
          <StudentDashBoard
            visible={selection.id == 7 && userInfo.role == "STUDENT"}
          />
          <InternshipOfferValidation
            visible={selection.id == 3}
            toggleDialog={toggleDialog}
            dialogVisibility={dialogVisibility}
          />
          <AsssignedStudentSupervisorView
            visible={selection.id == 10 && userInfo.role == "SUPERVISOR"}
          />
          <ListUserDroppable
            role="SUPERVISOR"
            visible={
              selection.id == 11 && userInfo.role == "INTERNSHIP_MANAGER"
            }
          />
          <ListUserDraggable
            role="STUDENT"
            visible={
              selection.id == 11 && userInfo.role == "INTERNSHIP_MANAGER"
            }
          />
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
