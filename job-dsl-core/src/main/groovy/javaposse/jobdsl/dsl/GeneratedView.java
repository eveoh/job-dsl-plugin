package javaposse.jobdsl.dsl;

public class GeneratedView implements Comparable {
    private String viewName;

    public GeneratedView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof GeneratedView) {
            return viewName.compareTo(((GeneratedView) o).getViewName());
        } else {
            return viewName.compareTo(o.toString());
        }
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((viewName == null) ? 0 : viewName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GeneratedView other = (GeneratedView) obj;
        if (viewName == null) {
            if (other.viewName != null)
                return false;
        } else if (!viewName.equals(other.viewName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "GeneratedJob{" +
                "viewName='" + viewName + "'" +
                "}";
    }
}
