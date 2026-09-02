# CHATGPT_RESUME_PROMPT.md — use when Codex context/limit is exhausted

When you return to ChatGPT, attach or paste:
- `AGENTS.md`
- `docs/CURRENT_STATE.md`
- `docs/ROADMAP.md`
- optionally Codex's latest summary/diff/error

Then send this prompt:

---

Я продолжаю разработку LAST CARAVAN. Это fork/derivative Shattered Pixel Dungeon под GPLv3.

Прочитай приложенные AGENTS.md, CURRENT_STATE.md и ROADMAP.md как основной контекст проекта.

Codex продолжал разработку после нашего прошлого чата. Вот его последняя сводка/что было сделано:

[ВСТАВЬ СЮДА ПОСЛЕДНЮЮ СВОДКУ CODEX]

Вот текущая ошибка или следующий незавершённый шаг:

[ВСТАВЬ СЮДА]

Важно:
- отвечай по-русски;
- работаем маленькими шагами;
- давай точные пути и код;
- не делай массовых переписываний SPD;
- desktop: `gradlew.bat desktop:debug`;
- Android: `gradlew.bat android:assembleDebug`;
- я делаю Git только через UI VS Code;
- когда пора коммитить, скажи «Пора коммитить» и дай только commit message без git-команд;
- локальный worktree важнее GitHub master;
- продолжай с текущего LC milestone, не перескакивай вперёд.

Сначала скажи, что ты понял как текущее состояние, и дай только следующий конкретный шаг.

---

## What to ask Codex before its context is exhausted

Before ending a Codex session, ask it to produce:

1. Current milestone (`LC-XXX`)
2. Exact completed files/changes
3. Exact files still modified/uncommitted
4. Last successful build command/result
5. What was tested manually
6. Known bugs
7. Next intended step
8. Suggested commit message if ready
9. Any architectural decisions made that are not yet in docs

Save that summary as:
`docs/CODEX_LAST_SESSION.md`

Then ChatGPT can continue with minimal context loss.
