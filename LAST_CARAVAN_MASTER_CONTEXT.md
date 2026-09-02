# LAST CARAVAN — MASTER CONTEXT FOR CODEX / CHATGPT

This single file is a compact handoff if you cannot load the full documentation set.

## Identity

LAST CARAVAN is a commercial Android-first turn-based survival roguelike built as a GPLv3 derivative/fork of Shattered Pixel Dungeon.

Repo:
`https://github.com/elkazajo/the-last-caravan`

Typical local path:
`D:\Dev\the-last-caravan`

Desktop:
`gradlew.bat desktop:debug`

Android:
`gradlew.bat android:assembleDebug`

User works in VS Code and does ALL Git actions via VS Code UI.

Whenever a logical chunk is ready:
**Пора коммитить.**
Then provide:
**Commit message:** `LC-XXX: ...`
Never provide git add/commit/push commands.

## Design

Fantasy:
A lone scout explores ahead of a caravan because everyone behind depends on what the scout finds.

USP:
**Every resource can save you. Or someone else.**
**Каждая находка может спасти тебя. Или кого-то другого.**

Loop:
Explore → Risk → Loot → Return → Sacrifice → Consequence → Continue

Persistent caravan:
- Population 30
- Food 24
- Water 24
- Medicine 2
- Morale 70

Scout start:
- Knife 5–8
- Water x2
- Bandage x1

First expedition:
- Dry Steppe
- abandoned farm
- recover Water Supply Cache
- return to starting point
- caravan Water +4

First enemy:
- Jackal
- HP 18
- damage 3–5
- melee
- no loot / no kill XP

## Keep SPD systems

- turn scheduler
- grid
- pathfinding
- FOV
- Level/RegularLevel
- Room/Builder/Painter
- Mob AI
- Hero internal
- Dungeon internal
- Items/Belongings
- Bundle save
- GameScene

Do not rewrite engine or mass rename packages.

## Current implementation

LC-001 baseline complete.
LC-002 technical rebrand complete.
LC-003 Steppe/caravan loop substantially complete.
LC-004 visible SPD loot/progression cleanup intended complete after local verification.
LC-005 Jackal is current WIP and not explicitly user-verified.

Important local classes/systems:
- `LastCaravan`
- `LastCaravanRun`
- `CaravanState`
- `CaravanScene`
- `SteppeLevel`
- `SteppePainter`
- `OpenSteppeRoom`
- `RoadRoom`
- `FarmRoom`
- `SteppeEntranceRoom`
- `RouteEndRoom`
- `WaterSupplyCache`
- `ScoutKnife`
- `ScoutWater`
- `Bandage`
- `ScoutLoadout`
- `ScoutGameStart`
- latest intended `Jackal`

LC-004 intent:
- disable random SPD loot on Steppe
- disable grass-generated seeds/dew
- no class selection
- no visible level/XP/talent UI
- save preview shows LC caravan data
- WndHero shows Scout/Health/Status/Expedition/Population/Food/Water/Buffs

Because local worktree can be ahead of GitHub, inspect and compile before assuming anything.

## Current first action

1. inspect local worktree
2. compile desktop
3. verify WndHero cleanup and save UI
4. verify Jackal exists/spawns
5. fix current LC-005.1 only
6. test
7. commit suggestion

## Near-term roadmap

LC-005:
- Jackal
- Unaware/Alerted/Engaged
- Noise prototype

LC-006:
- ScoutState
- personal Water meter
- consume ScoutWater
- dehydration
- Water HUD

LC-007:
- weight capacity 12
- meaningful items

LC-008:
- Resource Sharing keep/give screen

LC-009:
- Hunger/rations

LC-010:
- event framework
- Aliya fever event

LC-011:
- travel tick
- JourneyState
- first route choice

LC-012:
- Clinic/Storage POIs

LC-013:
- raiders
- pistol/ammo
- ranged/noise/cover

LC-014:
- non-kill progression / perks

LC-015:
- scout death, caravan persists

LC-016:
- caravan upgrades

LC-017:
- Steppe MVP content complete

LC-018:
- Android UX pass

LC-019:
- art/audio identity

LC-020:
- Dead City vertical slice

LC-021:
- full region campaign structure

LC-022:
- balance/analytics

LC-023:
- release hardening / GPL / Play Store

## Scope

Do NOT add before MVP validation:
- crafting
- city builder
- open world
- multiplayer
- durability
- dozens of resources
- huge skill tree
- complex faction diplomacy
- AI-generated narrative system

MVP question:
**Is it fun to loot when you must choose between self and caravan?**
