pipeline 
{
	agent any
	tools {			 
		gradle 'gradle 6.8'
	}
	environment {
		PROJECT = 'agora'
		APP_API = 'module-api'
		APP_CHAT = 'module-chat'
		APP_KURENTO = 'module-kurento'
	}
	stages {
		stage('environment') {
			when {
				changeset "env-config/**/*"
			} 
			steps {
				echo 'Environment Settings Start'
				sh 'docker-compose -f env-config/docker-compose-env.yml up -d'
				echo 'Environment Settings End'
			}
		}
		stage('build-module-api') {
			when {
				branch 'main'
				anyOf {
					changeset "backend/module-core/**/*"
					changeset "backend/module-api/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_API}"'
				sh 'chmod +x backend/gradlew'
				sh 'backend/gradlew ${APP_API}:build -x test'
				echo 'Build End "${APP_API}"'
			}
		}
		stage('build-module-chat') {
			when {
				changeset "backend/module-chat/**/*"
			}
			steps {
				echo 'Build Start "${APP_CHAT}"'
				sh 'chmod +x backend/gradlew'
				sh 'backend/gradlew ${APP_CHAT}:build -x test'
				echo 'Build End "${APP_CHAT}"'
			}
		}
		stage('build-module-kurento') {
			when {
				changeset "backend/module-kurento/**/*"
			}
			steps {
				echo 'Build Start "${APP_KURENTO}"'
				sh 'chmod +x backend/gradlew'
				sh 'backend/gradlew ${APP_KURENTO}:build -x test'
				echo 'Build End "${APP_KURENTO}"'
			}
		}
		stage('build-front') {
			when {
				changeset "frontend_tem/**/*"
			}
			steps {
				echo 'Build Start Front App'
				sh 'docker build -t app-vue frontend_tem/.'
				echo 'Build End Front App'
			}
		}
		stage('deploy-module-api') {
			when {
				anyOf {
					changeset "backend/module-core/**/*"
					changeset "backend/module-api/**/*"
				}
			}
			steps {
				echo 'Deploy Start "${APP_API}"'
				sh 'docker-compose -f backend/module-core/docker-compose.yml up -d'
				echo 'Deploy End "${APP_API}"'
			}
		}
		stage('deploy-module-chat') {
			when {
				changeset "backend/module-chat/**/*"
			}
			steps {
				echo 'Deploy Start "${APP_CHAT}"'
				sh 'docker-compose -f backend/module-chat/docker-compose.yml up -d'
				echo 'Deploy End "${APP_CHAT}"'
			}
		}
		stage('deploy-module-kurento') {
			when {
				changeset "backend/module-kurento/**/*"
			}
			steps {
				echo 'Deploy Start "${APP_KURENTO}"'
				sh 'docker-compose -f backend/module-kurento/docker-compose.yml up -d'
				echo 'Deploy End "${APP_KURENTO}"'
			}
		}
		stage('build-deploy-front') {
			when {
				changeset "frontend_tem/**/*"
			}
			steps {
				echo 'Deploy Start Front App'
				sh 'docker run -d -p 80:8083 --name front-app app-vue'
				echo 'Deploy End Front App'
			}
		}
	}
}
