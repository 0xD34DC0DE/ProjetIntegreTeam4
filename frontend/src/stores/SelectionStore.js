import React, { createContext, useReducer, useEffect } from "react";
import SelectionReducer from "./SelectionReducer";
import { sidebarList } from "../components/Configuration";

export const initialState =
  JSON.parse(sessionStorage.getItem("selection")) || sidebarList[0];

const SelectionStore = ({ children }) => {
  const [state, dispatch] = useReducer(SelectionReducer, initialState);

  useEffect(() => {
    dispatch(JSON.parse(sessionStorage.getItem("selection")) || sidebarList[0]);
  }, []);

  return (
    <SelectionContext.Provider value={[state, dispatch]}>
      {children}
    </SelectionContext.Provider>
  );
};

export const SelectionContext = createContext(initialState);
export default SelectionStore;
