package com.study.toDoList.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private Boolean isFinished;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Builder
    public Task(String title, String description, LocalDate endDate, Member member) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.member = member;
        this.isFinished = false;
    }

    public void update(String title, String description, LocalDate endDate, Boolean isFinished){
        this.title = title;
        this.description = description;
        this.endDate =endDate;
        this.isFinished = isFinished;
    }

}
