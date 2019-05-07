package com.example.greenchecks.malus;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
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
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UFile;
import org.jetbrains.uast.UMethod;
import org.jetbrains.uast.UParameter;
import org.jetbrains.uast.UastUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SensorLeakDetector2 extends Detector implements Detector.UastScanner {


    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "SensorLeak",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "You did not unregister a sensor properly",

            //Description complète de l'issue
            "Always make sure to disable sensors you don't need, especially when your activity is paused. Failing to do so can drain the battery in just a few hours. Note that the system will not disable sensors automatically when the screen turns off",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.FATAL,
            new Implementation(
                    SensorLeakDetector2.class,
                    Scope.JAVA_FILE_SCOPE
            )
    )
            .addMoreInfo("https://developer.android.com/reference/android/hardware/SensorManager")
            .setAndroidSpecific(true);


    private static final String REGISTER_METHOD = "registerListener";
    private static final String UNREGISTER_METHOD = "unregisterListener";
    private static final String SENSORMANAGER_CLS = "android.hardware.SensorManager";

    private ArrayList<String> registrations;
    private ArrayList<String> unregistrations;
    private JavaContext jctx;

    //variable test
    private boolean mTruc = false;
    private ArrayList<Location> mLocationList;

    @Override
    public void beforeCheckRootProject(@NotNull Context context) {
        registrations = new ArrayList<>();
        unregistrations = new ArrayList<>();
    }


    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList(REGISTER_METHOD, UNREGISTER_METHOD);
//      return Collections.singletonList(UParameter.class);
    }


    @Override
    public void visitMethodCall(@NotNull JavaContext context, @NotNull UCallExpression node, @NotNull PsiMethod method) {
        JavaEvaluator jeval = context.getEvaluator();
        jctx = context;

        if (!jeval.isMemberInClass(method, SENSORMANAGER_CLS)) return;



        if (node.getMethodName().contains(REGISTER_METHOD)) {
            registrations.add(node.getValueArguments().get(0).toString());
            mLocationList.add(context.getLocation(node));
            context.report(ISSUE, node, context.getLocation(node), "The unregistration of this sensor is missing");
        }


        if (node.getMethodName().contains(UNREGISTER_METHOD)) {
            unregistrations.add(node.getValueArguments().get(0).toString());
        }
    }

    @Override
    public void afterCheckRootProject (@NotNull Context context) {
        /*if (!registrations.isEmpty() && unregistrations.isEmpty()) {
            for(UCallExpression n:registrations) {
                context.report(ISSUE, jctx.getLocation(n), "The unregistration of this sensor is missing");
            }
        } else if (!registrations.isEmpty() && !unregistrations.isEmpty()) {
            UMethod enclosingMethod = UastUtils.getParentOfType(unregistrations.get(0), true, UMethod.class);
            if (!enclosingMethod.getName().equals("onPause")) {
                context.report(ISSUE, jctx.getLocation(unregistrations.get(0)), "The unregistration of this sensor is misplaced");

            }
        }*/
        for( int i=0; i<registrations.size(); i++ ){
            for(int j=0; j<unregistrations.size();j++){

                if(registrations.get(i).equals(unregistrations.get(j))){

                }else {
                    mTruc=true;
                }

            }
            if ( unregistrations.isEmpty()){
                mTruc=true;
            }else{
                mTruc=true;
            }
            if (mTruc){
                context.report(ISSUE, mLocationList.get(i), "The unregistration of this sensor is missing");
            }
        }
    }
}
