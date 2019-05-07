package com.example.greenchecks.bonus;

import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;

import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.example.greenchecks.MyIssueRegistry;
import com.android.tools.lint.detector.api.GradleContext;
import com.android.tools.lint.detector.api.Project;
import com.android.tools.lint.detector.api.Context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.UImportStatement;
import org.jetbrains.uast.UastContext;
import org.jetbrains.uast.values.UDependency;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

public class BatteryEfficientLocationDetector extends Detector implements SourceCodeScanner, Detector.GradleScanner {

    private static final String ERROR_MESSAGE = "Use battery-friendly APIs for location tracking";
    private static final String IMPORT_STR_LOCATION ="com.google.android.gms.location";

    private List<UImportStatement> ImportList;
    private int i;
    private Location location;

    // import import com.google.android.gms.location.*

    // Et PAS Dans graddle + quick fix  pour ajouter ca à graddle
    /*

    implementation "com.google.android.gms:play-services-location:15.0.1"

or if you're not using latest gradle version:

compile "com.google.android.gms:play-services-location:15.0.1"

     */
    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "BatteryEfficientLocation",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "Use battery-friendly APIs for location tracking",

            //Description complète de l'issue
            "Monitoring location changes is a very battery-intensive task when\\n\" +\n" +
                    "                    \"done in the regular way, while there exist optimized solution",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.INFORMATIONAL,
            new Implementation(
                    BatteryEfficientLocationDetector.class,
                    EnumSet.of(Scope.JAVA_FILE, Scope.GRADLE_FILE)
            )
    ).setAndroidSpecific(true);

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UFile.class);
    }
    private Project project;
    @Override
    public UElementHandler createUastHandler(JavaContext context) {

        return new UElementHandler() {
//
        /*project.getGradleProjectModel();*/


             public void visitBuildScript(JavaContext context, java.util.Map<java.lang.String,java.lang.Object> sharedData){

             }
        };
//
//
//
// @Override


//            public void visitFile(@NotNull UFile node) {
//                ImportList = node.getImports();
//                location = context.getLocation(node);
//                i=0;
//
//                while(i<ImportList.size()){
//                    if(ImportList.get(i).toString().contains(IMPORT_STR_LOCATION)){
//                        ///////////////////////////////////////////////////////////
//                        //
//                        //
//                        //
//                        //      une fois qu'on est dans ce if, il faudrait voir les lignes dans gradle suivant la version
//                        //
//                        //
//                        //      on va stocker le fichier (fakeApp projet ou module app on verra (build.gradle)) dans une var
//                        //      pour la réutiliser en afterVisit
//                        //
//                        //
//                        //
//                        //
//                        ////////////////////////////////////////////////////////////
//                        /*if( !(ImportList.get(i).toString().contains("android.bluetooth.le")) ) {
//                            context.report(ISSUE, node, location, ERROR_MESSAGE);
//                        }*/
//                    }
//                    i++;
//                }
//            }
//        };
   }


}


//    private void checkBuildScripts(Project project, Project main) {
//        List<Detector> detectors = mScopeDetectors.get(Scope.GRADLE_FILE);
//        if (detectors != null) {
//            List<File> files = project.getSubset();
//            if (files == null) {
//                files = project.getGradleBuildScripts();
//            }
//            for (File file : files) {
//                Context context = new Context(this, project, main, file);
//                fireEvent(EventType.SCANNING_FILE, context);
//                for (Detector detector : detectors) {
//                    if (detector.appliesTo(context, file)) {
//                        detector.beforeCheckFile(context);
//                        detector.visitBuildScript(context, Maps.<String, Object>newHashMap());
//                        detector.afterCheckFile(context);
//                    }
//                }
//            }
//        }
//    }




//for (Map.Entry<File, Project> entry : fileToProject.entrySet()) {
//        File file = entry.getKey();
//        Project project = entry.getValue();
//        if (!file.equals(project.getDir())) {
//            if (file.isDirectory()) {
//                try {
//                    File dir = file.getCanonicalFile();
//                    if (dir.equals(project.getDir())) {
//                        continue;
//                    }
//                } catch (IOException ioe) {
//                    // pass
//                }
//            }
//
//            project.addFile(file);
//        }
//    }