#!/bin/sh
( sleep 5 ; \
rabbitmqctl add_user cadadmin $2a$10$HDvnXQKnL.mU2OWs0S5BEOdRnXmsmn7plQCjrMGPnBXRa39OPRg.y 2>/dev/null ; \
rabbitmqctl add_user cadAndroid $2a$10$EFUirckYdv8qBuNgcOfwNOw4OCvJh.jUmur.azfUo6L35UzO1T90y 2>/dev/null ; \
rabbitmqctl add_user cadCEP $2a$10$zHO/pf5MR0c9hzMCRzpYBOS/EgArrnQhWsia.Qpd24cNpslpD3fAm 2>/dev/null ; \
rabbitmqctl add_user caduser $2a$10$WXRqNnyFpanKyyUHHUSMO.wIo3audKqQ4vv7jv2jEMQJwIwAwFWIS 2>/dev/null ; \
rabbitmqctl add_user cadWeatherApi $2a$10$IgaJjhYdiUXLQGcxX1CZQuEtDsRgiu6dtqx9iizrW6fHXfD0EaAJC 2>/dev/null ; \
rabbitmqctl add_user cadWebApp $2a$10$b3fCIksXKUdpGnKVy5DZrOt0KNP9ddl48aovjrwpbM86MUnxEHeUe 2>/dev/null ; \
rabbitmqctl add_user testuser $2a$10$5Z2qb54c/HMG5eB8PL1LKOyPXDqinVNmLVKCbTb8mt7QipM4/cNHm 2>/dev/null ; \
rabbitmqctl add_user testuser1 $2a$10$i4M..O.ZKDjUa3OWLK7b0.jDoZAXkvAQda9kNrVH50T5Fi1/b2pka 2>/dev/null ; \
rabbitmqctl add_user testuser10 $2a$10$h4eAyGiAobpanaCe4fVpCei6IMZ53YpGTnz3V0/koFBoxo9jdU8A6 2>/dev/null ; \
rabbitmqctl add_user testuser11 $2a$10$H69/ZJ3ChmSpIcbprW1nV.xCVmM6rurqCEkviqUeMmYfGHBliF75C 2>/dev/null ; \
rabbitmqctl add_user testuser12 $2a$10$PhhzvWoAGXpDx5GPEpRZt.YaT/IDAk1gZQSR37EQHwq3k69yWUzu6 2>/dev/null ; \
rabbitmqctl add_user testuser13 $2a$10$Oj.9vp90yWJRxzE3MhBbouhv8CIXMvfg.7uE4G1FcpSmgdf21BZ7i 2>/dev/null ; \
rabbitmqctl add_user testuser2 $2a$10$y2L.Qu61a7H9hPfK5JQEt.JvQrD451NonALBEZsvkc4Uxc6.0y8ki 2>/dev/null ; \
rabbitmqctl add_user testuser3 $2a$10$YYf4muf5E3r70bm4uyna3uGJym05RfdihcQgv0CV7dgch9ordiHsi 2>/dev/null ; \
rabbitmqctl add_user testuser4 $2a$10$uZr6pmhtvexK2do60dvRS.yBWdSiZELNw8cuI9qApNAxyeQ0rai0i 2>/dev/null ; \
rabbitmqctl add_user testuser5 $2a$10$ftQEI4f6PGrERUD/IRaSNOES5sqTA75iYwfM3G3zcGwKRVrMw14e6 2>/dev/null ; \
rabbitmqctl add_user testuser6 $2a$10$.yOvjQLrWt9R42K06L/ZnO7Jb/IEg7bRvdJOX/m2y4Dub7Stj3aV6 2>/dev/null ; \
rabbitmqctl add_user testuser7 $2a$10$/MqHuZ3.pN/CYGVPPIgpZOkDDrIYL/ZwCjcyHlBsc6jD6pxPoIc7S 2>/dev/null ; \
rabbitmqctl add_user testuser9 $2a$10$PgVK8S/tHuR/.uvxKwer3uZXInOS07qsXhl8XzN35qEbEzE.IDGEG 2>/dev/null ; \
rabbitmqctl set_permissions -p weatherTenantOne testuser1".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne testuser10".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne testuser12".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne testuser13".*" ".*" ".*" ; \
rabbitmqctl set_permissions -p weatherTenantOne testuser9".*" ".*" ".*" ; \
) &
rabbitmq-server $@