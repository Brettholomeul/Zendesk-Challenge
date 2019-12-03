package reduxForm;
import javax.swing.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


public class Main {
	
	
	
	public static void main(String[] args) {
		
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		String baseUrl = "https://redux-form.com/6.6.1/examples/wizard/";
		
		driver.get(baseUrl);
		
		
		/*
		 *  Testing that we are on the correct page by checking the title
		*/
		
		String headTitle = driver.findElement(By.id("wizard-form")).getText();
		
		if (headTitle.contentEquals("Wizard Form")) {
			System.out.println("Test Passed. On the correct page, based on the header.");
		}
		else {
			System.out.println("Test Failed: Header does not match.");
		}
		
		
		/* 
		 * Testing the First Name and Last Name boxes
		*/
		
		Actions builder = new Actions(driver);
		
		WebElement required;
		
		// Test when first name is blank
		typeFirstLastName("", "Smith", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!required.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Required' is displayed when first name is not provided.");
		}
		
		// Test when last name is blank
		typeFirstLastName("John", "", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/span"));
		if (!required.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Required' is displayed when last name is not provided.");
		}
		
		// Test when first and last name are provided
		typeFirstLastName("John", "Smith", builder, driver);
		
		clickNext(builder, driver);
		
		
		/*
		 *  Testing the email box
		 */
		
		// Test when email is blank
		typeEmail("", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!required.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Required' is displayed when email is not provided.");
		}
		
		
		WebElement invalid;
		typeEmail("invalid", builder, driver);
		invalid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!invalid.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Invalid email address' is displayed when email is not valid format.");
		}
		
		typeEmail("invalid@fakemail", builder, driver);
		invalid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!invalid.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Invalid email address' is displayed when email is not valid format.");
		}
		
		typeEmail("valid@fakemail.com", builder, driver);
		
		
		/*
		 *  Test the sex radio buttons
		 */
		
		clickNext(builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/span"));
		if (!required.getText().contentEquals("")) {
			System.out.println("Test Passed. 'Required' is displayed when sex is not selected.");
		}
		
		clickMale(builder, driver);
		clickFemale(builder, driver);
		
		System.out.println("Test Passed. Sex buttons work.");
		
		//driver.close();

	}
	
	public static void typeFirstLastName(String first, String last, Actions builder, WebDriver driver) {
		WebElement firstName = driver.findElement(By.name("firstName"));
		WebElement lastName = driver.findElement(By.name("lastName"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(firstName)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.sendKeys(firstName, first)
				.moveToElement(lastName)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.sendKeys(lastName, last)
				.build();
		
		series.perform();
	}
	
	public static void clickNext(Actions builder, WebDriver driver) {
		WebElement nextButton = driver.findElement(By.className("next"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(nextButton)
				.click()
				.build();
		
		series.perform();
	}
	
	public static void typeEmail(String email, Actions builder, WebDriver driver) {
		WebElement emailElement = driver.findElement(By.name("email"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(emailElement)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.sendKeys(emailElement, email)
				.build();
		
		series.perform();
		clickNext(builder, driver);
	}
	
	public static void clickMale(Actions builder, WebDriver driver) {
		WebElement sexMale = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/label[1]/input"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(sexMale)
				.click()
				.build();
		
		series.perform();
	}
	
	public static void clickFemale(Actions builder, WebDriver driver) {
		WebElement sexFemale = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/label[2]/input"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(sexFemale)
				.click()
				.build();
		
		series.perform();
	}

}
