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

## Extra logs

Added debugging logs to a custom lint-gradle 26.1.4 jar with seems to point out that `Project`s are generated for depdendencies.


```
➜  lint-bug-3rd-party-deps git:(master) ✗ ./gradlew runLint
Starting a Gradle Daemon, 1 stopped Daemon could not be reused, use --status for details
Parallel execution is an incubating feature.

> Task :app:lintFlavor1Flavor4Debug
runLint before : android? true var: VariantImpl{name=flavor1Flavor4Debug, displayName=flavor1-flavor4-debug, buildTypeName=debug, productFlavorNames=[flavor1, flavor4], mergedFlavor=ProductFlavorImpl{name=, mDimension=null, mMinSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@592, mTargetSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@725, mMaxSdkVersion=null, mRenderscriptTargetApi=null, mRenderscriptSupportMode=null, mRenderscriptSupportModeBlas=null, mRenderscriptNdkMode=null, mVersionCode=1, mVersionName=1.0, mApplicationId=evel.io.lintbug, mTestApplicationId=null, mTestInstrumentationRunner=null, mTestInstrumentationRunnerArguments={}, mTestHandleProfiling=null, mTestFunctionalTest=null, mResourceConfigurations=[], mVectorDrawablesOptions=DefaultVectorDrawablesOptions{mGeneratedDensities=[xxxhdpi, mdpi, ldpi, xxhdpi, hdpi, xhdpi], mUseSupportLibrary=false}, mWearAppUnbundled=null}, mainArtifactInfo=AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@17aac273, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@d906dedd, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug, sourceGenTaskName=generateFlavor1Flavor4DebugSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@a66a53f3, testOptions=null, instrumentedTestTaskName=null}, extraAndroidArtifacts=[AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@49099858, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@48e0b4c2, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug.test, sourceGenTaskName=generateFlavor1Flavor4DebugAndroidTestSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/androidTest/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/androidTest/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@30f53523, testOptions=com.android.build.gradle.internal.ide.TestOptionsImpl@5328931, instrumentedTestTaskName=connectedFlavor1Flavor4DebugAndroidTest}], extraJavaArtifacts=[com.android.build.gradle.internal.ide.JavaArtifactImpl@6591477e], testedTargetVariants=[]} in: flavor1Flavor4Debug jars: [/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/intermediates/lint/lint.jar]
runLint run.
runLint createDriver. request com.android.tools.lint.client.api.LintRequest@7b2e5d92
runLint request project Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app]
runLint type REGISTERED_PROJECT
runLint type STARTING

runLint Scanning app: type SCANNING_PROJECTrunLint type SCANNING_FILE file: /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/src/main/java/evel/io/lintbug/MainActivity.java
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fbcore-1.11.0.aar/5fc1fe8e92863692665e10e707063ca1]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/drawee-1.11.0.aar/668c650055447a08301ee576d69e9b71]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/nativeimagefilters-1.11.0.aar/22b0491d2be1547e0b58251169b3dfae]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fresco-1.11.0.aar/0218742c4f31a4fbcc5d7ca65868cf62]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.android.support/support-annotations/28.0.0/ed73f5337a002d1fd24339d5fb08c2c9d9ca60d8/support-annotations-28.0.0.jar]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.parse.bolts/bolts-tasks/1.4.0/d85884acf6810a3bbbecb587f239005cbc846dc4/bolts-tasks-1.4.0.jar]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/soloader-0.5.1.aar/2d81e6327cf86749c6ad721ceaa74d1d]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-1.11.0.aar/86b12557468c70bd43d19a421c791b9d]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-base-1.11.0.aar/8628fc28ebb468cf389f70f86c7bdc64]
runLint report project Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303]
runLint FOUND ISSUE /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/src/main/java/evel/io/lintbug/MainActivity.java l: Location [file=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class, start=null, end=null, message=null] ps: [Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fbcore-1.11.0.aar/5fc1fe8e92863692665e10e707063ca1], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/drawee-1.11.0.aar/668c650055447a08301ee576d69e9b71], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/nativeimagefilters-1.11.0.aar/22b0491d2be1547e0b58251169b3dfae], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fresco-1.11.0.aar/0218742c4f31a4fbcc5d7ca65868cf62], Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.android.support/support-annotations/28.0.0/ed73f5337a002d1fd24339d5fb08c2c9d9ca60d8/support-annotations-28.0.0.jar], Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.parse.bolts/bolts-tasks/1.4.0/d85884acf6810a3bbbecb587f239005cbc846dc4/bolts-tasks-1.4.0.jar], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/soloader-0.5.1.aar/2d81e6327cf86749c6ad721ceaa74d1d], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-1.11.0.aar/86b12557468c70bd43d19a421c791b9d], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-base-1.11.0.aar/8628fc28ebb468cf389f70f86c7bdc64], Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303]]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fbcore-1.11.0.aar/5fc1fe8e92863692665e10e707063ca1]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/drawee-1.11.0.aar/668c650055447a08301ee576d69e9b71]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/nativeimagefilters-1.11.0.aar/22b0491d2be1547e0b58251169b3dfae]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fresco-1.11.0.aar/0218742c4f31a4fbcc5d7ca65868cf62]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.android.support/support-annotations/28.0.0/ed73f5337a002d1fd24339d5fb08c2c9d9ca60d8/support-annotations-28.0.0.jar]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.parse.bolts/bolts-tasks/1.4.0/d85884acf6810a3bbbecb587f239005cbc846dc4/bolts-tasks-1.4.0.jar]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/soloader-0.5.1.aar/2d81e6327cf86749c6ad721ceaa74d1d]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-1.11.0.aar/86b12557468c70bd43d19a421c791b9d]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-base-1.11.0.aar/8628fc28ebb468cf389f70f86c7bdc64]
runLint report project Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app]
runLint report project Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303]
runLint FOUND ISSUE /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/src/main/java/evel/io/lintbug/MainActivity.java l: Location [file=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class, start=null, end=null, message=null] ps: [Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fbcore-1.11.0.aar/5fc1fe8e92863692665e10e707063ca1], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/drawee-1.11.0.aar/668c650055447a08301ee576d69e9b71], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/nativeimagefilters-1.11.0.aar/22b0491d2be1547e0b58251169b3dfae], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/fresco-1.11.0.aar/0218742c4f31a4fbcc5d7ca65868cf62], Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.android.support/support-annotations/28.0.0/ed73f5337a002d1fd24339d5fb08c2c9d9ca60d8/support-annotations-28.0.0.jar], Project [dir=/Users/eveliotc/.gradle/caches/modules-2/files-2.1/com.parse.bolts/bolts-tasks/1.4.0/d85884acf6810a3bbbecb587f239005cbc846dc4/bolts-tasks-1.4.0.jar], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/soloader-0.5.1.aar/2d81e6327cf86749c6ad721ceaa74d1d], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-1.11.0.aar/86b12557468c70bd43d19a421c791b9d], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/imagepipeline-base-1.11.0.aar/8628fc28ebb468cf389f70f86c7bdc64], Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app], Project [dir=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303]]
runLint type COMPLETED
Wrote HTML report to file:///Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/reports/lint-results-flavor1Flavor4Debug.html
Wrote XML report to file:///Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/reports/lint-results-flavor1Flavor4Debug.xml
Lint found 2 errors
runLint: after android? true var: VariantImpl{name=flavor1Flavor4Debug, displayName=flavor1-flavor4-debug, buildTypeName=debug, productFlavorNames=[flavor1, flavor4], mergedFlavor=ProductFlavorImpl{name=, mDimension=null, mMinSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@592, mTargetSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@725, mMaxSdkVersion=null, mRenderscriptTargetApi=null, mRenderscriptSupportMode=null, mRenderscriptSupportModeBlas=null, mRenderscriptNdkMode=null, mVersionCode=1, mVersionName=1.0, mApplicationId=evel.io.lintbug, mTestApplicationId=null, mTestInstrumentationRunner=null, mTestInstrumentationRunnerArguments={}, mTestHandleProfiling=null, mTestFunctionalTest=null, mResourceConfigurations=[], mVectorDrawablesOptions=DefaultVectorDrawablesOptions{mGeneratedDensities=[xxxhdpi, mdpi, ldpi, xxhdpi, hdpi, xhdpi], mUseSupportLibrary=false}, mWearAppUnbundled=null}, mainArtifactInfo=AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@17aac273, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@d906dedd, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug, sourceGenTaskName=generateFlavor1Flavor4DebugSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@a66a53f3, testOptions=null, instrumentedTestTaskName=null}, extraAndroidArtifacts=[AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@49099858, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@48e0b4c2, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug.test, sourceGenTaskName=generateFlavor1Flavor4DebugAndroidTestSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/androidTest/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/androidTest/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@30f53523, testOptions=com.android.build.gradle.internal.ide.TestOptionsImpl@5328931, instrumentedTestTaskName=connectedFlavor1Flavor4DebugAndroidTest}], extraJavaArtifacts=[com.android.build.gradle.internal.ide.JavaArtifactImpl@6591477e], testedTargetVariants=[]} in: flavor1Flavor4Debug jars: [/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/intermediates/lint/lint.jar]
runLint found warning Warning{issue=WrongAnnotation, message='Incorrect annotation [javax.annotation.Nullable]. Please use android.support.annotation.Nullable instead.', file=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class, line=-1} in /Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class p: Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app] ap: null
runLint found warning Warning{issue=WrongAnnotation, message='Incorrect annotation [javax.annotation.Nullable]. Please use android.support.annotation.Nullable instead.', file=/Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class, line=-1} in /Users/eveliotc/.gradle/caches/transforms-1/files-1.1/animated-drawable-1.11.0.aar/d574b622c20392f51fbb2c463b1a7303/jars/classes.jar!/com/facebook/fresco/animation/bitmap/BitmapAnimationBackend.class p: Project [dir=/Users/eveliotc/dev/lint-bug-3rd-party-deps/app] ap: null
runLint got message Lint found errors in the project; aborting build.

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


FAILURE: Build failed with an exception.

* What went wrong:
# lint-bug-3rd-party-deps
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



  runLint: after android? true var: VariantImpl{name=flavor1Flavor4Debug, displayName=flavor1-flavor4-debug, buildTypeName=debug, productFlavorNames=[flavor1, flavor4], mergedFlavor=ProductFlavorImpl{name=, mDimension=null, mMinSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@592, mTargetSdkVersion=com.android.build.gradle.internal.ide.ApiVersionImpl@725, mMaxSdkVersion=null, mRenderscriptTargetApi=null, mRenderscriptSupportMode=null, mRenderscriptSupportModeBlas=null, mRenderscriptNdkMode=null, mVersionCode=1, mVersionName=1.0, mApplicationId=evel.io.lintbug, mTestApplicationId=null, mTestInstrumentationRunner=null, mTestInstrumentationRunnerArguments={}, mTestHandleProfiling=null, mTestFunctionalTest=null, mResourceConfigurations=[], mVectorDrawablesOptions=DefaultVectorDrawablesOptions{mGeneratedDensities=[xxxhdpi, mdpi, ldpi, xxhdpi, hdpi, xhdpi], mUseSupportLibrary=false}, mWearAppUnbundled=null}, mainArtifactInfo=AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@17aac273, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@d906dedd, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug, sourceGenTaskName=generateFlavor1Flavor4DebugSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@a66a53f3, testOptions=null, instrumentedTestTaskName=null}, extraAndroidArtifacts=[AndroidArtifactImpl{manifestProxy=com.android.build.gradle.internal.ide.BuildOutputsSupplier@49099858, splitOutputsSupplier=com.android.build.gradle.internal.ide.BuildOutputsSupplier@48e0b4c2, isSigned=true, signingConfigName=debug, applicationId=evel.io.lintbug.test, sourceGenTaskName=generateFlavor1Flavor4DebugAndroidTestSources, generatedResourceFolders=[/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/rs/androidTest/flavor1Flavor4/debug, /Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/generated/res/resValues/androidTest/flavor1Flavor4/debug], abiFilters=null, nativeLibraries=[], buildConfigFields={}, resValues={}, instantRun=com.android.build.gradle.internal.ide.InstantRunImpl@30f53523, testOptions=com.android.build.gradle.internal.ide.TestOptionsImpl@5328931, instrumentedTestTaskName=connectedFlavor1Flavor4DebugAndroidTest}], extraJavaArtifacts=[com.android.build.gradle.internal.ide.JavaArtifactImpl@6591477e], testedTargetVariants=[]} in: flavor1Flavor4Debug jars: [/Users/eveliotc/dev/lint-bug-3rd-party-deps/app/build/intermediates/lint/lint.jar]

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output. Run with --scan to get full insights.

* Get more help at https://help.gradle.org

BUILD FAILED in 17s
18 actionable tasks: 18 executed
```
