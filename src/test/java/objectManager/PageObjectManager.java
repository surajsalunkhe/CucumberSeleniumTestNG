package objectManager;

import org.openqa.selenium.WebDriver;
import pages.Login.LoginPage;
import pages.Login.RegistrationPage;


public class PageObjectManager {
    private WebDriver driver;
    LoginPage loginPage;
    RegistrationPage registrationPage;
    
    public PageObjectManager(WebDriver driver){
        this.driver=driver;
     }
    
    public LoginPage getLoginPage(){
        return (loginPage ==null)? loginPage =new LoginPage(driver): loginPage;
    }
    
    public RegistrationPage getRegistrationPage(){
        return (registrationPage ==null)? registrationPage =new RegistrationPage(driver): registrationPage;
    }
}
