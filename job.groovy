pipelineJob('build and push container') {
    definition {
        cps {
          script('''
            pipeline {
                agent any
                environment {
                registry = "pelegov/nginx_proxy"
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
                                dir('nginx'){
                                script {
                                    sh 'nginx_conf.sh'
                                    sh 'docker commit tempnginx pelegov/nginx'
                                    docker.withRegistry('', registryCredential).push()
                                    }
                                }
                        
                            }
                        }
                    }
                    post {
                        always {
                            sh "docker rmi tempnginx -f"
                            sh "docker rm tempnginx -f"
                        }
                    }
                }
                '''.stripIndent())
                sandbox()
            }
       }
}