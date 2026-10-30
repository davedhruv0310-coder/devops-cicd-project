pipeline {
    agent any

    environment {
        APP_NAME       = "devops-demo"
        APP_IMAGE      = "devops-demo:latest"
        APP_PORT_HOST  = "8081"
        APP_PORT_CONT  = "8080"
        HEALTH_URL     = "http://localhost:8081/api/status"
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== Stage 1: Checkout Source Code ==='
                checkout scm
                script {
                    if (isUnix()) {
                        sh '''
                            echo "Latest commit details:"
                            git log -1 --stat
                        '''
                    } else {
                        bat '''
                            echo Latest commit details:
                            git log -1 --stat
                        '''
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo '=== Stage 2: Gradle Build ==='
                script {
                    if (isUnix()) {
                        sh 'chmod +x gradlew'
                        sh './gradlew clean build -x test'
                    } else {
                        bat 'gradlew.bat clean build -x test'
                    }
                }
            }
        }

        stage('Testing') {
            steps {
                echo '=== Stage 3: JUnit Automated Testing ==='
                script {
                    if (isUnix()) {
                        sh './gradlew test'
                    } else {
                        bat 'gradlew.bat test'
                    }
                }
            }
        }

        stage('Docker Build') {
            steps {
                echo '=== Stage 4: Docker Image Build ==='
                script {
                    if (isUnix()) {
                        sh "docker build -t ${APP_IMAGE} ."
                    } else {
                        bat "docker build -t ${APP_IMAGE} ."
                    }
                }
            }
        }

        stage('Deployment') {
            steps {
                echo '=== Stage 5: Local Docker Container Deployment ==='
                script {
                    if (isUnix()) {
                        sh """
                            docker stop ${APP_NAME} || true
                            docker rm ${APP_NAME} || true
                            docker run -d -p ${APP_PORT_HOST}:${APP_PORT_CONT} --name ${APP_NAME} ${APP_IMAGE}
                        """
                    } else {
                        bat """
                            docker stop ${APP_NAME} 2>nul || ver >nul
                            docker rm ${APP_NAME} 2>nul || ver >nul
                            docker run -d -p ${APP_PORT_HOST}:${APP_PORT_CONT} --name ${APP_NAME} ${APP_IMAGE}
                        """
                    }
                }
            }
        }

        stage('Health Check') {
            steps {
                echo '=== Stage 6: Health Check Verification ==='
                script {
                    echo 'Waiting 10 seconds for Spring Boot container to initialize...'
                    sleep(time: 10, unit: 'SECONDS')
                    if (isUnix()) {
                        sh """
                            echo "Querying Health Endpoint: ${HEALTH_URL}"
                            curl -s -f ${HEALTH_URL} || exit 1
                            echo ""
                            echo "Health Check Passed!"
                        """
                    } else {
                        bat """
                            echo Querying Health Endpoint: ${HEALTH_URL}
                            curl.exe -s -f ${HEALTH_URL} || exit 1
                            echo.
                            echo Health Check Passed!
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'build/test-results/test/*.xml'
        }
        success {
            echo '============================================================'
            echo ' SUCCESS: DevOps CI/CD Pipeline Completed Successfully!'
            echo " Application URL: http://localhost:${APP_PORT_HOST}/"
            echo " Status Endpoint: ${HEALTH_URL}"
            echo '============================================================'
        }
        failure {
            echo '============================================================'
            echo ' FAILURE: Pipeline failed. Deployment stopped.'
            echo ' Inspect stage logs and JUnit test results.'
            echo '============================================================'
        }
    }
}
