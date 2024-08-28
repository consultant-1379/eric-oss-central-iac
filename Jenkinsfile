pipeline {
    agent {
        node {
            label 'Jenkins_gazelles_podj'
        }
    }
    options {
        skipDefaultCheckout true
        timestamps()
        timeout(time: 5, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '40', artifactNumToKeepStr: '20'))
    }
    stages {
        stage('Clean') {
            steps {
                sh '''
                    echo "Cleanup workspace"
                    sudo chmod -fR 777 "${WORKSPACE}"
                    sudo rm -Rf ./*
                '''
                echo 'SCM Checkout:'
                checkout scm
            }
        }
        stage('Init') {
            steps {
                echo 'Init stage'
                sh '''
                  curl -LO https://storage.googleapis.com/spinnaker-artifacts/spin/$(curl -s https://storage.googleapis.com/spinnaker-artifacts/spin/latest)/linux/amd64/spin
                  chmod +x spin
                  sudo mv spin /usr/local/bin/spin
                '''
                echo 'Inject spin config file'
                configFileProvider([configFile(fileId: "$SPIN_CLI_CONFIG", targetLocation: "${env.WORKSPACE}/.spin/")]) {}
            }
        }
        stage('Dry-run Spinnaker pipeline') {
            when {
                expression { params.PRE_CODE_REVIEW == true }
            }
            steps {
                echo 'Dry-run stage to implement PCR job:'
                echo "Checking Spinnaker API endpoint availability and getting current Spinnaker Pipeline ${params.SPINNAKER_PIPELINE_NAME} json output.\n"
                sh "sudo /usr/local/bin/spin pipeline --application ${params.SPINNAKER_APPLICATION_NAME} get --name ${params.SPINNAKER_PIPELINE_NAME} --config ${env.WORKSPACE}/.spin/config"
                echo '\nPlan the provided Pipeline Template config'
                sh "sudo /usr/local/bin/spin pipeline-template plan --file ${env.WORKSPACE}/spinnaker_pipeline_as_code/${params.PIPELINE_TEMPLATE_FILE} --config ${env.WORKSPACE}/.spin/config"
            }
        }
        stage('Dry-run Jenkins Config as Code pipeline') {
            when {
                expression { params.PRE_CODE_REVIEW == true }
            }
            steps {
                echo 'Future JCasC Pre_Code_Review stage implementation...'
            }
        }
        stage('Apply-changes') {
            when {
                expression { params.PRE_CODE_REVIEW == false }
            }
            steps {
                echo 'Rolling out spinnaker pipeline changes...'
                sh "sudo /usr/local/bin/spin pipeline save --file spinnaker_pipeline_as_code/${params.PIPELINE_TEMPLATE_FILE} --config ${env.WORKSPACE}/.spin/config"
            }
        }
    }
}
