# Android Studio Gradle Sync Debugger

1. Clone this repository

2. Run this project with your project path

```
cd android-studio-gradle-sync-debugger
 ./gradlew run --args="../yourProject"
```

You can access Android Studio accessing the Gradle build info.

```
0 issues found!
buildModel.projects:
GradleProject{path=':'}
GradleProject{path=':corecomponent'}
GradleProject{path=':data'}
GradleProject{path=':ext'}
GradleProject{path=':feature'}
GradleProject{path=':frontend'}
GradleProject{path=':model'}
```

This app uses `gradle-tooling-api` for connecting like Android Studio.  
You can change the code and you can debug it.

```kotlin
fun main(args: Array<String>) {
    val projectPath = args[0]
    val connect = GradleConnector.newConnector()
        .forProjectDirectory(File(projectPath))
        .connect()
```