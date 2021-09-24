import React from "react";
import { DialogContentText, MenuItem, Select } from "@mui/material";

const SelectFormField = ({
  visible,
  dialogContentText,
  labelId,
  id,
  value,
  label,
  name,
  onChange,
  items,
}) => {
  return (
    <>
      {visible && (
        <>
          <DialogContentText>{dialogContentText}</DialogContentText>
          <Select
            labelId={labelId}
            id={id}
            value={value}
            label={label}
            name={name}
            variant="standard"
            sx={{ flexGrow: 1, width: "100%" }}
            onChange={onChange}
          >
            {items.map((item) => {
              return (
                <MenuItem key={item.value} value={item.value}>
                  {item.type}
                </MenuItem>
              );
            })}
          </Select>
        </>
      )}
    </>
  );
};

export default SelectFormField;
