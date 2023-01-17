#!/bin/bash

docker-compose up -d

###check status
status=$(curl -I http://localhost:80 2> /dev/null | head -n 1 | cut -d$' ' -f2)
if [ $status -eq 200]; 
then
    echo "success"
else
    echo "fail"
fi

docker-compose down --rmi all
