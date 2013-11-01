package javaposse.jobdsl.dsl.views

import javaposse.jobdsl.dsl.View
import javaposse.jobdsl.dsl.helpers.Context

import static com.google.common.base.Preconditions.checkNotNull
import static java.lang.String.CASE_INSENSITIVE_ORDER
import static javaposse.jobdsl.dsl.helpers.AbstractContextHelper.executeInContext

class ListView extends View {
    private Set<String> jobNames = []

    void statusFilter(StatusFilter filter) {
        checkNotNull(filter, "filter must not be null")

        execute {
            if (filter == StatusFilter.ALL) {
                it.children().removeAll { it instanceof Node && it.name() == 'statusFilter' }
            } else {
                it / methodMissing('statusFilter', filter == StatusFilter.ENABLED)
            }
        }
    }

    void jobs(Closure jobsClosure) {
        JobsContext context = new JobsContext()
        jobsClosure.resolveStrategy = Closure.DELEGATE_FIRST
        executeInContext(jobsClosure, context)

        this.jobNames.addAll(context.jobNames)

        execute {
            it / 'jobNames' {
                comparator(class: 'hudson.util.CaseInsensitiveComparator')
                for (String job : this.jobNames.sort(CASE_INSENSITIVE_ORDER)) {
                    string(job)
                }
            }
            if (context.regex) {
                it / includeRegex(context.regex)
            }
        }
    }

    void columns(Closure columnsClosure) {
        ColumnsContext context = new ColumnsContext()
        columnsClosure.resolveStrategy = Closure.DELEGATE_FIRST
        executeInContext(columnsClosure, context)

        execute {
            for (Node columnNode : context.columnNodes) {
                it / 'columns' << columnNode
            }
        }
    }

    def propertyMissing(String name) {
        try {
            return StatusFilter.valueOf(name)
        } catch (IllegalArgumentException ignore) {
            throw new MissingPropertyException(name, ListView)
        }
    }

    @Override
    protected String getTemplate() {
        return '''<?xml version='1.0' encoding='UTF-8'?>
<hudson.model.ListView>
    <filterExecutors>false</filterExecutors>
    <filterQueue>false</filterQueue>
    <properties class="hudson.model.View$PropertyList"/>
    <jobNames class="tree-set">
        <comparator class="hudson.util.CaseInsensitiveComparator"/>
    </jobNames>
    <jobFilters/>
    <columns/>
</hudson.model.ListView>'''
    }

    static enum StatusFilter {
        ALL, ENABLED, DISABLED
    }

    static class JobsContext implements Context {
        private Set<String> jobNames = []
        private String regex

        void name(String jobName) {
            checkNotNull(jobName, "jobName must not be null")

            this.jobNames.add(jobName)
        }

        void names(String... jobNames) {
            for (String jobName : jobNames) {
                name(jobName)
            }
        }

        void regex(String regex) {
            this.regex = regex
        }
    }

    static class ColumnsContext implements Context {
        List<Node> columnNodes = []

        void status() {
            columnNodes << new Node(null, "hudson.views.StatusColumn")
        }

        void weather() {
            columnNodes << new Node(null, "hudson.views.WeatherColumn")
        }

        void name() {
            columnNodes << new Node(null, "hudson.views.JobColumn")
        }

        void lastSuccess() {
            columnNodes << new Node(null, "hudson.views.LastSuccessColumn")
        }

        void lastFailure() {
            columnNodes << new Node(null, "hudson.views.LastFailureColumn")
        }

        void lastDuration() {
            columnNodes << new Node(null, "hudson.views.LastDurationColumn")
        }

        void buildButton() {
            columnNodes << new Node(null, "hudson.views.BuildButtonColumn")
        }
    }
}
