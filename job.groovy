pipelineJob('main') {
    definition {
        cps {
            script(readFileFromWorkspace('../step_one/flask/flask.jenkinsfile'))
            sandbox()
        }
    }
}


job('nginx_conf') {
    scm {
        github('pelegov/flask_nginx', 'main')
    }
    steps {
        shell(readFileFromWorkspace('../step_one/nginx/nginx_conf.sh'))
        
        dockerBuildAndPublish {
            repositoryName('pelegov/nginx_proxy')
            registryCredentials('dockerhub')
            buildContext('./nginx')
            forceTag(false)
        }  
	}
}