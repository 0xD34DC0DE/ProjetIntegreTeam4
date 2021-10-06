import React, {createContext, useReducer, useEffect} from "react";
import UserInfoReducer from './UserInfoReducer'

const initialState = {
  email: "",
  role: "",
  loggedIn: false
}

const UserInfoStore = ({children}) =>  {
  const [state, dispatch] = useReducer(UserInfoReducer, initialState);
  
  useEffect(() => {
    dispatch({type: 'REFRESH'})
  }, [])

  return (
    <UserInfoContext.Provider value={[state, dispatch]}>
      {children}
    </UserInfoContext.Provider>
  )
}

export const UserInfoContext = createContext(initialState);
export default UserInfoStore;