#!/bin/bash

# Variables personnelles à renseigner
readonly install_dir=~/leekwars
readonly farmer_name=MON_NOM
readonly java_version=1.7
readonly lwutils_version=1.7

readonly lwutils_dir=$(dirname $(dirname $0)/../leekwars-utils)

# Creation de l'arborescence du projet Maven (comme le ferait un vrai archetype)
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
  echo "	<groupId>org.${farmer_name}</groupId>"
  echo "	<artifactId>leekwars-${farmer_name}</artifactId>"
  echo "	<packaging>jar</packaging>"
  echo "	<version>1.0.0</version>"
  echo "	<name>leekwars-${farmer_name}</name>"
  echo ""
  echo "	<properties>"
  echo "	  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>"
  echo "	  <slf4j.version>1.7.25</slf4j.version>"
  echo "	  <logback.version>1.2.3</logback.version>"
  echo "	  <leekwars.version>${lwutils_version}</leekwars.version>"
  echo "	</properties>"
  echo ""
  echo "	<dependencies>"
  echo "  	<dependency>"
  echo "			<groupId>org.slf4j</groupId>"
  echo "			<artifactId>slf4j-api</artifactId>"
  echo "			<version>\${slf4j.version}</version>"
  echo "		</dependency>"
  echo "		<dependency>"
  echo "		  <groupId>ch.qos.logback</groupId>"
  echo "		  <artifactId>logback-classic</artifactId>"
  echo "			<version>\${logback.version}</version>"
  echo "			<scope>runtime</scope>"
  echo "		</dependency>"
  echo "		<dependency>"
  echo "			<groupId>com.leekwars</groupId>"
  echo " 			<artifactId>leekwars-utils</artifactId>"
  echo "			<version>\${leekwars.version}</version>"
  echo "		</dependency>"
  echo "	</dependencies>"
  echo ""
  echo "	<build>"
  echo "      <pluginManagement>"
  echo "        <plugins>"
  echo "          <plugin>"
  echo "            <groupId>org.apache.maven.plugins</groupId>"
  echo "            <artifactId>maven-compiler-plugin</artifactId>"
  echo "            <configuration>"
  echo "              <source>${java_version}</source>"
  echo "              <target>${java_version}</target>"
  echo "              <encoding>UTF-8</encoding>"
  echo "             </configuration>"
  echo "          </plugin>"
  echo "        </plugins>"
  echo "      </pluginManagement>"
  echo "        <plugins>"
  echo "          <!-- copy libs -->"
  echo "          <plugin>"
  echo "            <groupId>org.apache.maven.plugins</groupId>"
  echo "           <artifactId>maven-dependency-plugin</artifactId>"
  echo "            <executions>"
  echo "              <execution>"
  echo "              <phase>package</phase>"
  echo "              <goals>"
  echo "                <goal>copy-dependencies</goal>"
  echo "              </goals>"
  echo "              <configuration>"
  echo "                 <excludeScope>test</excludeScope>"
  echo "                 <outputDirectory>\${project.build.directory}/lib</outputDirectory>"
  echo "             </configuration>"
  echo "          </execution>"
  echo "        </executions>"
  echo "      </plugin>"
  echo "    </plugins>"
  echo "  </build>"
  echo "</project>"
  echo ""
} > pom.xml

# Creation config logback.xml
{
  echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
  echo "<configuration>"
  echo "  <!-- appenders -->"
  echo "    <appender name=\"STDOUT\" class=\"ch.qos.logback.core.ConsoleAppender\">"
  echo "        <encoder>"
  echo "            <pattern>%-5level|%d{HH:mm:ss.SSS}|%logger{50}|%msg%n</pattern>"
  echo "        </encoder>"
  echo "    </appender>"
  echo "  <appender name=\"FILE\" class=\"ch.qos.logback.core.FileAppender\">"
  echo "    <file>target/logs/LW_Utils.log</file>"
  echo "    <encoder>"
  echo "      <pattern>%-5level|%d{HH:mm:ss.SSS}|%logger{50}|%msg%n</pattern>"
  echo "    </encoder>"
  echo "  </appender>"
  echo "  <appender name=\"TRACE_API\" class=\"ch.qos.logback.core.FileAppender\">"
  echo "    <file>target/logs/TRACE_API.log</file>"
  echo "    <encoder>"
  echo "      <pattern>%msg%n</pattern>"
  echo "    </encoder>"
  echo "  </appender>"
  echo "  <!-- loggers -->"
  echo "  <root level=\"ERROR\">"
  echo "    <appender-ref ref=\"STDOUT\" />"
  echo "  </root>"
  echo "    <logger name=\"com.leekwars\" level=\"INFO\">"
  echo "        <appender-ref ref=\"STDOUT\" />"
  echo "        <appender-ref ref=\"FILE\" />"
  echo "    </logger>"
  echo "    <logger name=\"JSON_TRACE\" level=\"OFF\">"
  echo "        <appender-ref ref=\"TRACE_API\" />"
  echo "    </logger>"
  echo "</configuration>"
  echo ""
} > src/main/resources/logback.xml

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
  echo "# Nombre d'agressions maximales d'un seul éleveur"
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
