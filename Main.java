package reduxForm;
import java.util.ArrayList;

import javax.swing.Action;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;


public class Main {
	
	static boolean employed = false;
	static boolean areNotes = false;
	static boolean allPassed = true;
	
	public static void main(String[] args) {
		
		// Assumes chromedriver.exe is in the immediate C:\ drive
		System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		
		// Get the URL of the website to test
		String baseUrl = "https://redux-form.com/6.6.1/examples/wizard/";
		driver.get(baseUrl);
		
		// To be used to complete all Selenium actions
		Actions builder = new Actions(driver);
		
		/*
		 *  Testing that we are on the correct page by checking the title
		*/
		
		String headTitle = driver.findElement(By.id("wizard-form")).getText();
		
		if (headTitle.contentEquals("Wizard Form"))
			System.out.println("Test Passed. On the correct page, based on the header.");
		else {
			System.out.println("Test Failed. Header does not match.");
			allPassed = false;
		}
		
		
		/* 
		 * Testing the First Name and Last Name boxes
		*/
		
		WebElement required;
		
		// Test when first name is blank
		typeFirstLastName("", "Smith", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		
		if (!required.getText().contentEquals(""))
			System.out.println("Test Passed. 'Required' is displayed when first name is not provided.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		
		
		// Test when last name is blank
		typeFirstLastName("John", "", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/span"));
		if (!required.getText().contentEquals(""))
			System.out.println("Test Passed. 'Required' is displayed when last name is not provided.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		
		
		// Test when first and last name are provided
		typeFirstLastName("John", "Smith", builder, driver);
		
		clickNext(builder, driver);
		
		
		/*
		 *  Quick check that the previous button works
		 */
		
		clickPrevious(builder, driver);
		clickNext(builder, driver);
		
		
		/*
		 *  Testing the email box
		 */
		
		// Test when email is blank
		typeEmail("", builder, driver);
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!required.getText().contentEquals(""))
			System.out.println("Test Passed. 'Required' is displayed when email is not provided.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		
		
		// Test when email provided is invalid (missing "@" and all after)
		WebElement invalid;
		typeEmail("invalid", builder, driver);
		invalid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!invalid.getText().contentEquals(""))
			System.out.println("Test Passed. 'Invalid email address' is displayed when email is not valid format.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
			
		// Test when email provided is invalid (missing ".com")
		typeEmail("invalid@fakemail", builder, driver);
		invalid = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!invalid.getText().contentEquals(""))
			System.out.println("Test Passed. 'Invalid email address' is displayed when email is not valid format.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		
		// Test when email provided is valid
		typeEmail("valid@fakemail.com", builder, driver);
		clickNext(builder, driver);
		
		
		/*
		 *  Test the sex radio buttons
		 */
		
		// Test to make sure Required appears when a sex is not selected
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/span"));
		if (!required.getText().contentEquals(""))
			System.out.println("Test Passed. 'Required' is displayed when sex is not selected.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		// Test when "Male" is selected
		clickMale(builder, driver);
		
		// Test when "Female" is selected
		clickFemale(builder, driver);
		
		clickNext(builder, driver);
		
		
		/*
		 *  Test the favorite color drop-down
		 */
		
		// Test when no selection is made
		clickSubmit(builder, driver);
		
		required = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[1]/div/span"));
		if (!required.getText().contentEquals(""))
			System.out.println("Test Passed. 'Required' is displayed when a color is not selected.");
		else {
			System.out.println("Test Failed. Message did not appear.");
			allPassed = false;
		}
		
		// Test selecting each other color option
		Select colorDrop = new Select(driver.findElement(By.name("favoriteColor")));
		
		ArrayList<String> colors = new ArrayList<String>();
		colors.add("Red");
		colors.add("Orange");
		colors.add("Yellow");
		colors.add("Green");
		colors.add("Blue");
		colors.add("Indigo");
		colors.add("Violet");
		
		for (int i = 0; i < colors.size(); i++) {
			colorDrop.selectByValue(colors.get(i));
		}
		
		
		/*
		 *  Test the Employed check box
		 */
		
		// Test when box is not checked
		try {
			WebElement employedValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[24]"));
		} catch (Exception e) {
			System.out.println("Tesy Passed. 'Employed' does not appear in Values if box is not checked.");
		}
		
		// Test when box is selected, deselected, selected
		clickEmployed(builder, driver);
		clickEmployed(builder, driver);
		clickEmployed(builder, driver);
		
		
		/*
		 *  Test the Notes box
		 */
		
		// Test when no notes
		writeNotes("", builder, driver);
		try {
			WebElement notesValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[28]"));
		} catch (Exception e) {
			System.out.println("Test Passed. 'Notes' does not appear in Values if nothing is entered.");
		}
		
		// Test entering notes with one word "notes"
		writeNotes("notes", builder, driver);
		
		
		/*
		 *  Verify information provided
		 */
		
		if (checkValues(builder, driver)) {
			System.out.println("Test Passed. Values match what is expected.");
		}
		else {
			System.out.println("Test Failed. Something went wrong.");
			allPassed = false;
		}
		
		if (allPassed)
			System.out.println("All tests passed! Congratulations!");
		else
			System.out.println("One or more tests failed. Better find out why!");
		
		driver.close();

	}
	
	/*
	 *  typeFirstLastName finds the WebElements for each name field, selects them, clears
	 *  what is already in there (by double-clicking and using backspace), then types
	 *  what is provided by String first and String last.
	 */
	
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
	
	/*
	 *  clickNext navigates to the next button and clicks it.
	 */
	
	public static void clickNext(Actions builder, WebDriver driver) {
		WebElement nextButton = driver.findElement(By.className("next"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(nextButton)
				.click()
				.build();
		
		series.perform();
	}
	
	/*
	 *  clickPrevious navigates to the previous button and clicks it.
	 */
	public static void clickPrevious(Actions builder, WebDriver driver) {
		WebElement previousButton = driver.findElement(By.className("previous"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(previousButton)
				.click()
				.build();
		
		series.perform();
	}
	
	/*
	 *  clickSubmit navigates to the submit button and clicks it.
	 */
	
	public static void clickSubmit(Actions builder, WebDriver driver) {
		WebElement submitButton = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[4]/button[2]"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(submitButton)
				.click()
				.build();
		
		series.perform();
	}
	
	/*
	 *  typeEmail finds the email box WebElements, double-clicks and backspaces
	 *  three times (to ensure that each section of a potential invalid email 
	 *  address up to that point is cleared), then enters in the email provided
	 *  by String email.
	 */
	
	public static void typeEmail(String email, Actions builder, WebDriver driver) {
		WebElement emailElement = driver.findElement(By.name("email"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(emailElement)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.sendKeys(emailElement, email)
				.build();
		
		series.perform();
		clickNext(builder, driver);
	}
	
	/*
	 *  clickMale selects the Male option for sex, then checks to make sure the
	 *  radio button has been selected.
	 */
	
	public static void clickMale(Actions builder, WebDriver driver) {
		WebElement sexMale = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/label[1]/input"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(sexMale)
				.click()
				.build();
		
		series.perform();
		
		if (sexMale.isSelected())
			System.out.println("Test Passed. 'Male' is selected when clicked.");
		else {
			System.out.println("Test Failed. 'Male' is not selected.");
			allPassed = false;
		}
	}
	
	/*
	 *  clickFemale selects the Female option for sex, then checks to make sure the
	 *  radio button has been selected.
	 */
	
	public static void clickFemale(Actions builder, WebDriver driver) {
		WebElement sexFemale = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[2]/form/div[2]/div/label[2]/input"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(sexFemale)
				.click()
				.build();
		
		series.perform();
		
		if (sexFemale.isSelected())
			System.out.println("Test Passed. 'Female' is selected when clicked.");
		else {
			System.out.println("Test Failed. 'Female' is not selected.");
			allPassed = false;
		}
	}

	/*
	 *  clickEmployed clicks on the check-box for Employed, toggles the global
	 *  employed boolean variable, then checks to make sure the box is checked
	 *  if it's supposed to be and isn't if it isn't supposed to be.
	 */
	public static void clickEmployed(Actions builder, WebDriver driver) {
		WebElement employedButton = driver.findElement(By.name("employed"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(employedButton)
				.click()
				.build();
		
		series.perform();
		
		employed = !employed;
		
		if (employed) {
			if (employedButton.isSelected())
				System.out.println("Test Passed. Employed box is selected when clicked.");
			else {
				System.out.println("Test Failed. Employed box is not selected.");
				allPassed = false;
			}
		} else {
			if (!employedButton.isSelected())
				System.out.println("Test Passed. Employed box is deselected when not clicked/clicked twice.");
			else {
				System.out.println("Test Failed. Employed box is not deselected.");
				allPassed = false;
			}
		}
	}
	
	/*
	 *  writeNotes selects the Notes box, clears what is in there, then types
	 *  in the notes provided by String notes. It then sets the global boolean
	 *  variable areNotes, based on if there are notes or if String notes was
	 *  empty.
	 */
	public static void writeNotes(String notes, Actions builder, WebDriver driver) {
		WebElement notesBox = driver.findElement(By.name("notes"));
		org.openqa.selenium.interactions.Action series = builder
				.moveToElement(notesBox)
				.doubleClick()
				.sendKeys(Keys.BACK_SPACE)
				.sendKeys(notesBox, notes)
				.build();
		
		series.perform();
		
		if (!notes.contentEquals(""))
			areNotes = true;
		else
			areNotes = false;
	}
	
	/*
	 *  checkValues pulls the values provided in the Values section of the page,
	 *  if they are available. In the cases of Notes and Employed, we use the
	 *  global boolean variables to make sure they exist, then assign them.
	 *  Once we have all the values, we check if they match what is expected
	 *  (this is manual but could be expanded on for more complicated test
	 *  cases), then return true if things match up.
	 */
	public static boolean checkValues(Actions builder, WebDriver driver) {
		String firstNameValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[4]")).getText();
		String lastNameValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[8]")).getText();
		String emailValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[12]")).getText();
		String colorValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[20]")).getText();
		String employedValue = "";
		String notesValue = "";
		
		
		if (areNotes)
			notesValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[28]")).getText();
		
		if (employed)
			employedValue = driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/div/div[3]/div/pre/code/span[24]")).getText();
		
		if (firstNameValue.contentEquals("\"John\"") && lastNameValue.contentEquals("\"Smith\"")
				&& emailValue.contentEquals("\"valid@fakemail.com\"")
				&& colorValue.contentEquals("\"Violet\"") && employedValue.contentEquals("true")
				&& notesValue.contentEquals("\"notes\"")) {
			return true;
		}
		
		return false;
	}
}
