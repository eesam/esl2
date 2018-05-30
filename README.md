# esl2
https://wiki.freeswitch.org/wiki/Java_ESL
use esl.jar (freeswitch core)

# 安装esl.jar
mvn install:install-file -DgroupId=org.freeswitch -DartifactId=esl -Dversion=1.6.19 -Dpackaging=jar -Dfile=esl.jar

# 编译
gradle release

# 部署
build/libs/lib和build/libs/***.jar放到一个目录下，执行java -jar ***.jar
