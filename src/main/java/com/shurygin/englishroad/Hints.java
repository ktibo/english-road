package com.shurygin.englishroad;

import lombok.Getter;

@Getter
public enum Hints {

    HALF ("hint1", "Убрать половину вариантов<br><em>(пройдите начальный уровень)</em>"),
    SHIELD ("hint2", "Можно ошибиться в текущем вопросе<br><em>(пройдите средний уровень)</em>"),
    LIFE ("hint3", "Дополнительная жизнь<br><em>(пройдите продвинутый уровень)</em>");

    private final String id;
    private final String description;

    Hints(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public static long addHint(int levelIndex, long hints) {
        return hints | (long)Math.pow(2, levelIndex - 1);
    }

    public static long spendHint(int hintIndex, long hints) {
        return hints & ~((long)Math.pow(2, hintIndex - 1));
    }
}
