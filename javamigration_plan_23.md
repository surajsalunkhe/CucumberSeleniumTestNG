# Java 21 to Java 23 Migration Plan
## Cucumber Selenium TestNG Test Automation Framework

---

**Migration Type:** Java 21 (LTS) → Java 23 (Non-LTS)  
**Project:** CucumberSeleniumTestNG  
**Framework:** Cucumber BDD + Selenium WebDriver + TestNG  
**Current Date:** November 17, 2025  
**Plan Version:** 1.0  
**Prepared By:** Java Version Migration Planner Agent

---

## ⚠️ CRITICAL NOTICE

**Java 23 is NOT a Long-Term Support (LTS) release.**

### LTS Recommendation
- **Java 23 Support:** 6 months only (ends March 2024)
- **Java 25 LTS:** Released September 2025, 8-year support (until 2032)
- **Java 21 LTS:** Current version, supported until September 2028

### ⭐ RECOMMENDATION
Consider migrating to **Java 25 LTS** instead of Java 23 for:
- 8-year long-term support (vs 6-month support)
- Stable, production-ready features
- Better ROI on migration effort
- Future-proof investment

**If you still want to proceed with Java 23:** Continue with this plan for testing/experimentation purposes.

---

## Executive Summary

### Current State Analysis
- **Current Java Version:** 21 (LTS)
- **Maven Compiler Plugin:** 3.8.1 (needs update)
- **Maven Surefire Plugin:** 3.2.5 (compatible)
- **Total Dependencies:** 24
- **Total Java Files:** 22
- **Framework Type:** Test Automation (Selenium + Cucumber + TestNG)

### Migration Complexity Assessment
- **Overall Risk Level:** 🟢 **LOW**
- **Estimated Duration:** 3-4 weeks
- **Resource Requirements:** 1 Developer, 1 QA Engineer
- **Code Changes Required:** Minimal (javax.mail migration)
- **Breaking Changes:** None detected in current codebase

### Key Findings
✅ **Good News:**
- No deprecated Java 21 APIs in use
- No module system conflicts detected
- All test framework dependencies are Java 23 compatible
- Minimal code refactoring required

⚠️ **Action Required:**
- Update `maven-compiler-plugin` to 3.13.0+ (required for Java 23)
- Migrate `javax.mail` to Jakarta Mail or alternative
- Update Selenium to 4.25.0+ (recommended)
- Review JVM arguments for Java 23 compatibility

---

## Table of Contents
1. [Dependency Compatibility Matrix](#dependency-compatibility-matrix)
2. [Code Impact Analysis](#code-impact-analysis)
3. [Migration Phases](#migration-phases)
4. [Configuration Updates](#configuration-updates)
5. [Testing Strategy](#testing-strategy)
6. [Risk Assessment & Rollback Plan](#risk-assessment--rollback-plan)
7. [Timeline & Milestones](#timeline--milestones)
8. [Troubleshooting Guide](#troubleshooting-guide)

---

## Dependency Compatibility Matrix

### Maven Plugins

| Plugin | Current Version | Java 23 Compatible | Recommended Version | Risk | Action Required |
|--------|----------------|---------------------|---------------------|------|-----------------|
| maven-compiler-plugin | 3.8.1 | ❌ No | 3.13.0 | 🟡 MEDIUM | **UPDATE REQUIRED** |
| maven-surefire-plugin | 3.2.5 | ✅ Yes | 3.2.5+ | 🟢 LOW | Optional update to 3.5.0 |
| extentreports-cucumberjson-plugin | 2.16.0 | ✅ Yes | 2.16.0+ | 🟢 LOW | No change needed |

### Test Framework Dependencies

| Dependency | Current Version | Java 23 Compatible | Recommended Version | Risk | Action Required |
|------------|----------------|---------------------|---------------------|------|-----------------|
| selenium-java | 4.10.0 | ✅ Yes | 4.25.0+ | 🟢 LOW | **Recommended update** |
| cucumber-java | 7.15.0 | ✅ Yes | 7.18.0 | 🟢 LOW | Optional update |
| cucumber-testng | 7.15.0 | ✅ Yes | 7.18.0 | 🟢 LOW | Optional update |
| cucumber-picocontainer | 7.15.0 | ✅ Yes | 7.18.0 | 🟢 LOW | Optional update |
| testng | 7.9.0 | ✅ Yes | 7.10.0 | 🟢 LOW | Optional update |
| webdrivermanager | 5.9.2 | ✅ Yes | 5.9.2+ | 🟢 LOW | No change needed |

### Reporting & Utility Dependencies

| Dependency | Current Version | Java 23 Compatible | Recommended Version | Risk | Action Required |
|------------|----------------|---------------------|---------------------|------|-----------------|
| allure-cucumber7-jvm | 2.25.0 | ✅ Yes | 2.25.0+ | 🟢 LOW | No change needed |
| extentreports-cucumber7-adapter | 1.14.0 | ✅ Yes | 1.14.0+ | 🟢 LOW | No change needed |
| rest-assured | 5.4.0 | ✅ Yes | 5.5.0 | 🟢 LOW | Optional update |
| log4j-api | 2.23.0 | ✅ Yes | 2.23.0+ | 🟢 LOW | No change needed |
| log4j-core | 2.23.0 | ✅ Yes | 2.23.0+ | 🟢 LOW | No change needed |

### Data Processing Dependencies

| Dependency | Current Version | Java 23 Compatible | Recommended Version | Risk | Action Required |
|------------|----------------|---------------------|---------------------|------|-----------------|
| apache-poi | 5.2.5 | ✅ Yes | 5.3.0+ | 🟢 LOW | Recommended update |
| apache-poi-ooxml | 5.2.5 | ✅ Yes | 5.3.0+ | 🟢 LOW | Recommended update |
| pdfbox | 2.0.27 | ✅ Yes | 2.0.31 | 🟢 LOW | Optional update |
| gson | 2.10.1 | ✅ Yes | 2.11.0 | 🟢 LOW | Optional update |
| json-simple | 1.1.1 | ✅ Yes | 1.1.1 | 🟢 LOW | No change needed |
| javafaker | 1.0.2 | ✅ Yes | 1.0.2 | 🟢 LOW | No change needed |
| jsch | 0.1.55 | ✅ Yes | 0.1.55 | 🟢 LOW | No change needed |

### ⚠️ Critical Dependency Issue

| Dependency | Current Version | Java 23 Compatible | Recommended Version | Risk | Action Required |
|------------|----------------|---------------------|---------------------|------|-----------------|
| **javax.mail** | 1.5.0-b01 | ❌ **No** | Use Jakarta Mail 2.0.1 or alternatives | 🔴 **CRITICAL** | **MIGRATION REQUIRED** |

**Issue:** `javax.mail` is part of Java EE which was removed in Java 11+. The dependency still works but is legacy.

**Solutions:**
1. **Option A (Recommended):** Migrate to Jakarta Mail 2.0.1+
   ```xml
   <dependency>
       <groupId>com.sun.mail</groupId>
       <artifactId>jakarta.mail</artifactId>
       <version>2.0.1</version>
   </dependency>
   ```
   
2. **Option B:** Remove email functionality if not critical
   
3. **Option C:** Use alternative email library (Apache Commons Email)

---

## Code Impact Analysis

### Files Scanned
- **Total Java Files:** 22
- **Files with Issues:** 1
- **Deprecated APIs Found:** 1 location
- **Module System Conflicts:** 0

### Critical Code Changes Required

#### 1. javax.mail Migration
**File:** `src/test/java/utils/SendTestAutomationReport.java`

**Current Code (Lines 4-5):**
```java
import javax.mail.*;
import javax.mail.internet.*;
```

**Action Required:** Migrate to Jakarta Mail

**Option A - Jakarta Mail (Recommended):**
```java
import jakarta.mail.*;
import jakarta.mail.internet.*;
```

**Update POM.xml:**
```xml
<!-- Remove old dependency -->
<!-- <dependency>
    <groupId>javax.mail</groupId>
    <artifactId>mail</artifactId>
    <version>1.5.0-b01</version>
</dependency> -->

<!-- Add Jakarta Mail -->
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>jakarta.mail</artifactId>
    <version>2.0.1</version>
</dependency>
```

**API Changes:**
- Most `javax.mail.*` classes → `jakarta.mail.*` (simple find-replace)
- No functional changes expected
- Test email sending functionality thoroughly

**Estimated Effort:** 1-2 hours

---

### ✅ Clean Code Areas (No Changes Required)

The following areas were analyzed and found to be Java 23 compatible:

1. **Driver Management** (`driverFactory/DriverManager.java`)
   - ThreadLocal WebDriver usage: ✅ Compatible
   - No deprecated thread APIs
   
2. **Test Context** (`cucumber/TestContext.java`)
   - Picocontainer DI: ✅ Compatible
   
3. **Page Objects** (`pages/Login/*.java`)
   - Selenium WebDriver usage: ✅ Compatible
   
4. **Step Definitions** (`stepDef/*.java`)
   - Cucumber annotations: ✅ Compatible
   
5. **Utilities** (`utils/*.java`)
   - No sun.misc.Unsafe usage
   - No deprecated APIs (except javax.mail)
   - No finalize() methods
   
6. **Test Runners** (`testRunner/*.java`)
   - TestNG integration: ✅ Compatible

---

## Migration Phases

### Phase 1: Preparation (Week 1)
**Duration:** 5 working days  
**Owner:** DevOps + Tech Lead

#### Task 1.1: Environment Setup
- [ ] Download and install Java 23 JDK
  - Oracle JDK 23: https://www.oracle.com/java/technologies/downloads/#java23
  - OpenJDK 23: https://jdk.java.net/23/
- [ ] Update JAVA_HOME environment variable
- [ ] Verify installation: `java -version` should show "23"
- [ ] Update IDE (IntelliJ IDEA/Eclipse) to support Java 23
  - IntelliJ IDEA: 2023.3+
  - Eclipse: 2023-12+
  - VS Code: Update Java Extension Pack

**Risk:** 🟢 LOW  
**Estimated Time:** 2-4 hours

---

#### Task 1.2: Create Migration Branch
- [ ] Create feature branch from main/master
  ```bash
  git checkout main
  git pull origin main
  git checkout -b feature/java-23-migration
  ```
- [ ] Push to remote repository
  ```bash
  git push -u origin feature/java-23-migration
  ```
- [ ] Set up parallel CI/CD pipeline for testing (optional)

**Risk:** 🟢 LOW  
**Estimated Time:** 1 hour

---

#### Task 1.3: Backup Current State
- [ ] Create backup of current working branch
  ```bash
  git tag backup-java21-$(date +%Y%m%d)
  git push origin --tags
  ```
- [ ] Document current test execution results
  ```bash
  mvn clean verify > test-baseline-java21.log 2>&1
  ```
- [ ] Save test metrics for comparison
  - Execution time
  - Pass/fail rates
  - Memory usage

**Risk:** 🟢 LOW  
**Estimated Time:** 1 hour

---

#### Task 1.4: Update POM.xml - Compiler Settings Only
**File:** `pom.xml`

**Changes:**
```xml
<properties>
    <maven.compiler.source>23</maven.compiler.source>
    <maven.compiler.target>23</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>    
</properties>
```

**Update maven-compiler-plugin:**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.13.0</version> <!-- Updated for Java 23 -->
    <configuration>
        <source>23</source>
        <target>23</target>
        <!-- Optional: Enable preview features -->
        <!-- <compilerArgs>
            <arg>--enable-preview</arg>
        </compilerArgs> -->
    </configuration>
</plugin>
```

**Do NOT update dependencies yet** - only compiler settings!

**Risk:** 🟢 LOW  
**Estimated Time:** 30 minutes

---

#### Task 1.5: Initial Build Test
- [ ] Run clean compile
  ```bash
  mvn clean compile
  ```
- [ ] Document all compilation errors
- [ ] Expected outcome: Should compile with warnings about javax.mail

**Risk:** 🟡 MEDIUM (javax.mail issues expected)  
**Estimated Time:** 1 hour

---

### Phase 2: Dependency Updates (Week 2)
**Duration:** 5 working days  
**Owner:** Developer

#### Task 2.1: Update Maven Plugins
**File:** `pom.xml`

Update plugins in this order:

1. **maven-compiler-plugin** (Already done in Phase 1)
   
2. **maven-surefire-plugin** (Optional - current version works)
   ```xml
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-surefire-plugin</artifactId>
       <version>3.5.0</version>
       <configuration>
           <argLine>
               --add-opens java.base/java.lang=ALL-UNNAMED
               --add-opens java.base/java.util=ALL-UNNAMED
           </argLine>
           <testFailureIgnore>true</testFailureIgnore>
           <properties>
               <property>
                   <name>dataproviderthreadcount</name>
                   <value>1</value>
               </property>
           </properties>
           <suiteXmlFiles>
               <suiteXmlFile>${project.basedir}/testng.xml</suiteXmlFile>
           </suiteXmlFiles>
       </configuration>
   </plugin>
   ```

**Test after each update:**
```bash
mvn clean compile
```

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 2 hours

---

#### Task 2.2: Migrate javax.mail to Jakarta Mail
**Priority:** 🔴 **CRITICAL**

**Step 1:** Update POM.xml dependency
```xml
<!-- Remove javax.mail -->
<!-- 
<dependency>
    <groupId>javax.mail</groupId>
    <artifactId>mail</artifactId>
    <version>1.5.0-b01</version>
</dependency>
-->

<!-- Add Jakarta Mail -->
<dependency>
    <groupId>com.sun.mail</groupId>
    <artifactId>jakarta.mail</artifactId>
    <version>2.0.1</version>
</dependency>
```

**Step 2:** Update imports in `SendTestAutomationReport.java`
```java
// OLD
import javax.mail.*;
import javax.mail.internet.*;

// NEW
import jakarta.mail.*;
import jakarta.mail.internet.*;
```

**Step 3:** Test email functionality
- Run unit tests for email sending
- Test with actual email credentials (QA environment)
- Verify email delivery

**Step 4:** Verify no other javax.mail usages
```bash
grep -r "javax.mail" src/test/java/
```

**Risk:** 🟡 MEDIUM (API is nearly identical, but requires testing)  
**Estimated Time:** 3-4 hours

---

#### Task 2.3: Update Selenium WebDriver (Recommended)
**Current:** 4.10.0  
**Target:** 4.25.0+

**Update POM.xml:**
```xml
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.25.0</version>
</dependency>
```

**Why Update?**
- Better Java 21+ support
- Bug fixes and performance improvements
- Enhanced stability

**Test After Update:**
```bash
mvn clean test -Dtest=TestRunner "-Dcucumber.filter.tags=@Smoke"
```

**Risk:** 🟢 LOW (backward compatible API)  
**Estimated Time:** 2 hours (including testing)

---

#### Task 2.4: Optional Dependency Updates
Update these dependencies for better Java 23 support (optional):

```xml
<!-- Cucumber -->
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>7.18.0</version>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-testng</artifactId>
    <version>7.18.0</version>
</dependency>
<dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-picocontainer</artifactId>
    <version>7.18.0</version>
</dependency>

<!-- TestNG -->
<dependency>
    <groupId>org.testng</groupId>
    <artifactId>testng</artifactId>
    <version>7.10.0</version>
    <scope>test</scope>
</dependency>

<!-- Apache POI -->
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi</artifactId>
    <version>5.3.0</version>
</dependency>
<dependency>
    <groupId>org.apache.poi</groupId>
    <artifactId>poi-ooxml</artifactId>
    <version>5.3.0</version>
</dependency>

<!-- REST Assured -->
<dependency>
    <groupId>io.rest-assured</groupId>
    <artifactId>rest-assured</artifactId>
    <version>5.5.0</version>
</dependency>

<!-- PDF Box -->
<dependency>
    <groupId>org.apache.pdfbox</groupId>
    <artifactId>pdfbox</artifactId>
    <version>2.0.31</version>
</dependency>

<!-- Gson -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.11.0</version>
</dependency>
```

**Process:**
- Update one dependency at a time
- Run regression tests after each update
- Rollback if tests fail

**Risk:** 🟢 LOW  
**Estimated Time:** 4-6 hours (if all optional updates done)

---

### Phase 3: Code Refactoring & JVM Tuning (Week 2-3)
**Duration:** 3-5 working days  
**Owner:** Developer

#### Task 3.1: Fix javax.mail References
**Already covered in Task 2.2** ✅

---

#### Task 3.2: Review JVM Arguments
**Current JVM Args in pom.xml:**
```xml
<argLine>--add-opens java.base/java.lang=ALL-UNNAMED</argLine>
```

**Recommended for Java 23:**
```xml
<argLine>
    --add-opens java.base/java.lang=ALL-UNNAMED
    --add-opens java.base/java.util=ALL-UNNAMED
    --add-opens java.base/java.time=ALL-UNNAMED
</argLine>
```

**Why?**
- Java 23 has stricter module encapsulation
- Test frameworks (TestNG, Cucumber) may need reflection access
- `--add-opens` grants access without warnings

**Risk:** 🟢 LOW  
**Estimated Time:** 1 hour

---

#### Task 3.3: (Optional) Leverage Java 23 Features
**Only if time permits and adds value**

Java 23 preview features you could adopt:
1. **Markdown in Javadoc comments**
2. **Module import declarations** (if using modules)
3. **Structured Concurrency improvements** (preview)

**Example - Markdown in Javadoc:**
```java
/**
 * ## LoginPage Class
 * 
 * This page object handles:
 * - User login functionality
 * - Username/password validation
 * - Error message handling
 * 
 * @see ElementUtil
 */
public class LoginPage {
    // ...
}
```

**Note:** Preview features require `--enable-preview` flag

**Risk:** 🟢 LOW (optional)  
**Estimated Time:** 2-4 hours (only if doing this)

---

### Phase 4: Testing & Validation (Week 3)
**Duration:** 5 working days  
**Owner:** QA Engineer + Developer

#### Task 4.1: Unit Test Execution
- [ ] Run all unit tests
  ```bash
  mvn clean test
  ```
- [ ] Fix any failing tests
- [ ] Target: 100% test pass rate
- [ ] Document any test failures with root cause

**Expected Issues:**
- Email sending tests may fail if Jakarta Mail not properly configured
- No other failures expected

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 1 day

---

#### Task 4.2: Integration Test Execution
- [ ] Run full test suite
  ```bash
  mvn clean verify
  ```
- [ ] Test all feature scenarios
- [ ] Verify all Cucumber scenarios pass
- [ ] Check ExtentReports generation

**Test Scenarios:**
- Login tests
- Registration tests
- All tagged scenarios (@Smoke, @Regression)

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 2 days

---

#### Task 4.3: Performance Baseline Comparison
**Metrics to Compare:**

| Metric | Java 21 Baseline | Java 23 Result | Threshold | Status |
|--------|------------------|----------------|-----------|--------|
| Test Execution Time | [Record] | [Record] | ±10% | ⏸️ |
| Memory Usage (Heap) | [Record] | [Record] | ±15% | ⏸️ |
| Build Time | [Record] | [Record] | ±10% | ⏸️ |
| Test Pass Rate | [Record] | [Record] | 100% | ⏸️ |

**Commands:**
```bash
# Java 21 Baseline (before migration)
time mvn clean verify > java21-metrics.log 2>&1

# Java 23 (after migration)
time mvn clean verify > java23-metrics.log 2>&1

# Compare
diff java21-metrics.log java23-metrics.log
```

**Acceptance Criteria:**
- Performance should NOT degrade by more than 10%
- All tests should pass
- No new errors or warnings (except expected)

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 1 day

---

#### Task 4.4: Cross-Browser Testing
Test on all supported browsers:
- [ ] Chrome (latest)
- [ ] Firefox (latest)
- [ ] Edge (latest)
- [ ] Safari (latest - macOS only)

**Commands:**
```bash
# Chrome
mvn clean test -Dbrowser=chrome -Dtest=TestRunner

# Firefox
mvn clean test -Dbrowser=firefox -Dtest=TestRunner

# Edge
mvn clean test -Dbrowser=edge -Dtest=TestRunner

# Safari (macOS only)
mvn clean test -Dbrowser=safari -Dtest=TestRunner
```

**Risk:** 🟢 LOW  
**Estimated Time:** 1 day

---

#### Task 4.5: Security & Dependency Scan
- [ ] Run OWASP dependency check
  ```bash
  mvn org.owasp:dependency-check-maven:check
  ```
- [ ] Review vulnerability report
- [ ] Update any dependencies with known vulnerabilities
- [ ] Document all findings

**Risk:** 🟢 LOW  
**Estimated Time:** 2-3 hours

---

### Phase 5: Deployment (Week 4)
**Duration:** 5 working days  
**Owner:** DevOps + Operations

#### Task 5.1: Update CI/CD Pipeline
**If using GitHub Actions:**

Update `.github/workflows/test.yml` (or similar):
```yaml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - name: Set up JDK 23
        uses: actions/setup-java@v4
        with:
          java-version: '23'
          distribution: 'temurin'
      - name: Run tests
        run: mvn clean verify
```

**If using Jenkins:**
```groovy
pipeline {
    agent any
    tools {
        jdk 'JDK-23'
        maven 'Maven-3.9'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn verify'
            }
        }
    }
}
```

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 4-6 hours

---

#### Task 5.2: Staging Environment Deployment
- [ ] Deploy to staging/QA environment with Java 23
- [ ] Update staging server Java version
- [ ] Deploy updated application
- [ ] Run smoke tests
  ```bash
  mvn clean test -Denv=QA -Dtest=TestRunner "-Dcucumber.filter.tags=@Smoke"
  ```
- [ ] Monitor for 24-48 hours

**Risk:** 🟡 MEDIUM  
**Estimated Time:** 1 day

---

#### Task 5.3: Production Deployment Planning
**Blue-Green Deployment Strategy:**

1. **Keep Java 21 environment running** (Green)
2. **Deploy Java 23 environment** (Blue)
3. **Route 10% traffic to Blue**
4. **Monitor metrics for 48 hours**
   - Error rates
   - Response times
   - Test execution times
5. **If successful, route 100% to Blue**
6. **Keep Green ready for rollback (1 week)**

**Rollback Triggers:**
- Error rate > 5% increase
- Performance degradation > 20%
- Critical test failures
- Production incidents

**Risk:** 🟠 HIGH  
**Estimated Time:** 2-3 days

---

#### Task 5.4: Documentation Updates
- [ ] Update README.md with Java 23 requirement
- [ ] Update developer setup guide
- [ ] Document new JVM arguments
- [ ] Update architecture diagrams (if applicable)
- [ ] Create migration retrospective document

**Risk:** 🟢 LOW  
**Estimated Time:** 1 day

---

#### Task 5.5: Decommission Old Environment
**Wait 2 weeks after successful deployment**

- [ ] Archive Java 21 environment configuration
- [ ] Remove Java 21 from servers
- [ ] Update documentation to reflect Java 23 as standard
- [ ] Close migration ticket/epic

**Risk:** 🟢 LOW  
**Estimated Time:** 1 day

---

## Configuration Updates

### Complete Updated POM.xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.SeleniumCucumberTestNg</groupId>
    <artifactId>SeleniumCucumberTestNg</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <!-- Java 23 Compiler Settings -->
        <maven.compiler.source>23</maven.compiler.source>
        <maven.compiler.target>23</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        
        <!-- Dependency Versions (Optional - for better management) -->
        <selenium.version>4.25.0</selenium.version>
        <cucumber.version>7.18.0</cucumber.version>
        <testng.version>7.10.0</testng.version>
        <poi.version>5.3.0</poi.version>
    </properties>
    
    <build>
        <plugins>
            <!-- Maven Compiler Plugin - REQUIRED UPDATE for Java 23 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>23</source>
                    <target>23</target>
                    <!-- Optional: Enable preview features -->
                    <!-- <compilerArgs>
                        <arg>--enable-preview</arg>
                    </compilerArgs> -->
                </configuration>
            </plugin>
            
            <!-- Maven Surefire Plugin - Optional update -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.0</version>
                <configuration>
                    <argLine>
                        --add-opens java.base/java.lang=ALL-UNNAMED
                        --add-opens java.base/java.util=ALL-UNNAMED
                        --add-opens java.base/java.time=ALL-UNNAMED
                    </argLine>
                    <testFailureIgnore>true</testFailureIgnore>
                    <properties>
                        <property>
                            <name>dataproviderthreadcount</name>
                            <value>1</value>
                        </property>
                    </properties>
                    <suiteXmlFiles>
                        <suiteXmlFile>${project.basedir}/testng.xml</suiteXmlFile>
                    </suiteXmlFiles>
                </configuration>
                <executions>
                    <execution>
                        <id>surefire-integration-test</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>test</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>
            
            <!-- ExtentReports Plugin - No change -->
            <plugin>
                <groupId>tech.grasshopper</groupId>
                <artifactId>extentreports-cucumberjson-plugin</artifactId>
                <version>2.16.0</version>
                <executions>
                    <execution>
                        <id>report</id>
                        <phase>post-integration-test</phase>
                        <goals>
                            <goal>extentreport</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <cucumberJsonReportDirectory>${project.build.directory}/json-report</cucumberJsonReportDirectory>
                    <extentPropertiesDirectory>${project.basedir}/src/test/resources</extentPropertiesDirectory>
                    <displayAllHooks>false</displayAllHooks>
                    <strictCucumber6Behavior>true</strictCucumber6Behavior>
                </configuration>
            </plugin>
        </plugins>
    </build>
    
    <dependencies>
        <!-- PDF Processing -->
        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox</artifactId>
            <version>2.0.31</version>
        </dependency>
        
        <!-- Email - CRITICAL CHANGE: javax.mail → Jakarta Mail -->
        <dependency>
            <groupId>com.sun.mail</groupId>
            <artifactId>jakarta.mail</artifactId>
            <version>2.0.1</version>
        </dependency>
        
        <!-- Reporting -->
        <dependency>
            <groupId>tech.grasshopper</groupId>
            <artifactId>extentreports-cucumber7-adapter</artifactId>
            <version>1.14.0</version>
        </dependency>
        
        <!-- Cucumber BDD Framework -->
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-java</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-testng</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        <dependency>
            <groupId>io.cucumber</groupId>
            <artifactId>cucumber-picocontainer</artifactId>
            <version>${cucumber.version}</version>
        </dependency>
        
        <!-- REST API Testing -->
        <dependency>
            <groupId>io.rest-assured</groupId>
            <artifactId>rest-assured</artifactId>
            <version>5.5.0</version>
        </dependency>
        
        <!-- Selenium WebDriver - RECOMMENDED UPDATE -->
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>${selenium.version}</version>
        </dependency>
        
        <!-- Logging -->
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-api</artifactId>
            <version>2.23.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.logging.log4j</groupId>
            <artifactId>log4j-core</artifactId>
            <version>2.23.0</version>
        </dependency>
        
        <!-- JSON Processing -->
        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1.1</version>
        </dependency>
        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.11.0</version>
        </dependency>
        
        <!-- TestNG -->
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>${testng.version}</version>
            <scope>test</scope>
        </dependency>
        
        <!-- Excel Processing -->
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>${poi.version}</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi-ooxml</artifactId>
            <version>${poi.version}</version>
        </dependency>
        
        <!-- Test Data Generation -->
        <dependency>
            <groupId>com.github.javafaker</groupId>
            <artifactId>javafaker</artifactId>
            <version>1.0.2</version>
        </dependency>
        
        <!-- Allure Reporting -->
        <dependency>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-cucumber7-jvm</artifactId>
            <version>2.25.0</version>
        </dependency>
        
        <!-- WebDriver Manager -->
        <dependency>
            <groupId>io.github.bonigarcia</groupId>
            <artifactId>webdrivermanager</artifactId>
            <version>5.9.2</version>
        </dependency>
        
        <!-- SSH/SFTP Support -->
        <dependency>
            <groupId>com.jcraft</groupId>
            <artifactId>jsch</artifactId>
            <version>0.1.55</version>
        </dependency>
    </dependencies>
</project>
```

### Key Changes Summary:
1. ✅ Java version: 21 → 23
2. ✅ maven-compiler-plugin: 3.8.1 → 3.13.0
3. ✅ maven-surefire-plugin: 3.2.5 → 3.5.0 (optional)
4. ✅ javax.mail → jakarta.mail (CRITICAL)
5. ✅ selenium-java: 4.10.0 → 4.25.0 (recommended)
6. ✅ Other optional updates for better compatibility

---

## Testing Strategy

### Test Pyramid

```
                    /\
                   /  \
                  / E2E \
                 /--------\
                /          \
               / Integration \
              /--------------\
             /                \
            /   Unit Tests     \
           /____________________\
```

### Testing Phases

#### 1. Unit Testing
**Scope:** Individual Java classes and methods

**Commands:**
```bash
# Run all unit tests
mvn clean test

# Run specific test class
mvn test -Dtest=ElementUtilTest

# Skip integration tests
mvn test -DskipITs
```

**Success Criteria:**
- 100% unit test pass rate
- No new test failures
- No performance regression

---

#### 2. Integration Testing
**Scope:** Cucumber scenarios, Selenium tests

**Commands:**
```bash
# Full integration test suite
mvn clean verify

# Smoke tests only
mvn clean test -Dtest=TestRunner "-Dcucumber.filter.tags=@Smoke"

# Regression tests
mvn clean test -Dtest=TestRunner "-Dcucumber.filter.tags=@Regression"

# Environment-specific
mvn clean verify -Denv=QA -Dbrowser=chrome
mvn clean verify -Denv=UAT -Dbrowser=firefox
```

**Success Criteria:**
- All Cucumber scenarios pass
- Reports generated successfully
- No browser compatibility issues

---

#### 3. Performance Testing
**Metrics to Track:**

| Metric | How to Measure | Threshold |
|--------|----------------|-----------|
| Test Execution Time | `time mvn clean verify` | ±10% |
| Memory Usage | JVM monitoring | ±15% |
| Build Time | Maven logs | ±10% |
| Browser Response Time | Selenium implicit waits | No change |

**Performance Test Script:**
```bash
#!/bin/bash
# performance-test.sh

echo "=== Java 23 Performance Test ==="
echo "Date: $(date)"
echo "Java Version: $(java -version 2>&1 | head -1)"

# Run 3 times and average
for i in {1..3}; do
    echo "Run $i:"
    time mvn clean verify -q > perf-run-$i.log 2>&1
    echo "---"
done

# Generate summary
echo "Test runs completed. Check perf-run-*.log files"
```

---

#### 4. Regression Testing
**Full Test Matrix:**

| Environment | Browser | Test Suite | Expected Duration |
|-------------|---------|------------|-------------------|
| QA | Chrome | @Smoke | 5-10 min |
| QA | Firefox | @Smoke | 5-10 min |
| QA | Edge | @Smoke | 5-10 min |
| QA | Chrome | @Regression | 30-45 min |
| UAT | Chrome | @Smoke | 5-10 min |
| UAT | All | @Regression | 45-60 min |

**Automated Regression Script:**
```bash
#!/bin/bash
# regression-suite.sh

envs=("QA" "UAT")
browsers=("chrome" "firefox" "edge")

for env in "${envs[@]}"; do
    for browser in "${browsers[@]}"; do
        echo "Testing $env with $browser..."
        mvn clean test -Denv=$env -Dbrowser=$browser \
            -Dtest=TestRunner "-Dcucumber.filter.tags=@Smoke" \
            > regression-$env-$browser.log 2>&1
        
        if [ $? -eq 0 ]; then
            echo "✅ PASS: $env-$browser"
        else
            echo "❌ FAIL: $env-$browser"
        fi
    done
done
```

---

## Risk Assessment & Rollback Plan

### Risk Matrix

| Risk | Probability | Impact | Severity | Mitigation |
|------|-------------|--------|----------|------------|
| javax.mail migration issues | Medium | Medium | 🟡 MEDIUM | Test email functionality thoroughly, have rollback ready |
| Dependency conflicts | Low | Medium | 🟡 MEDIUM | Update dependencies one at a time, test after each |
| Performance degradation | Low | High | 🟠 MEDIUM-HIGH | Baseline performance metrics, monitor closely |
| Test failures | Medium | Medium | 🟡 MEDIUM | Comprehensive testing in staging before production |
| Module system issues | Low | Low | 🟢 LOW | JVM arguments already configured |
| CI/CD pipeline failure | Low | High | 🟡 MEDIUM | Test pipeline in branch before merging |

### Overall Risk Assessment
**Migration Risk Level:** 🟢 **LOW to MEDIUM**

**Justification:**
- Only 1 critical code change required (javax.mail)
- All dependencies are Java 23 compatible
- No breaking changes in core framework
- Comprehensive testing strategy
- Clear rollback plan

---

### Rollback Plan

#### Scenario 1: Build Failures During Phase 1-2
**Trigger:** Cannot compile after Java 23 update

**Rollback Steps:**
```bash
# 1. Revert POM.xml changes
git checkout HEAD -- pom.xml

# 2. Rebuild with Java 21
mvn clean install

# 3. Verify tests pass
mvn verify
```

**Estimated Rollback Time:** < 15 minutes  
**Impact:** None (still in development branch)

---

#### Scenario 2: Test Failures During Phase 4
**Trigger:** Tests fail after migration, cannot be fixed quickly

**Rollback Steps:**
```bash
# 1. Checkout pre-migration tag
git checkout backup-java21-$(date +%Y%m%d)

# 2. Rebuild
mvn clean install

# 3. Verify
mvn verify

# 4. Push to branch (if needed)
git push origin HEAD:feature/java-23-migration -f
```

**Estimated Rollback Time:** < 30 minutes  
**Impact:** Minimal (still in feature branch)

---

#### Scenario 3: Staging Environment Issues
**Trigger:** Critical issues in staging after deployment

**Rollback Steps:**
```bash
# 1. Redeploy previous Java 21 version
cd /opt/application
./deploy.sh rollback java21

# 2. Restart services
systemctl restart test-automation-service

# 3. Verify health
curl http://staging-server/health

# 4. Run smoke tests
mvn clean test -Denv=QA "-Dcucumber.filter.tags=@Smoke"
```

**Estimated Rollback Time:** < 30 minutes  
**Impact:** Staging only, no production impact

---

#### Scenario 4: Production Issues (Blue-Green Deployment)
**Trigger:** Error rate > 5%, performance degraded > 20%, critical failures

**Rollback Steps:**
```bash
# 1. Route all traffic back to Green (Java 21) environment
aws elb configure-routing --target green --weight 100

# 2. Keep Blue (Java 23) running for investigation
# DO NOT tear down yet

# 3. Monitor error rates
./monitor-errors.sh

# 4. Investigate root cause
./investigate-java23-issues.sh

# 5. If issue confirmed, tear down Blue after 24 hours
```

**Estimated Rollback Time:** < 5 minutes (instant traffic routing)  
**Impact:** Brief period of potential issues until rollback complete

---

### Rollback Decision Criteria

**Automatic Rollback Triggers:**
- Build fails and cannot be fixed in 2 hours
- > 50% test failure rate
- Critical production bug affecting users
- Performance degradation > 30%
- Security vulnerability discovered

**Manual Rollback Decision:**
- Stakeholder request
- Timeline at risk
- Resource constraints

---

## Timeline & Milestones

### Gantt Chart (4-Week Plan)

```
Week 1: Preparation
├── Day 1-2: Environment Setup & Branching
├── Day 3: POM.xml Update (compiler only)
├── Day 4: Initial Build Test
└── Day 5: Document findings

Week 2: Dependencies & Code
├── Day 1: Update Maven Plugins
├── Day 2-3: Migrate javax.mail → Jakarta Mail
├── Day 4: Update Selenium (recommended)
└── Day 5: Optional dependency updates

Week 3: Testing & Validation
├── Day 1: Unit Tests
├── Day 2-3: Integration Tests (all scenarios)
├── Day 4: Performance Baseline Comparison
└── Day 5: Cross-browser Testing

Week 4: Deployment
├── Day 1: Update CI/CD Pipeline
├── Day 2: Staging Deployment
├── Day 3-4: Production Deployment (Blue-Green)
└── Day 5: Documentation & Retrospective

Week 5+ (Optional):
└── Monitor production, decommission old environment after 2 weeks
```

### Key Milestones

| Milestone | Target Date | Deliverable | Status |
|-----------|-------------|-------------|--------|
| M1: Environment Ready | End of Week 1 | Java 23 installed, branch created | ⏸️ Pending |
| M2: Build Success | End of Week 2 | Code compiles with Java 23 | ⏸️ Pending |
| M3: All Tests Pass | End of Week 3 | 100% test pass rate | ⏸️ Pending |
| M4: Staging Deployed | Mid Week 4 | Java 23 in staging | ⏸️ Pending |
| M5: Production Deployed | End of Week 4 | Java 23 in production | ⏸️ Pending |
| M6: Stable in Production | 2 weeks post-deploy | No rollback needed | ⏸️ Pending |

---

### Critical Path Items
**These MUST be completed in order:**

1. ✅ Install Java 23 → Cannot proceed without it
2. ✅ Update maven-compiler-plugin → Build will fail
3. ✅ Migrate javax.mail → Compilation error
4. ✅ Pass all tests → Cannot deploy
5. ✅ Staging validation → Cannot go to production

**Parallel Activities (can be done simultaneously):**
- Optional dependency updates
- Documentation updates
- CI/CD pipeline preparation

---

## Troubleshooting Guide

### Common Issues & Solutions

#### Issue 1: Compilation Error - Unrecognized VM Option
**Error Message:**
```
Unrecognized VM option 'EnablePreview'
Error: Could not create the Java Virtual Machine.
```

**Cause:** Incorrect JVM argument syntax or Java version mismatch

**Solution:**
```xml
<!-- Correct syntax in POM.xml -->
<compilerArgs>
    <arg>--enable-preview</arg> <!-- Use -- not - -->
</compilerArgs>
```

Or remove if not using preview features.

---

#### Issue 2: javax.mail Classes Not Found
**Error Message:**
```
[ERROR] package javax.mail does not exist
```

**Cause:** javax.mail dependency not updated to Jakarta Mail

**Solution:**
1. Verify POM.xml has Jakarta Mail dependency
2. Update imports in Java files:
   ```java
   // Change this
   import javax.mail.*;
   
   // To this
   import jakarta.mail.*;
   ```
3. Clean and rebuild:
   ```bash
   mvn clean compile
   ```

---

#### Issue 3: Test Failures - Module Access Error
**Error Message:**
```
java.lang.reflect.InaccessibleObjectException: 
Unable to make field private accessible
```

**Cause:** Java module system restricting reflection access

**Solution:**
Add to surefire plugin configuration:
```xml
<argLine>
    --add-opens java.base/java.lang=ALL-UNNAMED
    --add-opens java.base/java.util=ALL-UNNAMED
    --add-opens java.base/java.time=ALL-UNNAMED
</argLine>
```

---

#### Issue 4: Selenium WebDriver Timeout
**Error Message:**
```
org.openqa.selenium.TimeoutException: 
Expected condition failed
```

**Cause:** May be unrelated to Java version, but check driver compatibility

**Solution:**
1. Update WebDriverManager:
   ```xml
   <dependency>
       <groupId>io.github.bonigarcia</groupId>
       <artifactId>webdrivermanager</artifactId>
       <version>5.9.2</version>
   </dependency>
   ```
2. Clear driver cache:
   ```bash
   rm -rf ~/.cache/selenium
   ```
3. Rerun test

---

#### Issue 5: Performance Degradation
**Symptom:** Tests run 20%+ slower on Java 23

**Diagnosis:**
```bash
# Run with GC logging
mvn test -Dargline="-Xlog:gc*:file=gc.log"

# Analyze GC log
cat gc.log
```

**Solutions:**
1. Tune GC settings:
   ```xml
   <argLine>
       -XX:+UseZGC
       -XX:+ZGenerational
       -Xmx2g
   </argLine>
   ```
2. Increase heap size if needed
3. Profile with VisualVM

---

#### Issue 6: CI/CD Pipeline Failure
**Error Message:**
```
Java version not found: 23
```

**Cause:** CI/CD environment doesn't have Java 23 installed

**Solution for GitHub Actions:**
```yaml
- name: Set up JDK 23
  uses: actions/setup-java@v4
  with:
    java-version: '23'
    distribution: 'temurin'
```

**Solution for Jenkins:**
1. Install Java 23 on Jenkins server
2. Update Jenkinsfile:
   ```groovy
   tools {
       jdk 'JDK-23'
   }
   ```

---

#### Issue 7: Dependency Conflict
**Error Message:**
```
[ERROR] Failed to execute goal ... dependency conflict
```

**Diagnosis:**
```bash
mvn dependency:tree > dependency-tree.txt
cat dependency-tree.txt | grep "CONFLICT"
```

**Solution:**
Use dependency management to force versions:
```xml
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>conflicting.group</groupId>
            <artifactId>conflicting-artifact</artifactId>
            <version>correct.version</version>
        </dependency>
    </dependencies>
</dependencyManagement>
```

---

## Additional Resources

### Official Documentation
- **Java 23 Release Notes:** https://jdk.java.net/23/release-notes
- **Java 23 Documentation:** https://docs.oracle.com/en/java/javase/23/
- **Migration Guide:** https://docs.oracle.com/en/java/javase/23/migrate/

### Tool-Specific Guides
- **Maven Compiler Plugin:** https://maven.apache.org/plugins/maven-compiler-plugin/
- **Maven Surefire Plugin:** https://maven.apache.org/surefire/maven-surefire-plugin/
- **Jakarta Mail:** https://eclipse-ee4j.github.io/mail/

### Framework Documentation
- **Selenium Java:** https://www.selenium.dev/documentation/
- **Cucumber Java:** https://cucumber.io/docs/cucumber/
- **TestNG:** https://testng.org/doc/documentation-main.html

### Community Support
- **Stack Overflow:** Tag questions with `java-23`, `maven`, `selenium`
- **GitHub Issues:** Check framework repositories for known issues

---

## Appendix

### A. Pre-Migration Checklist

- [ ] Current Java version documented (21)
- [ ] Baseline metrics captured
- [ ] All tests passing on Java 21
- [ ] Backup/tag created
- [ ] Team notified of migration plan
- [ ] Java 23 downloaded and ready
- [ ] Migration branch created
- [ ] POM.xml backup saved

### B. Post-Migration Checklist

- [ ] All tests passing on Java 23
- [ ] Performance within acceptable range
- [ ] Email functionality verified
- [ ] All browsers tested
- [ ] CI/CD pipeline updated
- [ ] Staging environment deployed
- [ ] Production deployment plan approved
- [ ] Documentation updated
- [ ] Team trained on any changes
- [ ] Rollback plan tested
- [ ] Monitoring in place

### C. Key Contacts

| Role | Contact | Responsibility |
|------|---------|----------------|
| Tech Lead | [Name] | Overall migration approval |
| Developer | [Name] | Code changes & testing |
| QA Engineer | [Name] | Test execution & validation |
| DevOps | [Name] | Environment & CI/CD |
| Operations | [Name] | Production deployment |

### D. Success Criteria

**Migration is considered successful when:**
1. ✅ All tests pass (100% pass rate)
2. ✅ Performance within ±10% of baseline
3. ✅ No critical bugs in production
4. ✅ Zero rollbacks required
5. ✅ Stakeholder approval obtained
6. ✅ Documentation complete
7. ✅ Team trained and comfortable with Java 23

---

## Sign-Off

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Project Manager | __________ | __________ | ______ |
| Tech Lead | __________ | __________ | ______ |
| QA Lead | __________ | __________ | ______ |
| DevOps Lead | __________ | __________ | ______ |

---

**Document Version:** 1.0  
**Last Updated:** November 17, 2025  
**Next Review Date:** December 1, 2025  
**Status:** ⏸️ READY FOR REVIEW

---

## Final Recommendations

### ✅ Proceed with Java 23 if:
- This is for testing/experimentation
- Short-term project (< 6 months)
- You plan to migrate to Java 25 in Q3 2026

### ⚠️ Reconsider Java 23 if:
- This is a production application
- You need long-term stability
- You want to minimize migration efforts

### ⭐ Consider Java 25 LTS instead:
- Released September 2025 (currently available)
- 8-year support (until 2032)
- Stable, production-ready features
- Better long-term investment

**For Java 25 migration plan, request:** "Create test plan to migrate to Java 25"

---

**End of Migration Plan**
