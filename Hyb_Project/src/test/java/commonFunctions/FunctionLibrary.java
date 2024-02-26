package commonFunctions;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
//method for launching bowser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load file
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("edge"))
	{
		driver = new EdgeDriver();
		driver.manage().window().maximize();
	}
	else
	{
		Reporter.log("Browser value is Not matching",true);
	}
	return driver;
}
//method for launch url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
}
//method for wait for element
public static void waitForElement(String Locator_Type,String Locator_Value,String TestData)
{
	WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(TestData)));
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(Locator_Value)));
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator_Value)));	
	}
}
//method for textboxes
public static void typeAction(String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_Value)).clear();
		driver.findElement(By.id(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_Value)).clear();
		driver.findElement(By.xpath(Locator_Value)).sendKeys(Test_Data);
	}
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_Value)).clear();
		driver.findElement(By.name(Locator_Value)).sendKeys(Test_Data);
	}
}
//method for buttons,checkboxes,radio buttons,links and images
public static void clickAction(String Locator_Type,String Locator_value)
{
	if(Locator_Type.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(Locator_value)).click();
	}
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(Locator_value)).click();
		
	}
	if(Locator_Type.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(Locator_value)).sendKeys(Keys.ENTER);
	}
}
//method for validating title
public static void validateTitle(String Expected_Title)
{
	String Actual_Title = driver.getTitle();
	try {
	Assert.assertEquals(Expected_Title, Actual_Title, "Title is Not Matching");
	}catch(Throwable t)
	{
		System.out.println(t.getMessage());
	}
}
//method for closing browser
public static void closeBrowser()
{
	driver.quit();
}

//method for date generate
public static String generateDate()
{
	Date date = new Date();
	DateFormat df = new SimpleDateFormat("YYYY_MM_dd");
	return df.format(date);
}
//Method for listbox
public static void dropDownAction(String Locator_Type,String Locator_Value,String Test_Data)
{
	if(Locator_Type.equalsIgnoreCase("xpath"))
	{
		int value = Integer.parseInt(Test_Data);
		Select element = new Select(driver.findElement(By.xpath(Locator_Value)));
		element.selectByIndex(value);
	}
	
		if(Locator_Type.equalsIgnoreCase("name"))
		{
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.name(Locator_Value)));
			element.selectByIndex(value);
	     }
		if(Locator_Type.equalsIgnoreCase("id"))
		{
			int value = Integer.parseInt(Test_Data);
			Select element = new Select(driver.findElement(By.id(Locator_Value)));
			element.selectByIndex(value);
		}
}
		//Method for capturing stock number into note pad
		public static void CaptureStockNum(String Locator_Type,String Locator_Value)throws Throwable
		{
			String StockNum = "";
			if(Locator_Type.equalsIgnoreCase("id"))
			{
				StockNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
			}
			if(Locator_Type.equalsIgnoreCase("name"))
			{
				StockNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
			}
			if(Locator_Type.equalsIgnoreCase("xpath"))
			{
				StockNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
			}
			
			FileWriter fw = new FileWriter("./CaptureData/stocknumber.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(StockNum);
			bw.flush();
			bw.close();
		
		}

           //Method for Stock Table
             public static void stockTable() throws Throwable
             {
            	 FileReader fr = new FileReader("./CaptureData/stocknumber.txt");
            	 BufferedReader br = new BufferedReader(fr);
            	 String Exp_Data =  br.readLine();
            	 
            	 if(!driver.findElement(By.xpath(conpro.getProperty("search-tetbox"))).isDisplayed())
            	 driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();           	
            	 driver.findElement(By.xpath(conpro.getProperty("search-tetbox"))).clear();
            	 driver.findElement(By.xpath(conpro.getProperty("search-tetbox"))).sendKeys(Exp_Data);
            	 driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
            	 Thread.sleep(4000);
            	 String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
            	 Reporter.log(Exp_Data+"  "+Act_Data,true);
            	 try {
            		 Assert.assertEquals(Exp_Data, Act_Data,"Stock Number Not Matching");
            	 }catch(AssertionError a)
            	 {
            		 System.out.println(a.getMessage());
            	 }
             }
             //Method for capture supplier Number
             public static void capturesup(String Locator_Type,String Locator_Value) throws Throwable
             {
            	 String supplierNum="";
            	 
            	 if(Locator_Type.equalsIgnoreCase("xpath"))
            	 {
            		 supplierNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
            	 }
            	 if(Locator_Type.equalsIgnoreCase("name"))
            	 {
            		 supplierNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
            	 }
            	 if(Locator_Type.equalsIgnoreCase("id"))
            	 {
            		 supplierNum = driver.findElement(By.id(Locator_Value)).getAttribute("value"); 
            	 }
            	 FileWriter fr = new FileWriter("./CaptureData/suppliernumber.txt");
            	 BufferedWriter br = new BufferedWriter(fr);
            	 br.write(supplierNum);
            	 br.flush();
            	 br.close();
            	 }
             //Method For Supplier Table
             public static void supplierTable() throws Throwable
             {
            	 FileReader fr = new FileReader("./CaptureData/suppliernumber.txt");
            	 BufferedReader br = new BufferedReader(fr);    
            	 String Exp_Data = br.readLine();
            	 if(!driver.findElement(By.xpath(conpro.getProperty("search-panel"))).isDisplayed())
            
            		 driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
            		 driver.findElement(By.xpath(conpro.getProperty("search-tetbox"))).clear();
            		 driver.findElement(By.xpath(conpro.getProperty("search-tetbox"))).sendKeys(Exp_Data);
            		 driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
            		 Thread.sleep(4000);
            		 String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText(); 
                 	 Reporter.log(Exp_Data+"     "+Act_Data,true);
            		try {
            		Assert.assertEquals(Exp_Data, Act_Data, "Supplier number Not Matching");
            		}catch(AssertionError a)
            		{
            			System.out.println(a.getMessage());
            		}
            	 }
             
             //Method for capture customer number
             public static void capturecus(String Locator_Type,String Locator_Value) throws Throwable
             {
            	 String customerNum="";
            	 if(Locator_Type.equalsIgnoreCase("xpath"))
            	 {
            		 customerNum = driver.findElement(By.xpath(Locator_Value)).getAttribute("value");
            	 }
            	 if(Locator_Type.equalsIgnoreCase("name"))
            	 {
            		 customerNum = driver.findElement(By.name(Locator_Value)).getAttribute("value");
            	 }
            	 if(Locator_Type.equalsIgnoreCase("id"))
            	 {
            		 customerNum = driver.findElement(By.id(Locator_Value)).getAttribute("value");
            	 }
            	 FileWriter fw = new FileWriter("./CaptureData/customernumber.txt");
            	 BufferedWriter bw = new BufferedWriter(fw);
            	 bw.write(customerNum);
            	 bw.flush();
            	 bw.close();
             }
             //Method for capture Customer Table
             public static void customerTable()throws Throwable
             {
             	FileReader fr = new FileReader("./CaptureData/customernumber.txt");
             	BufferedReader br = new BufferedReader(fr);
             	String Exp_Data = br.readLine();
             	if(!driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).isDisplayed())
             		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
             	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).clear();
             	driver.findElement(By.xpath(conpro.getProperty("search-textbox"))).sendKeys(Exp_Data);
             	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
             	Thread.sleep(4000);
             	String Act_Data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
             	Reporter.log(Exp_Data+"     "+Act_Data,true);
             	try {
             	Assert.assertEquals(Exp_Data, Act_Data, "Customer number Not Matching");
             	}catch(AssertionError a)
             	{
             		System.out.println(a.getMessage());
             	}
             }
             
             }


             
             


