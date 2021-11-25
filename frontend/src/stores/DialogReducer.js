import { dialogContextinitialState } from "./DialogStore";

const DialogReducer = (state, action) => {
  switch (action.type) {
    case "OPEN":
      return {
        ...dialogContextinitialState,
        [action.dialogName]: { visible: true, data: action.payload },
      };
    case "CLOSE":
      return {
        ...dialogContextinitialState,
        [action.dialogName]: { visible: false, data: null },
      };
    case "REFRESH":
      return {
        ...dialogContextinitialState,
      };
    default:
      return state;
  }
};

export default DialogReducer;
