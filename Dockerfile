# Usar la última versión de la imagen oficial de MySQL
FROM mysql:latest

# Establecer variables de entorno para la configuración de MySQL
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_DATABASE=prueba
ENV MYSQL_USER=admin
ENV MYSQL_PASSWORD=admin

# Exponer el puerto 3306 para permitir conexiones a MySQL
EXPOSE 3306


# Comando por defecto para iniciar MySQL
CMD ["mysqld"]
