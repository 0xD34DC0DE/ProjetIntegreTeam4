const SelectionReducer = (state, action) => {
  sessionStorage.setItem("selection", JSON.stringify(action));

  return action;
};

export default SelectionReducer;
