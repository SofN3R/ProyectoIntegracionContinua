pipeline {
  agent any

  environment {
    JAVA_HOME = "${tool 'jdk21'}"
    PATH      = "${JAVA_HOME}\\bin;${env.PATH}"
    IMAGE_TAG = "${env.BRANCH_NAME ?: 'local'}-${env.BUILD_NUMBER ?: '0'}"
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Gradle Build') {
      steps {
        bat '''
          cd inventoryservice
          gradlew.bat clean build -x test
          cd ..\\billingservice
          gradlew.bat clean build -x test
        '''
      }
      post {
        always {
          archiveArtifacts artifacts: 'inventoryservice\\build\\libs\\*.jar, billingservice\\build\\libs\\*.jar', fingerprint: true
        }
      }
    }

    stage('Docker Build (local)') {
      steps {
        bat """
          docker build -t inventory-service:%IMAGE_TAG% ./inventoryservice
          docker build -t billing-service:%IMAGE_TAG%   ./billingservice
        """
      }
    }

    stage('Compose Smoke Test') {
      steps {
        bat """
          docker compose down || echo ok
          docker compose build
          docker compose up -d
          powershell -NoProfile -Command "Start-Sleep -Seconds 10"
          powershell -NoProfile -Command "Invoke-WebRequest -Uri http://localhost:8081/products -UseBasicParsing | Select -ExpandProperty StatusCode"
          powershell -NoProfile -Command "Invoke-RestMethod -Method Post -Uri http://localhost:8082/invoices -Body '{\\\"productId\\\":1,\\\"quantity\\\":1}' -ContentType 'application/json' | ConvertTo-Json -Depth 5"
        """
      }
      post {
        always { bat 'docker compose down || echo ok' }
      }
    }
  }

  post {
    success { echo "OK build #${env.BUILD_NUMBER} — imágenes locales tag=${IMAGE_TAG}" }
    failure { echo 'Build FAILED' }
  }
}