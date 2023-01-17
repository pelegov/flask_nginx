tee >> Dockerfile <<EOF
RUN echo 'server { \n \\
    listen 80; \n \\
    location / { \n \\
        proxy_pass http://app:5000; \n \\
        proxy_set_header Host "localhost"; \n \\
    } \n \\
}' > /etc/nginx/conf.d/default.conf
EOF