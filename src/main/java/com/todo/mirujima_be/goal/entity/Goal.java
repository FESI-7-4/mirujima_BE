package com.todo.mirujima_be.goal.entity;

import com.todo.mirujima_be.common.entity.BaseUserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "goal")
public class Goal extends BaseUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private LocalDateTime completionDate;

    public Goal(Long id) {
        this.id = id;
    }
}
