package com.infinity.infoway.bmef.HrAppPojo;

import java.util.List;

public class ReligionPojo {


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * religion_id : 1
         * religion_name : HINDU
         */

        private int religion_id;
        private String religion_name;

        public int getReligion_id() {
            return religion_id;
        }

        public void setReligion_id(int religion_id) {
            this.religion_id = religion_id;
        }

        public String getReligion_name() {
            return religion_name;
        }

        public void setReligion_name(String religion_name) {
            this.religion_name = religion_name;
        }
    }
}
