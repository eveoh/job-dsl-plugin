package javaposse.jobdsl.dsl

public enum ViewType {
    ListView('project')

    String elementName

    public ViewType(String elementName) {
        this.elementName = elementName
    }

    public static find(String enumName) {
        values().find { it.name().toLowerCase() == enumName.toLowerCase() }
    }
}
