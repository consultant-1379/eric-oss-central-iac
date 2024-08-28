import io.jenkins.plugins.casc.ConfigurationAsCode
pipeline {
    agent { label 'Jenkins_gazelles_podj' } 
    environment {
        PIPELINE_LAST_STAGE_STATUS = 'UNKNOWN'
    }
    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'master',
                        url: 'ssh://gerrit.ericsson.se:29418/OSS/com.ericsson.oss.ci/eric-oss-central-iac'
            }
        }
        stage ('Upload Configurations') {
            steps {
                script {
                    archiveArtifacts 'jenkins_config_as_code/casc_config/jcasc_fem125.yaml'
                }
            }
        }
        stage ('Reload Configurations') {
            steps {
                script {
                    ConfigurationAsCode.get().configure()
                }
            }
        }
    }
}
