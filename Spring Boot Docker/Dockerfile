#FROM openjdk:8-jdk-alpine
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

        ##gradle command:

        ##1)create an executable JAR -> ./gradlew build && java -jar build/libs/gs-spring-boot-docker-0.1.0.jar

        ##docker commands:

        ##2)build image -> docker build --build-arg JAR_FILE=build/libs/*.jar -t springio/gs-spring-boot-docker .
        ##Of course, once you have chosen a build system, you don’t need the ARG - you can just hard code the jar location :
#FROM openjdk:8-jdk-alpine

#VOLUME /tmp
        ##Volumes are stored in a part of the host filesystem which is managed by Docker .
        ##Non-Docker processes should not modify this part of the filesystem.
        ##Volumes are the best way to persist data in Docker.

        ##display volumes -> docker volume ls
        ##create volume -> docker volume create
        ##create volume and set name -> docker volume create --name <volume name>
        ##display full information volume -> docker volume inspect <volume name>
        ##remove unused volumes -> docker volume prune <volume name>.

#COPY build/libs/*.jar app.jar

#ENTRYPOINT ["java","-jar","/app.jar"]

        ##build image -> docker build -t springio/gs-spring-boot-docker .

        ##3)display images -> docker images

        ##4)remove image-> docker image rm <image ID>

        ##5)run container -> docker run -p 8080:8080 -t springio/gs-spring-boot-docker
        ## set name to docker -> docker run --name <container name> -p 8080:8080 -t springio/gs-spring-boot-docker

        ##6)display container is running-> docker ps

        ##7)display container is running and stop-> docker ps -a

        ##8)stop container -> docker stop <container ID>

        ##9)kill container -> docker rm <container ID>
########################################################
#FROM openjdk:8-jdk-alpine
#VOLUME /tmp
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} app.jar
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
        #you want to have the option to add java command lline options at runtime :
        #docker run -p 9000:9000 springio/gs-spring-boot-docker5 --server.port=9000
        #docker run --name third -p 8080:8080 -t springio/gs-spring-boot-docker5 -e "JAVA_OPTS=-Ddebug -Xmx128m"
########################################################

#FROM openjdk:8-jdk-alpine
        ##Running applications with user privileges helps to mitigate some risks.
        ##So, an important improvement to the Dockerfile is to run the app as a non-root user

#RUN addgroup -S spring && adduser -S spring -G spring

#USER spring:spring

#ARG DEPENDENCY=target/dependency
#COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY ${DEPENDENCY}/META-INF /app/META-INF
#COPY ${DEPENDENCY}/BOOT-INF/classes /app
        ## to take advantage of the clean separation between dependencies and application resources
        ## in a Spring Boot fat jar file, we will use a slightly different implementation of the Dockerfile.
        ## This Dockerfile has a DEPENDENCY parameter pointing to a directory where we have unpacked the fat jar.
        ## If we get that right, it already contains a BOOT-INF/lib directory with the dependency jars in it,
        ## and a BOOT-INF/classes directory with the application classes in it.

        #ENTRYPOINT ["java","-cp","app:app/lib/*","io.spring.springbootdocker.SpringBootDockerApplication"]

        ##commands:

        ##make directory and unpacked the fat jar -> mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

        ##build image -> docker build --build-arg DEPENDENCY=build/dependency -t springio/gs-spring-boot-docker .

        ##run -> docker run -p 8080:8080 -t springio/gs-spring-boot-docker
        ###########################################################