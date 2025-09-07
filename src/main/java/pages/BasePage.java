package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.FluentWait;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected FluentWait<WebDriver> fluentWait;
    
    private static final int DEFAULT_TIMEOUT = 15;
    private static final int POLLING_INTERVAL = 1;
    
    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
        this.fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(POLLING_INTERVAL))
                .ignoring(NoSuchElementException.class, StaleElementReferenceException.class);
    }
    
    protected WebElement findElement(By locator) {
        try {
            return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new RuntimeException("Element not found within timeout: " + locator, e);
        }
    }
    
    protected List<WebElement> findElements(By locator) {
        return driver.findElements(locator);
    }
    
    protected WebElement findClickableElement(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected WebElement findVisibleElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void clickElement(By locator) {
        try {
            WebElement element = findClickableElement(locator);
            element.click();
        } catch (ElementClickInterceptedException e) {
            // Retry with JavaScript click
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (Exception e) {
            throw new RuntimeException("Failed to click element: " + locator, e);
        }
    }
    
    protected void sendKeys(By locator, String text) {
        try {
            WebElement element = findVisibleElement(locator);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send keys to element: " + locator + ", text: " + text, e);
        }
    }
    
    protected String getText(By locator) {
        return findVisibleElement(locator).getText();
    }
    
    protected String getAttribute(By locator, String attributeName) {
        return findElement(locator).getAttribute(attributeName);
    }
    
    protected boolean isElementDisplayed(By locator) {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));
            WebElement element = shortWait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return element.isDisplayed();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
    
    protected boolean isElementClickable(By locator) {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(locator));
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    protected void waitForElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
    
    protected void waitForElementToBeClickable(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    protected void waitForTextToBePresentInElement(By locator, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }
    
    protected void scrollToElement(By locator) {
        try {
            WebElement element = findElement(locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(500); // Brief pause after smooth scroll
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Warning: Could not scroll to element " + locator + ": " + e.getMessage());
        }
    }
    
    protected void scrollToElementByJS(WebElement element) {
        try {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});", element);
            Thread.sleep(500); // Brief pause after smooth scroll
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Warning: Could not scroll to element: " + e.getMessage());
        }
    }
    
    protected void scrollToTop() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0);");
    }
    
    protected void scrollToBottom() {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");
    }
    
    protected void hoverOverElement(WebElement element) {
        try {
            Actions actions = new Actions(driver);
            actions.moveToElement(element).perform();
            Thread.sleep(1000); // Wait for hover effects
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            System.out.println("Warning: Could not hover over element: " + e.getMessage());
        }
    }
    
    protected void refreshPage() {
        try {
            driver.navigate().refresh();
            Thread.sleep(2000); // Wait for page refresh
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    protected String getPageTitle() {
        return driver.getTitle();
    }
    
    protected String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    protected void switchToWindow(String windowHandle) {
        driver.switchTo().window(windowHandle);
    }
    
    protected void acceptAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).accept();
    }
    
    protected void dismissAlert() {
        wait.until(ExpectedConditions.alertIsPresent()).dismiss();
    }
    
    protected String getAlertText() {
        return wait.until(ExpectedConditions.alertIsPresent()).getText();
    }
}