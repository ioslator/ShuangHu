package com.shaunghu_hrms.shuanghu.model;

import java.util.Date;

public class Notice {
    private Integer notice_id;
    private String notice_title;
    private String notice_content;
    private String publish_dept; // 发布部门
    private Date create_time;
    private Integer status;

    public String getNotice_content() {
        return notice_content;
    }

    public void setNotice_content(String notice_content) {
        this.notice_content = notice_content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public String getPublish_dept() {
        return publish_dept;
    }

    public void setPublish_dept(String publish_dept) {
        this.publish_dept = publish_dept;
    }

    public Integer getNotice_id() {
        return notice_id;
    }

    public void setNotice_id(Integer notice_id) {
        this.notice_id = notice_id;
    }

    public String getNotice_title() {
        return notice_title;
    }

    public void setNotice_title(String notice_title) {
        this.notice_title = notice_title;
    }


    // 无参构造
    public Notice() {}

    // 全参构造
    public Notice(Integer id, String title, String content, String publishDept, Date createTime,Integer status) {
        this.notice_id = id;
        this.notice_title = title;
        this.notice_content = content;
        this.publish_dept = publishDept;
        this.create_time = createTime;
        this.status=status;
    }


}