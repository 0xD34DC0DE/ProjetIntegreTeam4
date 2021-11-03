import { createTheme } from "@mui/material/styles";
import zIndex from "@mui/material/styles/zIndex";

const theme = createTheme({
  zIndex: {
    appBar: zIndex.drawer + 1,
  },
  palette: {
    action: {
      disabled: "rgba(255, 255, 255, 0.2)",
      selected: "rgba(255, 255, 255, 0.2)",
    },
    primary: {
      main: "#18181B",
      secondary: "#0e0e10",
    },
    text: {
      primary: "#FFFFFF",
      secondary: "#FFFFFF",
    },
  },
  mixins: {
    toolbar: {
      height: 45,
    },
  },
  components: {
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: "none",
          fontSize: "0.8em",
        },
      },
    },
    MuiSelect: {
      styleOverrides: {
        icon: {
          fill: "white",
        },
      },
    },
    MuiInput: {
      styleOverrides: {
        underline: {
          "&:before": {
            borderBottom: "1px solid rgba(100, 100, 100, 0.5) !important",
          },
          "&:after": {
            borderBottom: "2px solid rgba(250, 250, 250, 1)",
          },
          "&:hover:before": {
            borderBottom: "2px solid rgba(150, 150, 150, 1) !important",
          },
        },
      },
    },
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          "& .Mui-focused": {
            "& .MuiOutlinedInput-notchedOutline": {
              borderColor: "rgba(100, 100, 100, 0.5)",
            },
          },
          "& .MuiOutlinedInput-notchedOutline": {
            borderColor: "rgba(100, 100, 100, 0.5)",
          },
          "& .MuiOutlinedInput-input.Mui-disabled" : {
            backgroundColor: "rgba(255, 255, 255, 0.6)",
            opacity: 1
          }
        },
      },
    },
    MuiTabs: {
      styleOverrides: {
        indicator: {
          backgroundColor: "white",
        },
        root: {
          "& .MuiButtonBase-root": {
            color: "rgba(155, 155, 155, 0.8)",
          },

          "& .MuiButtonBase-root.Mui-selected": {
            color: "white",
          },
        },
      },
    },
    MuiDialog: {
      styleOverrides: {
        paper: {
          backgroundColor: "rgba(24, 24, 27, 0.95)",
        },
        root: {
          "& .MuiInputLabel-root": {
            color: "rgba(150, 150, 150, 0.8)",
          },
          "& .MuiInput-root": {
            color: "white",
          },
          "& .MuiInput-underline": {
            color: "white",
            backgroundColor: "rgba(0, 0, 0, 0)",
          },
          "& .MuiInputBase-input": {
            backgroundColor: "rgba(0, 0, 0, 0)",
          },
          "& .MuiButton-root": {
            color: "rgba(150, 150, 150, 0.7)",
          },
          "& .MuiButton-root:disabled": {
            color: "rgba(100, 100, 100, 0.5)",
          },
          "& .MuiButton-root:hover": {
            color: "rgba(255, 255, 255, 0.8)",
          },
          "& .MuiMobileStepper-root": {
            backgroundColor: "rgba(24, 24, 27, 0)",
          },
          "& .MuiMobileStepper-dot": {
            backgroundColor: "rgba(100, 100, 100, 0.3)",
          },
          "& .MuiMobileStepper-dotActive": {
            backgroundColor: "rgba(255, 255, 255, 0.3)",
          },
          "& .MuiDialogContentText-root": {
            color: "rgba(255, 255, 255, 0.5)",
          },
        },
      },
    },
    MuiMenu: {
      styleOverrides: {
        root: {
          "& .MuiMenuItem-root:hover": {
            backgroundColor: "rgba(100, 100, 100, 0.3)",
          },
          "& .MuiList-root": {
            backgroundColor: "rgba(24, 24, 27, 1)",
          },
          "& .MuiPaper-root": {
            backgroundColor: "rgba(24, 24, 27, 1)",
          },
        },
      },
    },
    MuiDialogContentText: {
      styleOverrides: {
        root: {
          color: "white",
        },
      },
    },
  },
});

export default theme;
