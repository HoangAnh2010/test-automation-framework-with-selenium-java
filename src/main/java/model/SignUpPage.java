package model;

import com.google.gson.annotations.SerializedName;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SignUpPage implements Serializable {
    @XmlElement(name ="ScriptID")
    @SerializedName("ScriptID")
    private String scriptID;
    @XmlElement(name ="ScriptTitle")
    @SerializedName("ScriptTitle")
    private String scriptTitle;
    @XmlElement(name ="StepID")
    @SerializedName("StepID")
    private String stepID;
    @XmlElement(name ="Description")
    @SerializedName("Description")
    private String description;
    @XmlElement(name ="Keyword")
    @SerializedName("Keyword")
    private String keyword;
    @XmlElement(name ="LocatorType")
    @SerializedName("LocatorType")
    private String locatorType;
    @XmlElement(name ="LocatorValue")
    @SerializedName("LocatorValue")
    private String locatorValue;
    @XmlElement(name ="TestData")
    @SerializedName("TestData")
    private String testData;

    public String getScriptID() {
        return scriptID;
    }

    public void setScriptID(String scriptID) {
        this.scriptID = scriptID;
    }

    public String getScriptTitle() {
        return scriptTitle;
    }

    public void setScriptTitle(String scriptTitle) {
        this.scriptTitle = scriptTitle;
    }

    public String getStepID() {
        return stepID;
    }

    public void setStepID(String stepID) {
        this.stepID = stepID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocatorType() {
        return locatorType;
    }

    public void setLocatorType(String locatorType) {
        this.locatorType = locatorType;
    }

    public String getLocatorValue() {
        return locatorValue;
    }

    public void setLocatorValue(String locatorValue) {
        this.locatorValue = locatorValue;
    }

    public String getTestData() {
        return testData;
    }

    public void setTestData(String testData) {
        this.testData = testData;
    }

    @Override
    public String toString() {
        return "SignUpPage{" +
                "scriptID='" + scriptID + '\'' +
                ", scriptTitle='" + scriptTitle + '\'' +
                ", stepID='" + stepID + '\'' +
                ", description='" + description + '\'' +
                ", keyword='" + keyword + '\'' +
                ", locatorType='" + locatorType + '\'' +
                ", locatorValue='" + locatorValue + '\'' +
                ", testData='" + testData + '\'' +
                '}';
    }
}