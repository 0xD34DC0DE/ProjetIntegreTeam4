import {
  Divider,
  Drawer,
  Icon,
  List,
  ListItem,
  ListItemIcon,
  ListItemText,
  Typography,
} from "@mui/material";
import { makeStyles } from "@mui/styles";
import React from "react";
export const drawerWidth = 240;
const useStyles = makeStyles((theme) => ({
  root: {
    flexGrow: 1,
    width: drawerWidth,
  },
}));
/**
 *
 *
 * @description The SideBar component with a Drawer from Material-Ui
 * @author Maxime Dupuis
 */
const SideBar = () => {
  return (
    <>
      <Drawer
        className={drawerWidth}
        variant="persistent"
        anchor="left"
        open={true}
      >
        <Typography variant="h6">Header</Typography>
        <List>
          {["Inbox", "Starred", "Send email", "Drafts"].map((text, index) => (
            <ListItem button key={text}>
              <ListItemIcon>
                <Icon>star</Icon>
              </ListItemIcon>
              <ListItemText primary={text} />
            </ListItem>
          ))}
        </List>
        <Divider />
      </Drawer>
    </>
  );
};

export default SideBar;
