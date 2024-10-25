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
}