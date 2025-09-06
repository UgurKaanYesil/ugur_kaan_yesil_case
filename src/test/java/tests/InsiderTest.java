package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import utils.TestUtils;

public class InsiderTest {
    private WebDriver driver;
    private HomePage homePage;
    
    @BeforeMethod
    public void setUp() {
        System.out.println("Setting up test environment...");
        
        // Initialize WebDriver using TestUtils
        driver = TestUtils.initializeDriver();
        System.out.println("WebDriver initialized successfully");
        
        // Initialize page objects
        homePage = new HomePage(driver);
        System.out.println("Page objects initialized successfully");
    }
    
    @Test(description = "Test Scenario 1: Visit https://useinsider.com/ and verify homepage opens")
    public void testHomepageLoads() {
        System.out.println("Starting Test Scenario 1: Homepage verification");
        
        try {
            // Step 1: Navigate to Insider homepage
            System.out.println("Step 1: Navigating to Insider homepage...");
            homePage.navigateToHomePage();
            System.out.println("Successfully navigated to homepage");
            
            // Step 2: Verify the page URL contains the expected domain
            System.out.println("Step 2: Verifying page URL...");
            String currentUrl = homePage.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);
            TestUtils.assertUrlContains(driver, "useinsider.com", "Homepage URL verification");
            System.out.println("✓ URL verification passed");
            
            // Step 3: Verify the page title contains 'Insider'
            System.out.println("Step 3: Verifying page title...");
            String pageTitle = homePage.getPageTitle();
            System.out.println("Page title: " + pageTitle);
            TestUtils.assertTitleContains(driver, "Insider", "Homepage title verification");
            System.out.println("✓ Title verification passed");
            
            // Step 4: Verify homepage elements are displayed
            System.out.println("Step 4: Verifying homepage elements...");
            
            // Check if homepage content is loaded
            TestUtils.assertElementDisplayed(homePage.isHomePageLoaded(), "Homepage content");
            System.out.println("✓ Homepage content loaded successfully");
            
            // Check if Insider logo is displayed
            TestUtils.assertElementDisplayed(homePage.isInsiderLogoDisplayed(), "Insider logo");
            System.out.println("✓ Insider logo is displayed");
            
            // Check if navigation menu is displayed
            TestUtils.assertElementDisplayed(homePage.isNavigationMenuDisplayed(), "Navigation menu");
            System.out.println("✓ Navigation menu is displayed");
            
            // Check if page content is displayed
            TestUtils.assertElementDisplayed(homePage.isPageContentDisplayed(), "Page content");
            System.out.println("✓ Page content is displayed");
            
            System.out.println("🎉 Test Scenario 1 completed successfully!");
            System.out.println("All homepage verification checks passed:");
            System.out.println("  ✓ URL contains 'useinsider.com'");
            System.out.println("  ✓ Page title contains 'Insider'");
            System.out.println("  ✓ Homepage elements are displayed");
            System.out.println("  ✓ Navigation is functional");
            
        } catch (Exception e) {
            System.err.println("❌ Test Scenario 1 failed: " + e.getMessage());
            throw e;
        }
    }
    
    @Test(description = "Additional verification: Check homepage responsiveness", enabled = false)
    public void testHomepageResponsiveness() {
        System.out.println("Starting additional test: Homepage responsiveness");
        
        // Navigate to homepage
        homePage.navigateToHomePage();
        
        // Verify page loads in reasonable time and elements are interactive
        TestUtils.assertElementDisplayed(homePage.isHomePageLoaded(), "Homepage loads within timeout");
        
        // Test scrolling functionality
        homePage.scrollToBottom();
        homePage.scrollToTop();
        
        // Verify page is still functional after scrolling
        TestUtils.assertElementDisplayed(homePage.isInsiderLogoDisplayed(), "Logo still visible after scrolling");
        
        System.out.println("✓ Homepage responsiveness test passed");
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            // Capture screenshot on failure
            if (result.getStatus() == ITestResult.FAILURE) {
                System.out.println("Test failed, capturing screenshot...");
                String screenshotPath = TestUtils.captureScreenshot(driver, result.getMethod().getMethodName());
                if (screenshotPath != null) {
                    System.out.println("Screenshot saved: " + screenshotPath);
                }
            }
            
            System.out.println("Closing browser and cleaning up...");
            driver.quit();
            System.out.println("Test cleanup completed");
        }
    }
    
    @BeforeClass
    public void beforeClass() {
        System.out.println("=".repeat(80));
        System.out.println("INSIDER TEST AUTOMATION - SCENARIO 1");
        System.out.println("Testing homepage functionality at: " + TestUtils.getBaseUrl());
        System.out.println("Browser: " + TestUtils.getProperty("browser"));
        System.out.println("=".repeat(80));
    }
    
    @AfterClass
    public void afterClass() {
        System.out.println("=".repeat(80));
        System.out.println("INSIDER TEST AUTOMATION - COMPLETED");
        System.out.println("=".repeat(80));
    }
}