pipeline{
    agent any
    tools{
        maven 'Maven-3.9.3'
    }
    environment {   
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        DOCKER_IMAGE_NAME = "dayalathakodagi/devops-integration:latest"
       	CHATGPT_API_TOKEN_SERVICE_URL = 'http://localhost:8092/generateResponse'
 
    }

    stages{
        stage('Build Maven'){
            steps{
               script{
                  try{
                      checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/dmnglor/cocd4']])
                      bat 'mvn clean install > buildd.log 2>&1'
                      }catch(Exception buildError){
						currentBuild.result='FAILURE'
						 def logFilePath = 'C:/ProgramData/Jenkins/.jenkins/workspace/DevOps-AI-Integration/buildd.log'
						 echo "Checking if file exists: ${logFilePath}"
                        echo logFilePath
						def logContent = readFile file: logFilePath
						echo "Error Messages in logcontent is111:${logContent}"
                        def errorMessages1 = logContent.readLines()
                        def errorMessageString = errorMessages1.join('\n')
                         def lines = errorMessageString.split('\n')
                        def errorMessages = []
                        lines.each { line ->
                            if (line.startsWith("[ERROR] ")) {
                                errorMessages.add(line)
                              }
                        }
                        //def prompt = "I encountered a build issue: C:/ProgramData/Jenkins/.jenkins/workspace/DevOps-AI-Integration/src/main/java/com/example/devops/cicddemo/CicddemoApplication.java:[14,2] reached end of file while parsing"
                         echo "Error messages are:${errorMessages}"
                   def serviceUrl = CHATGPT_API_TOKEN_SERVICE_URL
                   // Retrieve the bearer token from Jenkins credentials
                    def bearerTokenCredentialId = 'YourBearerTokenCredentialID'
                    def bearerTokenCredential = credentials(bearerTokenCredentialId)
                    def bearerToken = bearerTokenCredential ? bearerTokenCredential.toString() : ''
                    
                    def headers = [
                        Authorization: "Bearer $bearerToken"
                    ]

                    def response = httpRequest(
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: "{\"inputText\": \"I encountered a build issue: ${errorMessages}\"}",
                        headers: headers,
                        url: serviceUrl
                    )
                    echo "ChatGPT suggests: ${response.getContent()}"
                       ////chatgpt end
             }
            } 
				
            }
            
        }
        
        
        
        stage('Docker Image to Docker Hub'){
            steps{
                script{
                    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_HUB_CREDENTIALS_PSW', usernameVariable: 'DOCKER_HUB_CREDENTIALS_USR')]) {
                        
                        bat "docker build -t ${DOCKER_IMAGE_NAME} ."
                        bat "docker login -u ${DOCKER_HUB_CREDENTIALS_USR} -p ${DOCKER_HUB_CREDENTIALS_PSW} ${DOCKER_REGISTRY}"
                        bat "docker push ${DOCKER_IMAGE_NAME}"
                }
            }
        }
        }
    }
		post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
