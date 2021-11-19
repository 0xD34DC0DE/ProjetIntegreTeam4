
const handlers = {
  "SIGN_CONTRACT" :{
    handle: ({toggleDialog, data}) => {
      toggleDialog("signContractDialog", true, data);
    }
  }
};

export default handlers;