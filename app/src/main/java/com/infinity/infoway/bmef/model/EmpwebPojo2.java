package com.infinity.infoway.bmef.model;

import java.util.List;

public class EmpwebPojo2 {

    private List<EnrollBean> enroll;

    public List<EnrollBean> getEnroll() {
        return enroll;
    }

    public void setEnroll(List<EnrollBean> enroll) {
        this.enroll = enroll;
    }

    public static class EnrollBean {
        /**
         * URL : http://bmef.icrp.in/cms/index.aspx?f2F6Vc9Sjxa0000060cyCtRmB+KFoteixivTUieEpq6GWur000000000000fRef0lTn2V5FJ0000Wz7MF3fQ0000xSf7loYlSwFGrfqKj+8ipi0xFX
         */

        private String URL;

        public String getURL() {
            return URL;
        }

        public void setURL(String URL) {
            this.URL = URL;
        }
    }
}
