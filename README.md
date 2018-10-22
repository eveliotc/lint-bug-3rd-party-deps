# lint-bug-3rd-party-deps
Sample projects to repro a Lint but see https://groups.google.com/forum/#!topic/lint-dev/x_g9giWmgxk

## Repro
Run `./gradlew runLint`

## Expected
Lint should not check third party dependencies such as aars

## Actual
Lint checks third party depedencies and reports issues with them:

```
➜  lint-bug-3rd-party-deps git:(master) ✗ ./gradlew runLint
Parallel execution is an incubating feature.

> Task :app:lintFlavor1Flavor4Debug FAILED
Wrote HTML report to file:///Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/reports/lint-results-flavor1Flavor4Debug.html
Wrote XML report to file:///Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/reports/lint-results-flavor1Flavor4Debug.xml
Lint found 2 errors


FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':app:lintFlavor1Flavor4Debug'.
> Lint found errors in the project; aborting build.

  Fix the issues identified by lint, or add the following to your build script to proceed with errors:
  ...
  android {
      lintOptions {
          abortOnError false
      }
  }
  ...

  Errors found:

  /Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class: Error: Incorrect annotation [javax.annotation.Nullable]. Please use android.support.annotation.Nullable instead. [WrongAnnotation]
  /Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class: Error: Incorrect annotation [javax.annotation.Nullable]. Please use android.support.annotation.Nullable instead. [WrongAnnotation]



* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 3s
18 actionable tasks: 1 executed, 17 up-to-date
```


