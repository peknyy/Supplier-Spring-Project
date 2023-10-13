package com.example.projectcourse4.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Table(name = "groups")
public class Group {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "group_id", nullable = false)
    private Long groupId;


    @Basic
    @Column(name = "group_name", nullable = false, length = 255)
    private String groupName;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Group user = (Group) o;
        return getGroupId() != null && Objects.equals(getGroupId(), user.getGroupId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

