package service;

import model.Epic;
import model.Subtask;
import model.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

import static model.Sequence.getNextId;

public class Manager {
    public final String NEW = "NEW";
    public final String IN_PROGRESS = "IN_PROGRESS";
    public final String DONE = "DONE";
    private HashMap<Long, Task> tasks = new HashMap<>();
    private HashMap<Long, Epic> epics = new HashMap<>();
    private HashMap<Long, Subtask> subtasks = new HashMap<>();

    public void createTask(Task task) {
        task.setId(getNextId());
        task.setStatus(NEW);

        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId(getNextId());
        epic.setStatus(NEW);

        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtask.setStatus(NEW);

        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtaskId(subtask.getId());
    }

    public Collection<Task> getAllTasks() {
        return tasks.values();
    }

    public Collection<Epic> getAllEpics() {
        return epics.values();
    }

    public Collection<Subtask> getAllSubtasks() {
        return subtasks.values();
    }

    public void clearAllTasks() {
        tasks.clear();
    }

    public void clearAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    public void clearAllSubtasks() {
        subtasks.clear();

        epics.values().forEach(epic -> {
            epic.setStatus(NEW);
            epic.clearSubtaskId();
        });
    }

    public Collection<Subtask> getAllSubtasksByEpicId(long epicId) {
        return subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .collect(Collectors.toList());
    }

    public Task getTaskById(long taskId) {
        return tasks.get(taskId);
    }

    public Epic getEpicById(long epicId) {
        return epics.get(epicId);
    }

    public Subtask getSubtaskById(long subtaskId) {
        return subtasks.get(subtaskId);
    }

    public void removeTaskById(long taskId) {
        tasks.remove(taskId);
    }

    public void removeSubtaskById(long subtaskId) {
        Epic epic = epics.get(subtasks.get(subtaskId).getEpicId());
        epic.getSubtaskId().remove(subtaskId);

        subtasks.remove(subtaskId);

        calcEpicStatus(epic);
    }

    public void removeEpicById(long epicId) {
        epics.get(epicId).getSubtaskId().forEach(subtask -> subtasks.remove(subtask));
        epics.remove(epicId);
    }

    public void updateTask(Task task) {
        updateMainTaskInfo(task, tasks.get(task.getId()));
    }

    public void updateSubtask(Subtask subtask) {
        Subtask subtaskForUpdate = subtasks.get(subtask.getId());
        updateMainTaskInfo(subtask, subtaskForUpdate);

        calcEpicStatus(epics.get(subtaskForUpdate.getEpicId()));
    }

    public void updateEpic(Epic epic) {
        updateMainTaskInfo(epic, epics.get(epic.getId()));
    }

    private void updateMainTaskInfo(Task newTask, Task taskForUpdate) {
        taskForUpdate.setName(newTask.getName());
        taskForUpdate.setDescription(newTask.getDescription());
        taskForUpdate.setStatus(newTask.getStatus());
    }

    private void calcEpicStatus(Epic epic) {
        long newStatusCounter = 0;
        long doneStatusCounter = 0;
        long allSubtaskCounter = 0;

        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == epic.getId()){
                switch (subtask.getStatus()) {
                    case NEW:
                        newStatusCounter++;
                        break;
                    case IN_PROGRESS:
                        break;
                    case DONE:
                        doneStatusCounter++;
                        break;
                }

                allSubtaskCounter++;
            }
        }

        if (allSubtaskCounter == 0 || newStatusCounter == allSubtaskCounter) {
            epic.setStatus(NEW);
        } else if (doneStatusCounter == allSubtaskCounter) {
            epic.setStatus(DONE);
        } else {
            epic.setStatus(IN_PROGRESS);
        }
    }
}
