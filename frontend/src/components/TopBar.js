import AccountCircleIcon from "@mui/icons-material/AccountCircle";
import { Icon, MenuItem, Select, Toolbar } from "@mui/material";
import MuiAppBar from "@mui/material/AppBar";
import Badge from "@mui/material/Badge";
import IconButton from "@mui/material/IconButton";
import { createTheme, styled, ThemeProvider } from "@mui/material/styles";
import React from "react";
import OfferForm from "./OfferForm";
import Register from "./Register";
import Login from "./Login";

const AppBar = styled(MuiAppBar, {
  shouldForwardProp: (prop) => prop,
})(({ theme, open }) => ({
  overflowX: "hidden",
  float: "right",
  width: `calc(100% - ${theme.spacing(50)})`,
  ...(!open && {
    width: `calc(100% - ${theme.spacing(9)})`,
  }),
}));

const mdTheme = createTheme();

const TopBar = ({ open, setOpen, userInformations, setUserInformations }) => {
  const [mountedForm, setMountedForm] = React.useState(0);
  const connectionSelect = (
    <>
      <Select IconComponent={AccountCircleIcon}>
        <MenuItem value={1} onClick={() => setMountedForm(1)}>
          S'enregistrer
        </MenuItem>
        <MenuItem value={2} onClick={() => setMountedForm(2)}>
          Se Connecter
        </MenuItem>
      </Select>
    </>
  );
  return (
    <ThemeProvider theme={mdTheme}>
      <AppBar position="static" open={open}>
        <Toolbar
          sx={{
            display: "flex",
            alignItems: "center",
            justifyContent: "flex-end",
            px: [1],
          }}
        >
          <IconButton color="inherit">
            <Badge badgeContent={4} color="secondary">
              <Icon>notifications</Icon>
            </Badge>
          </IconButton>
          <div className="selectConnection"></div>
          {connectionSelect}
        </Toolbar>
      </AppBar>
      <Register open={mountedForm === 1} setOpen={setOpen} />
      <Login
        open={mountedForm === 2}
        userInformations={userInformations}
        setUserInformations={setUserInformations}
      />
    </ThemeProvider>
  );
};

export default TopBar;
