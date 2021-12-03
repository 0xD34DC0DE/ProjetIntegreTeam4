import React, { createContext, useReducer, useEffect } from "react";
import DialogReducer from "./DialogReducer";

export const dialogContextinitialState = {
  loginDialog: { visible: false, data: null },
  registerDialog: { visible: false, data: null },
  internshipOfferDialog: { visible: false, data: null },
  internshipOfferDialogValidation: { visible: false, data: null },
  emailSenderDialog: { visible: false, data: null },
  internshipDetailsDialog: { visible: false, data: null },
  reportDialog: { visible: false, data: null },
  cvDialog: { visible: false, data: null },
  signContractMonitorDialog: { visible: false, data: null },
  evaluationDialogPreview: { visible: false, data: null },
  cvRejectionExplanationDialog: { visible: false, data: null },
  signContractDialog: { visible: false, data: null },
  exclusiveOfferDialog: { visible: false, data: null },
};

const DialogStore = ({ children }) => {
  const [state, dispatch] = useReducer(
    DialogReducer,
    dialogContextinitialState
  );

  useEffect(() => {
    dispatch({ type: "REFRESH" });
  }, []);

  return (
    <DialogContext.Provider value={[state, dispatch]}>
      {children}
    </DialogContext.Provider>
  );
};

export const DialogContext = createContext(dialogContextinitialState);
export default DialogStore;
