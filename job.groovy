pipelineJob('main') {
    definition {
        cps {
            script(readFileFromWorkspace('flask/flask.jenkinsfile'))
            sandbox()
        }
    }
}


job('Nginx configuration') {
    scm {
        github('amir-landau/jenkins-task', 'main')
    }
    steps {
        shell(readFileFromWorkspace('nginx/nginx_conf.sh'))
        
        dockerBuildAndPublish {
            repositoryName('pelegov/nginx_proxy')
            registryCredentials('dockerhub')
            buildContext('./nginx')
            forceTag(false)
        }  
	}
}
