package com.example.projectcourse4.controller;

import com.example.projectcourse4.entity.Group;
import com.example.projectcourse4.service.GroupService;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Log
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    public Iterable<Group> getAll () {
        return groupService.getAll();
    }

    @GetMapping("/findByGroupname")
    public Optional<Group> findByGroupname (@RequestBody String groupName) {
        return groupService.findBygroupName(groupName);
    }
    @GetMapping("/findByGroupId")
    public Optional<Group> findByGroupId (@RequestBody Map<String,Long> payload) {
        return groupService.findBygroupId(payload.get("group_id"));
    }

    @DeleteMapping("/deleteGroup/{groupId}")
    public void deleteGroup(@PathVariable Long groupId) {
        groupService.deleteById(groupId);
    }

    @PostMapping("/saveGroup")
    @ResponseBody
    public Group addGroup(@RequestBody Map<String,String> payload) {
        Group group = new Group(Long.valueOf(payload.get("group_id")),payload.get("group_name"));
        return groupService.save(group);
    }

    @GetMapping("/updateGroup")
    public Group updateGroups(@RequestBody Map<String,String> payload){
        Group group = new Group(Long.valueOf(payload.get("group_id")),payload.get("group_name"));
        return groupService.update(group);
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}

