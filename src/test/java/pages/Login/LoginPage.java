package pages.Login;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import utils.ElementUtil;
import utils.ObjectRepository;
import utils.PropertiesFileManager;

import java.util.List;

public class LoginPage {
    WebDriver driver;
    ElementUtil elementutil;
    Logger logger = LogManager.getLogger(LoginPage.class);
    By loginUsername= ObjectRepository.getLocator("LOGIN_USERNAME");
    By loginPassword= ObjectRepository.getLocator("LOGIN_PASSWORD");
    By loginButton= ObjectRepository.getLocator("LOGIN_BUTTON");
    By logoutButton= ObjectRepository.getLocator("LOGOUT_BUTTON");
    By loginSuccessMessage=ObjectRepository.getLocator("LOGIN_SUCCESS_MESSAGE");
    By loginFailedMessage=ObjectRepository.getLocator("LOGIN_FAILED_MESSAGE");

    public LoginPage(WebDriver driver){
        this.driver=driver;
        elementutil=new ElementUtil(driver);
        PageFactory.initElements(driver, this);
    }

    public void navigateToSite(String url){
        logger.info("Launching browser and navigating to URL= {}", url);
        driver.navigate().to(url);
    }

    public void enterUsernameAndPassword(String userName,String password){
        logger.info("Entering username and password");
        elementutil.doSendKeys(loginUsername,userName);
        elementutil.doSendKeys(loginPassword,password);
        elementutil.doClick(loginButton);
    }
    public void verifyTheMessage(String isLoginSuccess, String expectedMessage){
        String messageDisplayed;
        if(isLoginSuccess.equalsIgnoreCase("Y")){
            elementutil.waitForElementToBePresent(loginSuccessMessage);
            messageDisplayed= elementutil.getTextOfElement(loginSuccessMessage);
        }else{
            messageDisplayed= elementutil.getTextOfElement(loginFailedMessage);
        }
        Assert.assertEquals(messageDisplayed,expectedMessage,"Verification of message failed");
    }
    public void verifyTheMessageFromData(String isLoginSuccess, String expectedMessage){
        String messageDisplayed;
        if(isLoginSuccess.equalsIgnoreCase("Y")){
            elementutil.waitForElementToBePresent(loginSuccessMessage);
            messageDisplayed= elementutil.getTextOfElement(loginSuccessMessage);
        }else{
            messageDisplayed= elementutil.getTextOfElement(loginFailedMessage);
        }
        Assert.assertEquals(messageDisplayed,expectedMessage,"Verification of message failed");
        String url= PropertiesFileManager.getPropertyValue("TestLoginURL");
        driver.navigate().to(url);
    }
    public boolean isLogoutButtonDisplayed(){
        return elementutil.isElementDisplayed(logoutButton);
    }
    public String getLoginSuccessMessage(){
        return elementutil.getTextOfElement(loginSuccessMessage);
    }
    public String getLoginFailedMessage(){
        return elementutil.getTextOfElement(loginFailedMessage);
    }

    public void verifyTheOutput(List<Object[]> testData){
        for (Object[] row : testData) {
            String isLoginSuccessful = (String) row[2]; // Login success flag
            String expectedMessage = (String) row[3]; // Expected message

            boolean expectedStatus = isLoginSuccessful.equals("Y");

            if (expectedStatus) {
                Assert.assertTrue(isLogoutButtonDisplayed(), "Login should be successful");
                String actualSuccessMessage = getLoginSuccessMessage();
                Assert.assertEquals(actualSuccessMessage,expectedMessage,"Success message does not match!");
            } else {
                String actualFailedMessage =getLoginFailedMessage();
                Assert.assertEquals(actualFailedMessage,expectedMessage,"Failed message does not match!");
            }
            String url= PropertiesFileManager.getPropertyValue("TestLoginURL");
            driver.navigate().to(url);
            break;
        }
    }
}
