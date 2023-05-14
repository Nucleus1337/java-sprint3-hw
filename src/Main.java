import model.Epic;
import model.Subtask;
import model.Task;
import service.InMemoryTaskManager;
import service.Managers;

import static model.TaskStatus.DONE;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();

        inMemoryTaskManager.createTask(new Task("Task1", "TaskDesc1")); /*1*/
        inMemoryTaskManager.createTask(new Task("Task2", "TaskDesc2")); /*2*/

        inMemoryTaskManager.createEpic(new Epic("Epic1", "Epic1 desc")); /*3*/
        inMemoryTaskManager.createSubtask(new Subtask("Subtask1 Epic1", "Subtask1 Epic1 desc", 3)); /*4*/

        inMemoryTaskManager.createEpic(new Epic("Epic2", "Epic2 desc")); /*5*/
        inMemoryTaskManager.createSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc", 5)); /*6*/
        inMemoryTaskManager.createSubtask(new Subtask("Subtask2 Epic2", "Subtask2 Epic2 desc", 5)); /*7*/

        System.out.println("=====CREATED=====");
        System.out.println("TASKS\n" + inMemoryTaskManager.getAllTasks());
        System.out.println("EPICS\n" + inMemoryTaskManager.getAllEpics());
        System.out.println("SUBTASKS\n" + inMemoryTaskManager.getAllSubtasks());

        inMemoryTaskManager.getTaskById(1);
        inMemoryTaskManager.getTaskById(1);

        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getEpicById(3);
        inMemoryTaskManager.getEpicById(5);

        inMemoryTaskManager.getSubtaskById(6);
        inMemoryTaskManager.getSubtaskById(7);
        inMemoryTaskManager.getSubtaskById(4);
        inMemoryTaskManager.getSubtaskById(4);
        inMemoryTaskManager.getSubtaskById(4);

        System.out.println("=====HISTORY=====");
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.updateTask(new Task("Task1", "TaskDesc1", DONE, 1));
        inMemoryTaskManager.updateSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc plus some more text",
                DONE, 4));
        inMemoryTaskManager.updateSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc",
                DONE, 6));

        System.out.println("=====UPDATED=====");
        System.out.println("TASKS\n" + inMemoryTaskManager.getAllTasks());
        System.out.println("EPICS\n" + inMemoryTaskManager.getAllEpics());
        System.out.println("SUBTASKS\n" + inMemoryTaskManager.getAllSubtasks());

        inMemoryTaskManager.removeTaskById(1);
        inMemoryTaskManager.removeSubtaskById(7);

        System.out.println("=====REMOVE-1 (task+subtask)=====");
        System.out.println("TASKS\n" + inMemoryTaskManager.getAllTasks());
        System.out.println("EPICS\n" + inMemoryTaskManager.getAllEpics());
        System.out.println("SUBTASKS\n" + inMemoryTaskManager.getAllSubtasks());

        inMemoryTaskManager.removeSubtaskById(6);

        System.out.println("=====REMOVE-2 (subtask)=====");
        System.out.println("TASKS\n" + inMemoryTaskManager.getAllTasks());
        System.out.println("EPICS\n" + inMemoryTaskManager.getAllEpics());
        System.out.println("SUBTASKS\n" + inMemoryTaskManager.getAllSubtasks());

        inMemoryTaskManager.clearAllEpics();

        System.out.println("=====CLEAR (epics)=====");
        System.out.println("TASKS\n" + inMemoryTaskManager.getAllTasks());
        System.out.println("EPICS\n" + inMemoryTaskManager.getAllEpics());
        System.out.println("SUBTASKS\n" + inMemoryTaskManager.getAllSubtasks());
    }
}