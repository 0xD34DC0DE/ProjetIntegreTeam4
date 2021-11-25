import React, { createContext, useReducer, useEffect } from "react";
import SelectionReducer from "./SelectionReducer";
import { sidebarList } from "../components/Configuration";

export const initialState = sidebarList[0];

const SelectionStore = ({ children }) => {
  const [state, dispatch] = useReducer(SelectionReducer, initialState);

  useEffect(() => {
    dispatch(sidebarList[0]);
  }, []);

  return (
    <SelectionContext.Provider value={[state, dispatch]}>
      {children}
    </SelectionContext.Provider>
  );
};

export const SelectionContext = createContext(initialState);
export default SelectionStore;
