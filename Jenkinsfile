node{
    stage('Checkout'){
        git url:'https://git.epam.com/suraj_shivajisalunkhe/testautomationproject', branch:'main'
    }
    stage('Build'){
          echo "Building the project..."
          bat "mvn clean compile"
    }
    stage('Run Tests'){
         echo "Running tests..."
         bat "mvn test"
    }
    stage('Publish Test Results') {
            // Publish Extent PDF results
            echo "Publishing Extent Report..."
            publishHTML([
                reportName: 'Extent PDF Report',
                reportDir: 'test-output/PdfReport', // Adjust directory if necessary
                reportFiles: 'TestExecutionResult.pdf', // Extent Report
                keepAll: true
            ])
    }
    stage('Post Actions') {
        // Archive the generated test artifacts
        //echo "Archiving build artifacts..."
        //archiveArtifacts artifacts: 'target/SeleniumCucumberTestNg-1.0-SNAPSHOT.jar', allowEmptyArchive: true

        // Send email notification based on the build result
        script {
            def recipients = 'suraj_shivajisalunkhe@epam.com,suraj@technosnoop.com'  // Add your recipient email addresses separated by commas

            if (currentBuild.result == 'SUCCESS') {
                emailext(
                    to: recipients,
                    subject: "SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                    body: "The build was successful. Check the report at ${env.BUILD_URL}"
                )
            } else {
                emailext(
                    to: recipients,
                    subject: "FAILURE: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
                    body: "The build failed. Check the details at ${env.BUILD_URL}"
                )
            }
        }
    }

}