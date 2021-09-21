
import React from "react"
import { DialogContentText, MenuItem, Select } from "@mui/material"

const SelectFormField = (props) => {
    return (
        <React.Fragment>
        {props.visible &&
            <React.Fragment>
                <DialogContentText>
                    {props.dialogContentText}
                </DialogContentText>
                <Select
                    labelId={props.labelId}
                    id={props.id}
                    value={props.value}
                    label={props.label}
                    name={props.name}
                    variant="standard"
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
            </React.Fragment>
        }
        </React.Fragment>
    )
}

export default SelectFormField
