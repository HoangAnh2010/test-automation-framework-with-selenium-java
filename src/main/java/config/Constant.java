package config;

public class Constant {
	//This is the list of System Variables
	//Declared as 'public', so that it can be used in other classes of this project
	//Declared as 'static', so that we do not need to instantiate a class object
	//Declared as 'final', so that the value of this variable can be changed
	//'String' & 'int' are the data type for storing a type of value

	public static final String URL="file:///G:/DEMO_TEACHING/00.PROJECT_DEMO/bmi.html";
	//public static final String Path_TestData=System.getProperty("user.dir")+"//src//test//java//dataEngine//data.xlsx";
	public static final String Path_OR=System.getProperty("user.dir")+"//src//config//OR.txt";
	//public static final String File_TestData="data.xlsx";
	
	//List of Data Sheet Column Numbers
	public static final int Col_TestCaseID = 0;
	public static final int Col_TestScenarioID = 1;
	public static final int Col_ActionKeyword = 3;
	public static final int Col_PageName = 4;
	public static final int Col_PageObject = 5;
	public static final int Col_DataSet = 6;
	
	//List of Data Engine Excel sheets
	public static final String Sheet_TestSteps="Test Steps";
	
	//List of Test Data
//	public static final String Username = "testuser1@gmail.com";
//	public static final String Password = "test@123";

}

