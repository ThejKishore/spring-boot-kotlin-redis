package com.kish.cust.msec.resource;

import com.kish.cust.msec.model.Foo;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @PostAuthorize("hasPermission(returnObject, 'read')")
    @GetMapping("/foos/{id}")
    @ResponseBody
    public Foo findById(@PathVariable long id) {
        return new Foo("Sample");
    }

    @PreAuthorize("hasPermission(#foo, 'write')")
    @PostMapping("/foos")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public Foo create(@RequestBody Foo foo) {
        return foo;
    }
}
