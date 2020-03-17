FROM jboss/wildfly:9.0.2.Final

EXPOSE 8080 9990

COPY api.war /opt/jboss/wildfly/standalone/deployments/api.war

#RUN /opt/jboss/wildfly/bin/add-user.sh admin Admin#70365 --silent

RUN /opt/jboss/wildfly/bin/add-user.sh admin 1234 --silent

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
