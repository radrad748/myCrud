package com.radik.crud.service;

import com.radik.crud.entity.Task;
import com.radik.crud.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class TaskService {

    private TaskRepository taskRep;

    public void save(Task task) {
         taskRep.save(task);
    }

    public void delete(int id) {
        taskRep.deleteById(id);
    }

    public Optional<Task> findById(Integer id) {
        return taskRep.findById(id);
    }

    public List<Task> findAll() {
        return taskRep.findAll();
    }

    public Page<Task> findAll(int page, int size) {
        return taskRep.findAll(PageRequest.of(page, size));
    }

    public boolean update(Task task) {
        Optional<Task> optional = findById(task.getId());
        if (optional.isPresent()) {
            save(task);
            return true;
        } else
            return false;
    }

    public Long getCount() {
        return taskRep.count();
    }

}
