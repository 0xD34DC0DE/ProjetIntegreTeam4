const fs = require('fs');
const { exit } = require('process');
const path = require('path');


current_working_director = path.dirname(__filename);

worker_filepath = current_working_director + "\\..\\node_modules\\pdfjs-dist\\build\\pdf.worker.min.js";

destination_filepath = current_working_director + "\\..\\public\\pdf.worker.min.js";

fs.copyFile(worker_filepath, destination_filepath, (err) => {
  if (err) {
    console.error("Error Found:", err);
    exit(1);
  }
});
