
import React from "react"
import { DialogContentText, FormControl, TextField } from "@mui/material"

const TextFormField = (props) => {
    return (
        <React.Fragment>
        {props.visible &&
            <React.Fragment>
                <DialogContentText>
                    {props.dialogContentText}
                </DialogContentText>
                <FormControl sx={{width: "100%"}}>
                    <TextField
                        autoFocus={props.focus}
                        margin="dense"
                        id={props.id}
                        label={props.label}
                        type={props.type}
                        helperText = {props.error}
                        variant="standard"
                        sx={{flexGrow: 1}}
                        value = {props.value}
                        error = {props.error !== ""}
                        onChange={props.onChange}
                    />
                </FormControl>
            </React.Fragment>
        }
        </React.Fragment>
    )
}

export default TextFormField
