package com.in28minutes.rest.webservices.restfulwebservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {

    // URI Versioning - Twitter
    @GetMapping("v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Bob charlie");
    }

    @GetMapping("v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

//Use by Micosoft
    @GetMapping(path = "person", params = "version=1")
    public PersonV1 getFirstVersionOfPersonRequestParameter() {
        return new PersonV1("Bob charlie");
    }

    @GetMapping(path="person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Use by Microsoft
    @GetMapping(path = "person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader() {
        return new PersonV1("Bob charlie");
    }

    @GetMapping(path="person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader() {
        return new PersonV2(new Name("Bob", "Charlie"));
    }

    // Use Accept as a Header
    // Use By Github
    @GetMapping(path = "person/accept", produces = "application/vnd.company.app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader() {
        return new PersonV1("Bob charlie");
    }
}
