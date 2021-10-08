import jwt_decode from "jwt-decode";
import { useHistory } from "react-router";

const UserInfoReducer = (state, action) => {

  const history = useHistory();

  switch (action.type) {
    case "LOGIN":
      sessionStorage.setItem("jwt", `Bearer ${action.payload.token}`);

      const decodedJWT = jwt_decode(action.payload.token);

      return {
        email: decodedJWT.sub,
        role: decodedJWT.role,
        loggedIn: true,
      };
    case "LOGOUT":
      sessionStorage.removeItem("jwt");
      history.push("/");
      
      return {
        email: "",
        role: "",
        loggedIn: false,
      };
    case "REFRESH":
      const jwtToken = sessionStorage.getItem("jwt");

      if (!jwtToken) {
        return {
          email: "",
          role: "",
          loggedIn: false,
        };
      } else {
        const decodedJwtToken = jwt_decode(jwtToken);

        return {
          email: decodedJwtToken.sub,
          role: decodedJwtToken.role,
          loggedIn: true,
        };
      }
    default:
      return state;
  }
};

export default UserInfoReducer;
