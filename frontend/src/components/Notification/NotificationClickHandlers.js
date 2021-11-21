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
};

export default handlers;
