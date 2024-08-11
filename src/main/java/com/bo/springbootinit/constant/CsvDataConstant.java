package com.bo.springbootinit.constant;

import org.springframework.stereotype.Component;

@Component
public class CsvDataConstant {
        private String csvData;

        public String getCsvData() {
            return csvData;
        }

        public void setCsvData(String csvData) {
            this.csvData = csvData;
        }
}
