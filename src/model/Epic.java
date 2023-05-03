package model;

public class Epic extends Task {
    public Epic(String name, String description, String status, long id) {
        super(name, description, status, id);
    }

    @Override
    public void setStatus(String status) {
        super.setStatus(status);
    }

    @Override
    public String toString() {
        return "Epic{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", id=" + this.getId() +
                '}' + "\n";
    }
}
