package com.example.greenchecks;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Issue;
import com.example.greenchecks.bonus.BLEDetector;
import com.example.greenchecks.bonus.BatteryEfficientLocationDetector;
import com.example.greenchecks.bonus.DarkUIDetector;
import com.example.greenchecks.bonus.SensorLatencyDetector;
import com.example.greenchecks.malus.ClassNameDetector;
import com.example.greenchecks.malus.EverlastingServiceDetector;
import com.example.greenchecks.malus.IITLDetector;
import com.example.greenchecks.malus.IntensiveLoggingDetector;
import com.example.greenchecks.malus.SensorLeakDetector2;
import com.example.greenchecks.malus.WakeLockDetector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//Cette classe contient les "issues" qui seront vérifiées
public class MyIssueRegistry extends IssueRegistry {

    public static final Category GREENNESS = Category.create("Greenness", 30);


    private static final List<Issue> sIssues;

    static {
        List<Issue> issues = new ArrayList<Issue>(10);

        //bonus
        issues.add(BatteryEfficientLocationDetector.ISSUE);
        //Rajout de BLEDetector
        issues.add(BLEDetector.ISSUE);
        issues.add(DarkUIDetector.ISSUE);
        issues.add(SensorLatencyDetector.ISSUE);
        //malus
        issues.add(EverlastingServiceDetector.ISSUE);
        issues.add(IITLDetector.ISSUE);
        issues.add(IntensiveLoggingDetector.ISSUE);
        issues.add(SensorLeakDetector2.ISSUE);
        issues.add(WakeLockDetector.ISSUE);

        //RAJOUT regleTest
        issues.add(ClassNameDetector.ISSUE);

        sIssues = Collections.unmodifiableList(issues);
    }



    @Override
    public List<Issue> getIssues() {
        return sIssues;
    }
}
