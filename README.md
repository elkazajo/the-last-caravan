# LAST CARAVAN — Codex handoff package

Copy this package into the root of the local repository.

Recommended resulting structure:

```text
the-last-caravan/
├── AGENTS.md
├── LAST_CARAVAN_MASTER_CONTEXT.md
└── docs/
    ├── CURRENT_STATE.md
    ├── GAME_DESIGN_DOCUMENT.md
    ├── TECHNICAL_ARCHITECTURE.md
    ├── DECISIONS.md
    ├── ROADMAP.md
    ├── CODEX_WORKFLOW.md
    ├── QA_CHECKLIST.md
    └── CHATGPT_RESUME_PROMPT.md
```

Start Codex by asking it to:
1. read `AGENTS.md`;
2. read `docs/CURRENT_STATE.md`;
3. inspect the local worktree;
4. run the desktop build;
5. continue the current LC-005 milestone only.

Suggested first prompt to Codex:

> Прочитай AGENTS.md, docs/CURRENT_STATE.md, docs/ROADMAP.md и docs/CODEX_WORKFLOW.md. Затем проверь текущий локальный worktree и сборку. GitHub master может отставать от локальной версии. Продолжай с текущего LC-005, не перескакивая вперед. Сначала дай краткую сводку фактического состояния и следующий маленький шаг. Git я делаю через VS Code UI, поэтому не выполняй git commit/push и не давай git-команды.

When Codex approaches its context limit, ask it to write/update:
`docs/CODEX_LAST_SESSION.md`

Use the template/instructions in:
`docs/CHATGPT_RESUME_PROMPT.md`

Then return to ChatGPT and attach that summary plus the handoff docs.
