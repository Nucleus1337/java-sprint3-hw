package model;

import java.util.Collection;

public interface TaskManager {
    /**
     * Создать задачу
     * @param task объект класса Task
     */
    void createTask(Task task);

    /**
     * Создать эпик-задачу
     * @param epic объект класса Epic
     */
    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void clearAllTasks();

    void clearAllEpics();

    void clearAllSubtasks();

    Collection<Task> getAllTasks();

    Collection<Epic> getAllEpics();

    Collection<Subtask> getAllSubtasks();

    Collection<Subtask> getAllSubtasksByEpicId(long epicId);

    Task getTaskById(long taskId);

    Epic getEpicById(long epicId);

    Subtask getSubtaskById(long subtaskId);

    void removeTaskById(long taskId);

    void removeSubtaskById(long subtaskId);

    void removeEpicById(long epicId);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);
}
