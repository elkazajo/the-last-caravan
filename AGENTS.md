# AGENTS.md — LAST CARAVAN

## Project identity

**LAST CARAVAN** is a commercial mobile-first turn-based survival roguelike built as a GPLv3 derivative/fork of **Shattered Pixel Dungeon (SPD)**.

Repository:
- GitHub: `https://github.com/elkazajo/the-last-caravan`
- Typical local path: `D:\Dev\the-last-caravan`
- Default upstream branch is `master`, but the user's LOCAL working tree can be ahead of GitHub. Never assume GitHub `master` contains the latest WIP.

Primary target:
- Android mobile, portrait/touch, one-handed where practical.
- Desktop is used for fast development/testing only.

Core stack:
- Java
- libGDX
- Gradle
- SPD technical core
- Android SDK
- Java 17 installed; SPD source compatibility is Java 11.

Useful commands:
- Fast desktop test: `gradlew.bat desktop:debug`
- Android debug APK: `gradlew.bat android:assembleDebug`
- APK path: `android\build\outputs\apk\debug\android-debug.apk`

## Communication / workflow rules

The user prefers:
- Russian language.
- Direct, incremental, step-by-step development.
- Exact file paths.
- Exact code changes.
- Short explanation of why.
- One coherent change at a time.
- Build/test after each meaningful slice.
- Android-first design, desktop for quick verification.

### Git rule — critical

The user performs Git operations manually through VS Code UI.

DO NOT run or propose:
- `git add`
- `git commit`
- `git push`
- destructive Git commands

When a coherent logical chunk is ready, explicitly say:

**Пора коммитить.**

Then give only:

**Commit message:** `LC-XXX: ...`

Do not provide Git commands.

Before making changes:
- inspect the current branch/worktree;
- inspect local files if available;
- do not assume the local WIP matches GitHub.

## Legal / GPL requirements

This is a GPLv3 derivative of Shattered Pixel Dungeon.

Must:
- preserve GPL license notices;
- preserve attribution/copyright notices where required;
- provide source for distributed derivative under GPLv3 obligations;
- avoid pretending LAST CARAVAN is official SPD/Pixel Dungeon;
- rebrand art/audio/text/UI over time;
- avoid copying third-party assets without compatible rights.

Do NOT:
- mass-remove license headers;
- mass-rename the entire SPD Java package just for branding;
- casually reset Android `versionCode`.

## High-level design

Fantasy:
> A lone scout goes ahead because the people behind depend on what the scout finds.

Core loop:
**Explore → Risk → Loot → Return → Sacrifice → Consequence → Continue**

USP:
**Personal Survival vs Caravan Survival**
> Every resource can save you. Or someone else.

Russian:
> Каждая находка может спасти тебя. Или кого-то другого.

This is NOT:
- RTS
- colony simulator
- city builder
- crafting sandbox
- real-time combat
- open-world rewrite

The project should preserve the high-value SPD technical systems and replace the fantasy/dungeon content gradually.

## Technical philosophy

Keep SPD:
- Actor scheduler / turn time
- grid movement
- pathfinding
- FOV
- GameScene
- Level / RegularLevel room graph
- Mob AI foundation
- Buff
- Item / Belongings
- Bundle save system
- QuickSlot
- CellSelector
- platform modules

Adapt gradually:
- `Hero` remains internally for now, UI calls the character **Scout / Разведчик**
- `Dungeon` remains the runtime singleton for now
- internal `HeroClass.WARRIOR` may remain as temporary compatibility while class selection is hidden
- `hero.lvl`, `hero.exp`, talents may remain internally until dependencies are safely removed
- new LAST CARAVAN systems should live under `com.elkazajo.lastcaravan`

Protected / high-risk SPD core:
- Actor scheduler
- PathFinder
- base Level
- FOV implementation
- save/load backbone
- rendering engine
- platform modules

Medium-risk:
- Hero
- Dungeon
- GameScene
- Belongings
- Bag
- Mob

Safer extension points:
- new LC classes/packages
- new rooms/painters
- custom items
- custom mobs
- caravan state
- journey/events/noise/sharing systems
- LC scenes/windows

Rule:
> If a mechanic can be expressed through existing SPD systems plus a narrow extension, prefer that over rewriting the engine.

## Current milestone interpretation

LC-001 — baseline: complete.
LC-002 — technical rebrand: complete.
LC-003 — first Steppe expedition loop: substantially complete.
LC-004 — cleanup of visible SPD progression/content: intended complete if local tests pass.
LC-005 — first real threat (Jackal): CURRENT WIP.

Important:
The latest user-visible instruction was to add a `Jackal` near `FarmRoom`.
This has NOT been explicitly verified by the user yet.
Before continuing LC-005, inspect and compile the local working tree.

See:
- `docs/CURRENT_STATE.md`
- `docs/GAME_DESIGN_DOCUMENT.md`
- `docs/ROADMAP.md`
- `docs/TECHNICAL_ARCHITECTURE.md`
- `docs/DECISIONS.md`
- `docs/QA_CHECKLIST.md`

## Scope discipline

For every requested feature ask:
1. Does this strengthen the core decision: scout vs caravan?
2. Can it reuse SPD systems?
3. Is it needed for the MVP?
4. Can it be implemented with minimal new content/art?

Avoid scope creep.

MVP validation question:
> Is it fun to loot when you must choose between self and caravan?

## Coding style / integration guidance

Prefer:
- small classes;
- explicit LC package ownership;
- narrow hooks into SPD;
- save-safe state via `Bundlable` / `Bundle`;
- deterministic procedural generation based on run/expedition seed;
- localization keys instead of hard-coded player-facing text.

When modifying SPD files:
- keep changes narrow;
- comment LC-specific deviations clearly when helpful;
- preserve original copyright/GPL headers.

## Required behavior before large changes

Before deleting or replacing a deep SPD subsystem:
- search all references;
- understand save/load impact;
- understand UI impact;
- keep the old internal system hidden rather than deleting it if deletion is risky.

Do not aggressively remove:
- HeroClass
- HeroSubClass
- Talent
- Statistics
- Dungeon depth machinery
until LAST CARAVAN has replacements and references are understood.

## User-testing rhythm

After each slice:
1. compile desktop;
2. run quick gameplay test;
3. test save/continue if state was touched;
4. when stable, say **Пора коммитить** and provide one commit message.

For Android-facing UI/controls:
- also build Android APK and test on a real phone before declaring the UI complete.
