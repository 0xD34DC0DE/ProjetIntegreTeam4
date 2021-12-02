import jwt_decode from "jwt-decode";
import initialState from "./UserInfoStore";
import axios from "axios";
import { sidebarList } from "../components/Configuration";

const UserInfoReducer = (state, action) => {
  const jwtToken = sessionStorage.getItem("jwt");

  const fetchUserProfileImage = async (email, jwtToken) => {
    return await axios({
      method: "GET",
      url: "http://localhost:8080/profileImage",
      headers: {
        Authorization: jwtToken,
      },
      params: {
        uploaderEmail: email,
      },
      responseType: "arraybuffer",
    })
      .then(async (response) => {
        const arrayBuffer = response.data;
        const headers = response.headers;
        const blobUrl = await arrayBufferToBlobUrl(arrayBuffer, headers);
        return blobUrl;
      })
      .catch((error) => console.error(error));
  };

  const arrayBufferToBlobUrl = (arrayBuffer, headers) => {
    const imageType = headers["Content-Type"];
    const blob = new Blob([arrayBuffer], { type: imageType });
    return window.URL.createObjectURL(blob);
  };

  const decodeJwtToken = (token) => {
    if (!token) return initialState;
    const decodedJwtToken = jwt_decode(token);
    return {
      id: decodedJwtToken.id,
      email: decodedJwtToken.sub,
      role: decodedJwtToken.role,
      loggedIn: true,
      profileImage: fetchUserProfileImage(decodedJwtToken.sub, token),
      jwt: token,
    };
  };

  switch (action.type) {
    case "LOGIN":
      const bearerToken = `Bearer ${action.payload.token}`;
      sessionStorage.setItem("jwt", bearerToken);
      return decodeJwtToken(bearerToken);
    case "LOGOUT":
      sessionStorage.removeItem("jwt");
      sessionStorage.removeItem("selection");
      return initialState;
    case "REFRESH":
      return decodeJwtToken(jwtToken);
    case "UPDATE_PROFILE_IMAGE":
      let decodedJwtToken = jwt_decode(jwtToken);
      decodedJwtToken.profileImage = action.payload.profileImage;
      return {
        id: decodedJwtToken.id,
        email: decodedJwtToken.sub,
        role: decodedJwtToken.role,
        loggedIn: true,
        profileImage: decodedJwtToken.profileImage,
        jwt: jwtToken,
      };
    default:
      return state;
  }
};

export default UserInfoReducer;
