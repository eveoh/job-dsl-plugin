import javaposse.jobdsl.dsl.DslScript

/**
 * External class referenced by a Caller
 */
class Callee {

    static def makeJob(DslScript dslScript, String nameOfJob) {
        dslScript.job {
            name nameOfJob
        }
    }
}
