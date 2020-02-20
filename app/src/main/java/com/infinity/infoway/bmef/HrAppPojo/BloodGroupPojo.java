package com.infinity.infoway.bmef.HrAppPojo;

import java.util.List;

public class BloodGroupPojo {


    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * blood_id : 1
         * blood_group : A+
         */

        private int blood_id;
        private String blood_group;

        public int getBlood_id() {
            return blood_id;
        }

        public void setBlood_id(int blood_id) {
            this.blood_id = blood_id;
        }

        public String getBlood_group() {
            return blood_group;
        }

        public void setBlood_group(String blood_group) {
            this.blood_group = blood_group;
        }
    }
}
