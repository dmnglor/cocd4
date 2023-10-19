pipeline{
    agent any
    tools{
        maven 'Maven-3.9.3'
    }
    environment {   
        DOCKER_REGISTRY = "https://index.docker.io/v1/"
        DOCKER_IMAGE_NAME = "dayalathakodagi/devops-integration:latest"
       	CHATGPT_API_TOKEN_SERVICE_URL = 'http://localhost:8092'
 
    }

    stages{
        stage('Build Maven'){
            steps{
                script{
                  try{
                      checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/dmnglor/cocd4']])
                      bat 'mvn clean install'
              }catch(Exception buildError){
                   currentBuild.result='FAILURE'
                   def prompt = "I encountered a build issue: ${buildError}"
                   def serviceUrl = CHATGPT_API_TOKEN_SERVICE_URL
                   // Retrieve the bearer token from Jenkins credentials
                    def bearerTokenCredentialId = 'YourBearerTokenCredentialID' // Replace with your credential ID
                    def bearerTokenCredential = credentials(bearerTokenCredentialId)
                    def bearerToken = bearerTokenCredential ? bearerTokenCredential.toString() : ''
                    
                    def headers = [
                        Authorization: "Bearer $bearerToken"
                       ]

                    def response = httpRequest(
                        contentType: 'APPLICATION_JSON',
                        httpMode: 'POST',
                        requestBody: "{\"inputText\": \"${prompt}\"}",
                        headers: headers,
                        url: serviceUrl
                    )
                        echo prompt
                        // Print the ChatGPT response
                        echo "ChatGPT suggests: ${response.getContent()}"
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

                        // Push the Docker image to your container registry
                        bat "docker push ${DOCKER_IMAGE_NAME}"
                }
            }
        }
        }
    }
		post {
        success {
            // Perform actions when the pipeline succeeds
            echo 'Pipeline succeeded!'
        }
        failure {
            // Perform actions when the pipeline fails
            echo 'Pipeline failed!'
        }
    }
}
