import { sidebarList } from "../Configuration";

const handlers = {
  SIGN_CONTRACT: {
    handle: ({ dialogDispatch, data }) => {
      dialogDispatch({
        type: "OPEN",
        dialogName: "signContractDialog",
        payload: data,
      });
    },
  },
  SHOW_CV: {
    handle: ({ selectionDispatch }) => {
      selectionDispatch(sidebarList[5]);
    },
  },
  NEW_INTERNSHIP_OFFER: {
    handle: ({ selectionDispatch }) => {
      selectionDispatch(sidebarList[4]);
    },
  },
};

export default handlers;
