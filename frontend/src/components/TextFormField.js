import React from "react";
import { DialogContentText, FormControl, TextField } from "@mui/material";

const TextFormField = ({
  focus,
  id,
  label,
  type,
  error,
  value,
  onChange,
  visible,
  dialogContentText,
}) => {
  return (
    <>
      {visible && (
        <>
          <DialogContentText>{dialogContentText}</DialogContentText>
          <FormControl sx={{ width: "100%" }}>
            <TextField
              autoFocus={focus}
              margin="dense"
              id={id}
              label={label}
              type={type}
              helperText={error}
              variant="standard"
              sx={{ flexGrow: 1 }}
              value={value}
              error={error !== ""}
              onChange={onChange}
              visible={visible.toString()}
            />
          </FormControl>
        </>
      )}
    </>
  );
};

export default TextFormField;
