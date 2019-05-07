package com.example.greenchecks.malus;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.android.tools.lint.detector.api.JavaContext;
import com.example.greenchecks.MyIssueRegistry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UComment;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.UMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ClassNameDetector extends Detector implements SourceCodeScanner {

    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "ClassName",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "Name your class with more than two charaters",

            //Description complète de l'issue
            "Classes cannot be named with less than three characters",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.FATAL,
            new Implementation(
                    ClassNameDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    ).setAndroidSpecific(true);


    private UClass aFuckingInterestingClass;
    private  Location hereBaby;

    @Nullable
    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {

        return Arrays.asList(UClass.class, UMethod.class);

    }

     @Override
    public UElementHandler createUastHandler(JavaContext context) {
        // Note: Visiting UAST nodes is a pretty general purpose mechanism;
        // Lint has specialized support to do common things like "visit every class
        // that extends a given super class or implements a given interface", and
        // "visit every call site that calls a method by a given name" etc.
        // Take a careful look at UastScanner and the various existing lint check
        // implementations before doing things the "hard way".
        // Also be aware of context.getJavaEvaluator() which provides a lot of
        // utility functionality.
        return new UElementHandler() {
            @Override
            public void visitClass(@NotNull UClass node) {

                    if(node.getName().length() > 25){
                       // context.report(ISSUE, context.getLocation((UElement)node), "Salut marche plz");
                    }







            }


           /* @Override
            public void visitMethod(@NotNull UMethod node) {
                super.visitMethod(node);


                hereBaby = context.getLocation(node);





            }*/
        };
}

}