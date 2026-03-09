package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;

public class FlightBookingAutomation {
	
	WebDriver driver;
	
	
	@BeforeClass
	public void init() {
		
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
	}
	
	
	@Test
	public void navigate() throws InterruptedException {
		driver.get("https://blazedemo.com/");
		System.out.println("Title : "+driver.getTitle());
	    System.out.println();

		
		Thread.sleep(2000);
	}
	
	@Test(dependsOnMethods = "navigate")
	public void depCity() throws InterruptedException {
	     WebElement welcome = driver.findElement(By.xpath("/html/body/div[2]/div/h1"));
	     System.out.println(welcome.getText());
		    System.out.println();

		
		 Select dep = new Select(driver.findElement(By.name("fromPort")));
		 dep.selectByVisibleText("San Diego");
		 
		Thread.sleep(2000);
		System.out.println("Departure city selected");
	    System.out.println();


	}
	
	@Test(dependsOnMethods = "depCity")
	public void destCity() throws InterruptedException {
		Select dest = new Select(driver.findElement(By.name("toPort")));
		dest.selectByVisibleText("Dublin");
		
		Thread.sleep(2000);
		
		System.out.println("Destination city selected");
	    System.out.println();

}
	
	
	@Test(dependsOnMethods = "destCity")
	public void findFlights() {
		driver.findElement(By.xpath("/html/body/div[3]/form/div/input")).click();
	}
	
	
	@Test(dependsOnMethods = "findFlights")
	public void dispFlights() throws InterruptedException {
		List<WebElement> flights = driver.findElements(By.xpath("//table[@class='table']//tr"));
		
		if(flights.size() > 1) {
			System.out.println("Flights list displayed for the selected route");
		    System.out.println();

		}
		else {
			System.out.println("No flights found for the selected route");
		    System.out.println();

		}
		
		Thread.sleep(2000);

	}
	
	@Test(dependsOnMethods = "dispFlights")
	public void chooseFlight() {
		driver.findElement(By.xpath("/html/body/div[2]/table/tbody/tr[3]/td[1]/input")).click();
	}
	
	@Test(dependsOnMethods = "chooseFlight")
	public void verifyPage() throws InterruptedException {
//		Assert.assertTrue(driver.getPageSource().contains("Your flight from")); 
		
		WebElement heading = driver.findElement(By.xpath("//h2"));
	    System.out.println(heading.getText());
	    System.out.println();


	    if(heading.getText().contains("Your flight from")) {
	        System.out.println("Flight route verified");
		    System.out.println();

	    }

	    WebElement flightNumber = driver.findElement(By.xpath("//p[1]"));
	    System.out.println("------ Flight Details ------  \n" + flightNumber.getText());

	    WebElement totalCost = driver.findElement(By.xpath("/html/body/div[2]/p[5]"));
	    System.out.println(totalCost.getText());
	    System.out.println();

	    
		Thread.sleep(2000);


	}
	
	@Test(dependsOnMethods = "verifyPage")
	public void personalDetails() throws InterruptedException {
		driver.findElement(By.id("inputName")).sendKeys("Saksham Srivastava");
		Thread.sleep(1000);

		driver.findElement(By.id("address")).sendKeys("Main Road");
		Thread.sleep(1000);

		driver.findElement(By.id("city")).sendKeys("Ranchi");
		Thread.sleep(1000);

		driver.findElement(By.id("state")).sendKeys("Jharkhand");
		Thread.sleep(1000);

		driver.findElement(By.id("zipCode")).sendKeys("834001");
		
	    System.out.println("Personal details entered successfully");
	    System.out.println();


	}
	
	@Test(dependsOnMethods = "personalDetails")
	public void paymentDetails() throws InterruptedException {

		Select cardType = new Select (driver.findElement(By.id("cardType")));
		cardType.selectByVisibleText("American Express");
		Thread.sleep(1000);

		
		driver.findElement(By.id("creditCardNumber")).sendKeys("1234 5678 0909");
		Thread.sleep(1000);

		WebElement cardMonth = driver.findElement(By.id("creditCardMonth"));
		cardMonth.clear();
		cardMonth.sendKeys("03");

		Thread.sleep(1000);

		WebElement cardYear = driver.findElement(By.id("creditCardYear"));
		cardYear.clear();
		cardYear.sendKeys("2027");
		
		Thread.sleep(1000);


		driver.findElement(By.id("nameOnCard")).sendKeys("Saksham Srivastava");
		Thread.sleep(1000);

		
		WebElement rememberMe = driver.findElement(By.id("rememberMe"));
		
		if(!rememberMe.isSelected()) {
		    rememberMe.click();
		}
		
	    System.out.println("Payment details entered successfully");
	    System.out.println();


		
		Thread.sleep(1000);
		
}
	
	@Test(dependsOnMethods = "paymentDetails")
	public void purchaseFlight() {
		driver.findElement(By.xpath("/html/body/div[2]/form/div[11]/div/input")).click();
		
	}
	
	@Test(dependsOnMethods = "purchaseFlight")
	public void verifyBooking() throws InterruptedException {
		WebElement confirmation = driver.findElement(By.xpath("/html/body/div[2]/div/h1"));
		Assert.assertTrue(confirmation.getText().contains("Thank you for your purchase today!"));
		
		System.out.println("*** "+confirmation.getText() + " Your flight booking is confirmed ***");
	    System.out.println();

		Thread.sleep(3000);
	}
	
	@AfterClass
    public void teardown() {
        driver.quit();
    }
	
	

}
