package com.example.greenchecks.malus;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Detector.UastScanner;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.example.greenchecks.MyIssueRegistry;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;

import java.util.Arrays;
import java.util.List;

public class IntensiveLoggingDetector extends Detector implements UastScanner {


    public static final Issue ISSUE = Issue.create(
        // ID: utilisé dans les avertissements "warning" @SuppressLint etc
        "IntensiveLogging",

        //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
        // dans la fenêtre de résultats de l'analyse, etc
        "Overuse of the `android.util.Log` API",

        //Description complète de l'issue
        "TODO",

            MyIssueRegistry.GREENNESS,
        6,
        Severity.WARNING,
        new Implementation(
            IntensiveLoggingDetector.class,
            Scope.JAVA_FILE_SCOPE
        )
    );

    private int logCounter = 0;
    private Location lastLogLocation;
    private long LOC;

    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("i", "v", "e", "d", "w");
    }

    private final String LOG_CLS = "android.util.Log";

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        JavaEvaluator eval = context.getEvaluator();

        LOC = context.getContents().chars().filter(ch -> ch==';').count();

        if (eval.isMemberInClass(method, LOG_CLS)) {
            logCounter++;
            lastLogLocation = context.getLocation(node);
        }
    }


    @Override
    public void afterCheckProject(@NotNull Context context) {
        if (logCounter>15) context.report(ISSUE, lastLogLocation,
                "Maximum logging exceeded for "+ String.valueOf(LOC) +" statements");
    }
}