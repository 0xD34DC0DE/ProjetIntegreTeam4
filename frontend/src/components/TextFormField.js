
import React from "react"
import { DialogContentText, FormControl, TextField } from "@mui/material"

const TextFormField = (props) => {
    return (
        <div>
            <DialogContentText>
                {props.dialogContentText}
            </DialogContentText>
            <FormControl sx={{width: "100%"}}>
                <TextField
                    autoFocus
                    margin="dense"
                    id={props.id}
                    label={props.label}
                    type={props.type}
                    variant="standard"
                    sx={{flexGrow: 1}}
                    value = {props.value}
                    onChange={props.onChange}
                />
            </FormControl>
        </div>
    )
}

export default TextFormField
