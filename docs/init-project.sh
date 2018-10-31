#!/bin/sh

# Variables personnelles à renseigner
install_dir=~/leekwars
farmer_name=MON_NOM

lwutils_dir=$(dirname $(dirname $0)/../leekwars-utils)

# Creation de l'arborescence
mkdir --parents ${install_dir}
cd ${install_dir}
mkdir leekwars-${farmer_name}
cd leekwars-${farmer_name}
mkdir src
mkdir src/main
mkdir src/main/java
mkdir src/main/resources

# Creation du pom.xml
{
  echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
  echo "<project xmlns=\"http://maven.apache.org/POM/4.0.0\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
  echo "  xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd\">"
  echo "	<modelVersion>4.0.0</modelVersion>"
  echo "	<groupId>com.leekwars</groupId>"
  echo "	<artifactId>leekwars-${farmer_name}</artifactId>"
  echo "	<packaging>jar</packaging>"
  echo "	<version>1.0.0</version>"
  echo "	<name>leekwars-${farmer_name}</name>"
  echo ""
  echo "	<dependencies>"
  echo "		<dependency>"
  echo "			<groupId>log4j</groupId>"
  echo "			<artifactId>log4j</artifactId>"
  echo "			<version>1.2.17</version>"
  echo "		</dependency>"
  echo "		<dependency>"
  echo "			<groupId>com.leekwars</groupId>"
  echo " 			<artifactId>leekwars-utils</artifactId>"
  echo "			<version>1.4-SNAPSHOT</version>"
  echo "		</dependency>"
  echo "	</dependencies>"
  echo "	</build>"
  echo "</project>"
  echo ""
} > pom.xml

# Creation config log4j
{
  echo "#-------------------------------------------------------------"
  echo "# Paramétrage de Log4J"
  echo "#-------------------------------------------------------------"
  echo "# Set root logger level to DEBUG and its only appender to STDOUT."
  echo "log4j.rootLogger=WARN, STDOUT"
  echo "log4j.logger.com.leekwars=DEBUG, STDOUT"
  echo "log4j.additivity.com.leekwars=false"
  echo "log4j.logger.com.google=WARN, STDOUT"
  echo "log4j.additivity.com.google=false"
  echo "log4j.logger.REPORT=INFO, REPORT"
  echo "log4j.additivity.REPORT=false"
  echo "log4j.logger.JSON_TRACE=INFO, TRACE_API"
  echo "log4j.additivity.JSON_TRACE=false"
  echo ""
  echo "# STDOUT is set to be a ConsoleAppender."
  echo "log4j.appender.STDOUT=org.apache.log4j.ConsoleAppender"
  echo "log4j.appender.STDOUT.layout=org.apache.log4j.PatternLayout"
  echo "log4j.appender.STDOUT.layout.ConversionPattern=%-5p|%m%n"
  echo "log4j.appender.STDOUT.Threshold=INFO"
  echo "# un appender pour des rapports textes"
  echo "log4j.appender.REPORT=org.apache.log4j.FileAppender"
  echo "log4j.appender.REPORT.File=target/logs/REPORT.log"
  echo "log4j.appender.REPORT.layout=org.apache.log4j.PatternLayout"
  echo "log4j.appender.REPORT.layout.ConversionPattern=%m%n"
  echo "log4j.appender.REPORT.Threshold=OFF"
  echo "# un appender pour des traces des flux JSON recus"
  echo "log4j.appender.TRACE_API=org.apache.log4j.FileAppender"
  echo "log4j.appender.TRACE_API.File=target/logs/TRACE_API.log"
  echo "log4j.appender.TRACE_API.layout=org.apache.log4j.PatternLayout"
  echo "log4j.appender.TRACE_API.layout.ConversionPattern=%m%n"
  echo "log4j.appender.TRACE_API.Threshold=OFF"
  echo ""
} > src/main/resources/log4j.properties

# Creation config fast garden
{
  echo "#-------------------------------------------------------------"
  echo "# Paramétrage du FastGarden"
  echo "#-------------------------------------------------------------"
  echo "# Ce paramètre permet de limiter le nombre de combats maximum par entité"
  echo "fastGarden.maxStartFights=10"
  echo "# Nombre de tentatives maximales pour récupérer le résultat d'un combat"
  echo "fastGarden.maxRetryForFightResult=10"
  echo "# Nombre de secondes à attendre entre 2 tentatives de récupération du résultat d'un combat"
  echo "fastGarden.waitTimeBeforeRetry=3"
  echo "# Nombre de secondes à attendre pour laisser les combats se terminer"
  echo "fastGarden.waitTimeToGetResults=20"
  echo "# Nombre de secondes à attendre entre 2 combats"
  echo "fastGarden.sleepTimeBetweenFights=2"
  echo "# Nombre d'agressions maximales d'un seul "
  echo "fastGarden.maxFarmerAttacks=2"
  echo "# Seuil d'acception en pourcentage d'un talent pour déterminer si l'on peut combattre l'adversaire"
  echo "fastGarden.talentDiffAcceptance=20"
  echo "# Pour eviter les boucles infinies, nombres d'erreurs maximum tolérées (par entité)"
  echo "fastGarden.maxStartFightErrors=10"
  echo "# Stratégie de répartition des combats entre les différentes entités"
  echo "fastGarden.fightingDistributionStrategy=BALANCED_PARAM_LIMITED"
  echo ""
} > src/main/resources/fastgarden.properties

# Creation du template html pour le fast garden
cp ${lwutils_dir}/src/main/resources/report_template.html src/main/resources/report_template.html
# Récupération du truststore
cp ${lwutils_dir}/src/main/resources/lw.jks src/main/resources/lw.jks
