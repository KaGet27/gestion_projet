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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.UImportStatement;

import java.util.Collections;
import java.util.List;


public class BLEDetector extends Detector implements SourceCodeScanner {

    private static final String ERROR_MESSAGE = "Using BLE API is user-friendly";
    private static final String IMPORT_STR_CLASSIC_B = "android.bluetooth";
    private static final String IMPORT_STR_BLE = "android.bluetooth.le";

    private List<UImportStatement> ImportList;
    private int i;
    private Location location;


    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "BluetoothLowEnergy",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "Flag   s imports of the Low Energy Bluetooth API",

            //Description complète de l'issue
            "In contrast to Classic Bluetooth, Bluetooth Low Energy (BLE) is designed to provide significantly lower power consumption",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.INFORMATIONAL,
            new Implementation(
                    BLEDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    ).setAndroidSpecific(true)
      .addMoreInfo("https://developer.android.com/guide/topics/connectivity/bluetooth-le");


    // ---- implements SourceCodeScanner ----

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UFile.class);
    }

    @Override
    public UElementHandler createUastHandler(JavaContext context) {
        return new UElementHandler() {
            @Override
            public void visitFile(@NotNull UFile node) {
                ImportList = node.getImports();
                location = context.getLocation(node);
                i=0;

                while(i<ImportList.size()){
                    if(ImportList.get(i).toString().contains(IMPORT_STR_CLASSIC_B)){
                        if( !(ImportList.get(i).toString().contains(IMPORT_STR_BLE)) ) {
                            context.report(ISSUE, node, location, ERROR_MESSAGE);
                        }
                    }
                    i++;
                }
            }
        };
    }
}
