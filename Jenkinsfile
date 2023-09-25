pipeline {
    agent any
    tools{
        maven 'Maven-3.9.3'
    }
    environment {
    DOCKER_REGISTRY = "https://index.docker.io/v1/"
    DOCKER_IMAGE_NAME = "dayalathakodagi/devops-integration:latest"
    DOCKER_CREDENTIALS = credentials('DOCKER_HUB_CREDENTIALS')
  }

stages{
    stage('Build Maven'){
        steps{
            checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/dmnglor/cocd4']])
            //bat 'mvn clean install'
            bat 'mvnw clean install'
            bat 'mvnw test'
        }
    }
    stage('Docker Image to DockerHub'){
        steps{
            script{
             withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'DOCKER_HUB_CREDENTIALS_PSW', usernameVariable: 'DOCKER_HUB_CREDENTIALS_USR')]) {
                // Build a Docker image for your Spring Boot application
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