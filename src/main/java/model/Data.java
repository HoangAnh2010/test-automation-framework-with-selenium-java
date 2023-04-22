package model;
import com.google.gson.annotations.SerializedName;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data implements Serializable {
    @XmlElement(name = "SignInPage")
    @SerializedName("SignInPage")
    private List<SignInPage> signInPage;
    @XmlElement(name="DataOfSignIn")
    @SerializedName("DataOfSignIn")
    private List<DataOfSignIn> dataOfSignIn;
    @XmlElement(name="SignUpPage")
    @SerializedName("SignUpPage")
    private List<SignUpPage> signUpPage;
    @XmlElement(name="DataOfSignUp")
    @SerializedName("DataOfSignUp")
    private List<DataOfSignUp> dataOfSignUp;
    public List<SignInPage> getSignInPage() {
        return signInPage;
    }

    public void setSignInPage(List<SignInPage> signInPage) {
        this.signInPage = signInPage;
    }
    public List<DataOfSignIn> getDataOfSignIn() {
        return dataOfSignIn;
    }

    public void setDataOfSignIn(List<DataOfSignIn> dataOfSignIn) {
        this.dataOfSignIn = dataOfSignIn;
    }
    public List<SignUpPage> getSignUpPage() {
        return signUpPage;
    }

    public void setSignUpPage(List<SignUpPage> signUpPage) {
        this.signUpPage = signUpPage;
    }

    public List<DataOfSignUp> getDataOfSignUp() {
        return dataOfSignUp;
    }

    public void setDataOfSignUp(List<DataOfSignUp> dataOfSignUp) {
        this.dataOfSignUp = dataOfSignUp;
    }



    @Override
    public String toString() {
        return "Data{" +
                "signInPage=" + signInPage +
                ", dataOfSignIn=" + dataOfSignIn +
                ", signUpPage=" + signUpPage +
                ", dataOfSignUp=" + dataOfSignUp +
                '}';
    }
}