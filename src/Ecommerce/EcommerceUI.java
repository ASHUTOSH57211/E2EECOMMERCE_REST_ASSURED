package Ecommerce;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import utilities.ProjectConfigurations;

@Test
public class EcommerceUI {
	
	@SuppressWarnings("deprecation")
	public static void logintoEcomUI(String productName,String orerID,String token,String productID) throws IOException, InterruptedException {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//driver//chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		String url = ProjectConfigurations.LoadProperties("UIurl");
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		
		//enter userid and password and login
		
		driver.findElement(By.xpath("//input[@id = 'userEmail']")).sendKeys(ProjectConfigurations.LoadProperties("loginId"));
		
		driver.findElement(By.xpath("//input[@id = 'userPassword']")).sendKeys(ProjectConfigurations.LoadProperties("password"));
		
		driver.findElement(By.xpath("//input[@id = 'login']")).click();
		WebDriverWait wait =new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//button[contains(text(),'HOME')]"))));
		String dashBoardURL = driver.getCurrentUrl();
		System.out.println(dashBoardURL);
		Assert.assertEquals(dashBoardURL, "https://rahulshettyacademy.com/client/dashboard/dash");
		
		//Validate the product is created
		String xpathCreated =  "//b[text()="+"'"+productName+"'"+"]/parent::h5//parent::div/button[text()=' View']";
		
		boolean isdisplayedFlag = driver.findElement(By.xpath(xpathCreated)).isDisplayed();
		Assert.assertTrue(isdisplayedFlag);
		
		JavascriptExecutor js =  (JavascriptExecutor)driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		
		Thread.sleep(2000);
		js.executeScript("window.scrollTo(document.body.scrollHeight,0)");
		Thread.sleep(2000);
		//validate order is placed
		driver.findElement(By.xpath("//button[@ routerlink='/dashboard/myorders']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='View']")).click();
		
		String uiOrderID = driver.findElement(By.xpath("//small[@class ='col-title']/following-sibling::div")).getText();
		System.out.println(uiOrderID);
		Thread.sleep(2000);
		Assert.assertEquals(uiOrderID, orerID);
		
		//Delete the  Placed order
		driver.findElement(By.xpath("//button[@ routerlink='/dashboard/myorders']")).click();
		Thread.sleep(2000);
		driver.findElement(By.xpath("//button[text()='Delete']")).click();
		boolean flag = false;
		Thread.sleep(2000);
		
		String message = "You have No Orders to show at this time.";
		String actualMesage = driver.findElement(By.xpath("//div[@class='mt-4 ng-star-inserted']")).getText();
		System.out.println(actualMesage);
		if(actualMesage.contains(message)) {
			flag = true;
		}
		System.out.println(flag);
		
		
		
		//delete api is called for deleting the product
		DeleteProductApi.deleteProduct(token, productID);
		
		Thread.sleep(3000);
		driver.findElement(By.xpath("//button[@ routerlink='/dashboard/']")).click();
		try {
			driver.findElement(By.xpath(xpathCreated));
		}catch(org.openqa.selenium.NoSuchElementException e){
			isdisplayedFlag = false;
			
		}
		
		
		Assert.assertFalse(isdisplayedFlag);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		driver.quit();
		
		
	}
	
}
