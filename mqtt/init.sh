#!/bin/sh
( sleep 5 ; \
rabbitmqctl add_user cadadmin $2a$10$ef8SbMlqauOGmamiJ2JW7u2AnFwTI2D/XqFWzmR1EA0QtA/hRMGqC 2>/dev/null ; \
rabbitmqctl set_user_tags cadadmin administrator ; \
rabbitmqctl add_user cadAndroid $2a$10$TbID6zd4BiB543ROlzlKT.z03ljvlBvQcCs.OoMIlCYbu6CmPeWYO 2>/dev/null ; \
rabbitmqctl add_user cadCEP $2a$10$yRRb1ZiMwwKlcZkQmSmzUO0nIUbhqiDw3mbJeVQyWqRNfegG/fs9u 2>/dev/null ; \
rabbitmqctl add_user caduser $2a$10$D38xZxRHKPrbYRlxzQyMz.efNxQRHTBKVP1QIA4cH8oDwVdU343wi 2>/dev/null ; \
rabbitmqctl add_user cadWeatherApi $2a$10$WbAn9NdteCVuhtyw5cz4puvNHwvhhDZbvIbMEzlJ36S3VcU036MU. 2>/dev/null ; \
rabbitmqctl add_user cadWebApp $2a$10$SmFY4V2A1ragULN.3FlXIeOuGRBca/KhigttI0Mw85Sq.sPj8DH6a 2>/dev/null ; \
rabbitmqctl add_user testuser1 $2a$10$LDFj6b0Y33jn4ivF8cdK1OfWw4o28vyE7o0iazg5QY.YKI2xgxnDy 2>/dev/null ; \
rabbitmqctl add_user testuser2 $2a$10$LuIholq77ypR6Jo1PwoaHOtjWrpLDI69/MH2JCsOmIv2kCawzRVh. 2>/dev/null ; \
rabbitmqctl set_permissions -p / cadadmin ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne cadadmin ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p / cadAndroid ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne cadAndroid ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p / cadCEP ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne cadCEP ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne caduser ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p / cadWeatherApi ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne cadWeatherApi ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p / cadWebApp ".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne cadWebApp ".*" ".*" ".*" ; \
) &
rabbitmq-server $@