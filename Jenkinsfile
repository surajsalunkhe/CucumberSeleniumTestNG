pipeline {
    agent any
	environment {
        JAVA_HOME = 'C:\\Program Files\\Java\\jdk-21' // Set Java path if necessary
        MAVEN_HOME = 'C:\\Users\\suraj_shivajisalunkh\\Downloads\\Softwares\\apache-maven-3.8.8' // Set Maven path if necessary
        PATH = "${env.JAVA_HOME}/bin:${env.MAVEN_HOME}/bin:${env.PATH}"
    }

    stages {
        stage('Checkout Code') {
            steps {
                // Cloning the repo
                git branch: 'develop', url: 'https://git.epam.com/suraj_shivajisalunkhe/testautomationproject'
            }
        }

        stage('Build') {
            steps {
                // Clean and compile the project
                sh 'mvn clean compile'
            }
        }

        stage('Run Tests') {
            steps {
                // Execute tests and generate reports
                sh 'mvn test'
            }
            post {
                always {
                    // Publish test results
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Publish Report') {
            steps {
                // Generate HTML reports if configured
                publishHTML([
                    reportName: 'Test Report',
                    reportDir: 'target/surefire-reports',
                    reportFiles: 'index.html', // Adjust to your report file
                    keepAll: true
                ])
            }
        }
    }

    post {
        always {
            // Archive test results
            archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true

            // Send email notifications based on build status
            script {
                if (currentBuild.result == 'SUCCESS') {
                    emailext(
                        subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                        body: "The build succeeded. Check the report at ${env.BUILD_URL}",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                    )
                } else {
                    emailext(
                        subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                        body: "The build failed. Check the details at ${env.BUILD_URL}",
                        recipientProviders: [[$class: 'DevelopersRecipientProvider']]
                    )
                }
            }
        }
    }
}