import model.Epic;
import model.Sequince;
import model.Subtask;
import model.Task;
import service.Manager;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();

        manager.createTask(new Task("Task1", "TaskDesc1", manager.NEW, Sequince.getNextId()));
        manager.createTask(new Task("Task2", "TaskDesc2", manager.NEW, Sequince.getNextId()));

        long epicId = Sequince.getNextId();
        manager.createEpic(new Epic("Epic1", "Epic1 desc", manager.NEW, epicId));
        manager.createSubtask(new Subtask("Subtask1 Epic1", "Subtask1 Epic1 desc",
                manager.NEW, Sequince.getNextId(), epicId));

        epicId = Sequince.getNextId();
        manager.createEpic(new Epic("Epic2", "Epic2 desc", manager.NEW, epicId));
        manager.createSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc",
                manager.NEW, Sequince.getNextId(), epicId));
        manager.createSubtask(new Subtask("Subtask2 Epic2", "Subtask2 Epic2 desc",
                manager.NEW, Sequince.getNextId(), epicId));

        System.out.println("=====CREATED=====");
        System.out.println("TASKS\n" + manager.getAllTasks());
        System.out.println("EPICS\n" + manager.getAllEpics());
        System.out.println("SUBTASKS\n" + manager.getAllSubtasks());

        manager.updateTask(new Task("Task1", "TaskDesc1", manager.DONE, 1));
        manager.updateSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc plus some more text",
                manager.DONE, 4, 3));
        manager.updateSubtask(new Subtask("Subtask1 Epic2", "Subtask1 Epic2 desc",
                manager.DONE, 6, 5));

        System.out.println("=====UPDATED=====");
        System.out.println("TASKS\n" + manager.getAllTasks());
        System.out.println("EPICS\n" + manager.getAllEpics());
        System.out.println("SUBTASKS\n" + manager.getAllSubtasks());

        manager.removeTaskById(1);
        manager.removeSubtaskById(7);

        System.out.println("=====REMOVE-1 (task+subtask)=====");
        System.out.println("TASKS\n" + manager.getAllTasks());
        System.out.println("EPICS\n" + manager.getAllEpics());
        System.out.println("SUBTASKS\n" + manager.getAllSubtasks());

        manager.removeSubtaskById(6);

        System.out.println("=====REMOVE-2 (subtask)=====");
        System.out.println("TASKS\n" + manager.getAllTasks());
        System.out.println("EPICS\n" + manager.getAllEpics());
        System.out.println("SUBTASKS\n" + manager.getAllSubtasks());

        manager.clearAllEpics();

        System.out.println("=====CLEAR (epics)=====");
        System.out.println("TASKS\n" + manager.getAllTasks());
        System.out.println("EPICS\n" + manager.getAllEpics());
        System.out.println("SUBTASKS\n" + manager.getAllSubtasks());
    }
}