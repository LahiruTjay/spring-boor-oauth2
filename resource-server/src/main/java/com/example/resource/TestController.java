package com.example.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PreAuthorize("hasRole('ROLE_ONE')")
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    String getName() {
        return "OK";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/test1", method = RequestMethod.GET)
    String getName2() {
        return "OK PRE";
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    String getName3() {
        return "OK SCOPE READ";
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @RequestMapping(value = "/test3", method = RequestMethod.GET)
    String getName4() {
        return "OK SCOPE WRITE";
    }

    @RequestMapping(value = "/p/test", method = RequestMethod.GET)
    String getName5() {
        return "OK p";
    }

    @RequestMapping(value = "/o/test", method = RequestMethod.GET)
    String getName6() {
        return "OK p not";
    }

}
