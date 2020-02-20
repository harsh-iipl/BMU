package com.infinity.infoway.bmef.HrAppPojo;

import java.util.List;

public class CountryPojo {

    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * Country_id : 15182
         * Country_name : INDIA
         */

        private int Country_id;
        private String Country_name;

        public int getCountry_id() {
            return Country_id;
        }

        public void setCountry_id(int Country_id) {
            this.Country_id = Country_id;
        }

        public String getCountry_name() {
            return Country_name;
        }

        public void setCountry_name(String Country_name) {
            this.Country_name = Country_name;
        }
    }
}
