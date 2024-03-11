package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;
import java.util.Random;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private static String username= null;
	private static String fname= null;
	private static String lname= null;
	private static String password = null;

	private static String title = null;
	private static String description = null;
	private WebDriver driver;


	static{
		 username = generateRandomUsername();
		 fname ="nguyen";
		 lname="van duc";
		 password = "123";
		 title ="man united loser";
		 description ="man united loser";

	}

	public static String generateRandomUsername() {
		Random random = new Random();
		StringBuilder username = new StringBuilder();

		for (int i = 0; i < 8; i++) {
			char randomChar = (char) ('a' + random.nextInt(26));
			username.append(randomChar);
		}

		return username.toString();
	}
	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	//get page unauthorize
	@Test
	public void testUnauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/homepage");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/success");
		Assertions.assertEquals("Login", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/result");
		Assertions.assertEquals("Login", driver.getTitle());
	}


	// test with account not existed--->redirect login
	@Test
	public void testLoginAccountNotExisted(){
		doLogIn("1222222",password);
		Assertions.assertEquals("Login", driver.getTitle());
	}


	//fail, sign up page
	@Test
	public void testRegisterAccountExisted(){
		username="12333333334";
		password="123";
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,username,password);
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	@Test
	public void testAuthorizeAccess(){
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"nguyenduc",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("nguyenduc",password);

		doLogout();
		Assertions.assertEquals("Login", driver.getTitle());

	}

	@Test
	public void addNote(){
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"nguyenduc1234",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("nguyenduc1234",password);

        NotePage notePage = new NotePage(driver);
	    notePage.addNote(title,description);
		driver.get("http://localhost:" + this.port + "/homepage");
		notePage.clickNoteTab();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-note-title")).getText().contains("man united loser"));
	}

	@Test
	public void updateNote(){
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"b",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("b",password);
		NotePage notePage = new NotePage(driver);
		notePage.addNote(title,description);
		driver.get("http://localhost:" + this.port + "/homepage");
		notePage.clickNoteTab();
		title="ducnv";
		description="123";
		notePage.editNote(title,description);
		driver.get("http://localhost:" + this.port + "/homepage");
		notePage.clickNoteTab();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-note-title")).getText().contains("ducnv"));
	}

	@Test
	public void deleteNote() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"b",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("b",password);

		NotePage notePage = new NotePage(driver);
		notePage.addNote(title,description);
		driver.get("http://localhost:" + this.port + "/homepage");
		notePage.clickNoteTab();

		notePage.deleteNote();

		//driver.get("http://localhost:" + this.port + "/homepage");
		//notePage.clickNoteTab();

		Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='userTable']/tbody")).size());


	}


	private void doLogout(){
		driver.get("http://localhost:" + this.port + "/homepage");
		WebElement logoutButton= driver.findElement(By.id("log-out"));
		logoutButton.click();
	}

	@Test
	public void addCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"nguyenduc456",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("nguyenduc456",password);

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.addCredential("w3school","nguyen duc","123");
		driver.get("http://localhost:" + this.port + "/homepage");
		credentialPage.clickCredentialTab();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertFalse(driver.findElement(By.id("table-cred-url")).getText().contains("w3school"));

	}


	@Test
	public void updateCredential() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"nguyenduc456",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("nguyenduc456",password);

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.addCredential("123","nguyen duc","123");
		driver.get("http://localhost:" + this.port + "/homepage");
		credentialPage.clickCredentialTab();
		credentialPage.editCredential("google.com","nguyen duc","123");
		driver.get("http://localhost:" + this.port + "/homepage");
		credentialPage.clickCredentialTab();

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
		Assertions.assertTrue(driver.findElement(By.id("table-cred-url")).getText().contains("google.com"));
	}

	@Test
	public void deleteCre() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");
		doMockSignUp(fname,lname,"c",password);
		driver.get("http://localhost:" + this.port + "/login");
		doLogIn("c",password);

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.addCredential("google.com","nguyen duc","123");
		driver.get("http://localhost:" + this.port + "/homepage");
		credentialPage.clickCredentialTab();

        credentialPage.deleteCredential();
		//driver.get("http://localhost:" + this.port + "/homepage");
		//notePage.clickNoteTab();

		Assertions.assertEquals(0, driver.findElements(By.xpath("//*[@id='credentialTable']/tbody")).size());


	}
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();



		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		//Assertions.assertTrue(driver.findElement(By.id("message")).getText().contains("You successfully signed up!"));
	}





	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();


	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/homepage");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}



}
