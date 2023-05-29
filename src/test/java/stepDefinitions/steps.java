package stepDefinitions;

import java.util.ResourceBundle;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.*;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class steps {
	WebDriver driver;
	HomePage hp;
	LoginPage lp;
	MyAccountPage macc;

	List<HashMap<String, String>> datamap; // data driven

	Logger logger; // for logging
	ResourceBundle rb; // for reading properties file
	String br; // to store browser name

	@Before
	public void setup() // Junit hook - executes once before starting
	{
		// for logging
		logger = LogManager.getLogger(this.getClass());
		// Reading config.properties (for browser)
		rb = ResourceBundle.getBundle("config");
		br = rb.getString("browser");

	}

	@After
	public void tearDown(Scenario scenario) {
		System.out.println("Scenario status ---->" + scenario.getStatus());
		if (scenario.isFailed()) {
			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}
		driver.quit();
	}

	@Given("User Launch browser")
	public void user_launch_browser() {
		if (br.equals("chrome")) {
			driver = new ChromeDriver();
		} else if (br.equals("firefox")) {
			driver = new FirefoxDriver();
		} else if (br.equals("edge")) {
			driver = new EdgeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	}

	@Given("opens URL {string}")
	public void opens_url(String url) {
		driver.get(url);
		driver.manage().window().maximize();
	}

	@When("User navigate to MyAccount menu")
	public void user_navigate_to_my_account_menu() {
		hp = new HomePage(driver);
		hp.clickMyAccount();
		logger.info("Clicked on My Account");
	}

	@When("click on Login")
	public void click_on_login() {
		hp.clickLogin();
		logger.info("Clicked On Login");
	}

	@When("User enters Email as {string} and Password as {string}")
	public void user_enters_email_as_and_password_as(String email, String pwd) {

		lp = new LoginPage(driver);

		lp.setEmail(email);
		logger.info("Provided Email ");
		lp.setPassword(pwd);
		logger.info("Provided Password ");
	}

	@When("Click on Login button")
	public void click_on_login_button() {
		lp.clickLogin();
		logger.info("Clicked on Login button");
	}

	@Then("User navigates to MyAccount Page")
	public void user_navigates_to_my_account_page() {
		macc = new MyAccountPage(driver);
		boolean targetpage = macc.isMyAccountPageExists();

		if (targetpage) {
			logger.info("Login Success ");
			Assert.assertTrue(true);
		} else {
			logger.error("Login Failed ");
			Assert.assertTrue(false);
		}
	}

}
