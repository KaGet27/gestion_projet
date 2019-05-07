package com.example.greenchecks.malus;

import com.android.tools.lint.client.api.JavaEvaluator;
import com.android.tools.lint.detector.api.Context;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.example.greenchecks.MyIssueRegistry;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UCallExpression;
import org.jetbrains.uast.UElement;

import java.util.Arrays;
import java.util.List;

import static com.android.SdkConstants.CLASS_SERVICE;
import static com.android.SdkConstants.CLASS_CONTEXT;


public class EverlastingServiceDetector extends Detector implements Detector.UastScanner {


    public static final Issue ISSUE = Issue.create(
            // ID: utilisé dans les avertissements "warning" @SuppressLint etc
            "EverlastingService",

            //Titre -- montré dans le dialogue de préférences de l'IDE, comme en-tête de catégorie
            // dans la fenêtre de résultats de l'analyse, etc
            "You forgot to stop manually a running service",

            //Description complète de l'issue
            "You used `startService()` without a call to `stopService()` or `stopSelf()`",

            MyIssueRegistry.GREENNESS,
            6,
            Severity.ERROR,
            new Implementation(
                    EverlastingServiceDetector.class,
                    Scope.JAVA_FILE_SCOPE
            )
    );



    private UElement startServiceCallNode = null;
    private UElement stopServiceCallNode = null;
    private JavaContext jctx;


    @Nullable
    @Override
    public List<String> getApplicableMethodNames() {
        return Arrays.asList("startService", "stopService", "stopSelf", "stopSelfResult");
    }

    @Override
    public void visitMethod(JavaContext context, UCallExpression node, PsiMethod method) {
        JavaEvaluator eval = context.getEvaluator();
        jctx = context;

        if (eval.isMemberInSubClassOf(method, CLASS_CONTEXT, true)) {

            if (node.getMethodName().equals("startService")) {

                startServiceCallNode = node;
                context.report(ISSUE, node, context.getNameLocation(node),
                        "startService détécté");

            } else if(node.getMethodName().equals("stopService")) {

                stopServiceCallNode = node;
                context.report(ISSUE, node, context.getNameLocation(node),
                        "stopService détécté");

            }

        } else if(eval.isMemberInSubClassOf(method, CLASS_SERVICE, true)) {

            if (node.getMethodName().equals("stopSelf")) {

                stopServiceCallNode = node;
                context.report(ISSUE, node, context.getNameLocation(node),
                        "stopSelf détécté");

            } else if(node.getMethodName().equals("stopSelfResult")) {

                stopServiceCallNode = node;
                context.report(ISSUE, node, context.getNameLocation(node),
                        "stopSelfResult détécté");
            }

        } else {
            context.report(ISSUE, node, context.getNameLocation(node),
                    "Je ne sais pas trop ce qui se passe");
        }

        //super.visitMethod(context, node, method);
    }


    @Override
    public void afterCheckProject(Context context) {

        if (startServiceCallNode!=null && stopServiceCallNode==null) jctx.report(ISSUE, startServiceCallNode, jctx.getNameLocation(startServiceCallNode),
                "The explicit stop of this service seems missing");

        super.afterCheckFile(context);
    }


}
