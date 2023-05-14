package model;

import com.google.gson.annotations.SerializedName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataOfSignIn implements Serializable {
    @CsvBindByPosition(position = 0)
    @XmlElement(name ="TC_ID")
    @SerializedName("TC_ID")
    private String tcId;
    @CsvBindByPosition(position = 1)
    @XmlElement(name ="TC_Description")
    @SerializedName("TC_Description")
    private String tCDescription;
    @CsvBindByPosition(position = 2)
    @XmlElement(name ="Email")
    @SerializedName("Email")
    private String email;
    @CsvBindByPosition(position = 3)
    @XmlElement(name ="Password")
    @SerializedName("Password")
    private String password;
    @CsvBindByPosition(position = 4)
    @XmlElement(name ="Result")
    @SerializedName("Result")
    private String result;
    @CsvBindByPosition(position = 5)
    @XmlElement(name ="Note")
    @SerializedName("Note")
    private String note;

    public DataOfSignIn(String tcId, String tCDescription, String email, String password, String result, String note) {
        this.tcId = tcId;
        this.tCDescription = tCDescription;
        this.email = email;
        this.password = password;
        this.result = result;
        this.note = note;
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

    @Override
    public String toString() {
        return "DataOfSignIn{" +
                "tcId='" + tcId + '\'' +
                ", tCDescription='" + tCDescription + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", result='" + result + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}