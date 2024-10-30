package driverFactory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.safari.SafariDriver;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.DRIVER_FILE_PATH;

public class DriverManager {
    private static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();
    private static String  downloadPath = System.getProperty("user.dir")+ "\\DownloadedReports\\";
    String driverPath = System.getProperty("user.dir") + "/src/test/resources/SeleniumDrivers/chromedriver";
    public WebDriver getDriver() {
        return webDriver.get();
    }
    public void setDriver(String browser) {
        WebDriver driver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
                System.out.println("Os version="+System.getProperty("os.name"));
                if(System.getProperty("os.name").contains("linux")){
                    System.out.println("Inside linux driver setup");
                    System.setProperty("webdriver.chrome.driver", driverPath);
                    driver = new ChromeDriver();

                }else{
                    System.out.println("Inside Windows driver setup");
                    WebDriverManager.chromedriver().setup();
                    //WebDriverManager.chromedriver().driverVersion("130.0.6723.58").setup();
                    /*ChromeOptions chromeOptions = new ChromeOptions();
                    Map<String, Object> prefs = new HashMap<String, Object>();
                    prefs.put("download.default_directory", downloadPath);
                    prefs.put("download.prompt_for_download", false);
                    prefs.put("plugins.always_open_pdf_externally", true);
                    chromeOptions.setExperimentalOption("prefs", prefs);*/
                    driver = new ChromeDriver();
                }

                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addPreference("browser.download.folderList", 2);
                firefoxOptions.addPreference("browser.download.dir", downloadPath);
                firefoxOptions.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            // Add support for other browsers if needed
            case "safari":
                WebDriverManager.safaridriver().setup();
                driver=new SafariDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                /*Map<String, Object> edgeprefs = new HashMap<String, Object>();
                edgeprefs.put("download.default_directory",downloadPath);
                edgeprefs.put("download.prompt_for_download", false);
                edgeprefs.put("plugins.always_open_pdf_externally", true);
                EdgeOptions op=new EdgeOptions();
                op.setExperimentalOption("prefs", edgeprefs);*/
                driver=new EdgeDriver();
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        webDriver.set(driver);
    }

    public void closeDriver() {
        WebDriver driver = webDriver.get();
        if (driver != null) {
            driver.quit();
            webDriver.remove();
        }
    }

    public static String returnEnvironment(){
        String environmentName=System.getProperty("env");
        if(environmentName==null ||environmentName.isEmpty()){
            environmentName="QA";
        }
        return environmentName;
    }

    public static String returnBrowserName(){
        String browserName=System.getProperty("browser");
        if(browserName==null || browserName.isEmpty()){
            browserName="edge";
        }
        return browserName;
    }
}
