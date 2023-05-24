package constants;

import helpers.Helpers;
import helpers.PropertiesHelpers;
import utils.ReportUtils;
import java.io.File;

public final class FrwConstants {

	private FrwConstants() {
	}

	static {
		PropertiesHelpers.loadAllFiles();
		System.out.println("Data From FrameworkConstants: " + PropertiesHelpers.getProperties());
	}

	public static final String PROJECT_PATH = Helpers.getCurrentDir();
	public static final String EXCEL_DATA_FILE_PATH = PropertiesHelpers.getValue("EXCEL_DATA_FILE_PATH");
	public static final String JSON_DATA_FILE_PATH = PropertiesHelpers.getValue("JSON_DATA_FILE_PATH");
	public static final String XML_DATA_FILE_PATH = PropertiesHelpers.getValue("XML_DATA_FILE_PATH");
	public static final String CSV_DATA_FILE_PATH = PropertiesHelpers.getValue("CSV_DATA_FILE_PATH");
	public static final String BROWSER = PropertiesHelpers.getValue("BROWSER");
	public static final String URL_HRM = PropertiesHelpers.getValue("URL_HRM");
	public static final String REMOTE_URL = PropertiesHelpers.getValue("REMOTE_URL");
	public static final String REMOTE_PORT = PropertiesHelpers.getValue("REMOTE_PORT");
	public static final String PROJECT_NAME = PropertiesHelpers.getValue("PROJECT_NAME");
	public static final String REPORT_TITLE = PropertiesHelpers.getValue("REPORT_TITLE");
	public static final String EXTENT_REPORT_NAME = PropertiesHelpers.getValue("EXTENT_REPORT_NAME");
	public static final String EXTENT_REPORT_FOLDER = PropertiesHelpers.getValue("EXTENT_REPORT_FOLDER");
	public static final String EXPORT_VIDEO_PATH = PropertiesHelpers.getValue("EXPORT_VIDEO_PATH");
	public static final String EXPORT_CAPTURE_PATH = PropertiesHelpers.getValue("EXPORT_CAPTURE_PATH");
	public static final String AUTHOR = PropertiesHelpers.getValue("AUTHOR");
	public static final String TARGET = PropertiesHelpers.getValue("TARGET");
	public static final String HEADLESS = PropertiesHelpers.getValue("HEADLESS");
	public static final String OVERRIDE_REPORTS = PropertiesHelpers.getValue("OVERRIDE_REPORTS");
	public static final String OPEN_REPORTS_AFTER_EXECUTION = PropertiesHelpers
			.getValue("OPEN_REPORTS_AFTER_EXECUTION");
	public static final String SEND_EMAIL_TO_USERS = PropertiesHelpers.getValue("SEND_EMAIL_TO_USERS");
	public static final String SCREENSHOT_PASSED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_PASSED_STEPS");
	public static final String SCREENSHOT_FAILED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_FAILED_STEPS");
	public static final String SCREENSHOT_SKIPPED_STEPS = PropertiesHelpers.getValue("SCREENSHOT_SKIPPED_STEPS");
	public static final String SCREENSHOT_ALL_STEPS = PropertiesHelpers.getValue("SCREENSHOT_ALL_STEPS");
//	public static final String VIDEO_RECORD = PropertiesHelpers.getValue("VIDEO_RECORD");


	public static final int WAIT_DEFAULT = Integer.parseInt(PropertiesHelpers.getValue("WAIT_DEFAULT"));
	public static final int WAIT_PAGE_LOADED = Integer.parseInt(PropertiesHelpers.getValue("WAIT_PAGE_LOADED"));

	public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
	public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
	public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator + EXTENT_REPORT_FILE_NAME;


	public static final String YES = "yes";
	public static final String NO = "no";


	/* ICONS - START */


	public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
	public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
	public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";

	public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
	public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";

	/* style="text-align:center;" */
	public static final String ICON_SOCIAL_GITHUB_URL = "https://github.com/HoangAnh2010";
	public static final String ICON_SOCIAL_GITHUB = "<a href='" + ICON_SOCIAL_GITHUB_URL
			+ "'><i class='fa fa-github-square' style='font-size:24px'></i></a>";

	/* ICONS - END */

	public static String getExtentReportFilePath() {
		if (EXTENT_REPORT_FILE_PATH.isEmpty()) {
			EXTENT_REPORT_FILE_PATH = ReportUtils.createExtentReportPath();
		}
		return EXTENT_REPORT_FILE_PATH;
	}

}
