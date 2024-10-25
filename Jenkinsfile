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
                git branch: 'main', url: 'https://git.epam.com/suraj_shivajisalunkhe/testautomationproject'
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
    }
}