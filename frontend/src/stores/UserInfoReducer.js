import jwt_decode from "jwt-decode";

const UserInfoReducer = (state, action) => {
  switch (action.type) {
    case "LOGIN":
      sessionStorage.setItem("jwt", `Bearer ${action.payload.token}`);
      const decodedJWT = jwt_decode(action.payload.token);

      return {
        id: decodedJWT.id,
        email: decodedJWT.sub,
        role: decodedJWT.role,
        loggedIn: true,
        jwt: `Bearer ${action.payload.token}`,
      };
    case "LOGOUT":
      sessionStorage.removeItem("jwt");
      return {
        id: "",
        email: "",
        role: "",
        loggedIn: false,
        jwt: "",
      };
    case "REFRESH":
      const jwtToken = sessionStorage.getItem("jwt");

      if (!jwtToken) {
        return {
          id: "",
          email: "",
          role: "",
          loggedIn: false,
          jwt: "",
        };
      } else {
        const decodedJwtToken = jwt_decode(jwtToken);

        return {
          id: decodedJwtToken.id,
          email: decodedJwtToken.sub,
          role: decodedJwtToken.role,
          loggedIn: true,
          jwt: jwtToken,
        };
      }
    default:
      return state;
  }
};

export default UserInfoReducer;
