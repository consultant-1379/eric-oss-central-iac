FROM jenkins/jenkins:2.190.1
LABEL maintainer="PDLAZELLES@pdl.internal.ericsson.com"

ARG JAVA_OPTS
ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false ${JAVA_OPTS:-}"
ENV JENKINS_HOME /var/jenkins_home

COPY plugins_initial.txt /usr/share/jenkins/ref/plugins_initial.txt

RUN xargs /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins_initial.txt

WORKDIR /usr/share/jenkins/ref/plugins
RUN wget https://eiffel.lmera.ericsson.se/nexus/repository/releases/com/ericsson/duraci/eiffel-core/70.0.4/eiffel-core-70.0.4.hpi
RUN wget https://arm1s11-eiffel004.eiffel.gic.ericsson.se:8443/nexus/content/repositories/releases/com/ericsson/cifwk/cloud/lmicloud-plugin/1.0.193/lmicloud-plugin-1.0.193.hpi