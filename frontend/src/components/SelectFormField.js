
import React from "react"
import { DialogContentText, MenuItem, Select } from "@mui/material"

const SelectFormField = (props) => {
    return (
        <div>
            <DialogContentText>
                {props.dialogContentText}
            </DialogContentText>
            <Select
                labelId={props.labelId}
                id={props.id}
                value={props.value}
                label={props.label}
                name={props.name}
                sx={{flexGrow: 1, width: "100%"}}
                onChange={props.onChange}
            >
            {
                props.items.map((item) => {
                    return (
                        <MenuItem key={item.value} value={item.value}>{item.type}</MenuItem>
                    )
                })
            }
            </Select>
        </div>
    )
}

export default SelectFormField
