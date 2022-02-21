package com.af.course.api.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Tanglinfeng
 * @date 2022/2/20 15:11
 */
@AllArgsConstructor
@Getter
public enum QuestionDifficultyEnum {

    easy("EASY", 1),

    medium("MEDIUM", 2),

    difficulty("DIFFICULTY", 3);

    private String name;
    private int index;

    public static String getName(int index) {
        for (QuestionDifficultyEnum e : QuestionDifficultyEnum.values()) {
            if (e.getIndex() == index) {
                return e.getName();
            }
        }
        return null;
    }
}
