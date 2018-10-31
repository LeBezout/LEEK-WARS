# Comment utiliser leekwars-utils

## Pré-requis

* JDK installé et configuré
* Maven installé et configuré
* Un IDE installé  et configuré

## Installer la dépendance Maven

```bash
git clone https://github.com/LeBezout/LEEK-WARS.git
cd leekwars-utils
mvn install
```

## Créer son propre projet

Exécuter ce script shell d'initialisation : 

* Editer le fichier `docs/init-project.sh`
* renseigner `install_dir`
* renseigner `farmer_name`
* `chmod +x docs/init-project.sh`
* `./docs/init-project.sh`

Importer le fichier `pom.xml` dans votre IDE préféré.

## Utiliser le _Fast Garden_

Ecrire une classe Java sur ce modèle :

```java
package com.leekwars.farmer.garden;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.leekwars.utils.DefaultLeekWarsConnector;
import com.leekwars.utils.LWUtils;
import com.leekwars.utils.exceptions.LWException;
import com.leekwars.utils.fastgarden.FastGardenParam;
import com.leekwars.utils.fastgarden.UltraFastGarden;
import com.leekwars.utils.fastgarden.impl.HtmlReportFastGardenVisitor;
import com.leekwars.utils.fastgarden.impl.PropertiesFastGardenParamImpl;

/**
 * Batch pour lancement de tous les combats de l'éleveur
 */
public final class MyUltraFastGarden {
    private MyUltraFastGarden() {}
	
    private static final String TIMESTAMP = new SimpleDateFormat("yyyyMMdd_HH-mm").format(new Date());
    private static final DefaultLeekWarsConnector FARMER = new DefaultLeekWarsConnector("MonLogin", "MonPassword");
    private static final String REPORT_TEMPLATE = new File("src/main/resources/report_template.html").getAbsolutePath();

    /**
     * Ce programme attend un seul argument, le dossier où générer le rapport HTML
     * @param args arguments
     */
    public static void main(String... args) {
        if (args.length != 1) {
            System.err.println("Erreur, attendu un argument : dossier de génération du rapport");
            System.exit(1);
        }
        try {
            final FastGardenParam lParams = new PropertiesFastGardenParamImpl("/fastgarden.properties");
            final File output = new File(new File(args[0], pConnector.getUsername()), TIMESTAMP + ".html");
            HtmlReportFastGardenVisitor lReport = new HtmlReportFastGardenVisitor(new File(REPORT_TEMPLATE), output);
            lReport.setLWVersion(pConnector.getVersion());
            UltraFastGarden.setParams(lParams);
            UltraFastGarden.forAll(FARMER, lReport);
            lReport.generate();
            System.out.println(output.getAbsolutePath() + " generated.");
            LWUtils.openFileInDefaultBrowser(output);
        } catch (LWException e) {
            e.printStackTrace();
            System.exit(2);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
    }
}
```
