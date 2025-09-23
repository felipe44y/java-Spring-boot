package br.com.felipesilva.todolist.task;

import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/create")
    public ResponseEntity<Object> Create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
        System.out.println("Chegou no controller" );
        var idUser = request.getAttribute("userId");
        taskModel.setIdUser((UUID)idUser);

        var currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
          return ResponseEntity.status(400).body("A data de início deve ser futura.");
        }   

        if(taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
          return ResponseEntity.status(400).body("A data de início deve ser anterior à data de término.");
        } 
        
        var task = this.taskRepository.save(taskModel); 
        return ResponseEntity.status(201).body(task);
    }

    @GetMapping("/list")
    public List<TaskModel> list(HttpServletRequest request){
        var idUser = request.getAttribute("userId");
        var taskList = this.taskRepository.findByIdUser((UUID) idUser);
        return taskList;
    }
}
   