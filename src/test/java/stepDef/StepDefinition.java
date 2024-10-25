package stepDef;

import cucumber.TestContext;
import driverFactory.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.Login.LoginPage;
import utils.PropertiesFileManager;

public class StepDefinition {
    TestContext testContext;
    LoginPage loginPage;
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

    @When("User enters {string} and {string} and click on login")
    public void user_enters_and_and_click_on_login(String username, String password) {
        loginPage.enterUsernameAndPassword(username,password);
    }

    @Then("Verify {string} and verify the {string}")
    public void verify_and_verify_the_message(String isLoginSuccess, String message) {
        loginPage.verifyTheMessage(isLoginSuccess,message);
    }

    @Then("User close the browser")
    public void user_close_the_browser() {
        testContext.closeDriver();
    }




}
