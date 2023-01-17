#!/bin/bash

### Run rename and tag nginx image
docker run --name tempnginx -d -p 80:80 nginx
docker tag nginx:latest tempnginx:latest

### Create nginx.conf file
tee << EOF > ./nginx.conf
server {
    listen 80;
    server_name localhost;

    location / {
        proxy_pass http://flask:5000/;
        proxy_set_header Host "localhost";
    }
}

EOF

### Move script
tee << EOF > ./mv_file.sh
mv /etc/nginx/nginx.conf /etc/nginx/nginx.conf.bak
mv /etc/nginx.conf /etc/nginx/nginx.conf
exit

EOF


### Copying the new conf into the container
docker cp nginx.conf tempnginx://etc
docker cp mv_file.sh tempnginx://etc

### Executing commands in the container
docker exec -d -it tempnginx /bin/bash /etc/mv_file.sh


#### Commit the changes to New Image and Push
#docker commit tempnginx pelegov/nginx_proxy
#docker push pelegov/nginx_proxy

### cleaning the env
#docker rmi tempnginx -f
#docker rm tempnginx -f

