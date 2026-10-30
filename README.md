# Automated CI/CD Pipeline Using Jenkins, GitHub, Gradle, and Docker

> **A production-ready local DevOps mini project built from scratch on Windows.**

---

## 1. Project Introduction

In modern software engineering, manual application building, testing, and deployment introduce delays, human error, and inconsistent environments. This project demonstrates an **end-to-end Automated Continuous Integration and Continuous Deployment (CI/CD) Pipeline** running locally on a Windows machine.

The pipeline automates the entire lifecycle of a Java Spring Boot REST API application:
1. Pushing code to GitHub.
2. Automatically triggering a Jenkins pipeline.
3. Checking out source code and recording Git metadata.
4. Compiling and packaging the application with Gradle.
5. Running automated unit tests with JUnit 5.
6. Building a containerized Docker image.
7. Deploying the application in an isolated local Docker container.
8. Performing an automated HTTP health check to verify availability.

---

## 2. Problem Statement

Without a CI/CD pipeline:
* Developers manually compile and package artifacts, risking version discrepancies.
* Untested or faulty code gets deployed to production due to skipped testing stages.
* Environment differences between developer laptops and deployment environments cause *"it works on my machine"* bugs.
* Rollouts and rollbacks require manual container management and downtime.

**Solution:** Implement an automated pipeline that strictly halts execution if compilation or unit testing fails, ensuring that **only verified, healthy code** is packaged into a Docker image and deployed.

---

## 3. Project Objectives

* **Zero-Touch Automation:** Automatically trigger build, test, and deployment upon code push.
* **Fail-Fast Mechanism:** Immediately stop the pipeline if any JUnit test or compilation step fails, preventing deployment.
* **Deterministic Builds:** Package dependencies and build steps using the self-contained Gradle Wrapper (`gradlew`).
* **Containerized Deployment:** Package the Java runtime and application into a Docker container for isolation and portability.
* **Automated Verification:** Query the application status endpoint via cURL in the pipeline to ensure zero downtime.
* **100% Free & Local:** Run everything locally on Windows without paid cloud services, AWS, Kubernetes, SonarQube, or Maven.

---

## 4. Technology Stack

| Layer | Technology | Version / Specification | Purpose |
| :--- | :--- | :--- | :--- |
| **Language** | Java (OpenJDK) | Java 17 (LTS) | Core application programming language |
| **Framework** | Spring Boot | 3.2.5 (Spring Web) | Lightweight REST API framework |
| **Version Control** | Git | 2.x | Source code tracking and branching |
| **Repository Hosting** | GitHub | Public / Private repo | Centralized remote code repository |
| **CI/CD Automation** | Jenkins | 2.440+ (LTS) | Orchestration of build, test, and deploy stages |
| **Build Automation** | Gradle | 8.7 (Wrapper) | Compiling, testing, and packaging (No Maven) |
| **Testing** | JUnit 5 | Jupiter engine | Unit and integration testing |
| **Containerization** | Docker | Docker Desktop (Windows) | Packaging application into container image |
| **Deployment** | Docker Engine | Local container | Running container on `localhost:8081` |
| **Verification** | cURL & Browser | HTTP GET requests | Health check and response validation |

---

## 5. System Architecture

```text
+-------------------------------------------------------------+
|                      Developer Laptop                       |
|                                                             |
|  1. Developer writes Java code (Spring Boot REST API)       |
|  2. Developer commits & pushes changes to GitHub            |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                     GitHub Repository                       |
|               (repo: devops-cicd-project)                   |
|                                                             |
|  GitHub Webhook / SCM Polling notifies Jenkins of commit    |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                  Jenkins Automation Server                  |
|                    (http://localhost:8080)                  |
|                                                             |
|  +-------------------------------------------------------+  |
|  | Stage 1: Checkout (Git clone & commit info)           |  |
|  +---------------------------+---------------------------+  |
|                              v                              |
|  +-------------------------------------------------------+  |
|  | Stage 2: Gradle Build (gradlew clean build -x test)   |  |
|  +---------------------------+---------------------------+  |
|                              v                              |
|  +-------------------------------------------------------+  |
|  | Stage 3: Testing (gradlew test & JUnit reports)       |  |
|  |          * If any test fails, STOP pipeline *         |  |
|  +---------------------------+---------------------------+  |
|                              v                              |
|  +-------------------------------------------------------+  |
|  | Stage 4: Docker Build (docker build -t devops-demo)   |  |
|  +---------------------------+---------------------------+  |
|                              v                              |
|  +-------------------------------------------------------+  |
|  | Stage 5: Deployment (docker rm & docker run :8081)    |  |
|  +---------------------------+---------------------------+  |
|                              v                              |
|  +-------------------------------------------------------+  |
|  | Stage 6: Health Check (curl http://localhost:8081)    |  |
|  +-------------------------------------------------------+  |
+------------------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                     Docker Host (Local)                     |
|                                                             |
|  Container: devops-demo                                     |
|  Port Mapping: 8081 (Host) -> 8080 (Container)              |
|                                                             |
|  Endpoints:                                                 |
|    - GET http://localhost:8081/                             |
|    - GET http://localhost:8081/api/status                  |
+-------------------------------------------------------------+
```

---

## 6. Project Folder Structure

```text
devops-cicd-project/
|-- .gitignore                  # Ignores build/ and caches, keeps wrapper jar
|-- build.gradle                # Gradle build script (dependencies, tasks)
|-- Dockerfile                  # Container definition (Java 17 JRE base)
|-- Jenkinsfile                 # 6-stage Declarative CI/CD pipeline
|-- README.md                   # Comprehensive project documentation
|-- settings.gradle             # Project naming declaration
|-- gradlew                     # Gradle Wrapper script (Linux / macOS)
|-- gradlew.bat                 # Gradle Wrapper batch script (Windows)
|-- gradle/
|   `-- wrapper/
|       |-- gradle-wrapper.jar  # Gradle Wrapper execution engine
|       `-- gradle-wrapper.properties # Configures Gradle 8.7 distribution
|-- src/
    |-- main/
    |   `-- java/
    |       `-- com/
    |           `-- example/
    |               `-- devops/
    |                   |-- DevOpsApplication.java   # Spring Boot entry point
    |                   |-- controller/
    |                   |   `-- HomeController.java  # REST Controller (/ & /api/status)
    |                   `-- service/
    |                       `-- StatusService.java   # Business logic & status provider
    `-- test/
        `-- java/
            `-- com/
                `-- example/
                    `-- devops/
                        `-- StatusServiceTest.java   # JUnit 5 automated unit tests
```

---

## 7. Installation Instructions (Prerequisites)

Before configuring the pipeline, install the following tools on Windows.

### 7.1. Install Java 17 (JDK)
1. Download OpenJDK 17 or Eclipse Temurin 17 from [Adoptium](https://adoptium.net/).
2. Run the installer and check the box **"Set JAVA_HOME variable"** and **"Add to PATH"**.
3. Verify in PowerShell:
   ```powershell
   java -version
   ```
   *Expected Output:* `openjdk version "17.0.x" ...`

### 7.2. Install Git
1. Download from [git-scm.com](https://git-scm.com/download/win).
2. Install with default settings.
3. Configure your username and email:
   ```powershell
   git config --global user.name "Your Name"
   git config --global user.email "your.email@example.com"
   ```

### 7.3. Install Docker Desktop
1. Download Docker Desktop for Windows from [docker.com](https://www.docker.com/products/docker-desktop/).
2. Enable WSL 2 backend when prompted during installation.
3. Start Docker Desktop and ensure the engine status shows **"Engine running"** (green icon).
4. Verify in PowerShell:
   ```powershell
   docker --version
   docker ps
   ```

### 7.4. Install Jenkins
You can install Jenkins on Windows using either the native Windows installer or running Jenkins via Docker / Java WAR.

#### Recommended Option: Native Jenkins Service / WAR
1. Download the Jenkins Generic Java package (`jenkins.war`) or Windows MSI from [jenkins.io/download](https://www.jenkins.io/download/).
2. To run via Java WAR in PowerShell:
   ```powershell
   # Create Jenkins home folder
   mkdir C:\Jenkins
   cd C:\Jenkins
   # Download jenkins.war (or place downloaded WAR here)
   curl.exe -L -o jenkins.war https://get.jenkins.io/war-stable/latest/jenkins.war
   # Run Jenkins on port 8080 (default)
   java -jar jenkins.war --httpPort=8080
   ```
3. Open your browser at `http://localhost:8080`.
4. Copy the initial administrator password displayed in the terminal (or at `C:\Users\<User>\.jenkins\secrets\initialAdminPassword`).
5. Choose **"Install suggested plugins"** (installs Git, Pipeline, JUnit, and workspace plugins).
6. Create your Admin user and finalize setup.

---

## 8. GitHub Configuration

### 8.1. Create the GitHub Repository
1. Log into your GitHub account.
2. Click **New Repository**.
3. Repository name: `devops-cicd-project`.
4. Set visibility to **Public** (or Private if you use GitHub Personal Access Tokens).
5. Leave "Initialize with README" **unchecked** (we already have a local repo).
6. Click **Create repository**.

### 8.2. Push Local Project to GitHub
In PowerShell from the `c:\devopsproject\devops-cicd-project` directory:
```powershell
cd c:\devopsproject\devops-cicd-project

# Initialize git repository (if not already initialized)
git init -b main

# Add all project files
git add .

# Commit files
git commit -m "Initial commit: Automated CI/CD pipeline project"

# Link to your GitHub remote (replace with your actual GitHub username)
git remote add origin https://github.com/<YOUR_GITHUB_USERNAME>/devops-cicd-project.git

# Push to GitHub
git push -u origin main
```

---

## 9. Jenkins Configuration

### 9.1. Ensure Plugins are Installed
Navigate to **Manage Jenkins** > **Plugins** > **Installed plugins**, and ensure these are installed:
* **Git plugin**
* **Pipeline**
* **JUnit Plugin** (for publishing test results)

### 9.2. Create the Pipeline Job
1. On the Jenkins home screen, click **New Item**.
2. Enter Item Name: `devops-cicd-pipeline`.
3. Select **Pipeline** and click **OK**.

### 9.3. Configure the Pipeline Job
1. Under **General**, you can add a description: `Automated CI/CD Pipeline with Gradle and Docker`.
2. Under **Build Triggers**:
   * For local setups without public IP: Check **Poll SCM** and set Schedule to:
     ```text
     H/2 * * * *
     ```
     *(Jenkins will poll GitHub every 2 minutes for new commits).*
   * If using ngrok or a public IP: Check **GitHub hook trigger for GITScm polling**.
3. Under **Pipeline**:
   * **Definition**: Select `Pipeline script from SCM`.
   * **SCM**: Select `Git`.
   * **Repository URL**: `https://github.com/<YOUR_GITHUB_USERNAME>/devops-cicd-project.git`
   * **Credentials**: None (if public repository) or add GitHub Personal Access Token (if private).
   * **Branch Specifier**: `*/main`
   * **Script Path**: `Jenkinsfile`
4. Click **Save**.

### 9.4. Connect Docker with Jenkins on Windows
Jenkins needs permission to execute `docker` commands:
* If running Jenkins as a native Windows service:
  1. Ensure `docker` is available in system PATH (`C:\Program Files\Docker\Docker\resources\bin`).
  2. In Windows Services (`services.msc`), ensure the Jenkins service runs under an account with local Administrator privileges or log on as your current Windows user.
  3. Restart the Jenkins service.
* To verify, run a test pipeline step: `bat 'docker --version'`.

---

## 10. Gradle Configuration

The project uses Gradle 8.7 with the Gradle Wrapper.

### Key Gradle Tasks:
* **Clean & Build:**
  ```powershell
  .\gradlew.bat clean build
  ```
* **Run Tests Only:**
  ```powershell
  .\gradlew.bat test
  ```
* **Run Application Locally:**
  ```powershell
  .\gradlew.bat bootRun
  ```

### Build File Highlights (`build.gradle`):
* `org.springframework.boot` (3.2.5) & `io.spring.dependency-management` (1.1.4).
* Spring Web starter: `spring-boot-starter-web`.
* JUnit 5 Jupiter engine: `spring-boot-starter-test` with `useJUnitPlatform()`.
* Deterministic output artifact name: `bootJar { archiveFileName = 'app.jar' }`.

---

## 11. Docker Configuration

### Dockerfile Details:
```dockerfile
# Step 1: Lightweight Java 17 JRE Alpine base image
FROM eclipse-temurin:17-jre-alpine

# Metadata
LABEL maintainer="DevOps Team"
LABEL description="DevOps Demo Application Container"

# Workdir
WORKDIR /app

# Copy artifact generated by Gradle build
COPY build/libs/app.jar app.jar

# Expose HTTP port
EXPOSE 8080

# Run Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Docker Commands Used in Pipeline:
1. **Build Docker Image:**
   ```powershell
   docker build -t devops-demo:latest .
   ```
2. **Stop and Clean Previous Container:**
   ```powershell
   docker stop devops-demo
   docker rm devops-demo
   ```
3. **Deploy Container:**
   ```powershell
   docker run -d -p 8081:8080 --name devops-demo devops-demo:latest
   ```

---

## 12. Complete Execution Steps

### Step 1: Run and Verify Locally First
```powershell
cd c:\devopsproject\devops-cicd-project

# 1. Compile and build
.\gradlew.bat clean build

# 2. Run unit tests
.\gradlew.bat test

# 3. Build Docker container
docker build -t devops-demo:latest .

# 4. Run Docker container
docker run -d -p 8081:8080 --name devops-demo devops-demo:latest

# 5. Verify endpoints
curl.exe http://localhost:8081/
curl.exe http://localhost:8081/api/status
```

### Step 2: Trigger the Pipeline in Jenkins
1. Open Jenkins at `http://localhost:8080`.
2. Click on `devops-cicd-pipeline`.
3. Click **Build Now** to run the initial execution manually.
4. Watch the pipeline progression through Stage View:
   * **Stage 1: Checkout** -> Fetches code from GitHub.
   * **Stage 2: Build** -> Compiles Java code with Gradle.
   * **Stage 3: Testing** -> Executes JUnit 5 tests & archives XML reports.
   * **Stage 4: Docker Build** -> Creates `devops-demo:latest` Docker image.
   * **Stage 5: Deployment** -> Starts container on port 8081.
   * **Stage 6: Health Check** -> Confirms HTTP 200 and `"status": "UP"`.

---

## 13. Testing Scenarios & Demonstrations

### Scenario 1: Successful Build and Deployment
1. Make a valid change in `src/main/java/com/example/devops/service/StatusService.java`.
2. Commit and push:
   ```powershell
   git commit -am "feat: update message"
   git push origin main
   ```
3. **Result:** Jenkins automatically detects the push, runs all 6 stages, tests pass, Docker container updates, health check succeeds.

---

### Scenario 2: Build Failure (Compile Error)
1. Introduce a deliberate syntax error in `src/main/java/com/example/devops/controller/HomeController.java` (e.g., remove a semicolon or type `invalidCode;`).
2. Commit and push:
   ```powershell
   git commit -am "test: introduce compilation error"
   git push origin main
   ```
3. **Result:**
   * **Stage 2: Build FAILS** with compilation error.
   * Pipeline **immediately stops**.
   * **Stage 4 (Docker Build) and Stage 5 (Deployment) DO NOT EXECUTE**.
   * Previous running container remains untouched and safe.

---

### Scenario 3: Testing Failure (Failing Unit Test)
1. Modify `src/main/java/com/example/devops/service/StatusService.java`:
   ```java
   public String getStatus() {
       return "DOWN"; // Expected by test is "UP"
   }
   ```
2. Commit and push:
   ```powershell
   git commit -am "test: introduce failing unit test"
   git push origin main
   ```
3. **Result:**
   * **Stage 2 (Build) passes**.
   * **Stage 3 (Testing) FAILS**: JUnit assertion failure (`expected: <UP> but was: <DOWN>`).
   * Jenkins publishes the JUnit test report showing the failing test case in red.
   * Pipeline **aborts**.
   * **Docker Build and Deployment DO NOT EXECUTE**.

---

### Scenario 4: Successful Deployment Recovery
1. Revert the code back to working state:
   ```java
   public String getStatus() {
       return "UP";
   }
   ```
2. Commit and push:
   ```powershell
   git commit -am "fix: restore working code"
   git push origin main
   ```
3. **Result:**
   * All 6 stages succeed (Green).
   * New container is deployed.

---

### Scenario 5: Health Check Verification
1. Open your browser and navigate to:
   * Home Endpoint: [http://localhost:8081/](http://localhost:8081/)
   * Status Endpoint: [http://localhost:8081/api/status](http://localhost:8081/api/status)
2. Or in PowerShell:
   ```powershell
   curl.exe http://localhost:8081/api/status
   ```
3. Expected JSON response:
   ```json
   {
     "application": "DevOps Demo Application",
     "status": "UP"
   }
   ```

---

## 14. Expected Output

### API Responses:
* **GET `/`**
  ```json
  {
    "application": "DevOps Demo Application",
    "message": "Welcome to our automated CI/CD project"
  }
  ```

* **GET `/api/status`**
  ```json
  {
    "application": "DevOps Demo Application",
    "status": "UP"
  }
  ```

### Jenkins Pipeline Stage View:
```text
[Pipeline] Stage View
+--------------+-------------+---------------+----------------+----------------+----------------+
|   Checkout   |    Build    |    Testing    |  Docker Build  |   Deployment   |  Health Check  |
+--------------+-------------+---------------+----------------+----------------+----------------+
|     1s       |     8s      |      4s       |       5s       |       2s       |      11s       |
|   SUCCESS    |   SUCCESS   |    SUCCESS    |    SUCCESS     |    SUCCESS     |    SUCCESS     |
+--------------+-------------+---------------+----------------+----------------+----------------+
```

---

## 15. Conclusion

This project demonstrates a production-grade automated CI/CD workflow executed entirely locally on Windows. By combining **GitHub** for version control, **Jenkins** for pipeline orchestration, **Gradle** for self-contained compilation and testing, and **Docker** for standardized containerization and deployment, we have built a resilient, zero-cost, fail-safe DevOps pipeline.
