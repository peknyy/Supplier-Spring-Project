package com.example.projectcourse4.service;

import com.example.projectcourse4.entity.Group;
import com.example.projectcourse4.repository.GroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {
    private final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupRepository groupRepository;

    @Autowired // constructor inject
    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    public Optional<Group> findBygroupName(String groupName) {
        return groupRepository.findBygroupName(groupName);
    }
    public Optional<Group> findBygroupId(Long groupId) {
        return groupRepository.findById(groupId);
    }

    public Iterable<Group> getAll() {
        return groupRepository.findAll();
    }

    public Group save(Group group) {
        return groupRepository.save(group);
    }

    public Group update(Group group) {
        return groupRepository.save(group);
    }

    public void delete(Group group) {
        try {
            if (groupRepository.existsBygroupName(group.getGroupName())) {
                groupRepository.delete(group);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

}
