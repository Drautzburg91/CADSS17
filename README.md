# CADSS17
#CEP-Engine

Die Application kann wie jede normale ausführbare JAR Datei gestartet werden. Das bedeutet entweder über Java -JAR oder 
je nach Systemeinstellung über einen doppel klick

WICHTIG vor dem Ausführen der Anwendung müssen eingie Umgebungsvariablen gesetzt werden. Diese sind: 
MOM_HOST = Die Adresse der MoM. Diese muss ohne tcp:// angegeben werden. Beispiel: ec2-34-210-210-13.us-west-2.compute.amazonaws.com
MOM_USER = Username. Muss immer mit dem entsprechenden Tenant angegeben werden. Beispiel: weatherTenantOne:cadCEP
MOM_PW = Passwort. Muss in klartext angegeben werden. 

Ohne diese Variablen kann die CEP nicht richtig funktionieren. 

Beim Einbinden des Projekts in eine Entwicklungsumgebung muss darauf geachtet werden, dass das Projekt als Mavenprojekt erstellt wurde und 
dementsprechend Strukturiert ist.


