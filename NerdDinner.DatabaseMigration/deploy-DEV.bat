@echo off
set /p pw="Enter Password: "
"C:\Program Files\JetBrains\IntelliJ IDEA 2017.1.6\plugins\maven\lib\maven3\bin\mvn.cmd" -s settings.xml -Dflyway.configFile=flyway-dev.conf -Dflyway.password=%pw% flyway:migrate