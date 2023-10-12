package com.study.toDoList.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

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
    private LocalDateTime endDate;

    @Column
    boolean isFinished = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @Builder
    public Task(String title, String description, LocalDateTime endDate, Member member) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.member = member;

    }

    public void changeTitle(String title){
        this.title = title;
    }

    public void changeDescription(String description){
        this.description = description;
    }

    public void check(){
        this.isFinished = !isFinished;
    }
}
