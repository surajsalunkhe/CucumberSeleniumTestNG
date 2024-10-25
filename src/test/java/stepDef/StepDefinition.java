package stepDef;

import cucumber.TestContext;
import driverFactory.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.Login.LoginPage;
import utils.ExcelReader;
import utils.PropertiesFileManager;

import java.io.IOException;
import java.util.List;

import static utils.Constants.TEST_DATA_FILE_PATH;

public class StepDefinition {
    TestContext testContext;
    LoginPage loginPage;
    List<Object[]> testData;
    public static String environmentName=null;
    public StepDefinition(TestContext context){
        this.testContext = context;
    }
    @Given("User navigates to URL {string}")
    public void user_navigate_to_url(String url) {
        String testUrl= PropertiesFileManager.getPropertyValue(url);
        loginPage.navigateToSite(testUrl);
    }
    @Given("User launch the browser")
    public void user_launch_the_browser() {
        environmentName= DriverManager.returnEnvironment();
        String browserName=DriverManager.returnBrowserName();
        testContext.initializeDriver(browserName);
        loginPage =testContext.getPageObjectManager().getLoginPage();
    }
    @Given("User navigates to the site {string}")
    public void user_navigate_to_the_site(String url) {
        environmentName= DriverManager.returnEnvironment();
        String browserName=DriverManager.returnBrowserName();
        testContext.initializeDriver(browserName);
        loginPage =testContext.getPageObjectManager().getLoginPage();
        String testUrl= PropertiesFileManager.getPropertyValue(url);
        loginPage.navigateToSite(testUrl);
    }

    @When("User enters {string} and {string} and click on submit button")
    public void user_enters_and_and_click_on_login(String username, String password) {
        loginPage.enterUsernameAndPassword(username,password);
    }

    @Then("Verify {string} and verify the {string}")
    public void verify_and_verify_the_message(String isLoginSuccess, String message) {
        loginPage.verifyTheMessage(isLoginSuccess,message);
    }

    @Then("User closes the browser")
    public void user_closes_the_browser() {
        testContext.closeDriver();
    }
    @Given("User has test data from Excel")
    public void user_has_test_data_from_excel() throws IOException {
        // Specify the path to your Excel file here
        String excelPath = TEST_DATA_FILE_PATH+DriverManager.returnEnvironment()+"\\TestData\\TestData.xlsx";
        testData = ExcelReader.readExcelDataForLogin(excelPath);
    }
    @When("User enters username and password, and clicks on submit button and verify output")
    public void user_enters_username_and_password_and_clicks_on_submit_button() {
        for (Object[] row : testData) {
            String username = (String) row[0]; // Username
            String password = (String) row[1];// Password
            String isLoginSuccessful=(String) row[2];// Login Success Status
            String message=(String)row[3];//message to verify
            loginPage.enterUsernameAndPassword(username,password);
            loginPage.verifyTheMessageFromData(isLoginSuccessful,message);
        }
    }
    @Then("Verify login status and message")
    public void verify_login_status_and_message() {

    }
}
