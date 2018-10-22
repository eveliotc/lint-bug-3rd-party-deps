package evel.io.customlint;

import com.android.annotations.NonNull;
import com.android.tools.lint.checks.AbstractAnnotationDetector;
import com.android.tools.lint.client.api.UElementHandler;
import com.android.tools.lint.detector.api.AnnotationUsageType;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;
import com.android.tools.lint.detector.api.SourceCodeScanner;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UAnnotation;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UImportStatement;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Detects places in which we are using a wrong annotation.
 * In example <code>javax.annotation.Nullable</code> instead of <code>android.support.annotation.Nullable</code>
 */
public class SampleAnnotationDetector extends AbstractAnnotationDetector implements SourceCodeScanner {
    public static final Issue ISSUE = Issue.create(
            "WrongAnnotation",
            "Incorrect annotation",
            "Ensures that the annotation used is the one that was standardized for our Android code.",
            Category.CORRECTNESS,
            7,
            Severity.ERROR,
            new Implementation(SampleAnnotationDetector.class, Scope.JAVA_FILE_SCOPE));

    private static final Map<String, Set<String>> PREFERRED_ANNOTATION_MAPPING = new HashMap<String, Set<String>>() {{
        put("android.support.annotation.Nullable", new HashSet<>(Arrays.asList(
                "javax.annotation.Nullable",
                "com.android.annotations.Nullable",
                "org.jetbrains.annotations.Nullable",
                "com.sun.istack.Nullable",
                "com.sun.istack.internal.Nullable",
                "com.beust.jcommander.internal.Nullable"
        )));
        // Add more, key is the preferred annotation, value is a set of annotations that should not be used.
    }};

    private static final List<String> APPLICABLE_ANNOTATIONS = PREFERRED_ANNOTATION_MAPPING.values()
            .stream()
            .flatMap(Collection::stream)
            .distinct()
            .collect(Collectors.toList());

    private static final Map<String, String> ANNOTATION_MAPPING = PREFERRED_ANNOTATION_MAPPING.entrySet()
            .stream()
            .flatMap(entry -> entry.getValue().stream().map(annotation -> new SimpleImmutableEntry<>(annotation, entry.getKey())))
            .collect(Collectors.toMap(SimpleImmutableEntry::getKey, SimpleImmutableEntry::getValue));


    @Nullable
    @Override
    public List<String> applicableAnnotations() {
        return APPLICABLE_ANNOTATIONS;
    }

    @Override
    public List<Class<? extends UElement>> getApplicableUastTypes() {
        return Collections.singletonList(UImportStatement.class);
    }

    @Override
    public boolean isApplicableAnnotationUsage(AnnotationUsageType type) {
        return !type.equals(AnnotationUsageType.BINARY);
    }

    @Override
    public void visitAnnotationUsage(JavaContext context, UElement usage, AnnotationUsageType type, UAnnotation annotation, String qualifiedName, PsiMethod method, List<? extends UAnnotation> annotations, List<? extends UAnnotation> allMemberAnnotations, List<? extends UAnnotation> allClassAnnotations, List<? extends UAnnotation> allPackageAnnotations) {
        check(context, qualifiedName, usage, annotation);
    }

    void check(JavaContext context, String qualifiedName, UElement usage, UElement target) {
        final String preferred = ANNOTATION_MAPPING.get(qualifiedName);
        if (preferred != null) {
            report(context, ISSUE, usage, context.getLocation(target),
                    "Incorrect annotation [" + qualifiedName + "]. Please use " + preferred + " instead.");
        }
    }

    @Override
    public UElementHandler createUastHandler(@NonNull JavaContext context) {
        return new ImportVisitor(context, this);
    }

    private static class ImportVisitor extends UElementHandler {
        private final JavaContext context;
        private final SampleAnnotationDetector detector;

        ImportVisitor(JavaContext context, SampleAnnotationDetector detector) {
            this.context = context;
            this.detector = detector;
        }

        @Override
        public void visitImportStatement(@NonNull UImportStatement statement) {
            PsiElement resolved = statement.resolve();
            UElement importReference = statement.getImportReference();
            final String qualifiedName =
                    resolved instanceof PsiClass
                            ? ((PsiClass) resolved).getQualifiedName()
                            : (importReference != null ? importReference.asRenderString() : null);
            if (qualifiedName != null) {
                detector.check(context, qualifiedName, statement, statement);
            }
        }
    }
}
