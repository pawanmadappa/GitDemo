package com.orange.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class BaseClass_MavenOrange implements ITestListener {
	
	public static WebDriver driver;
	
	public static Logger lg = Logger.getLogger("BaseClass_MavenOrange");
	
	public static Map<String,String> locatorDataMap = new HashMap<String, String>();
	public static Map<String,String> testDataMap = new HashMap<String, String>();
	
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports exReports;
	public static ExtentTest exTest;
	
	public static void writeLogs(String msg) {
		lg.info(msg);
		exTest.log(Status.INFO, msg);
	}

	public static void writeErrorLogs(Throwable t) {
		String s = Arrays.toString(t.getStackTrace());
		String s1 = s.replaceAll(",", "\n");
		lg.error(s1);
		//exTest.log(Status.ERROR, s1);
	}
	
	public void onFinish(ITestContext arg0) {
		writeLogs("The test execution is completed");
		exTest.log(Status.INFO, "The test execution is completed");
		}

	public void onStart(ITestContext arg0) {
		
		File f = new File("./src/test/results/report_mavenorange.html");
		htmlReporter= new ExtentHtmlReporter(f);
		exReports = new ExtentReports();
		exReports.attachReporter(htmlReporter);
		exTest = exReports.createTest("Intializing steps");
		//exReports.flush();
		
		try {
			getAndStoreLocatorData();
			getAndStoreTestData();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		File f1 = new File("./src/test/results/results_mavenorange.txt");
		try {
		FileWriter fw = new FileWriter(f1);
		fw.write("Starting fresh execution"+"\n");
		fw.flush();
		fw.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		writeLogs("The test execution is started");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onTestFailure(ITestResult arg0) {
		String testCaseName = arg0.getName();
		try {
			writeResultsToFile(testCaseName, "Fail");
			writeLogs(" The test case by name "+ testCaseName + " is Failed!!");
			writeErrorLogs(arg0.getThrowable());
			exTest.log(Status.ERROR, "The test "+testCaseName+"failed");
			exReports.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void onTestSkipped(ITestResult arg0) {
		writeLogs("The test "+ arg0.getName()+ " is skipped");
	}

	public void onTestStart(ITestResult arg0) {
		writeLogs("The test "+ arg0.getName()+ " is getting started");
	}
	
	public void onTestSuccess(ITestResult arg0) {
		String testCaseName = arg0.getName();
		try {
			writeResultsToFile(testCaseName, "Pass");
			writeLogs(" The test case by name "+ testCaseName + " is Passed!!");
			exTest.log(Status.PASS, "The test "+ testCaseName+" Passed");
			exReports.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getConfigData(String key) throws Exception {
		File f = new File("./src/test/data/configdata_mavenorange.properties");
		FileInputStream fis = new FileInputStream(f);
		Properties prop = new Properties();
		prop.load(fis);
		String value = prop.getProperty(key);
		return value;
	}
	
	@BeforeMethod(alwaysRun = true)
	public static void launchBrowser() throws Exception {
		String url = getConfigData("url");
		String browser = getConfigData("browser");
		
		if(browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "./src/test/utilities/chromedriver.exe");
			driver = new ChromeDriver();
		}
		else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.gecko.driver", "./src/test/utilities/geckodriver.exe");
			driver = new FirefoxDriver();
		}
		
		driver.get(url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	/*
	public static String getLocatorData(String pageName, String elementName) throws Exception {
		String locator = " ";
		File f = new File("./data/locatordata_orange.xlsx");
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("webElement");
		int rows = ws.getLastRowNum();
		for(int i=1;i<=rows;i++) {
			String page = ws.getRow(i).getCell(0).getStringCellValue();
			String element = ws.getRow(i).getCell(1).getStringCellValue();
			if(page.equalsIgnoreCase(pageName)&& element.equalsIgnoreCase(elementName)) {
			locator	= ws.getRow(i).getCell(2).getStringCellValue();
			}
		}
		return locator;
	}
	*/
	
	public static void getAndStoreLocatorData() throws Exception {
		File f = new File("./src/test/data/locatordata_mavenorange.xlsx");
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("webElement");
		int rows = ws.getLastRowNum();
		for(int i=1;i<=rows;i++) {
			String page = ws.getRow(i).getCell(0).getStringCellValue();
			String element = ws.getRow(i).getCell(1).getStringCellValue();
			String locator = ws.getRow(i).getCell(2).getStringCellValue();
			locatorDataMap.put(page+"$"+element, locator);
		}
		wb.close();
	}
	
	public static String getLocatorFromMap(String pageName, String elementName) {
		
		String webElement = locatorDataMap.get(pageName+"$"+elementName);
		return webElement;
		
	}
	
	/*
	public static String getTestData(String pageName, String elementName) throws Exception {
		String data = " ";
		File f = new File("./data/testdata_orange.xlsx");
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("testData");
		int rows = ws.getLastRowNum();
		for(int i=1;i<=rows;i++) {
			String page = ws.getRow(i).getCell(0).getStringCellValue();
			String element = ws.getRow(i).getCell(1).getStringCellValue();
			if(page.equalsIgnoreCase(pageName)&& element.equalsIgnoreCase(elementName)) {
			data = ws.getRow(i).getCell(2).getStringCellValue();
			}
		}
		return data;
}
*/
	
	public static void getAndStoreTestData() throws Exception {
		
		File f = new File("./src/test/data/testdata_mavenorange.xlsx");
		FileInputStream fis = new FileInputStream(f);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet ws = wb.getSheet("testData");
		int rows = ws.getLastRowNum();
		for(int i=1;i<=rows;i++) {
			String page = ws.getRow(i).getCell(0).getStringCellValue();
			String element = ws.getRow(i).getCell(1).getStringCellValue();
			String value = ws.getRow(i).getCell(2).getStringCellValue();
			testDataMap.put(page+"$"+element, value);
		}
		wb.close();
	}
	
	public static String getTestDataFromMap(String pageName, String elementName) {
		String data = testDataMap.get(pageName+"$"+elementName);
		return data;
	}
	
	public static void writeResultsToFile(String testCase, String status) throws Exception {
		File f = new File("./src/test/results/results_mavenorange.txt");
		FileWriter fw = new FileWriter(f,true);
		fw.write(testCase+ "========="+ status + "\n");
		fw.flush();
		fw.close();
	}
	
	/*
	public static void extentReport() {
		File f = new File("./src/test/results/report_mavenorange.html");
		htmlReporter= new ExtentHtmlReporter(f);
		exReports = new ExtentReports();
		exReports.attachReporter(htmlReporter);
		exTest = exReports.createTest("Sample test");
		exTest.log(Status.INFO, "Starting the sample test case");
		exTest.log(Status.PASS, "The sample test case Passed");
		exReports.flush();	
	}
	*/
	
	@AfterMethod(alwaysRun = true)
	public static void closeBrowser() {
		driver.close();
	}
	
	
}

