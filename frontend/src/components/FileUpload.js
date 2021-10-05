import axios from "axios";
import React from "react";

const FileUpload = () => {

    const uploadFile = (evt) => {
        evt.preventDefault();

        let pathFilenameExtension = document.getElementById('file').value;
        let filenameExtension = pathFilenameExtension.split(/(\\|\/)/g).pop();
        let filename = filenameExtension.substr(0, filenameExtension.lastIndexOf('.')) || filenameExtension;
        let mimeType = 'application/pdf';

        const data = new FormData(evt.target);
        data.append('filename', filename);
        data.append('mimeType', mimeType);
        axios({
            method: "POST",
            baseURL: "http://localhost:8080",
            url: "/file/asset",
            data: data,
            headers: {
                Authorization: sessionStorage.getItem("jwt") ,
            },
        })
        .then((response) => {
            console.log(response.data);
        })
        .catch((error) => {
            console.error(error);
        });
    }

    return (
        <form action="/action_page.php" onSubmit={uploadFile}>
            <div>
                <label>Type : 
                    <input type="text" id="type" name="type" defaultValue="CV" readOnly />
                </label>
            </div>
            <div>
                <label>Fichier : 
                    <input type="file" id="file" name="file" accept="application/pdf, text/plain" />
                </label>
            </div>
            <div>
                <input type="submit" value="Submit" />
            </div>
        </form>
    );
}

export default FileUpload;

