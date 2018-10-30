package com.simplewebframework.extent;
import java.io.File;

import org.openqa.selenium.Platform;

/**
 * This class manages ExtentManager  by creating Extent Instances
 */
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.simplewebframework.configClass.DriverConfig;
import com.simplewebframework.configClass.TestConfig;
import com.simplewebframework.core.WebUIDriver;
import com.simplewebframework.helpers.ResourceHelper;

public class ExtentManager {
	
    private static ExtentReports extent;
    private static Platform platform;
  //  private static String reportFileName = "ExtentReports-Version3-Test-Automaton-Report.html";
  //  private static String macPath = System.getProperty("user.dir")+ "/TestReport";
  //  private static String windowsPath = System.getProperty("user.dir")+ "\\TestReport";
  //  private static String macReportFileLoc = macPath + "/" + reportFileName;
  //  private static String winReportFileLoc = windowsPath + "\\" + reportFileName;
      private static String defaultOS = TestConfig.DEFAULT_OS;
      private static String reportFileName = TestConfig.EXTENT_REPORT_FILENAME;
      private static String macPath = TestConfig.EXTENT_REPORT_PATH_MAC;
      private static String windowsPath = TestConfig.EXTENT_REPORT_PATH_WIN;
      private static String macReportFileLoc = TestConfig.EXTENT_REPORT_PATH_MAC + "/" + TestConfig.EXTENT_REPORT_FILENAME;
      private static String winReportFileLoc = TestConfig.EXTENT_REPORT_PATH_WIN + "\\" + TestConfig.EXTENT_REPORT_FILENAME;   
    
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public synchronized  static ExtentReports createInstance() {
        platform = getCurrentPlatform();
        String fileName = getReportFileLocation(platform);
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(fileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(fileName);
 
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
 
        return extent;
    }
 
    //Select the extent report file location based on platform
    private static String getReportFileLocation (Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + reportFileLocation + "\n");
                break;
            case WINDOWS:
                reportFileLocation = winReportFileLoc;;
                createReportPath(windowsPath);
                System.out.println("ExtentReport Path for WINDOWS: " + reportFileLocation + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        
        return reportFileLocation;
    }
    
/*    //Select the extent report file location based on platform
    private static String getReportFileLocation (Platform platform) {
        String reportFileLocation = null;
        switch (platform) {
            case MAC:
                reportFileLocation = macReportFileLoc;
                createReportPath(macPath);
                System.out.println("ExtentReport Path for MAC: " + macPath + "\n");
                break;
            case WINDOWS:
                reportFileLocation = winReportFileLoc;
                createReportPath(windowsPath);
                System.out.println("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
                break;
            default:
                System.out.println("ExtentReport path has not been set! There is a problem!\n");
                break;
        }
        return reportFileLocation;
    }*/
 
    //Create the report path if it does not exist
    private static void createReportPath (String path) {
        File testDirectory = new File(path);
        if (!testDirectory.exists()) {
            if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
            } else {
                System.out.println("Failed to create directory: " + path);
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
    }
 
    //Get current platform
    private static Platform getCurrentPlatform () {
        if (platform == null) {
           // String operSys = System.getProperty("defaultOS").toLowerCase();
            String operSys = TestConfig.DEFAULT_OS.toLowerCase();
            if (operSys.contains("win")) {
                platform = Platform.WINDOWS;
            } else if (operSys.contains("nix") || operSys.contains("nux")
                    || operSys.contains("aix")) {
                platform = Platform.LINUX;
            } else if (operSys.contains("mac")) {
                platform = Platform.MAC;
            }
        }
        return platform;
    }
}
