# CODEX_WORKFLOW.md — How Codex should continue LAST CARAVAN

## Start every session with this checklist

1. Read root `AGENTS.md`.
2. Read `docs/CURRENT_STATE.md`.
3. Read the relevant section of `docs/ROADMAP.md`.
4. Inspect the local worktree.
5. Do not assume GitHub master is current.
6. Compile before making broad assumptions.
7. Continue only one coherent slice at a time.

## Current immediate task

At handoff, current milestone is:
**LC-005 — Jackal**

First verify whether these local changes exist and compile:
- Jackal class
- FarmRoom reference
- one Jackal spawn
- WndHero cleanup
- StatusPane cleanup
- save preview

If Jackal is not implemented, implement `LC-005.1`.
If it is implemented but broken, fix it.
If it works, proceed to `LC-005.2 Awareness`.

## Response format to user

Prefer:

1. What we are doing now.
2. Exact file path.
3. Exact code/change.
4. Why this is minimal/safe.
5. What to test.
6. Do not jump to next step until test passes.

When ready:
**Пора коммитить.**

**Commit message:** `LC-005: ...`

No Git commands.

## Build discipline

Fast:
`gradlew.bat desktop:debug`

Android:
`gradlew.bat android:assembleDebug`

For core logic:
- desktop first
- Android later if UI/touch/platform affected

## Save discipline

Any change to:
- LastCaravanRun
- CaravanState
- ScoutState
- JourneyState
- Level objective state
must be tested with:
1. new game
2. save
3. exit to title
4. Continue
5. check state
6. repeat after phase transition

## Procedural level discipline

Whenever generation changes:
- run multiple fresh expeditions
- verify no impossible map
- verify entrance/POI/end topology
- verify objective reachable
- verify return reachable
- avoid assumptions from one seed

## UI discipline

Do not declare mobile UI done after desktop only.
Android phone validation required for:
- HUD
- quickslots
- resource sharing
- events
- inventory
- route choice

## Content discipline

Temporary SPD sprites/icons are acceptable for mechanic prototypes.
Do not spend a development milestone on final art before mechanic validation.

## When uncertain about an SPD API

Search the local source first.
Prefer using an existing SPD pattern over inventing a parallel framework.

## When a deep SPD subsystem appears obsolete

First hide it.
Then remove usage.
Only later delete it if safe.

## Do not do these without explicit user request

- mass package rename
- engine rewrite
- switch to Kotlin
- Godot port
- ECS rewrite
- open world
- crafting tree
- multiplayer
- large procedural narrative AI
