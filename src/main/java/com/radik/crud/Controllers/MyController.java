package com.radik.crud.Controllers;

import com.radik.crud.entity.Task;
import com.radik.crud.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class MyController {

    private TaskService service;

    @GetMapping("/")
    public String home(@RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "size", defaultValue = "3") int size, Model model) {
        if (page < 0) page = 0;
        if (size < 3) size = 3;
       long totalPages = service.getCount();
       if (totalPages > size) totalPages = (long) Math.ceil((double) totalPages / size);
       if (page > totalPages - 1) page = (int) totalPages - 1;

       List<Task> tasks = service.findAll(page, size).getContent();
       int[] pageSizeOptions = new int[]{3, 5, 10};

       model.addAttribute("tasks", tasks);
       model.addAttribute("totalPages", totalPages);
       model.addAttribute("currentPage", page);
       model.addAttribute("pageSize", size);
       model.addAttribute("pageSizeOptions", pageSizeOptions);
       return "home";
    }

}
