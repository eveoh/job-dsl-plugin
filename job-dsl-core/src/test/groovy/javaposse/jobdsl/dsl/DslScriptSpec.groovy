package javaposse.jobdsl.dsl

import javaposse.jobdsl.dsl.views.ListView
import spock.lang.Specification

class DslScriptSpec extends Specification {
    DslScript parent = Spy(DslScript)

    def 'default view type'() {
        when:
        View view = parent.view {
            name 'test'
        }

        then:
        view.name == 'test'
        view instanceof ListView
        parent.referencedViews.contains(view)
    }

    def 'list view'() {
        when:
        View view = parent.view(type: ViewType.ListView) {
            name 'test'
        }

        then:
        view.name == 'test'
        view instanceof ListView
        parent.referencedViews.contains(view)
    }

    def 'list view enums'() {
        when:
        View view = parent.view(type: ViewType.ListView) {
            name 'test'
            statusFilter(ALL)
        }

        then:
        view.name == 'test'
        view instanceof ListView
        parent.referencedViews.contains(view)
    }

    def 'list view undefined enum value'() {
        when:
        parent.view(type: ViewType.ListView) {
            name 'test'
            statusFilter(FOO)
        }

        then:
        thrown(MissingPropertyException)
    }
}
