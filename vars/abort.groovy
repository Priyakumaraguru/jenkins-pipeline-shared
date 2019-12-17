import hudson.model.Result
import hudson.model.Run
import jenkins.model.CauseOfInterruption.UserInterruption

def call(body) {
    // https://stackoverflow.com/a/49901413/4763512
    Run previousBuild = currentBuild.rawBuild.getPreviousBuildInProgress()
    def d= currentBuild.previousBuild.duration;
    
    while (previousBuild != null) {
        
        if (d>0.1) {
            def executor = previousBuild.getExecutor()
            if (executor != null) {
                echo ">> Aborting older build #${previousBuild.number}."
                executor.interrupt(Result.ABORTED, new UserInterruption(
                    "Aborted by newer build #${currentBuild.number}."
                ))
            }
        
        }
    
        previousBuild = previousBuild.getPreviousBuildInProgress()
    }
}
