# 使用官方精简的Java 17基础镜像
FROM eclipse-temurin:17-jre-jammy

# 创建一个应用目录
WORKDIR /app

# 将构建的jar文件复制到容器中
COPY target/rouge_springboot-1.jar app.jar

# 暴露应用端口（根据你的应用实际端口修改）
EXPOSE 3000

# 设置JVM参数（根据需要进行调整）
ENV JAVA_OPTS="-Xmx512m -Xms256m"

# 启动应用
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]