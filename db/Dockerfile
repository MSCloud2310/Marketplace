FROM mariadb:latest
ENV MYSQL_ROOT_PASSWORD="root" \
  MYSQL_DATABASE="auth"
EXPOSE 3306
HEALTHCHECK --interval=30s --timeout=5s \
  CMD mysqladmin ping -h localhost -u root -proot || exit 1

