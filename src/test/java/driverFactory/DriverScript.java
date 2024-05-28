package driverFactory;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import CommenFununctions.FunctionsLibrary;
import utilities.ExcleFileUtil;

public class DriverScript 
{

	WebDriver driver;
	String FileInputpath="./FileInupt/DataEngine.xlsx";
	String FileOutputpath="./FileOutput/HybridResult.xlsx";
	ExtentReports report;
	ExtentTest logger;
	String Tcsheet ="MasterTestCases";

	
	public void startTest()throws Throwable
	{
		String Module_status="";
		String Module_new="";

		ExcleFileUtil xl = new ExcleFileUtil(FileInputpath);
		for(int i=1;i<=xl.rowCount(Tcsheet);i++)
		{
			if(xl.getCellData(Tcsheet, i, 2).equalsIgnoreCase("Y"))
			{

				String TCModule=xl.getCellData(Tcsheet, i, 1);
				report=new ExtentReports("./target/ExtentReports/"+TCModule+FunctionsLibrary.generateDate()+".html");
				logger=report.startTest(TCModule);
				for(int j=1;j<=xl.rowCount(TCModule);j++) 
				{
					String Description=xl.getCellData(TCModule, j, 0);
					String ObjectType=xl.getCellData(TCModule, j, 1);
					String Ltype=xl.getCellData(TCModule, j, 2);
					String Lvalue=xl.getCellData(TCModule, j, 3);
					String TestData=xl.getCellData(TCModule, j, 4);

					try {

						if(ObjectType.equalsIgnoreCase("startBrowser"))
						{
							driver=FunctionsLibrary.startBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						if(ObjectType.equalsIgnoreCase("openUrl"))
						{
							FunctionsLibrary.openUrl();
							logger.log(LogStatus.INFO,Description);

						}
						if(ObjectType.equalsIgnoreCase("waitForElement"))
						{
							FunctionsLibrary.waitForElement(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO,Description);

						}
						if(ObjectType.equalsIgnoreCase("typeAction"))
						{
							FunctionsLibrary.typeAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO,Description);

						}
						if(ObjectType.equalsIgnoreCase("clickAction"))
						{
							FunctionsLibrary.clickAction(Ltype, Lvalue);
							logger.log(LogStatus.INFO,Description);

						}

						if(ObjectType.equalsIgnoreCase("validateTitle"))
						{
							FunctionsLibrary.validateTitle(TestData);
							logger.log(LogStatus.INFO,Description);

						}
						if(ObjectType.equalsIgnoreCase("closeBrowser"))
						{
							FunctionsLibrary.closeBrowser();
							logger.log(LogStatus.INFO,Description);
						}
						if(ObjectType.equalsIgnoreCase("dropdownAction"))
						{
							FunctionsLibrary.dropdownAction(Ltype, Lvalue, TestData);
							logger.log(LogStatus.INFO,Description);
						}
						
						if (ObjectType.equalsIgnoreCase("captureStock"))
						{
							FunctionsLibrary.captureStock(Ltype, Lvalue);
							logger.log(LogStatus.INFO, Description);
						}

						if(ObjectType.equalsIgnoreCase("StockTable"))
						{
							FunctionsLibrary.StockTable(Ltype, Lvalue);
						
							logger.log(LogStatus.INFO,Description);
						}
						if(ObjectType.equalsIgnoreCase("captureSup")) {
							FunctionsLibrary.captureSup(Ltype, Lvalue);
							logger.log(LogStatus.INFO,Description);
						}
						if(ObjectType.equalsIgnoreCase("supplierTable")) {
							FunctionsLibrary.supplierTable();
							logger.log(LogStatus.INFO,Description);
						}
						
						xl.setCellData(TCModule, j, 5, "pass", FileOutputpath);
						logger.log(LogStatus.PASS,Description);
						Module_status="True";
					
					}
					catch(Throwable t)
					{
						System.out.println(t.getMessage());
						xl.setCellData(TCModule, j, 5, "fail",FileOutputpath);
						logger.log(LogStatus.FAIL,Description);
						Module_new= "False";
						File screen=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
						FileUtils.copyFile(screen, new File("./target/Screenshot/"+Description+FunctionsLibrary.generateDate()+".png"));
					}
					if(Module_status.equalsIgnoreCase("True"))
					{
						xl.setCellData(Tcsheet, i, 3, "pass", FileOutputpath);


					}

					if(Module_new.equalsIgnoreCase("False"))
					{
						xl.setCellData(Tcsheet, i, 3, "fail", FileOutputpath);

					}
					report.endTest(logger);
					report.flush();

				}
			}else
			{
				xl.setCellData(Tcsheet, i, 3, "Blocked", FileOutputpath);
			}

		}	 


	}

}



















