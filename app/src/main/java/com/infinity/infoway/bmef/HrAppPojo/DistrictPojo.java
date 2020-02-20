package com.infinity.infoway.bmef.HrAppPojo;

import java.util.List;

public class DistrictPojo {


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * DM_id : 1
         * DM_name : Gandhinagar
         */

        private int DM_id;
        private String DM_name;

        public int getDM_id() {
            return DM_id;
        }

        public void setDM_id(int DM_id) {
            this.DM_id = DM_id;
        }

        public String getDM_name() {
            return DM_name;
        }

        public void setDM_name(String DM_name) {
            this.DM_name = DM_name;
        }
    }
}
