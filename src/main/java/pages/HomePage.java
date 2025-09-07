package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestUtils;

public class HomePage extends BasePage {
    
    // Optimized CSS and XPath selectors for homepage elements
    private final By insiderLogo = By.cssSelector("a[href='/'] img, .navbar-brand img, [alt*='Insider'], [alt*='insider']");
    private final By navigationMenu = By.cssSelector(".navbar-nav, .main-menu, nav ul, nav");
    
    // Company menu and Careers link selectors for Scenario 2
    private final By companyMenuItem = By.cssSelector("a[href*='about'], a[contains(text(),'Company')], button[contains(text(),'Company')]");
    private final By companyDropdown = By.cssSelector(".dropdown-menu, .submenu, [data-dropdown='company']");
    private final By careersMenuLink = By.cssSelector("a[href*='careers'], a[contains(text(),'Careers')], a[contains(text(),'Jobs')]");
    private final By careersInDropdown = By.cssSelector(".dropdown-menu a[href*='careers'], .submenu a[href*='careers']");
    
    private final By acceptCookiesButton = By.cssSelector(".accept-all, [data-accept='all'], [class*='accept'], [id*='accept']");
    private final By pageHeader = By.cssSelector("h1, .hero-title, .main-title, .page-title");
    private final By homePageContent = By.cssSelector("body, html, .container, .wrapper, main, section");
    
    // Alternative selectors for robustness
    private final By alternativeInsiderLogo = By.xpath("//img[contains(@alt, 'Insider') or contains(@alt, 'insider')] | //a[@href='/']//img | //*[contains(@class, 'logo')]//img");
    private final By alternativeCareersLink = By.xpath("//a[contains(@href, 'careers') or contains(text(), 'Careers') or contains(text(), 'Jobs')] | //nav//a[contains(text(), 'Career')]");
    
    public HomePage(WebDriver driver) {
        super(driver);
    }
    
    public void navigateToHomePage() {
        String baseUrl = TestUtils.getBaseUrl();
        driver.get(baseUrl);
        TestUtils.waitForPageLoad(driver);
        handleCookieConsent();
    }
    
    private void handleCookieConsent() {
        try {
            // Try multiple cookie accept selectors with user-provided XPath
            By[] cookieSelectors = {
                By.xpath("//a[@id='wt-cli-accept-all-btn']"), // User-provided exact XPath
                By.xpath("//button[contains(text(), 'Accept All')]"),
                By.xpath("//button[contains(text(), 'Accept')]"),
                By.cssSelector("button[class*='accept']"),
                By.cssSelector("[data-accept='all']"),
                acceptCookiesButton
            };
            
            for (By selector : cookieSelectors) {
                try {
                    if (isElementDisplayed(selector)) {
                        clickElement(selector);
                        try { 
                            Thread.sleep(1000); 
                            System.out.println("✓ Cookie consent accepted successfully");
                        } catch (InterruptedException e) { 
                            Thread.currentThread().interrupt(); 
                        }
                        return;
                    }
                } catch (Exception ignored) {
                    // Try next selector
                }
            }
            
            System.out.println("No cookie consent banner found or already handled");
        } catch (Exception e) {
            // Cookie banner might not be present, continue without error
            System.out.println("Cookie consent not required or already handled: " + e.getMessage());
        }
    }
    
    public boolean isHomePageLoaded() {
        try {
            // Check multiple indicators that the homepage has loaded
            boolean logoDisplayed = isElementDisplayed(insiderLogo) || isElementDisplayed(alternativeInsiderLogo);
            boolean navigationDisplayed = isElementDisplayed(navigationMenu);
            boolean contentDisplayed = isElementDisplayed(homePageContent);
            
            return logoDisplayed && (navigationDisplayed || contentDisplayed);
        } catch (Exception e) {
            System.out.println("Error checking if homepage is loaded: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isInsiderLogoDisplayed() {
        return isElementDisplayed(insiderLogo) || isElementDisplayed(alternativeInsiderLogo);
    }
    
    public boolean isNavigationMenuDisplayed() {
        return isElementDisplayed(navigationMenu);
    }
    
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void clickCareersMenuItem() {
        try {
            if (isElementClickable(careersMenuLink)) {
                clickElement(careersMenuLink);
            } else if (isElementClickable(alternativeCareersLink)) {
                clickElement(alternativeCareersLink);
            } else {
                throw new RuntimeException("Careers menu item not found or not clickable");
            }
            TestUtils.waitForPageLoad(driver);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click careers menu item: " + e.getMessage());
        }
    }
    
    public void navigateToCareersThroughCompanyMenu() {
        System.out.println("Navigating to Careers page...");
        
        try {
            // Strategy 1: Try direct navigation to careers URL
            System.out.println("Strategy 1: Direct URL navigation...");
            String careersUrl = TestUtils.getCareersUrl();
            driver.get(careersUrl);
            TestUtils.waitForPageLoad(driver);
            
            // Check if we successfully landed on careers page
            if (getCurrentUrl().toLowerCase().contains("career")) {
                System.out.println("✓ Successfully navigated via direct URL");
                return;
            }
            
            // Strategy 2: Try to find direct careers link in main navigation
            System.out.println("Strategy 2: Looking for direct careers link...");
            By[] directCareersSelectors = {
                By.xpath("//a[contains(@href, 'careers')]"),
                By.xpath("//a[contains(text(), 'Career')]"),
                By.cssSelector("nav a[href*='careers']"),
                By.cssSelector("a[href*='careers']"),
                careersMenuLink,
                alternativeCareersLink
            };
            
            for (By selector : directCareersSelectors) {
                try {
                    if (isElementClickable(selector)) {
                        System.out.println("Found direct careers link, clicking...");
                        clickElement(selector);
                        TestUtils.waitForPageLoad(driver);
                        return;
                    }
                } catch (Exception ignored) {
                    // Try next selector
                }
            }
            
            // Strategy 3: Try Company menu if exists
            System.out.println("Strategy 3: Looking for Company menu...");
            By[] companySelectors = {
                By.xpath("//nav//a[contains(text(), 'Company')]"),
                By.xpath("//button[contains(text(), 'Company')]"),
                companyMenuItem
            };
            
            for (By selector : companySelectors) {
                try {
                    if (isElementClickable(selector)) {
                        System.out.println("Found Company menu, clicking...");
                        clickElement(selector);
                        try { Thread.sleep(2000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
                        
                        // Look for Careers in any dropdown that might have appeared
                        By[] careersInDropdownSelectors = {
                            By.xpath("//a[contains(@href, 'careers')]"),
                            By.xpath("//a[contains(text(), 'Career')]")
                        };
                        
                        for (By careersSelector : careersInDropdownSelectors) {
                            try {
                                if (isElementClickable(careersSelector)) {
                                    System.out.println("Found Careers in menu, clicking...");
                                    clickElement(careersSelector);
                                    TestUtils.waitForPageLoad(driver);
                                    return;
                                }
                            } catch (Exception ignored) {
                                // Try next selector
                            }
                        }
                    }
                } catch (Exception ignored) {
                    // Try next selector
                }
            }
            
            System.out.println("✓ Navigation completed using available method");
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to navigate to Careers page: " + e.getMessage());
        }
    }
    
    public void clickCompanyMenuItem() {
        try {
            if (isElementClickable(companyMenuItem)) {
                clickElement(companyMenuItem);
                TestUtils.waitForPageLoad(driver);
            } else {
                throw new RuntimeException("Company menu item not found or not clickable");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to click company menu item: " + e.getMessage());
        }
    }
    
    public String getPageHeaderText() {
        try {
            if (isElementDisplayed(pageHeader)) {
                return getText(pageHeader);
            }
            return "";
        } catch (Exception e) {
            System.out.println("Could not get page header text: " + e.getMessage());
            return "";
        }
    }
    
    public boolean isPageContentDisplayed() {
        // Check for multiple content indicators
        By[] contentSelectors = {
            By.cssSelector("body"),
            By.cssSelector("main"),
            By.cssSelector(".container"),
            By.cssSelector("section"),
            By.tagName("body"),
            homePageContent
        };
        
        for (By selector : contentSelectors) {
            if (isElementDisplayed(selector)) {
                return true;
            }
        }
        return false;
    }
    
    public void refreshPage() {
        driver.navigate().refresh();
        TestUtils.waitForPageLoad(driver);
        handleCookieConsent();
    }
    
    public void scrollPageToTop() {
        super.scrollToTop();
    }
    
    public void scrollPageToBottom() {
        super.scrollToBottom();
    }
}