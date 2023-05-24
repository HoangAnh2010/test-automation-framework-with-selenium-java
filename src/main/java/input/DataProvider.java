package input;

import com.google.gson.annotations.SerializedName;
import com.opencsv.bean.CsvBindByPosition;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import model.SignInPage;
import model.SignUpPage;

import java.io.Serializable;
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DataProvider implements Serializable {

    @CsvBindByPosition(position = 0)
    @XmlElement(name = "ScriptID")
    @SerializedName("ScriptID")
    private String scriptID;
    @CsvBindByPosition(position = 1)
    @XmlElement(name = "ScriptTitle")
    @SerializedName("ScriptTitle")
    private String scriptTitle;
    @CsvBindByPosition(position = 2)
    @XmlElement(name = "StepID")
    @SerializedName("StepID")
    private String stepID;
    @CsvBindByPosition(position = 3)
    @XmlElement(name = "Description")
    @SerializedName("Description")
    private String description;
    @CsvBindByPosition(position = 4)
    @XmlElement(name = "Keyword")
    @SerializedName("Keyword")
    private String keyword;
    @CsvBindByPosition(position = 5)
    @XmlElement(name = "LocatorType")
    @SerializedName("LocatorType")
    private String locatorType;
    @CsvBindByPosition(position = 6)
    @XmlElement(name = "LocatorValue")
    @SerializedName("LocatorValue")
    private String locatorValue;
    @CsvBindByPosition(position = 7)
    @XmlElement(name = "TestData")
    @SerializedName("TestData")
    private String testData;

    public DataProvider(SignInPage signInPage) {
        this.scriptID = signInPage.getScriptID();
        this.scriptTitle = signInPage.getScriptTitle();
        this.stepID = signInPage.getStepID();
        this.description = signInPage.getDescription();
        this.keyword = signInPage.getKeyword();
        this.locatorType = signInPage.getLocatorType();
        this.locatorValue = signInPage.getLocatorValue();
        this.testData = signInPage.getTestData();
    }

    public DataProvider(SignUpPage signUpPage){
        this.scriptID = signUpPage.getScriptID();
        this.scriptTitle = signUpPage.getScriptTitle();
        this.stepID = signUpPage.getStepID();
        this.description = signUpPage.getDescription();
        this.keyword = signUpPage.getKeyword();
        this.locatorType = signUpPage.getLocatorType();
        this.locatorValue = signUpPage.getLocatorValue();
        this.testData = signUpPage.getTestData();
    }
    public DataProvider(String scriptID, String scriptTitle, String stepID, String description, String keyword, String locatorType, String locatorValue, String testData) {
        this.scriptID = scriptID;
        this.scriptTitle = scriptTitle;
        this.stepID = stepID;
        this.description = description;
        this.keyword = keyword;
        this.locatorType = locatorType;
        this.locatorValue = locatorValue;
        this.testData = testData;
    }

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
        return "SignInPage{" +
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