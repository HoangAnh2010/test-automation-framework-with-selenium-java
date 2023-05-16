package model;

import com.google.gson.annotations.SerializedName;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataOfSignUp  implements Serializable {
    @XmlElement(name ="TC_ID")
    @SerializedName("TC_ID")
    private String tcId;
    @XmlElement(name ="TC_Description")
    @SerializedName("TC_Description")
    private String tCDescription;
    @XmlElement(name ="Email")
    @SerializedName("Email")
    private String email;
    @XmlElement(name ="Password")
    @SerializedName("Password")
    private String password;
    @XmlElement(name ="Password_Confirm")
    @SerializedName("Password_Confirm")
    private String passwordConfirm;
    @XmlElement(name ="Result")
    @SerializedName("Result")
    private String result;
    @XmlElement(name ="Note")
    @SerializedName("Note")
    private String note;
    @XmlElement(name ="Name")
    @SerializedName("Name")
    private String name;

    public DataOfSignUp() {
    }

    public DataOfSignUp(String tcId, String tCDescription, String email, String password, String passwordConfirm, String result, String note, String name) {
        this.tcId = tcId;
        this.tCDescription = tCDescription;
        this.email = email;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.result = result;
        this.note = note;
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataOfSignUp{" +
                "tcId='" + tcId + '\'' +
                ", tCDescription='" + tCDescription + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", passwordConfirm='" + passwordConfirm + '\'' +
                ", result='" + result + '\'' +
                ", note='" + note + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    public String getTcId() {
        return tcId;
    }

    public void setTcId(String tcId) {
        this.tcId = tcId;
    }

    public String getTCDescription() {
        return tCDescription;
    }

    public void setTCDescription(String tCDescription) {
        this.tCDescription = tCDescription;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}