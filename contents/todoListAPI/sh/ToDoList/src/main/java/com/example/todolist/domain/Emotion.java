package com.example.todolist.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_boardId",nullable = false)
    private Board board;

    @Builder
    public Emotion(User user,Board board,EmotionStatus emotionStatus){
        this.user=user;
        this.board=board;
        this.emotionStatus=emotionStatus;
    }

}
