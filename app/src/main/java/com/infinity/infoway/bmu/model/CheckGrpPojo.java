package com.infinity.infoway.bmu.model;

import java.util.List;

public class CheckGrpPojo
{

    private List<TableBean> Table;

    public List<TableBean> getTable() {
        return Table;
    }

    public void setTable(List<TableBean> Table) {
        this.Table = Table;
    }

    public static class TableBean {
        /**
         * exist_status : 0
         */

        private String exist_status;

        public String getExist_status() {
            return exist_status;
        }

        public void setExist_status(String exist_status) {
            this.exist_status = exist_status;
        }
    }
}
