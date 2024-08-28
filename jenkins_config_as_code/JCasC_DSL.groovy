createPipelineTriggerJob()
def createPipelineTriggerJob() {
    pipelineJob('JCasC-reload-upload') {
        definition {
            cpsScm {
                scm {
                    git {
                        branch('master')
                        remote {
                            credentials('lciadm100_private_key')
                            url("${GERRIT_MIRROR}/OSS/com.ericsson.oss.ci/eric-oss-central-iac")
                        }
                        extensions {
                            cleanBeforeCheckout()
                        }
                    }
                }
                scriptPath("jenkins_config_as_code/JCasC_upload_reload_pipeline.groovy")
            }
        }
    }
}
