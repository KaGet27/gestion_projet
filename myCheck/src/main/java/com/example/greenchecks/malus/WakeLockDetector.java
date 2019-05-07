package com.example.greenchecks.malus;

import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.example.greenchecks.MyIssueRegistry;

public class WakeLockDetector extends Detector implements Detector.UastScanner, Detector.XmlScanner {

    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "WakeLock",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "Using WakeLock is dangerous",

            //Description complète de l'issue
            "TODO`",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.WARNING,
            new Implementation(
                    WakeLockDetector.class,
                    Scope.JAVA_AND_RESOURCE_FILES
            )
    );
}
