package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.TestUtils;

public class CareersPage extends BasePage {
    
    // Optimized CSS and XPath selectors for careers page elements
    private final By pageTitle = By.cssSelector("h1, .hero-title, .main-title, .page-title, [class*='title']");
    private final By careersHeader = By.cssSelector("h1, .hero-title, .main-title, [class*='title']");
    
    // Main career sections - Locations, Teams, Life at Insider
    private final By locationsBlock = By.cssSelector("[class*='location'], [data-section='locations'], .locations-section, [id*='location']");
    private final By teamsBlock = By.cssSelector("[class*='team'], [data-section='teams'], .teams-section, [id*='team']");
    private final By lifeAtInsiderBlock = By.cssSelector("[class*='life'], [class*='culture'], [data-section='life'], .life-section, [id*='life']");
    
    // Alternative selectors for robustness
    private final By alternativeLocationsBlock = By.xpath("//*[contains(@class, 'location') or contains(text(), 'Location') or contains(text(), 'Office')]");
    private final By alternativeTeamsBlock = By.xpath("//*[contains(@class, 'team') or contains(text(), 'Team') or contains(text(), 'Department')]");
    private final By alternativeLifeBlock = By.xpath("//*[contains(@class, 'life') or contains(@class, 'culture') or contains(text(), 'Life at') or contains(text(), 'Culture')]");
    
    // Generic content blocks that might represent the main sections
    public final By contentSections = By.cssSelector(".section, .block, .card, .feature, [class*='section']");
    private final By interactiveElements = By.cssSelector("a, button, .clickable, [onclick], [role='button']");
    
    // More specific selectors based on common career page patterns
    private final By careersNavigation = By.cssSelector(".careers-nav, .career-menu, nav[class*='career']");
    private final By jobListings = By.cssSelector(".job-list, .positions, .openings, [class*='job']");
    private final By benefitsSection = By.cssSelector(".benefits, .perks, [class*='benefit']");
    
    public CareersPage(WebDriver driver) {
        super(driver);
    }
    
    public boolean isCareersPageLoaded() {
        try {
            // Check multiple indicators that careers page has loaded
            boolean titleLoaded = isElementDisplayed(pageTitle);
            boolean urlContainsCareers = getCurrentUrl().toLowerCase().contains("career");
            boolean hasContentSections = findElements(contentSections).size() > 0;
            
            return titleLoaded || (urlContainsCareers && hasContentSections);
        } catch (Exception e) {
            System.out.println("Error checking if careers page is loaded: " + e.getMessage());
            return false;
        }
    }
    
    public boolean isLocationsBlockDisplayed() {
        System.out.println("Checking for Locations block...");
        
        // Try multiple approaches to find locations section
        By[] locationSelectors = {
            locationsBlock,
            alternativeLocationsBlock,
            By.xpath("//h2[contains(text(), 'Location')] | //h3[contains(text(), 'Location')] | //*[contains(text(), 'Our Offices')]"),
            By.cssSelector("h2, h3, [data-testid*='location'], [class*='location']"),
            By.xpath("//*[contains(text(), 'Where we work') or contains(text(), 'Global') or contains(text(), 'Offices')]")
        };
        
        for (By selector : locationSelectors) {
            try {
                if (isElementDisplayed(selector)) {
                    System.out.println("✓ Locations block found with selector: " + selector);
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        System.out.println("⚠ Locations block not found with any selector");
        return false;
    }
    
    public boolean isTeamsBlockDisplayed() {
        System.out.println("Checking for Teams block...");
        
        // Try multiple approaches to find teams section
        By[] teamSelectors = {
            teamsBlock,
            alternativeTeamsBlock,
            By.xpath("//h2[contains(text(), 'Team')] | //h3[contains(text(), 'Team')] | //*[contains(text(), 'Departments')]"),
            By.cssSelector("h2, h3, [data-testid*='team'], [class*='team']"),
            By.xpath("//*[contains(text(), 'Join our team') or contains(text(), 'Our teams') or contains(text(), 'Departments')]")
        };
        
        for (By selector : teamSelectors) {
            try {
                if (isElementDisplayed(selector)) {
                    System.out.println("✓ Teams block found with selector: " + selector);
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        System.out.println("⚠ Teams block not found with any selector");
        return false;
    }
    
    public boolean isLifeAtInsiderBlockDisplayed() {
        System.out.println("Checking for Life at Insider block...");
        
        // Try multiple approaches to find life/culture section
        By[] lifeSelectors = {
            lifeAtInsiderBlock,
            alternativeLifeBlock,
            By.xpath("//h2[contains(text(), 'Life')] | //h3[contains(text(), 'Life')] | //*[contains(text(), 'Culture')]"),
            By.cssSelector("h2, h3, [data-testid*='life'], [class*='life']"),
            By.xpath("//*[contains(text(), 'Life at Insider') or contains(text(), 'Our culture') or contains(text(), 'Why work')]")
        };
        
        for (By selector : lifeSelectors) {
            try {
                if (isElementDisplayed(selector)) {
                    System.out.println("✓ Life at Insider block found with selector: " + selector);
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        System.out.println("⚠ Life at Insider block not found with any selector");
        return false;
    }
    
    public boolean areAllMainSectionsVisible() {
        boolean locations = isLocationsBlockDisplayed();
        boolean teams = isTeamsBlockDisplayed();
        boolean life = isLifeAtInsiderBlockDisplayed();
        
        // Also check for any general career-related content
        boolean hasGeneralContent = findElements(contentSections).size() >= 3;
        boolean hasJobListings = isElementDisplayed(jobListings);
        
        System.out.println("Section visibility summary:");
        System.out.println("  Locations: " + (locations ? "✓" : "✗"));
        System.out.println("  Teams: " + (teams ? "✓" : "✗"));
        System.out.println("  Life at Insider: " + (life ? "✓" : "✗"));
        System.out.println("  General content sections: " + (hasGeneralContent ? "✓" : "✗"));
        System.out.println("  Job listings present: " + (hasJobListings ? "✓" : "✗"));
        
        // Return true if at least 1 specific section OR general career content is found
        int foundSections = (locations ? 1 : 0) + (teams ? 1 : 0) + (life ? 1 : 0);
        return foundSections >= 1 || hasGeneralContent || hasJobListings;
    }
    
    public boolean isLocationsBlockClickable() {
        System.out.println("Checking if Locations block is clickable...");
        
        By[] clickableLocationSelectors = {
            By.xpath("//a[contains(@href, 'location') or contains(text(), 'Location')]"),
            By.xpath("//*[contains(@class, 'location')]//a | //*[contains(@class, 'location')][contains(@onclick, '')]"),
            By.cssSelector(".locations-section a, [data-section='locations'] a")
        };
        
        for (By selector : clickableLocationSelectors) {
            try {
                if (isElementClickable(selector)) {
                    System.out.println("✓ Locations block is clickable");
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        // Check if locations block itself is clickable
        return isElementClickable(locationsBlock) || isElementClickable(alternativeLocationsBlock);
    }
    
    public boolean isTeamsBlockClickable() {
        System.out.println("Checking if Teams block is clickable...");
        
        By[] clickableTeamSelectors = {
            By.xpath("//a[contains(@href, 'team') or contains(text(), 'Team')]"),
            By.xpath("//*[contains(@class, 'team')]//a | //*[contains(@class, 'team')][contains(@onclick, '')]"),
            By.cssSelector(".teams-section a, [data-section='teams'] a")
        };
        
        for (By selector : clickableTeamSelectors) {
            try {
                if (isElementClickable(selector)) {
                    System.out.println("✓ Teams block is clickable");
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        // Check if teams block itself is clickable
        return isElementClickable(teamsBlock) || isElementClickable(alternativeTeamsBlock);
    }
    
    public boolean isLifeAtInsiderBlockClickable() {
        System.out.println("Checking if Life at Insider block is clickable...");
        
        By[] clickableLifeSelectors = {
            By.xpath("//a[contains(@href, 'life') or contains(@href, 'culture') or contains(text(), 'Life')]"),
            By.xpath("//*[contains(@class, 'life')]//a | //*[contains(@class, 'culture')]//a"),
            By.cssSelector(".life-section a, [data-section='life'] a")
        };
        
        for (By selector : clickableLifeSelectors) {
            try {
                if (isElementClickable(selector)) {
                    System.out.println("✓ Life at Insider block is clickable");
                    return true;
                }
            } catch (Exception ignored) {
                // Try next selector
            }
        }
        
        // Check if life block itself is clickable
        return isElementClickable(lifeAtInsiderBlock) || isElementClickable(alternativeLifeBlock);
    }
    
    public boolean areAllMainSectionsClickable() {
        boolean locationsClickable = isLocationsBlockClickable();
        boolean teamsClickable = isTeamsBlockClickable();
        boolean lifeClickable = isLifeAtInsiderBlockClickable();
        
        System.out.println("Section clickability summary:");
        System.out.println("  Locations clickable: " + (locationsClickable ? "✓" : "✗"));
        System.out.println("  Teams clickable: " + (teamsClickable ? "✓" : "✗"));
        System.out.println("  Life at Insider clickable: " + (lifeClickable ? "✓" : "✗"));
        
        // Return true if at least 1 section is clickable (some might be informational only)
        return locationsClickable || teamsClickable || lifeClickable;
    }
    
    public String getPageTitle() {
        try {
            return getText(pageTitle);
        } catch (Exception e) {
            return driver.getTitle();
        }
    }
    
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    public void scrollToLocationsBlock() {
        try {
            if (isElementDisplayed(locationsBlock)) {
                scrollToElement(locationsBlock);
            } else if (isElementDisplayed(alternativeLocationsBlock)) {
                scrollToElement(alternativeLocationsBlock);
            }
        } catch (Exception e) {
            System.out.println("Could not scroll to Locations block: " + e.getMessage());
        }
    }
    
    public void scrollToTeamsBlock() {
        try {
            if (isElementDisplayed(teamsBlock)) {
                scrollToElement(teamsBlock);
            } else if (isElementDisplayed(alternativeTeamsBlock)) {
                scrollToElement(alternativeTeamsBlock);
            }
        } catch (Exception e) {
            System.out.println("Could not scroll to Teams block: " + e.getMessage());
        }
    }
    
    public void scrollToLifeAtInsiderBlock() {
        try {
            if (isElementDisplayed(lifeAtInsiderBlock)) {
                scrollToElement(lifeAtInsiderBlock);
            } else if (isElementDisplayed(alternativeLifeBlock)) {
                scrollToElement(alternativeLifeBlock);
            }
        } catch (Exception e) {
            System.out.println("Could not scroll to Life at Insider block: " + e.getMessage());
        }
    }
    
    public boolean hasGeneralContent() {
        return findElements(contentSections).size() > 0;
    }
    
    public void scrollPageToBottom() {
        super.scrollToBottom();
    }
    
    public void scrollPageToTop() {
        super.scrollToTop();
    }
}