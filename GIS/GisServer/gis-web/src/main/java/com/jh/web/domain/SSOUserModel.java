package com.jh.web.domain;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * SSO单点用户信息
 *
 * @author jh
 */
public class SSOUserModel
{
    private Long oid;
    private String userId;
    private String userIdLow;
    private String userName;
    private String password;
    private Long deptid;
    private String jobNumber;
    private String idCardNum;
    private String sex;
    private String birthday;
    private String nation;
    private String highestEducation;
    private String jobTitle;
    private String post;
    private String nativePlace;
    private String mobile;
    private String tel;
    private String email;
    private String address;
    private String inheritDeptAuth;
    private String authTag;
    private String caKey;
    private String ipAddress;
    private String macAddress;
    private String distid;
    private Long jobState;
    private Long deleteFlag;
    private String caKeyExpiryDate;
    private String mobile2;
    private String token;
    private String uoid;
    private String sex_Caption;
    private String nation_Catpion;
    private String highestEducation_Caption;
    private String jobTitle_Caption;
    private String jobState_Caption;
    private String nativePlace_Caption;
    private Boolean hasError;
    private String errorMessage;

    public Long getOid()
    {
        return oid;
    }

    public void setOid(Long oid)
    {
        this.oid = oid;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserIdLow()
    {
        return userIdLow;
    }

    public void setUserIdLow(String userIdLow)
    {
        this.userIdLow = userIdLow;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Long getDeptid()
    {
        return deptid;
    }

    public void setDeptid(Long deptid)
    {
        this.deptid = deptid;
    }

    public String getJobNumber()
    {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber)
    {
        this.jobNumber = jobNumber;
    }

    public String getIdCardNum()
    {
        return idCardNum;
    }

    public void setIdCardNum(String idCardNum)
    {
        this.idCardNum = idCardNum;
    }

    public String getSex()
    {
        return sex;
    }

    public void setSex(String sex)
    {
        this.sex = sex;
    }

    public String getBirthday()
    {
        return birthday;
    }

    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }

    public String getNation()
    {
        return nation;
    }

    public void setNation(String nation)
    {
        this.nation = nation;
    }

    public String getHighestEducation()
    {
        return highestEducation;
    }

    public void setHighestEducation(String highestEducation)
    {
        this.highestEducation = highestEducation;
    }

    public String getJobTitle()
    {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle)
    {
        this.jobTitle = jobTitle;
    }

    public String getPost()
    {
        return post;
    }

    public void setPost(String post)
    {
        this.post = post;
    }

    public String getNativePlace()
    {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace)
    {
        this.nativePlace = nativePlace;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getInheritDeptAuth()
    {
        return inheritDeptAuth;
    }

    public void setInheritDeptAuth(String inheritDeptAuth)
    {
        this.inheritDeptAuth = inheritDeptAuth;
    }

    public String getAuthTag()
    {
        return authTag;
    }

    public void setAuthTag(String authTag)
    {
        this.authTag = authTag;
    }

    public String getCaKey()
    {
        return caKey;
    }

    public void setCaKey(String caKey)
    {
        this.caKey = caKey;
    }

    public String getIpAddress()
    {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress)
    {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress()
    {
        return macAddress;
    }

    public void setMacAddress(String macAddress)
    {
        this.macAddress = macAddress;
    }

    public String getDistid()
    {
        return distid;
    }

    public void setDistid(String distid)
    {
        this.distid = distid;
    }

    public Long getJobState()
    {
        return jobState;
    }

    public void setJobState(Long jobState)
    {
        this.jobState = jobState;
    }

    public Long getDeleteFlag()
    {
        return deleteFlag;
    }

    public void setDeleteFlag(Long deleteFlag)
    {
        this.deleteFlag = deleteFlag;
    }

    public String getCaKeyExpiryDate()
    {
        return caKeyExpiryDate;
    }

    public void setCaKeyExpiryDate(String caKeyExpiryDate)
    {
        this.caKeyExpiryDate = caKeyExpiryDate;
    }

    public String getMobile2()
    {
        return mobile2;
    }

    public void setMobile2(String mobile2)
    {
        this.mobile2 = mobile2;
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    public String getUoid()
    {
        return uoid;
    }

    public void setUoid(String uoid)
    {
        this.uoid = uoid;
    }

    public String getSex_Caption()
    {
        return sex_Caption;
    }

    public void setSex_Caption(String sex_Caption)
    {
        this.sex_Caption = sex_Caption;
    }

    public String getNation_Catpion()
    {
        return nation_Catpion;
    }

    public void setNation_Catpion(String nation_Catpion)
    {
        this.nation_Catpion = nation_Catpion;
    }

    public String getHighestEducation_Caption()
    {
        return highestEducation_Caption;
    }

    public void setHighestEducation_Caption(String highestEducation_Caption)
    {
        this.highestEducation_Caption = highestEducation_Caption;
    }

    public String getJobTitle_Caption()
    {
        return jobTitle_Caption;
    }

    public void setJobTitle_Caption(String jobTitle_Caption)
    {
        this.jobTitle_Caption = jobTitle_Caption;
    }

    public String getJobState_Caption()
    {
        return jobState_Caption;
    }

    public void setJobState_Caption(String jobState_Caption)
    {
        this.jobState_Caption = jobState_Caption;
    }

    public String getNativePlace_Caption()
    {
        return nativePlace_Caption;
    }

    public void setNativePlace_Caption(String nativePlace_Caption)
    {
        this.nativePlace_Caption = nativePlace_Caption;
    }

    public String getErrorMessage()
    {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }

    public Boolean getHasError()
    {
        return hasError;
    }

    public void setHasError(Boolean hasError)
    {
        this.hasError = hasError;
    }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("oid", oid)
                .append("userId", userId)
                .append("userIdLow", userIdLow)
                .append("userName", userName)
                .append("password", password)
                .append("deptid", deptid)
                .append("jobNumber", jobNumber)
                .append("idCardNum", idCardNum)
                .append("sex", sex)
                .append("birthday", birthday)
                .append("nation", nation)
                .append("highestEducation", highestEducation)
                .append("jobTitle", jobTitle)
                .append("post", post)
                .append("nativePlace", nativePlace)
                .append("mobile", mobile)
                .append("tel", tel)
                .append("email", email)
                .append("address", address)
                .append("inheritDeptAuth", inheritDeptAuth)
                .append("authTag", authTag)
                .append("caKey", caKey)
                .append("ipAddress", ipAddress)
                .append("macAddress", macAddress)
                .append("distid", distid)
                .append("jobState", jobState)
                .append("deleteFlag", deleteFlag)
                .append("caKeyExpiryDate", caKeyExpiryDate)
                .append("mobile2", mobile2)
                .append("token", token)
                .append("uoid", uoid)
                .append("sex_Caption", sex_Caption)
                .append("nation_Catpion", nation_Catpion)
                .append("highestEducation_Caption", highestEducation_Caption)
                .append("jobTitle_Caption", jobTitle_Caption)
                .append("jobState_Caption", jobState_Caption)
                .append("nativePlace_Caption", nativePlace_Caption)
                .append("hasError", hasError)
                .append("errorMessage", errorMessage)
                .toString();
    }
}
