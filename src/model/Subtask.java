package model;

public class Subtask extends Task {
    private long epicId;

    public Subtask(String name, String description, String status, long id, long epicId) {
        super(name, description, status, id);
        this.epicId = epicId;
    }

    public long getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return "Subtask{" +
                "name='" + this.getName() + '\'' +
                ", description='" + this.getDescription() + '\'' +
                ", status='" + this.getStatus() + '\'' +
                ", id=" + this.getId() +
                ", epicId=" + epicId +
                '}' + "\n";
    }
}
