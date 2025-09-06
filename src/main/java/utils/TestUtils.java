package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

public class TestUtils {
    private static Properties properties;
    private static final String CONFIG_FILE_PATH = "src/test/resources/config.properties";
    
    static {
        loadProperties();
    }
    
    private static void loadProperties() {
        properties = new Properties();
        try {
            FileInputStream fileInputStream = new FileInputStream(CONFIG_FILE_PATH);
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration properties: " + e.getMessage());
        }
    }
    
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public static WebDriver initializeDriver() {
        String browserName = getProperty("browser").toLowerCase();
        WebDriver driver;
        
        switch (browserName) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                
                if (Boolean.parseBoolean(getProperty("headless"))) {
                    chromeOptions.addArguments("--headless");
                }
                
                chromeOptions.addArguments("--disable-extensions");
                chromeOptions.addArguments("--disable-gpu");
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--remote-allow-origins=*");
                
                driver = new ChromeDriver(chromeOptions);
                break;
                
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
                
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
                
            default:
                throw new IllegalArgumentException("Browser not supported: " + browserName);
        }
        
        configureDriver(driver);
        return driver;
    }
    
    private static void configureDriver(WebDriver driver) {
        if (Boolean.parseBoolean(getProperty("window.maximize"))) {
            driver.manage().window().maximize();
        }
        
        driver.manage().timeouts().implicitlyWait(
            Duration.ofSeconds(Integer.parseInt(getProperty("implicit.wait")))
        );
        
        driver.manage().timeouts().pageLoadTimeout(
            Duration.ofSeconds(Integer.parseInt(getProperty("page.load.timeout")))
        );
        
        driver.manage().timeouts().scriptTimeout(
            Duration.ofSeconds(Integer.parseInt(getProperty("script.timeout")))
        );
    }
    
    public static String captureScreenshot(WebDriver driver, String testName) {
        if (!Boolean.parseBoolean(getProperty("screenshot.on.failure"))) {
            return null;
        }
        
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = testName + "_" + timestamp + ".png";
            
            String screenshotPath = getProperty("screenshot.path");
            File screenshotDir = new File(screenshotPath);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
            }
            
            File destFile = new File(screenshotPath, fileName);
            FileUtils.copyFile(sourceFile, destFile);
            
            return destFile.getAbsolutePath();
        } catch (IOException e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }
    
    public static void assertUrlContains(WebDriver driver, String expectedUrlPart, String message) {
        String actualUrl = driver.getCurrentUrl();
        Assert.assertTrue(actualUrl.contains(expectedUrlPart), 
            message + " - Expected URL to contain: " + expectedUrlPart + ", but was: " + actualUrl);
    }
    
    public static void assertTitleContains(WebDriver driver, String expectedTitlePart, String message) {
        String actualTitle = driver.getTitle();
        Assert.assertTrue(actualTitle.contains(expectedTitlePart), 
            message + " - Expected title to contain: " + expectedTitlePart + ", but was: " + actualTitle);
    }
    
    public static void assertElementDisplayed(boolean isDisplayed, String elementDescription) {
        Assert.assertTrue(isDisplayed, elementDescription + " should be displayed");
    }
    
    public static void assertElementNotDisplayed(boolean isDisplayed, String elementDescription) {
        Assert.assertFalse(isDisplayed, elementDescription + " should not be displayed");
    }
    
    public static void assertEquals(String actual, String expected, String message) {
        Assert.assertEquals(actual, expected, message);
    }
    
    public static void assertTrue(boolean condition, String message) {
        Assert.assertTrue(condition, message);
    }
    
    public static void assertFalse(boolean condition, String message) {
        Assert.assertFalse(condition, message);
    }
    
    public static void waitForPageLoad(WebDriver driver) {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    public static String getBaseUrl() {
        return getProperty("base.url");
    }
    
    public static String getCareersUrl() {
        return getProperty("careers.url");
    }
    
    public static String getQACareersUrl() {
        return getProperty("qa.careers.url");
    }
}