public class Role implements Comparable<Role> {
    private String name;
    private String projectName;

    public Role(String name, String projectName) {
        this.name = name;
        this.projectName = projectName;
    }

    public String getName() {
        return name;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Role o) {
        int result = projectName.compareTo(o.projectName);
        if (result == 0) {
            result = name.compareTo(o.name);
        }
        return result;
    }
}