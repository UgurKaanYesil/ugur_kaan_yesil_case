# Insider Test Automation Framework

A comprehensive Maven-based Java Selenium test automation framework implementing the Page Object Model (POM) pattern for testing Insider careers website functionality.

## 🚀 Project Overview

This project provides a robust, scalable test automation solution for testing the Insider website using modern best practices:

- **Framework**: Java + Selenium WebDriver + TestNG
- **Design Pattern**: Page Object Model (POM)
- **Build Tool**: Maven
- **Driver Management**: WebDriverManager (automatic driver setup)
- **Reporting**: Built-in screenshot capture on failures
- **Configuration**: Property-based configuration management

## 📁 Project Structure

```
insider-selenium-automation/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── pages/           # Page Object classes
│   │       │   ├── BasePage.java
│   │       │   └── HomePage.java
│   │       └── utils/           # Utility classes
│   │           └── TestUtils.java
│   └── test/
│       ├── java/
│       │   └── tests/           # Test classes
│       │       └── InsiderTest.java
│       └── resources/           # Configuration files
│           ├── config.properties
│           └── testng.xml
├── target/                      # Generated artifacts
│   ├── screenshots/            # Test failure screenshots
│   └── extent-reports/         # Test reports
├── pom.xml                     # Maven configuration
└── README.md                   # Project documentation
```

## 🛠️ Prerequisites

- **Java**: JDK 11 or higher
- **Maven**: 3.6+ 
- **Chrome Browser**: Latest version (automatically managed by WebDriverManager)
- **Internet Connection**: Required for WebDriverManager and test execution

## ⚡ Quick Setup

### 1. Clone or Download Project
```bash
# If using git
git clone <repository-url>
cd insider-selenium-automation

# Or download and extract the project files
```

### 2. Verify Java & Maven Installation
```bash
# Check Java version
java -version

# Check Maven version  
mvn -version
```

### 3. Install Dependencies
```bash
# Download all required dependencies
mvn clean install -DskipTests
```

## 🏃 Running Tests

### Option 1: Run All Tests via Maven
```bash
# Execute all tests with detailed output
mvn test

# Run tests with specific profile
mvn test -Dbrowser=chrome
```

### Option 2: Run Specific Test Class
```bash
# Run only the InsiderTest class
mvn test -Dtest=InsiderTest

# Run specific test method
mvn test -Dtest=InsiderTest#testHomepageLoads
```

### Option 3: Run via TestNG XML
```bash
# Execute using TestNG configuration
mvn test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Option 4: IDE Execution
1. Import project into IntelliJ IDEA or Eclipse
2. Navigate to `src/test/java/tests/InsiderTest.java`
3. Right-click and select "Run InsiderTest"

## ⚙️ Configuration

### Browser Configuration (`config.properties`)
```properties
# Browser settings
browser=chrome                    # Supported: chrome, firefox, edge
headless=false                   # Run in headless mode
window.maximize=true             # Maximize browser window

# Timeouts (seconds)
implicit.wait=10                 # Element location timeout
explicit.wait=15                 # Explicit wait timeout  
page.load.timeout=30            # Page load timeout
```

### URL Configuration
```properties
# Application URLs
base.url=https://useinsider.com/
careers.url=https://useinsider.com/careers/
```

### Screenshot & Reporting
```properties
# Screenshot settings
screenshot.on.failure=true       # Capture screenshots on test failures
screenshot.path=target/screenshots/

# Reporting
extent.report.path=target/extent-reports/
```

## 📋 Test Scenarios

### Scenario 1: Homepage Verification ✅

**Test Class**: `InsiderTest.java`  
**Test Method**: `testHomepageLoads()`

**Description**: Verifies that the Insider homepage loads correctly and all essential elements are present.

**Test Steps**:
1. Navigate to https://useinsider.com/
2. Verify URL contains "useinsider.com"
3. Verify page title contains "Insider"  
4. Verify homepage content is loaded
5. Verify Insider logo is displayed
6. Verify navigation menu is present
7. Verify page content is displayed

**Expected Results**:
- ✅ Homepage loads successfully
- ✅ URL verification passes
- ✅ Title verification passes  
- ✅ All page elements are visible
- ✅ Navigation is functional

**Error Handling**:
- Automatic screenshot capture on failure
- Detailed error messages for debugging
- Graceful handling of cookie consent popups

## 🔧 Framework Features

### Page Object Model (POM) Implementation

**BasePage.java** - Abstract base class providing:
- WebDriver management
- Common wait strategies (implicit/explicit)
- Element interaction methods (click, sendKeys, getText)
- Scroll operations
- Alert handling
- Utility methods for robust element operations

**HomePage.java** - Specific page class providing:
- Optimized CSS and XPath selectors
- Fallback selector strategies for robustness
- Homepage-specific actions and validations
- Cookie consent handling
- Navigation functionality

### TestUtils.java - Utility Methods

**Driver Management**:
- Automatic WebDriverManager setup
- Multi-browser support (Chrome, Firefox, Edge)
- Configurable browser options
- Proper timeout configurations

**Assertion Helpers**:
- Custom assertion methods with detailed messages
- URL and title validation helpers
- Element visibility assertions
- Screenshot capture utilities

**Configuration Management**:
- Property-based configuration loading
- Environment-specific settings
- Runtime property access

### Test Structure (TestNG)

**Annotations Used**:
- `@BeforeMethod`: WebDriver initialization and page object setup
- `@Test`: Test execution with descriptive names
- `@AfterMethod`: Cleanup and screenshot capture on failures
- `@BeforeClass/@AfterClass`: Test suite setup and teardown

**Features**:
- Detailed console logging for test execution tracking
- Automatic screenshot capture on test failures
- Comprehensive error reporting
- Test method isolation

## 🐛 Troubleshooting

### Common Issues & Solutions

**Issue**: Tests fail with "WebDriver not found"
```bash
# Solution: Clean and reinstall dependencies
mvn clean install -DskipTests
```

**Issue**: Chrome browser not launching
```bash
# Solution: Update WebDriverManager or specify Chrome path
# Add to config.properties:
webdriver.chrome.driver.path=/path/to/chromedriver
```

**Issue**: Tests timeout waiting for elements  
```bash
# Solution: Increase timeout values in config.properties
implicit.wait=20
explicit.wait=30
page.load.timeout=60
```

**Issue**: Tests fail on CI/CD pipeline
```bash
# Solution: Enable headless mode
headless=true
```

### Debugging Tips

1. **Enable Verbose Logging**: Check console output for detailed step-by-step execution
2. **Screenshot Analysis**: Check `target/screenshots/` for failure screenshots  
3. **Element Inspection**: Use browser dev tools to verify selectors
4. **Network Issues**: Ensure stable internet connection for WebDriverManager

## 🔄 Extending the Framework

### Adding New Page Objects
1. Create new class extending `BasePage`
2. Define page-specific locators using optimized selectors
3. Implement page-specific methods and validations
4. Follow the established naming conventions

### Adding New Tests
1. Create test methods in existing test classes or new test classes
2. Use descriptive test names and documentation
3. Follow the AAA pattern (Arrange, Act, Assert)
4. Include proper error handling and logging

### Configuration Extensions
1. Add new properties to `config.properties`
2. Update `TestUtils.java` to support new configurations
3. Document new configuration options

## 📊 Test Execution Results

### Successful Test Run Output:
```
===============================================================================
INSIDER TEST AUTOMATION - SCENARIO 1
Testing homepage functionality at: https://useinsider.com/
Browser: chrome
===============================================================================

Setting up test environment...
WebDriver initialized successfully
Page objects initialized successfully

Starting Test Scenario 1: Homepage verification
Step 1: Navigating to Insider homepage...
Successfully navigated to homepage
Step 2: Verifying page URL...
Current URL: https://useinsider.com/
✓ URL verification passed
Step 3: Verifying page title...
Page title: Insider - Build it, Own it, Scale it
✓ Title verification passed
Step 4: Verifying homepage elements...
✓ Homepage content loaded successfully
✓ Insider logo is displayed
✓ Navigation menu is displayed
✓ Page content is displayed

🎉 Test Scenario 1 completed successfully!
All homepage verification checks passed:
  ✓ URL contains 'useinsider.com'
  ✓ Page title contains 'Insider'
  ✓ Homepage elements are displayed
  ✓ Navigation is functional

Test cleanup completed

===============================================================================
INSIDER TEST AUTOMATION - COMPLETED
===============================================================================
```

## 🤝 Contributing

1. Follow the established coding standards and patterns
2. Add comprehensive documentation for new features
3. Include proper error handling and logging
4. Write clean, maintainable code with meaningful comments
5. Test thoroughly before submitting changes

## 📞 Support

For issues, questions, or contributions:
1. Check the troubleshooting section above
2. Review test execution logs for detailed error information
3. Verify configuration settings in `config.properties`
4. Ensure all prerequisites are properly installed

---

**Framework Version**: 1.0  
**Last Updated**: 2024  
**Maintained By**: Test Automation Team