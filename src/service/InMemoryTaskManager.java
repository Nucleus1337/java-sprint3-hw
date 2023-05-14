package service;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static model.Sequence.getNextId;
import static model.TaskStatus.*;

public class InMemoryTaskManager implements TaskManager {
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, Subtask> subtasks = new HashMap<>();
    private final List<Task> history = new ArrayList<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public void createTask(Task task) {
        task.setId(getNextId());
        task.setStatus(NEW);

        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(getNextId());
        epic.setStatus(NEW);

        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(getNextId());
        subtask.setStatus(NEW);

        subtasks.put(subtask.getId(), subtask);
        epics.get(subtask.getEpicId()).addSubtaskId(subtask.getId());
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void clearAllTasks() {
        tasks.clear();
    }

    @Override
    public void clearAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void clearAllSubtasks() {
        subtasks.clear();

        epics.values().forEach(epic -> {
            epic.setStatus(NEW);
            epic.clearSubtaskId();
        });
    }

    @Override
    public List<Subtask> getAllSubtasksByEpicId(long epicId) {
        return subtasks.values().stream()
                .filter(subtask -> subtask.getEpicId() == epicId)
                .collect(Collectors.toList());
    }

    @Override
    public Task getTaskById(long taskId) {
        addHistory(tasks.get(taskId));

        return tasks.get(taskId);
    }

    @Override
    public Epic getEpicById(long epicId) {
        addHistory(epics.get(epicId));

        return epics.get(epicId);
    }

    @Override
    public Subtask getSubtaskById(long subtaskId) {
        addHistory(subtasks.get(subtaskId));

        return subtasks.get(subtaskId);
    }

    @Override
    public void removeTaskById(long taskId) {
        tasks.remove(taskId);
    }

    @Override
    public void removeSubtaskById(long subtaskId) {
        Epic epic = epics.get(subtasks.get(subtaskId).getEpicId());
        epic.getSubtaskId().remove(subtaskId);

        subtasks.remove(subtaskId);

        calcEpicStatus(epic);
    }

    @Override
    public void removeEpicById(long epicId) {
        epics.get(epicId).getSubtaskId().forEach(subtasks::remove);
        epics.remove(epicId);
    }

    @Override
    public void updateTask(Task task) {
        updateMainTaskInfo(task, tasks.get(task.getId()));
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        Subtask subtaskForUpdate = subtasks.get(subtask.getId());
        updateMainTaskInfo(subtask, subtaskForUpdate);

        calcEpicStatus(epics.get(subtaskForUpdate.getEpicId()));
    }

    @Override
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

    private void addHistory(Task task) {
        if (history.size() == 10) {
            history.remove(0);
        }

        history.add(task);
    }

    public List<Task> getHistory() {
        return history;
    }
}
