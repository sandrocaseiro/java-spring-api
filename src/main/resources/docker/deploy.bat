cd ../../../../

CALL ./mvnw clean install -P docker

FOR %%F IN (.\target\*.war) DO COPY %%F .\src\main\resources\docker\api.war

cd src\main\resources\docker

docker stop wildfly-fd

docker rm -f wildfly-fd

docker build -t wildfly-fd .

del api.war

docker run -d -p 33200:8080 -p 33201:9990 --name wildfly-fd --link oracle:oracle wildfly-fd
