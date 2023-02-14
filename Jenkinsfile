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
				sh 'docker-compose -f env-config/docker-compose-env.yml down'
				sh 'docker-compose -f env-config/docker-compose-env.yml up -d'
				echo 'Environment Settings End'
			}
		}
		// stage('enviroment-nginx'){
		// 	when {
		// 		changeset "env-config/nginx/**/*"
		// 	}
		// 	steps {
		// 		echo 'Nginx Settings Start'
		// 		sh 'docker-compose -f env-config/nginx/docker-compose.yml up -d'
		// 		echo 'Nginx Settings End'
		// 	}
		// }
		stage('build-module-api') {
			when {
				anyOf {
					changeset "backend/module-core/**/*"
					changeset "backend/module-api/**/*"
				}
			}
			steps {
				echo 'Build Start "${APP_API}"'
				sh 'chmod +x backend/gradlew'
				sh '''
					backend/gradlew -p backend/${APP_API} build -x test
				'''
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
				sh '''
					backend/gradlew -p backend/${APP_CHAT} build -x test
				'''
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
				sh '''
					backend/gradlew -p backend/${APP_KURENTO} build -x test
				'''
				
				echo 'Build End "${APP_KURENTO}"'
			}
		}
		stage('build-front') {
			when {
				changeset "frontend_tem/**/*"
			}
			steps {
				echo 'Build Start Front App'
				sh 'docker build -t app-vue frontend_tem/. --no-cache'
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
				sh 'docker-compose -f backend/${APP_API}/docker-compose.yml build --no-cache'
				sh 'docker-compose -f backend/${APP_API}/docker-compose.yml up -d'
				echo 'Deploy End "${APP_API}"'
			}
		}
		stage('deploy-module-chat') {
			when {
				changeset "backend/module-chat/**/*"
			}
			steps {
				echo 'Deploy Start "${APP_CHAT}"'
				sh 'docker-compose -f backend/${APP_CHAT}/docker-compose.yml build --no-cache'
				sh 'docker-compose -f backend/${APP_CHAT}/docker-compose.yml up -d'
				echo 'Deploy End "${APP_CHAT}"'
			}
		}
		stage('deploy-module-kurento') {
			when {
				changeset "backend/module-kurento/**/*"
			}
			steps {
				echo 'Deploy Start "${APP_KURENTO}"'
				sh 'docker-compose -f backend/${APP_KURENTO}/docker-compose.yml up -d'
				echo 'Deploy End "${APP_KURENTO}"'
			}
		}
		stage('build-deploy-front') {
			when {
				changeset "frontend_tem/**/*"
			}
			steps {
				echo 'Deploy Start Front App'
				sh '''
					docker stop front-app
					docker rm front-app
					docker run -d -p 80:8083 --name front-app app-vue
				'''
				echo 'Deploy End Front App'
			}
		}
	}
}
