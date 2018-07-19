package com.ssm.entity;

import java.io.Serializable;

/**
 * zl
 * @author 
 */
public class Recruit implements Serializable {
    /**
     * 信息id
     */
    private Integer id;

    /**
     * 标题
     */
    private String title;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 职位月薪
     */
    private String monthlySalary;

    /**
     * 工作描述
     */
    private String jobDescription;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getMonthlySalary() {
        return monthlySalary;
    }

    public void setMonthlySalary(String monthlySalary) {
        this.monthlySalary = monthlySalary;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Recruit other = (Recruit) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getTitle() == null ? other.getTitle() == null : this.getTitle().equals(other.getTitle()))
            && (this.getCompanyName() == null ? other.getCompanyName() == null : this.getCompanyName().equals(other.getCompanyName()))
            && (this.getMonthlySalary() == null ? other.getMonthlySalary() == null : this.getMonthlySalary().equals(other.getMonthlySalary()))
            && (this.getJobDescription() == null ? other.getJobDescription() == null : this.getJobDescription().equals(other.getJobDescription()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
        result = prime * result + ((getCompanyName() == null) ? 0 : getCompanyName().hashCode());
        result = prime * result + ((getMonthlySalary() == null) ? 0 : getMonthlySalary().hashCode());
        result = prime * result + ((getJobDescription() == null) ? 0 : getJobDescription().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", title=").append(title);
        sb.append(", companyName=").append(companyName);
        sb.append(", monthlySalary=").append(monthlySalary);
        sb.append(", jobDescription=").append(jobDescription);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}