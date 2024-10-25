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
            // Publish TestNG results
            echo "Publishing Extent Report..."
            publishHTML([
                reportName: 'Extent Report',
                reportDir: 'test-output/PdfReport', // Adjust directory if necessary
                reportFiles: 'TestExecutionResult.pdf', // Extent Report
                keepAll: true
            ])
    }

}