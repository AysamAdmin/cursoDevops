pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/usuario/repo-dockerfile.git' // Reemplaza con tu URL de GitHub
        DOCKER_IMAGE = 'mi_mysql:latest'
        CONTAINER_NAME = 'mi_contenedor_mysql'
        DB_NAME = 'mi_base_de_datos'
        DB_USER = 'mi_usuario'
        DB_PASSWORD = 'mi_contraseña'
    }

    stages {
        stage('Clonar Repositorio') {
            steps {
                // Clonar el repositorio de GitHub
                git url: "${GIT_REPO}"
            }
        }
        stage('Construir Imagen Docker') {
            steps {
                script {
                    // Construir la imagen Docker
                    sh 'docker build -t ${DOCKER_IMAGE} .'
                }
            }
        }
        stage('Instanciar Contenedor MySQL') {
            steps {
                script {
                    // Instanciar el contenedor MySQL
                    sh 'docker run -d -p 3306:3306 --name ${CONTAINER_NAME} -e MYSQL_ROOT_PASSWORD=${DB_PASSWORD} -e MYSQL_DATABASE=${DB_NAME} -e MYSQL_USER=${DB_USER} -e MYSQL_PASSWORD=${DB_PASSWORD} ${DOCKER_IMAGE}'
                    // Esperar a que MySQL esté listo
                    sh 'sleep 30'
                }
            }
        }
        stage('Verificar Acceso a la Base de Datos') {
            steps {
                script {
                    // Verificar el acceso a la base de datos
                    sh 'docker exec -i ${CONTAINER_NAME} mysql -u ${DB_USER} -p${DB_PASSWORD} -e "SHOW DATABASES;"'
                }
            }
        }
    }

    post {
        always {
            // Eliminar el contenedor y la imagen
            script {
                sh 'docker rm -f ${CONTAINER_NAME}'
                sh 'docker rmi ${DOCKER_IMAGE}'
            }
        }
    }
}
