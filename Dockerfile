FROM nginx:alpine
COPY Gmail/ /usr/share/nginx/html/
EXPOSE 80
