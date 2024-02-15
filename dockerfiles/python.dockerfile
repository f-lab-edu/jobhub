#FROM python:3.11.8-alpine
## update APK repositories
#RUN echo "https://dl-4.alpinelinux.org/alpine/v3.10/main" >> /etc/apk/repositories && \
#    echo "https://dl-4.alpinelinux.org/alpine/v3.10/community" >> /etc/apk/repositories \
## install chromium
#RUN apk update
#RUN apk add chromium chromium-chromedriver
## upgrade pip
#RUN pip install --upgrade pip
## install requests
#RUN pip install requests
## install selenium
#RUN pip install selenium
#WORKDIR /app
#COPY ./crawling/src ./src
#CMD ["python3", "src/crawl_job_website.py"]
#
#
