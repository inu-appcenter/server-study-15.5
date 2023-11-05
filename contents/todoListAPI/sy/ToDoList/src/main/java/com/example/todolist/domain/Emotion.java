package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Emotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EmotionId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EmotionStatus emotionStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "toDo_Id",nullable = false)
    private ToDo toDo;

    @Builder
    public Emotion(User user,ToDo toDo,EmotionStatus emotionStatus){
        this.user=user;
        this.toDo=toDo;
        this.emotionStatus=emotionStatus;
    }

}
