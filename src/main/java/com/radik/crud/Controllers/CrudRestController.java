package com.radik.crud.Controllers;

import com.radik.crud.entity.Task;
import com.radik.crud.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
public class CrudRestController {

    private TaskService service;

    @GetMapping("/get-tasks")
    public ResponseEntity<List<Task>> getPageTask(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "3") int size) {
        if (size < 3)
            size = 3;

        List<Task> taskList = service.findAll(page, size).getContent();
        return ResponseEntity.ok().body(taskList);
    }
    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getFieldError().getDefaultMessage();
            return ResponseEntity.badRequest().body(error);
        }
        service.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    public ResponseEntity<String> update(@Valid @RequestBody Task task, BindingResult result) {
        if (result.hasErrors()) {
            String error = result.getFieldError().getDefaultMessage();
            System.out.println(error + "---------------------");
            return ResponseEntity.badRequest().body(error);
        }
        service.update(task);
        return ResponseEntity.noContent().build();
    }
}
