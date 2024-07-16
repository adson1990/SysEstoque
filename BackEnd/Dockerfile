FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
ADD ./target/SysEstoque-0.0.1-SNAPSHOT.jar sysestoque.jar
ENTRYPOINT [ "java","-jar","/sysestoque.jar" ]