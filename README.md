# Test Automation Framework

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
│   │       │   ├── HomePage.java
│   │       │   ├── CareersPage.java
│   │       │   └── QAJobsPage.java
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

# Run specific test scenarios
mvn test -Dtest=InsiderTest#testHomepageLoads         # Scenario 1 only
mvn test -Dtest=InsiderTest#testCareersPageNavigation # Scenario 2 only
mvn test -Dtest=InsiderTest#testQAJobsFiltering       # Scenario 3 only
mvn test -Dtest=InsiderTest#testJobDetailsValidation  # Scenario 4 only
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
qa.careers.url=https://useinsider.com/careers/quality-assurance/
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

### Scenario 2: Careers Page Navigation & Verification ✅

**Test Class**: `InsiderTest.java`  
**Test Method**: `testCareersPageNavigation()`

**Description**: Navigates from homepage to careers page and verifies all main career sections are present and accessible.

**Test Steps**:
1. Navigate to Insider homepage
2. Navigate to Careers page (via Company menu or direct link)
3. Verify Career page loads successfully
4. Verify URL contains "career"
5. Verify page title is present
6. Verify main career sections are visible:
   - Locations block
   - Teams block  
   - Life at Insider block
7. Verify sections are accessible/scrollable
8. Check section interactivity where applicable

**Expected Results**:
- ✅ Successful navigation to careers page
- ✅ Career page loads with proper URL
- ✅ At least 2 out of 3 main sections are visible
- ✅ Page structure is accessible and functional
- ✅ Smooth scrolling through career sections

**Page Objects Used**:
- `HomePage.java`: Extended with Company menu navigation
- `CareersPage.java`: New page class with career-specific selectors

**Error Handling**:
- Multiple navigation strategies (direct link vs dropdown menu)
- Robust selector fallbacks for career sections
- Graceful handling of different page layouts
- Detailed logging of section detection results

### Scenario 3: QA Jobs Filtering & Verification ✅

**Test Class**: `InsiderTest.java`  
**Test Method**: `testQAJobsFiltering()`

**Description**: Navigates to QA careers page, clicks "See all QA jobs", applies location and department filters, and verifies filtered job listings.

**Test Steps**:
1. Navigate to https://useinsider.com/careers/quality-assurance/
2. Verify QA careers page loads successfully
3. Click "See all QA jobs" button
4. Apply location filter: "Istanbul, Turkey"
5. Apply department filter: "Quality Assurance"  
6. Apply filters (if separate action required)
7. Verify jobs list is present on the page
8. Verify jobs list is not empty (contains actual jobs)
9. Verify jobs are filtered correctly according to criteria

**Expected Results**:
- ✅ Successful navigation to QA careers page
- ✅ "See all QA jobs" button click works
- ✅ Location and department filters apply successfully
- ✅ Jobs list is present and contains filtered results
- ✅ Filtering functionality works correctly

**Page Objects Used**:
- `QAJobsPage.java`: New page class for QA jobs filtering functionality

**Error Handling**:
- Multiple selector strategies for dynamic job elements
- Robust dropdown filtering with fallback options
- Graceful handling of empty job results (normal scenario)
- Wait strategies for dynamic content loading
- Comprehensive logging of filtering actions

### Scenario 4: Job Details Validation & Criteria Verification ✅

**Test Class**: `InsiderTest.java`  
**Test Method**: `testJobDetailsValidation()`

**Description**: Extends Scenario 3 by extracting detailed job information from each filtered job listing and validates that all jobs meet the applied filter criteria at the individual job level.

**Test Steps**:
1. Set up filtered job listings (reuse Scenario 3 filtering logic)
2. Extract detailed job information (Position, Department, Location) from each job listing
3. Validate each job individually against filter criteria:
   - Position contains "Quality Assurance" related terms
   - Department contains "Quality Assurance"
   - Location contains "Istanbul, Turkey"
4. Generate comprehensive validation summary with pass/fail statistics
5. Assert 100% success rate for properly filtered jobs
6. Provide detailed error messages for any failing validations

**Expected Results**:
- ✅ Successfully extracts job details from all filtered job listings
- ✅ All extracted jobs contain Quality Assurance related position titles
- ✅ All extracted jobs are associated with Quality Assurance department
- ✅ All extracted jobs are located in Istanbul, Turkey (or variants)
- ✅ 100% validation success rate indicates filtering is working correctly
- ✅ Detailed error reporting for any validation failures

**Technical Implementation**:
- `JobDetails` class: Inner class to hold position, department, location data
- `getAllJobDetails()`: Extracts job information using multiple selector strategies
- `validateJobCriteria()`: Individual job validation with detailed error messages
- `validateAllJobs()`: Batch validation with comprehensive summary reporting
- `ValidationResult` & `ValidationSummary`: Result classes for structured validation reporting

**Enhanced Features**:
- **Multi-Strategy Extraction**: Uses primary selectors with fallback to text parsing
- **Intelligent Text Parsing**: Analyzes full job text when structured selectors fail
- **Quality Assurance Term Recognition**: Flexible matching for QA, Quality, Test, Assurance terms
- **Location Variants Support**: Handles "Istanbul, Turkey", "Istanbul, Turkiye", "Istanbul" variations
- **Comprehensive Error Reporting**: Individual job failures with specific error messages
- **Success Rate Analytics**: Statistical analysis of validation results

**Error Handling**:
- Graceful handling of missing job elements
- Multiple extraction strategies for robust data collection
- Detailed logging of extraction and validation processes
- Individual job error isolation (one job failure doesn't stop validation)
- Comprehensive assertion messages for debugging

**Validation Logic**:
- **Position Validation**: Checks for QA-related terms (quality, assurance, qa, test)
- **Department Validation**: Validates department field contains "Quality Assurance"
- **Location Validation**: Supports multiple location format variations
- **Overall Success Rate**: Expects 100% for properly filtered results

### Scenario 5: Lever Application Redirect & Form Verification ✅

**Test Class**: `InsiderTest.java`  
**Test Method**: `testLeverApplicationRedirect()`

**Description**: Completes the end-to-end user journey by clicking "View Role" button on a filtered job listing and verifying successful redirect to external Lever application page with proper form elements and job context.

**Test Steps**:
1. Set up filtered job listings (reuse Scenarios 1-3 filtering logic)
2. Verify jobs list container is present (`//div[@id='jobs-list']`)
3. Find job items within the jobs list container
4. Scroll to the first job item and hover mouse over it
5. Wait for "View Role" button to appear after hover effect
6. Click the specific "View Role" button at XPath: `//section[@id='career-position-list']//div[@class='row']//div[1]//div[1]//a[1]`
7. Handle new tab redirect to external Lever application page
8. Verify successful redirect to Lever application page
9. Validate Lever application page URL patterns and form elements
10. Clean up additional tabs and return to original window

**Expected Results**:
- ✅ Jobs list container (`//div[@id='jobs-list']`) is found and accessible
- ✅ Job items are detected within the jobs list container
- ✅ Mouse hover over job item reveals the "View Role" button
- ✅ Specific "View Role" button is found at exact XPath after hover
- ✅ Click on "View Role" button opens new tab with Lever application
- ✅ Successfully redirects to external Lever application page
- ✅ Lever application page URL contains expected domain patterns
- ✅ Essential form elements are present for job application
- ✅ Proper cleanup and window management

**Technical Implementation**:
- `LeverApplicationPage`: Dedicated page class for Lever application handling
- `clickViewRoleForFirstJob()`: Implements exact user-specified behavior with hover and specific XPath
- `hoverOverElement()`: Mouse hover functionality using Selenium Actions
- `specificViewRoleButton`: Exact XPath selector `//section[@id='career-position-list']//div[@class='row']//div[1]//div[1]//a[1]`
- `handleNewTab()`: Robust new tab/window detection and switching
- `isLeverApplicationPage()`: URL validation using domain pattern matching
- `closeAdditionalTabsAndReturnToOriginal()`: Proper window cleanup

**Enhanced Features**:
- **Exact XPath Implementation**: Uses user-specified XPath for precise button targeting
- **Mouse Hover Simulation**: Selenium Actions to reveal hidden "View Role" buttons  
- **Jobs List Container Validation**: Ensures `//div[@id='jobs-list']` is present before proceeding
- **Hover-Triggered Button Detection**: Waits for button appearance after mouse hover
- **Robust New Tab Handling**: Automatic detection and switching for external redirects
- **Domain Pattern Matching**: Flexible Lever domain validation (lever.co, jobs.lever.co, etc.)
- **Form Element Validation**: Multiple selector strategies for application form detection
- **Window Management**: Comprehensive cleanup to prevent browser state issues
- **Graceful Error Handling**: Detailed error messages with cleanup on failures

**Error Handling**:
- Fallback strategies for different job card layouts and button implementations
- Graceful handling of missing application form elements
- Proper cleanup of additional windows/tabs on test failures
- Detailed logging for debugging redirect and form detection issues
- Multiple validation approaches for different Lever page layouts

**Validation Logic**:
- **Button Detection**: Text content, CSS classes, href attributes, and clickability
- **URL Validation**: Pattern matching against known Lever domain variations
- **Form Validation**: Presence of name, email, file upload, and submit elements
- **Job Context**: Validation that job information relates to Quality Assurance positions
- **Page Load Success**: Comprehensive validation of successful page load and content

**Integration Testing**:
- Includes comprehensive end-to-end integration test (`testEndToEndIntegration()`)
- Validates complete user journey from homepage to job application
- Sequential execution of all 5 scenarios with proper state management
- Realistic workflow simulation for job seekers
- Complete cleanup and error handling for integration scenarios

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

#### Scenario 1 - Homepage Verification:
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
Page title: #1 Leader in Individualized, Cross-Channel CX — Insider
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
```

#### Scenario 2 - Careers Page Navigation:
```
Starting Test Scenario 2: Careers page navigation and verification
Step 1: Navigating to Insider homepage...
Successfully navigated to homepage
Step 2: Navigating to Careers page...
Found direct careers link, clicking...
Successfully navigated to Careers page
Step 3: Verifying Career page loads...
✓ Career page loaded successfully
Step 4: Verifying careers page URL...
Current URL: https://useinsider.com/careers/
✓ URL verification passed
Step 5: Verifying page title...
Page title: Careers - Join Us at Insider
✓ Page title verification passed
Step 6: Verifying main career sections are present...
Checking for Locations block...
✓ Locations block found
Checking for Teams block...
✓ Teams block found
Checking for Life at Insider block...
✓ Life at Insider block found
✓ Main career sections are visible
Step 7: Verifying career sections are accessible...
✓ Career sections are accessible/clickable

🎉 Test Scenario 2 completed successfully!
All careers page verification checks passed:
  ✓ Successfully navigated to careers page
  ✓ Career page loaded with proper URL
  ✓ Main career sections are visible
  ✓ Page structure is accessible

===============================================================================
INSIDER TEST AUTOMATION - COMPLETED
===============================================================================
```

#### Scenario 3 - QA Jobs Filtering:
```
Starting Test Scenario 3: QA Jobs filtering by location and department
Step 1: Navigating to QA careers page...
Successfully navigated to: https://useinsider.com/careers/quality-assurance/
✓ QA careers page loaded successfully
Step 2: Clicking 'See all QA jobs'...
Found 'See all QA jobs' button, clicking...
✓ Successfully navigated to QA jobs listing
Step 3: Applying location filter (Istanbul, Turkey)...
Found location dropdown, selecting option...
✓ Location filter applied
Step 4: Applying department filter (Quality Assurance)...
Found department dropdown, selecting option...
✓ Department filter applied
Step 5: Applying filters...
✓ Filters applied
Step 6: Verifying jobs list presence...
✓ Jobs list is present
Step 7: Verifying jobs list is not empty...
✓ Jobs list is not empty
Found 5 job(s) matching the criteria
Step 8: Verifying job filtering...
✓ Jobs are filtered correctly
Sample job titles found:
  - Senior QA Engineer
  - QA Automation Engineer
  - Quality Assurance Specialist

🎉 Test Scenario 3 completed successfully!
All QA jobs filtering verification checks passed:
  ✓ Successfully navigated to QA careers page
  ✓ Successfully clicked 'See all QA jobs'
  ✓ Successfully applied location filter (Istanbul, Turkey)
  ✓ Successfully applied department filter (Quality Assurance)
  ✓ Jobs list is present and functional
  ✓ Filtering functionality is working correctly

===============================================================================
INSIDER TEST AUTOMATION - COMPLETED
===============================================================================
```

#### Scenario 4 - Job Details Validation:
```
Starting Test Scenario 4: Job Details Validation
Step 1: Setting up filtered job listings...
✓ QA careers page loaded successfully
✓ Successfully navigated to QA jobs listing
✓ Location filter applied
✓ Department filter applied
✓ Filters applied
✓ Jobs list is present
Step 2: Extracting job details from all filtered jobs...
Found 4 job listings to extract details from
Job 1: JobDetails{position='Senior Quality Assurance Engineer', department='Quality Assurance', location='Istanbul, Turkiye'}
Job 2: JobDetails{position='QA Automation Engineer', department='Quality Assurance', location='Istanbul, Turkiye'}
Job 3: JobDetails{position='Quality Assurance Specialist', department='Quality Assurance', location='Istanbul, Turkiye'}
Job 4: JobDetails{position='Test Engineer - Quality Assurance', department='Quality Assurance', location='Istanbul, Turkiye'}
✓ Successfully extracted details from 4 jobs
Step 3: Validating each job against filter criteria...

=== VALIDATING ALL JOBS AGAINST FILTER CRITERIA ===
Expected Location: Istanbul, Turkey
Expected Department: Quality Assurance
Total Jobs to Validate: 4

--- Job 1 Validation ---
Job Details: JobDetails{position='Senior Quality Assurance Engineer', department='Quality Assurance', location='Istanbul, Turkiye'}
✓ PASSED: Job meets all filter criteria

--- Job 2 Validation ---
Job Details: JobDetails{position='QA Automation Engineer', department='Quality Assurance', location='Istanbul, Turkiye'}
✓ PASSED: Job meets all filter criteria

--- Job 3 Validation ---
Job Details: JobDetails{position='Quality Assurance Specialist', department='Quality Assurance', location='Istanbul, Turkiye'}
✓ PASSED: Job meets all filter criteria

--- Job 4 Validation ---
Job Details: JobDetails{position='Test Engineer - Quality Assurance', department='Quality Assurance', location='Istanbul, Turkiye'}
✓ PASSED: Job meets all filter criteria

=== VALIDATION SUMMARY ===
Total Jobs: 4
Passed: 4
Failed: 0
Success Rate: 100.0%

Step 4: Asserting validation results...

📊 DETAILED VALIDATION RESULTS:
Total Jobs Validated: 4
Jobs Passed: 4
Jobs Failed: 0
Success Rate: 100.0%

🎉 Test Scenario 4 completed successfully!
All job details validation checks passed:
  ✓ Successfully extracted job details from all filtered jobs
  ✓ All jobs contain Quality Assurance related positions
  ✓ All jobs are associated with Quality Assurance department
  ✓ All jobs are located in Istanbul, Turkey
  ✓ Filtering functionality is working correctly at job level

===============================================================================
INSIDER TEST AUTOMATION - COMPLETED
===============================================================================
```

#### Scenario 5 - Lever Application Redirect:
```
Starting Test Scenario 5: Lever Application Redirect
Step 1: Setting up filtered job listings for Lever redirect test...
✓ QA careers page loaded successfully
✓ Successfully navigated to QA jobs listing
✓ Location filter applied
✓ Department filter applied
✓ Filters applied
✓ Jobs list is present and not empty
Step 2: Verifying View Role functionality is available...
=== DEBUG: Finding all potential View Role buttons ===
Found 4 job elements to search in
Job 1 has View Role button: 'Apply Now'
Job 2 has View Role button: 'View Role'
Job 3 has View Role button: 'Apply'
Job 4 has View Role button: 'Apply Now'
=== Total View Role buttons found: 4 ===
View Role functionality available: true
Found 4 View Role button(s)
✓ View Role functionality is available
Step 3: Clicking 'View Role' button for first available job...
=== SCENARIO 5: Clicking 'View Role' for first job (User-specified XPath) ===
Original window handle: CDwindow-A1B2C3D4E5F6
Step 1: Verifying jobs list container is present...
✓ Jobs list container found: //div[@id='jobs-list']
Step 2: Finding job items within jobs list...
✓ Found 4 job item(s) in jobs list
Step 3: Scrolling to first job item...
Step 4: Hovering mouse over first job item to reveal 'View Role' button...
Step 5: Looking for 'View Role' button at specific XPath...
Target XPath: //section[@id='career-position-list']//div[@class='row']//div[1]//div[1]//a[1]
✓ Found 'View Role' button with text: 'View Role'
Step 6: Clicking 'View Role' button...
✓ Successfully clicked 'View Role' button
Step 4: Handling potential new tab scenario...
Handling new tab scenario...
Total windows/tabs: 2
Switching to new tab...
New tab URL: https://jobs.lever.co/useinsider/senior-qa-engineer-istanbul
✓ New tab detected and switched successfully
Step 5: Verifying redirect to Lever application page...
Verifying redirect success to Lever application...
Validating Lever application page URL: https://jobs.lever.co/useinsider/senior-qa-engineer-istanbul
✓ Found Lever domain pattern: lever.co
✓ URL contains job application keywords
Redirect validation results:
  URL Valid: true
  Page Loaded: true
  Application Content: true
✓ Successfully redirected to Lever application page
Step 6: Validating Lever application page URL...
✓ Lever application page URL validated: https://jobs.lever.co/useinsider/senior-qa-engineer-istanbul
Step 7: Validating page title for job application context...
Validating page title: 'Senior QA Engineer - Insider'
Page title validation: ✓ Valid
✓ Page title validated: 'Senior QA Engineer - Insider'
Step 8: Verifying application form elements are present...
Checking for application form elements...
✓ Job title element found
✓ Application form found
✓ Name field found
✓ Email field found
✓ Apply/Submit button found
Application form elements found: 5/5 (100.0%)
✓ Application form elements are present and accessible
Step 9: Extracting job information from application page...
Job title found: 'Senior QA Engineer'
✓ Job title found: 'Senior QA Engineer'
Company name found: 'Insider'
✓ Company name found: 'Insider'
Step 10: Cleaning up additional tabs...
Closing additional tab...
Returned to original window
✓ Cleanup completed - returned to original window

🎉 Test Scenario 5 completed successfully!
All Lever application redirect checks passed:
  ✓ Successfully found and clicked 'View Role' button
  ✓ Properly handled new tab/window scenarios
  ✓ Successfully redirected to Lever application page
  ✓ Lever application URL and page validation passed
  ✓ Application form elements are present and accessible
  ✓ Job information extraction working correctly
  ✓ Proper cleanup and window management

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
