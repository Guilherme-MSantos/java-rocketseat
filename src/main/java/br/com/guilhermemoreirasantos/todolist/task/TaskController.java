package br.com.guilhermemoreirasantos.todolist.task;

import br.com.guilhermemoreirasantos.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskmodel, HttpServletRequest request) {

        //   System.out.println(request.getAttribute("idUser"));
        var idUser = request.getAttribute("idUser");
        taskmodel.setIdUser((UUID) idUser);

        var currentDate = LocalDateTime.now();
        if (currentDate.isAfter(taskmodel.getStartAt()) || currentDate.isAfter(taskmodel.getEndAt())) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("A data de inicio/termino deve ser maior que a data atual");
        }

        if (taskmodel.getStartAt().isAfter(taskmodel.getEndAt())) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("A data de inicio deve ser menor que a data de término");
        }
        var task = this.taskRepository.save(taskmodel);
        return ResponseEntity.status(HttpStatus.OK).body(task);

    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        var tasks = this.taskRepository.findByIdUser((UUID) idUser);

        return tasks;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id) {
        var idUser = request.getAttribute("idUser");
        var task = this.taskRepository.findById(id).orElse(null);

        if(task == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não encontrada");
        }

        if (!task.getIdUser().equals(idUser)) {
            return ResponseEntity.status((HttpStatus.BAD_REQUEST)).body("Essa tarefa não pertence ao usuário informado");

        }
        Utils.copyNonNullProperties(taskModel, task);


        var taskUpdated = this.taskRepository.save(task);

        return ResponseEntity.ok().body(taskUpdated);
    }
}