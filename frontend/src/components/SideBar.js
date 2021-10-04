import ChevronLeftIcon from "@mui/icons-material/ChevronLeft";
import PropTypes from "prop-types";
import {
  Divider,
  List,
  ListItemIcon,
  ListItemText,
  ListItemButton,
  ThemeProvider,
  Toolbar,
  Icon,
} from "@mui/material";
import Drawer from "@mui/material/Drawer";
import IconButton from "@mui/material/IconButton";
import { createTheme, styled } from "@mui/material/styles";
import React, { useContext } from "react";
import { UserInfoContext } from "../stores/UserInfoStore";
import { drawerListDialogs } from "../models/drawerListItems";
import OfferForm from "./OfferForm";
import { useHistory } from "react-router-dom";

const drawerWidth = 25;
const DrawerHeader = styled("div")(({ theme }) => ({
  display: "flex",
  alignItems: "center",
  padding: theme.spacing(0, 1),
  // necessary for content to be below app bar
  ...theme.mixins.toolbar,
  justifyContent: "flex-end",
}));

const mdTheme = createTheme();

function SideBar({
  open,
  setOpen,
  intershipOfferDialogVisible,
  toggleDialogs,
}) {
  const [userInfo] = useContext(UserInfoContext);
  const history = useHistory();

  const toggleDrawer = () => {
    console.log("open", open);
    setOpen(!open);
  };

  return (
    <ThemeProvider theme={mdTheme}>
      <Drawer
        sx={{
          width: `${drawerWidth}rem`,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: `${drawerWidth}rem`,
            boxSizing: "border-box",
          },
        }}
        variant="temporary"
        anchor="left"
        open={open}
      >
        <DrawerHeader>
          <IconButton onClick={toggleDrawer}>
            <ChevronLeftIcon />
          </IconButton>
        </DrawerHeader>
        <Divider />
        <List>
          {drawerListDialogs
            .filter((item) => item.roles.includes(userInfo.role))
            .map((item, key) => {
              return (
                <ListItemButton
                  key={key}
                  onClick={() => {
                    history.push("/offer");
                    //toggleDialogs(item.name, true);
                  }}
                >
                  <ListItemIcon>{item.icon}</ListItemIcon>
                  <ListItemText>{item.text}</ListItemText>
                </ListItemButton>
              );
            })}
        </List>
      </Drawer>
      <OfferForm
        dialogVisible={intershipOfferDialogVisible}
        toggleDialogs={toggleDialogs}
      />
    </ThemeProvider>
  );
}
export default SideBar;
