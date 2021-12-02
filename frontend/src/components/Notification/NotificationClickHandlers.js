import { sidebarList } from "../Configuration";

const getSelectionById = (id) => {

  for(let selection of sidebarList) {
    if(selection.id === id) {
      return selection;
    }
  }

  console.error("Could not find requested selection with id: " + id);
  return null;
}

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
      selectionDispatch(getSelectionById(5));
    },
  },
  NEW_INTERNSHIP_OFFER: {
    handle: ({ selectionDispatch }) => {
      selectionDispatch(getSelectionById(4));
    },
  },
};

export default handlers;
