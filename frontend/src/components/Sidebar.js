import React, { useContext } from "react";
import { Drawer, Typography, Button, Divider } from "@mui/material";
import FormatListBulletedOutlinedIcon from "@mui/icons-material/FormatListBulletedOutlined";
import { sidebarList } from "./Configuration";
import { UserInfoContext } from "../stores/UserInfoStore";
import { DialogContext } from "../stores/DialogStore";
import { SelectionContext } from "../stores/SelectionStore";

const Sidebar = ({ open }) => {
  const [userInfo] = useContext(UserInfoContext);
  const [dialog, dialogDispatch] = useContext(DialogContext);
  const [selection, selectionDispatch] = useContext(SelectionContext);

  return (
    <>
      {userInfo.loggedIn && (
        <Drawer
          variant="persistent"
          open={open}
          onClose={undefined}
          ModalProps={{
            keepMounted: true,
          }}
          PaperProps={{
            sx: { top: 45, backgroundColor: "primary.main", minWidth: "285px" },
            elevation: 3,
          }}
        >
          <Typography
            variant="subtitle2"
            fontSize="2.5em"
            sx={{ mt: 2, textAlign: "left", ml: 3 }}
          >
            OSER
            <FormatListBulletedOutlinedIcon sx={{ ml: 2 }} />
          </Typography>
          <Divider
            sx={{
              backgroundColor: "rgba(150, 150, 150, 0.3)",
              width: "80%",
              ml: "1.5em",
              mb: 2,
              mt: 1,
            }}
          />
          {sidebarList
            .filter((item) => item.role.includes(userInfo.role))
            .map((item, key) => {
              return (
                <Button
                  key={item.id}
                  sx={{
                    color: "text.primary",
                    ":hover": { backgroundColor: "rgba(100, 100, 100, 0.2)" },
                    justifyContent: "flex-start",
                    ml: 2,
                    mr: 2,
                  }}
                  onClick={() => {
                    if (item.isDialog)
                      dialogDispatch({
                        type: "OPEN",
                        dialogName: item.dialogName,
                      });
                    else selectionDispatch(item);
                  }}
                >
                  {item.icon}
                  {item.label}
                </Button>
              );
            })}
          <Typography
            variant="subtitle2"
            textAlign="center"
            sx={{
              mt: "auto",
              mb: 6,
              fontSize: "0.7em",
              color: "rgba(255, 255, 255, 0.5)",
            }}
          >
            &#169; 2021 Équipe 4. Tous droits réservés
          </Typography>
        </Drawer>
      )}
    </>
  );
};

export default Sidebar;
