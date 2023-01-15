pipeline {
    agent any
    environment {
    registry = "pelegov/flask"
    registryCredential = "dockerhub"
    dockerImage = ''
    }
    stages {
        stage('git_connect') {
            steps {
                script {
                    properties([pipelineTriggers([pollSCM('H/30 * * * *')])])
                }
                git branch: 'main', url: 'https://github.com/pelegov/flask_nginx.git'
            }
        }
        stage('run build') {
            steps {
                script {
                     dir('flask'){
                        git branch: 'main', url: 'https://github.com/pelegov/flask_nginx.git'
                     }
                     dockerImage = docker.build registry + ":$BUILD_NUMBER"
                     docker.withRegistry('', registryCredential) {
                     dockerImage.push()
                     sh 'docker ps'
                    }
                }
        
            }
        }
    }
    // post {
    //     always {
    //         sh "docker rmi $registry:$BUILD_NUMBER"
    //     }
    // }
}