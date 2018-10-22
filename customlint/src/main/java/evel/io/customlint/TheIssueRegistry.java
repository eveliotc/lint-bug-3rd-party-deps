package evel.io.customlint;

import com.android.annotations.NonNull;
import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.ApiKt;
import com.android.tools.lint.detector.api.Issue;

import java.util.Arrays;
import java.util.List;

public class TheIssueRegistry extends IssueRegistry {

    @Override
    public int getApi() {
        return ApiKt.CURRENT_API;
    }

    @NonNull
    @Override
    public List<Issue> getIssues() {
        return Arrays.asList(
                SampleAnnotationDetector.ISSUE
        );
    }
}