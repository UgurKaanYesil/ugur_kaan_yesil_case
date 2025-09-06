package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestUtils;

public class HomePage extends BasePage {
    
    // Optimized CSS and XPath selectors for homepage elements
    private final By insiderLogo = By.cssSelector("a[href='/'] img, .navbar-brand img, [alt*='Insider'], [alt*='insider']");
    private final By navigationMenu = By.cssSelector(".navbar-nav, .main-menu, nav ul, nav");
    private final By companyMenuItem = By.cssSelector("a[href*='about'], a[contains(text(),'Company')], a[contains(text(),'About')]");
    private final By careersMenuItem = By.cssSelector("a[href*='careers'], a[contains(text(),'Careers')], a[contains(text(),'Jobs')]");
    private final By acceptCookiesButton = By.cssSelector("button:contains('Accept All'), .accept-all, [data-accept='all']");
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
            // Try multiple cookie accept selectors
            By[] cookieSelectors = {
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
                        Thread.sleep(1000); // Brief wait after accepting cookies
                        System.out.println("Cookie consent accepted successfully");
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
            if (isElementClickable(careersMenuItem)) {
                clickElement(careersMenuItem);
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
    
    public void scrollToTop() {
        scrollToTop();
    }
    
    public void scrollToBottom() {
        scrollToBottom();
    }
}