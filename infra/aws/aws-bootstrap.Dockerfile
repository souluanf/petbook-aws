FROM amazon/aws-cli

USER root

RUN yum install -y bash curl jq && yum clean all

COPY aws-bootstrap.sh /aws-bootstrap.sh
RUN chmod +x /aws-bootstrap.sh

ENTRYPOINT ["/bin/bash", "/aws-bootstrap.sh"]