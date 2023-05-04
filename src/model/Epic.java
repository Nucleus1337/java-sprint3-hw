package model;

import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Long> subtaskId = new ArrayList<>();

    public Epic(String name, String description, String status, long id) {
        super(name, description, status, id);
    }

    public Epic(String name, String description, String status) {
        super(name, description, status);
    }

    public Epic(String name, String desc) {
        super.setName(name);
        super.setDescription(desc);
    }

    public ArrayList<Long> getSubtaskId() {
        return subtaskId;
    }

    public void addSubtaskId(long subtaskId) {
        this.subtaskId.add(subtaskId);
    }

    public void clearSubtaskId() {
        subtaskId.clear();
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
