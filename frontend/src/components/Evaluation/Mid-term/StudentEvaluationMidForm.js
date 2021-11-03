import React from "react";
import { Grid } from "@mui/material";
import CompanyIdentificationDropdown from "./CompanyIdentificationDropdown";
import StudentIdentificationDropdown from "./StudentIdentificationDropdown";
import StudentMidEvaluationDropdown from "./StudentMidEvaluationDropdown";
import CompanyObservationDropdown from "./CompanyObservationDropdown";
import { motion } from "framer-motion";
import SubmitEvaluationButton from "../SubmitEvaluationButton";

const dropdowns = [
  <CompanyIdentificationDropdown />,
  <StudentIdentificationDropdown />,
  <StudentMidEvaluationDropdown />,
  <CompanyObservationDropdown />,
];

const StudentEvaluationMidForm = ({ visible }) => {
  return (
    <>
      {visible && (
        <Grid container px={5} pb={3}>
          {dropdowns.map((dropdown, key) => {
            return (
              <Grid
                item
                xl={12}
                lg={12}
                md={12}
                sm={12}
                xs={12}
                mt={5}
                key={key}
              >
                <motion.div
                  animate={{ opacity: [0, 1] }}
                  transition={{
                    duration: 0.2,
                    delay: (key + 1) * 0.2,
                  }}
                >
                  {dropdown}
                </motion.div>
              </Grid>
            );
          })}
          <SubmitEvaluationButton delay={1} />
        </Grid>
      )}
    </>
  );
};

export default StudentEvaluationMidForm;
