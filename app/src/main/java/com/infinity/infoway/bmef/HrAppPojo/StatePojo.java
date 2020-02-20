package com.infinity.infoway.bmef.HrAppPojo;

import java.util.List;

public class StatePojo {
    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * state_id : 1
         * state_name : GUJARAT
         */

        private int state_id;
        private String state_name;

        public int getState_id() {
            return state_id;
        }

        public void setState_id(int state_id) {
            this.state_id = state_id;
        }

        public String getState_name() {
            return state_name;
        }

        public void setState_name(String state_name) {
            this.state_name = state_name;
        }
    }
}
