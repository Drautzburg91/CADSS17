#!/bin/sh

# create rabbitmq admin and user

( sleep 5 ; \
rabbitmqctl add_user $RABBITMQ_ADMIN $RABBITMQ_ADMINPASS 2>/dev/null ; \
rabbitmqctl set_user_tags $RABBITMQ_ADMIN administrator ; \
rabbitmqctl set_permissions -p / $RABBITMQ_ADMIN ".*" ".*" ".*" ; \
echo "*** User '$RABBITMQ_ADMIN' completed. ***" ;\
echo "*** Login-Address http://'hostname':15672 ***" ; \
rabbitmqctl add_user $RABBITMQ_USER $RABBITMQ_USERPASS 2>/dev/null ; \
rabbitmqctl set_permissions -p / $RABBITMQ_USER ".*" ".*" ".*" ; \
echo "*** User '$RABBITMQ_USER' completed. ***") & 
rabbitmq-server $@
