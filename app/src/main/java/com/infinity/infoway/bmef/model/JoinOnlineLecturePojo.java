package com.infinity.infoway.bmef.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JoinOnlineLecturePojo {

    @SerializedName("Table")
    @Expose
    private List<Faculty> faculty = null;

    public List<Faculty> getFaculty() {
        return faculty;
    }

    public void setFaculty(List<Faculty> faculty) {
        this.faculty = faculty;
    }

    public class Faculty {

        @SerializedName("ac_is_live_class")
        @Expose
        private Integer acIsLiveClass;
        @SerializedName("meeting_url")
        @Expose
        private String meetingUrl;
        @SerializedName("sm_name")
        @Expose
        private String smName;
        @SerializedName("dvm_name")
        @Expose
        private String dvmName;
        @SerializedName("sub_short_name")
        @Expose
        private String subShortName;
        @SerializedName("emp_name")
        @Expose
        private String empName;
        @SerializedName("lec_no")
        @Expose
        private String lecNo;
        @SerializedName("dl_id")
        @Expose
        private Integer dlId;
        @SerializedName("lcm_id")
        @Expose
        private Integer lcmId;
        @SerializedName("course_fullname")
        @Expose
        private String courseFullname;

        public Integer getAcIsLiveClass() {
            return acIsLiveClass;
        }

        public void setAcIsLiveClass(Integer acIsLiveClass) {
            this.acIsLiveClass = acIsLiveClass;
        }

        public String getMeetingUrl() {
            return meetingUrl;
        }

        public void setMeetingUrl(String meetingUrl) {
            this.meetingUrl = meetingUrl;
        }

        public String getSmName() {
            return smName;
        }

        public void setSmName(String smName) {
            this.smName = smName;
        }

        public String getDvmName() {
            return dvmName;
        }

        public void setDvmName(String dvmName) {
            this.dvmName = dvmName;
        }

        public String getSubShortName() {
            return subShortName;
        }

        public void setSubShortName(String subShortName) {
            this.subShortName = subShortName;
        }

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getLecNo() {
            return lecNo;
        }

        public void setLecNo(String lecNo) {
            this.lecNo = lecNo;
        }

        public Integer getDlId() {
            return dlId;
        }

        public void setDlId(Integer dlId) {
            this.dlId = dlId;
        }

        public Integer getLcmId() {
            return lcmId;
        }

        public void setLcmId(Integer lcmId) {
            this.lcmId = lcmId;
        }

        public String getCourseFullname() {
            return courseFullname;
        }

        public void setCourseFullname(String courseFullname) {
            this.courseFullname = courseFullname;
        }

    }

}
