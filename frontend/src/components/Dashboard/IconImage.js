import React, { useContext, useEffect, useState } from "react";
import { Avatar, Typography, Grid } from "@mui/material";
import CheckCircleIcon from "@mui/icons-material/CheckCircle";
import BlockIcon from "@mui/icons-material/Block";
import { UserInfoContext } from "../../stores/UserInfoStore";

const IconImage = ({ profile, role }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [profileImage, setProfileImage] = useState("");

  useEffect(async () => {
    if (userInfo === undefined) return;
    let profileImage = await userInfo.profileImage;
    setProfileImage(profileImage);
  }, [userInfo]);

  return (
    <>
      <Grid item justifyContent="center">
        <Avatar
          sx={{
            width: 200,
            height: 200,
            boxShadow: "0px 0px 15px 2px rgba(255, 255, 255, 0.5)",
          }}
          src={profileImage}
        >
          {profile.firstName.charAt(0)}
        </Avatar>
        {role === "STUDENT" && (
          <div>
            {profile.hasValidCv ? (
              <Typography
                sx={{ color: "green", textAlign: "center", m: 1, mt: 2 }}
                variant="subtitle1"
                component="div"
              >
                Vous avez un CV valide
                <CheckCircleIcon sx={{ ml: 1, verticalAlign: "middle" }} />
              </Typography>
            ) : (
              <Typography
                sx={{ color: "red", textAlign: "center", m: 1, mt: 2 }}
                variant="subtitle1"
                component="div"
                gutterBottom
              >
                Vous n'avez aucun CV valide
                <BlockIcon sx={{ ml: 2, verticalAlign: "middle" }} />
              </Typography>
            )}
          </div>
        )}
      </Grid>
    </>
  );
};

export default IconImage;
