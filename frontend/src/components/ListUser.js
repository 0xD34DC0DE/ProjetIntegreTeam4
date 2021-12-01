import React, { useState, useEffect, useContext } from 'react'
import ListUserDroppable from "./ListUserDroppable";
import ListUserDraggable from "./ListUserDraggable";
import axios from "axios";
import { UserInfoContext } from "../stores/UserInfoStore";


const ListUser = () => {
  const [userInfo] = useContext(UserInfoContext);
  const [students, setStudents] = useState([]);

  useEffect(() => {
    handleRemoval();
  }, []);

  const handleRemoval = async () => {
    let response = await axios({
      method: "GET",
      url: `http://localhost:8080/student/getAllStudentsNoSupervisor`,
      headers: {
        Authorization: userInfo.jwt,
      },
      responseType: "json",
    });
    console.log('students', students)
    setStudents(response.data);
  };

  return (
    <>
      <ListUserDroppable role="SUPERVISOR" students={students} setStudents={setStudents} handleRemoval={handleRemoval} />
      <ListUserDraggable role="STUDENT" students={students} setStudents={setStudents} />
    </>
  )
}

export default ListUser
