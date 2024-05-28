package CommenFununctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionsLibrary {
	public static WebDriver driver;
	public static Properties conpro;
	public static WebDriver startBrowser()throws Throwable
	{
		conpro=new Properties();
		conpro.load(new FileInputStream("./propertyFiles/Environment.properties"));
		if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
		{
			driver=new ChromeDriver();
			driver.manage().window().maximize();
		}
		else if(conpro.getProperty("Browser").equalsIgnoreCase("Firefox"))
		{
			driver=new FirefoxDriver();

		}

		else
		{
			Reporter.log("Browser value is not matching",true);

		}
		return driver;

	}
	public static void openUrl()
	{
		driver.get(conpro.getProperty("Url"));

	}
	public static void waitForElement(String Locatortype,String LocatorValue,String TestData)
	{
		WebDriverWait mywait=new WebDriverWait(driver,Duration.ofSeconds(Integer.parseInt(TestData)));
		if(Locatortype.equalsIgnoreCase("id"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));

		}
		if(Locatortype.equalsIgnoreCase("xpath"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));

		}
		if(Locatortype.equalsIgnoreCase("name"))
		{
			mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));

		}
	}

	public static void typeAction(String LocatorType,String LocatorValue,String Testdata)
	{
		if(LocatorType.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).clear();
			driver.findElement(By.id(LocatorValue)).sendKeys(Testdata);

		}
		if(LocatorType.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).clear();
			driver.findElement(By.name(LocatorValue)).sendKeys(Testdata);

		}
		if(LocatorType.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).clear();
			driver.findElement(By.xpath(LocatorValue)).sendKeys(Testdata);

		}
	}
	public static void clickAction(String Locatortype,String LocatorValue)
	{
		if (Locatortype.equalsIgnoreCase("xpath"))
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
		if (Locatortype.equalsIgnoreCase("name"))
		{
			driver.findElement(By.name(LocatorValue)).click();
		}
		if (Locatortype.equalsIgnoreCase("id"))
		{
			driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
		}
	}
	public static void validateTitle(String expected_title)
	{
		String Actual_Title=driver.getTitle();
		try {
			Assert.assertEquals(Actual_Title, expected_title,"Title not matching");


		}
		catch (AssertionError a) {
			System.out.println(a.getMessage());
		}

	}


	public static void 	closeBrowser()
	{
		driver.quit();
	}


	public static  String generateDate() {
		Date date=new Date();
		DateFormat df=new SimpleDateFormat("YYYY_MM_dd hh_mm_ss");
		return df.format(date);

	}




	public static void dropdownAction(String LocatorType,String LocatorValue,String TestData) {

		if (LocatorType.equalsIgnoreCase("id"))
		{

			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.id(LocatorValue)));
			element.selectByIndex(value);
		}
		if (LocatorType.equalsIgnoreCase("name"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.name(LocatorValue)));
			element.selectByIndex(value);

		}
		if (LocatorType.equalsIgnoreCase("xpath"))
		{
			int value=Integer.parseInt(TestData);
			Select element=new Select(driver.findElement(By.xpath(LocatorValue)));
			element.selectByIndex(value);

		}

	}
	public static void captureStock(String Locatortype,String LocatorValue) throws Throwable
	{
		String Stocknum="";
		if(Locatortype.equalsIgnoreCase("name")) {
			Stocknum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}


		if(Locatortype.equalsIgnoreCase("id")) {
			Stocknum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}

		if(Locatortype.equalsIgnoreCase("xpath")) {
			Stocknum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}

		FileWriter fw=new FileWriter("./CaptureData/stockNumber.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(Stocknum);
		bw.flush();
		bw.close();

	}

	public static void StockTable(String LocatorType,String LocatorValue)throws Throwable{
		FileReader fr=new FileReader("./CaptureData/stockNumber.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String act_Data=driver.findElement(By.xpath("//table[@id='tbl_a_stock_itemslist']/tbody/tr/td[8]/div/span/span")).getText();
		Reporter.log(act_Data+"  "+Exp_Data,true);
		try {
			Assert.assertEquals(act_Data, Exp_Data,"stock number not match");
		}
		catch (AssertionError m) {
			Reporter.log(m.getMessage(),true);

		}
	}
	public static void captureSup(String LocatorType, String LocatorValue) throws Throwable
	{


		String SupplierNum="";
		if(LocatorType.equalsIgnoreCase("name")) {
			SupplierNum=driver.findElement(By.name(LocatorValue)).getAttribute("value");
		}


		if(LocatorType.equalsIgnoreCase("id")) {
			SupplierNum=driver.findElement(By.id(LocatorValue)).getAttribute("value");
		}

		if(LocatorType.equalsIgnoreCase("xpath")) {
			SupplierNum=driver.findElement(By.xpath(LocatorValue)).getAttribute("value");
		}

		FileWriter fw=new FileWriter("./CaptureData/SupplierNum.txt");
		BufferedWriter bw=new BufferedWriter(fw);
		bw.write(SupplierNum);
		bw.flush();
		bw.close();


	}
	public static void supplierTable()throws Throwable
	{
		FileReader	fr=new FileReader("./CaptureData/SupplierNum.txt");
		BufferedReader br=new BufferedReader(fr);
		String Exp_Data=br.readLine();
		if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
		{
			driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
		}
		
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
		driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
		driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
		Thread.sleep(3000);
		String act_Data=driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr/td[6]/div/span/span")).getText();
		Reporter.log(act_Data+"  "+Exp_Data,true);
		try {
			Assert.assertEquals(act_Data, Exp_Data,"supplier number not match");
		}
		catch (AssertionError m) {
			Reporter.log(m.getMessage(),true);

		}
	}
}







