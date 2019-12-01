import com.android.builder.model.ProjectSyncIssues
import com.android.builder.model.SyncIssue
import org.gradle.tooling.BuildActionExecuter
import org.gradle.tooling.BuildController
import org.gradle.tooling.GradleConnector
import java.io.File
import java.util.*

fun main(args: Array<String>) {
    val projectPath = args[0]
    val connect = GradleConnector.newConnector()
        .forProjectDirectory(File(projectPath))
        .connect()
    val executor: BuildActionExecuter<Void> = connect
        .action()
        .projectsLoaded<List<SyncIssue>>({ controller: BuildController? ->
            controller?.let {
                println("buildModel.projects:\n" + controller.buildModel.projects.joinToString("\n"))
                controller.buildModel.projects.flatMap {
                    controller.findModel(it, ProjectSyncIssues::class.java)
                        ?.syncIssues
                        .orEmpty()
                }
            }
        }, { result: List<SyncIssue> ->
            println("${result.size} issues found!")
            result.forEach {issue->
                println("---")
                println("severity:"+issue.severity)
                println("message:"+issue.message)
                println("multiLineMessage:"+issue.multiLineMessage)
                println("type:"+issue.type)
            }
        })
        .build()
        .forTasks(Collections.emptyList())
        .setStandardError(System.err)
        .setStandardOutput(System.out)
    executor.run()
}