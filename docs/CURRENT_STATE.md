# CURRENT_STATE.md — LAST CARAVAN

Date of handoff: 2026-09-02.

This document is a snapshot of intended local state based on the development conversation.
The local worktree is authoritative. Some items below were explicitly verified by the user; others were implemented step-by-step and followed by “дальше” but were not independently inspected afterward.

## Repository / environment

- Repo: `https://github.com/elkazajo/the-last-caravan`
- Typical local clone: `D:\Dev\the-last-caravan`
- Windows 11
- VS Code
- User uses terminal for build/run.
- User uses VS Code UI for all Git operations.
- Desktop run:
  `gradlew.bat desktop:debug`
- Android:
  `gradlew.bat android:assembleDebug`
- Debug APK:
  `D:\Dev\the-last-caravan\android\build\outputs\apk\debug\android-debug.apk`

## Baseline

SPD synchronized to baseline around:
`7b8b845a76fe76c6b7c031ae9e570852411f56db`
`v3.3.8: updated version for amended v3.3.8 release`

Known project settings:
- `appName = 'LAST CARAVAN DEV'`
- `appPackageName = 'com.elkazajo.lastcaravan'`
- `versionCode = 896`
- `versionName = 3.3.8`
- Android compileSdk 36 / min21 / target36
- libGDX 1.14.0
- Gradle 9.4.0
- AGP 9.1.0
- Debug app id: `com.elkazajo.lastcaravan.indev`

## LC-001 — baseline

Status: COMPLETE / verified.

- cloned
- desktop build works
- Android build works
- APK launched on real phone

## LC-002 — technical rebrand

Status: COMPLETE.

Implemented:
- own entry point:
  `core/src/main/java/com/elkazajo/lastcaravan/LastCaravan.java`
  extends `ShatteredPixelDungeon`
- Android/Desktop launchers instantiate `LastCaravan`
- app name/package changed
- SPD update/news support disabled in LC builds
- Android preferences renamed from `ShatteredPixelDungeon` to `LastCaravan`
- desktop support references changed to LC dev wording
- GPL/credits preserved
- no global Java package rename

Historical commit messages used:
- `LC-002: add LastCaravan application entry point`
- `LC-002: separate app preferences and remove SPD support references`
- `LC-002: complete technical rebrand foundation`

## LC-003 — first Steppe expedition loop

Status: substantially complete.

### SteppeLevel

Path:
`core/src/main/java/com/elkazajo/lastcaravan/levels/SteppeLevel.java`

Depth 1 is redirected to `SteppeLevel` in local `Dungeon.newLevel()`.

The Steppe uses:
- custom rooms
- `LineBuilder`
- custom `SteppePainter`
- water expedition objective
- return-to-caravan flow
- persistent caravan state
- expedition number / seed variation

### OpenSteppeRoom

Path:
`core/src/main/java/com/elkazajo/lastcaravan/levels/rooms/OpenSteppeRoom.java`

Important:
- `StandardRoom`
- size 10–18 x 10–15
- `sizeCatProbs() -> {0f,0f,1f}`
- critical fix: `sizeFactor() -> 1`
- outer WALL, inner EMPTY
- doors EMPTY

`sizeFactor=1` fixed topology problems where giant rooms counted as multiple route nodes.

### RoadRoom

Path:
`core/src/main/java/com/elkazajo/lastcaravan/levels/rooms/RoadRoom.java`

- `ConnectionRoom`
- 8–12
- max total connections 2
- max per direction 1
- 3-wide `EMPTY_SP` road from real doors to central hub
- stable route generation after connection limit fix

### SteppePainter

Path:
`core/src/main/java/com/elkazajo/lastcaravan/levels/painters/SteppePainter.java`

Intent:
- preserve normal room painting mechanics
- doors become visually open
- connections widened
- `RoadRoom` connections use `Terrain.EMPTY_SP`
- no old decorative dungeon clutter

### Builder

Intended `SteppeLevel.builder()`:

```java
@Override
protected Builder builder() {
    LineBuilder builder = new LineBuilder();

    builder.setPathLength(
            1f,
            new float[]{1}
    );

    builder.setTunnelLength(
            new float[]{1},
            new float[]{1}
    );

    builder.setPathVariance(55f);
    builder.setExtraConnectionChance(0.10f);

    return builder;
}
```

Goal:
- all LC zones on main route
- bends allowed
- no ring/loop dungeon structure

### FarmRoom

Path:
`core/src/main/java/com/elkazajo/lastcaravan/levels/rooms/FarmRoom.java`

- recognizable abandoned farm POI
- 12–16
- max total connections 2
- furrowed field
- road/path surfaces
- non-magical well using `Terrain.EMPTY_WELL`
- explicit WaterSupplyCache near well

Verified visually by user.

### WaterSupplyCache

Path:
`core/src/main/java/com/elkazajo/lastcaravan/items/WaterSupplyCache.java`

Purpose:
- expedition objective cargo
- explicit farm spawn
- picking it up completes water objective
- returned cargo is consumed when expedition ends
- caravan receives +4 water

Localization exists in EN/RU.

### Objective state

`SteppeLevel` stores:
- water objective completed
- expedition returned

Expected flow:
1. start expedition
2. find farm
3. collect WaterSupplyCache
4. objective completed log appears
5. return to starting SURFACE transition
6. cache is removed from backpack
7. caravan Water +4
8. save
9. switch to CaravanScene

Water objective log was explicitly verified by user.

### CaravanScene

Path:
`core/src/main/java/com/elkazajo/lastcaravan/scenes/CaravanScene.java`

Purpose:
- intermission / caravan state screen
- displays real `CaravanState`
- has Next Expedition button
- exits to TitleScene through normal scene controls

Steppe → CaravanScene was explicitly verified.

### CaravanState

Path:
`core/src/main/java/com/elkazajo/lastcaravan/caravan/CaravanState.java`

Persistent values:
- Population = 30
- Food = 24
- Water = 24
- Medicine = 2
- Morale = 70

Current behavior includes at least:
- getters
- `addWater(int)`
- Bundle store/restore

### LastCaravanRun

Path:
`core/src/main/java/com/elkazajo/lastcaravan/LastCaravanRun.java`

Owns:
- `CaravanState`
- phase:
  - EXPEDITION
  - CARAVAN
- expedition number
- seed variation
- Bundle save/restore

Intended concepts:

```java
public enum Phase {
    EXPEDITION,
    CARAVAN
}
```

First expedition is internally index 0.
UI generally displays `expeditionNumber + 1`.

Expedition seed concept:

```java
public static long expeditionSeed(long baseSeed) {
    return baseSeed + 1_000_003L * expeditionNumber;
}
```

`Dungeon.seedCurDepth()` should vary depth-1 seed using `LastCaravanRun.expeditionSeed(...)`.

### Continue behavior

Intent:
- if saved phase is CARAVAN, Continue restores save then routes to `CaravanScene`
- if phase is EXPEDITION, Continue restores to `GameScene`

Implemented through a narrow `InterlevelScene` Continue routing hook.

### Full loop

Intended:
CaravanScene → Next Expedition → Interlevel RESET/current depth recreation → new Steppe seed → objective → return → caravan resources persist.

## LC-004 — remove obvious SPD content/progression

Status: intended complete if current local tests pass.

### Random SPD loot disabled on Steppe

Verified by user:
> на карте больше нет разбросанного лута

`SteppeLevel.createItems()` intentionally does not call `super.createItems()`.

### Grass-generated SPD loot disabled on Steppe

`HighGrass.trample()` was modified so Steppe grass does not create:
- dew
- seeds/stones
- Nature's Bounty berries

Grass remains visual/trampleable terrain.

### Own entrance room

Intended:
`core/src/main/java/com/elkazajo/lastcaravan/levels/rooms/SteppeEntranceRoom.java`

Purpose:
- avoid random SPD entrance room variants
- avoid guide pages / dungeon theming
- provide SURFACE return point

### Route endpoint instead of dungeon staircase

Latest intended class:
`core/src/main/java/com/elkazajo/lastcaravan/levels/rooms/RouteEndRoom.java`

Important:
- extends SPD `ExitRoom` only for builder topology semantics
- intentionally creates NO `Terrain.EXIT`
- intentionally creates NO `LevelTransition`
- paints a neutral route-end marker

Old REGULAR_EXIT handling may remain temporarily for old dev saves.

### Scout loadout

Own LC items intended:
- `ScoutKnife`
- `ScoutWater`
- `Bandage`

Paths:
- `core/src/main/java/com/elkazajo/lastcaravan/items/ScoutKnife.java`
- `core/src/main/java/com/elkazajo/lastcaravan/items/ScoutWater.java`
- `core/src/main/java/com/elkazajo/lastcaravan/items/Bandage.java`

Start:
- Knife damage 5–8
- Water x2
- Bandage x1
- no SPD armor/food/waterskin/pouch/magic class gear

`ScoutLoadout`:
`core/src/main/java/com/elkazajo/lastcaravan/scout/ScoutLoadout.java`

`Dungeon.init()` should use it instead of `GamesInProgress.selectedClass.initHero(hero)`.

Temporary internal compatibility:
- hero may still use `HeroClass.WARRIOR`
- talent structures may still be initialized internally
- player should not see class selection

### Class selection bypass

Intended:
`core/src/main/java/com/elkazajo/lastcaravan/scout/ScoutGameStart.java`

New game:
- assigns temporary `HeroClass.WARRIOR`
- no class-selection screen
- resets unsupported SPD challenge/custom-seed config
- initializes seed
- switches to Interlevel DESCEND

Title/StartScene should route new game through `ScoutGameStart`.

`HeroSelectScene` should remain in code but be unreachable through normal LC flow.

### Save UI

Intended visible save info:
- Scout / Разведчик
- Health
- Status (Caravan/Expedition)
- Population
- Food
- Water
- Expedition number

Hidden from player:
- Warrior/Mage/etc
- STR
- XP
- Level
- Gold
- dungeon depth

`GamesInProgress.Info` may still store old fields internally for compatibility.

LAST CARAVAN preview fields intended:
- caravanPopulation
- caravanFood
- caravanWater
- caravanMedicine
- caravanMorale
- expeditionNumber
- atCaravan

`LastCaravanRun` should provide:
- `fillSaveInfo(info)`
- `previewSaveInfo(info, bundle)`

`Dungeon.preview()` should call LC preview after normal Hero/Statistics preview.

### HUD

`StatusPane` intended to hide:
- XP bar
- XP text
- level number
- level-up star particles
- talent-point blink
- dungeon exit compass

Keep:
- avatar
- HP
- shields if any
- buffs
- low-health warning

Internal `hero.exp/lvl` remain for compatibility.

### WndHero

The portrait window was being converted to show:
- Scout
- Health
- Status
- Expedition number
- Population
- Food
- Water
- Buffs tab

Removed/hidden:
- class title
- level
- STR
- XP
- gold
- max depth
- seed
- class info button
- talents tab

The user pasted the modified file; an old SPD block remained and was instructed to be deleted.
The user then said “дальше”.
Codex MUST inspect the actual local `WndHero.java` and compile before assuming it is clean.

Suggested commit if verified:
`LC-004: replace SPD progression UI with scout status`

## LC-005 — current WIP: Jackal

This is the latest development task.

Intended new file:
`core/src/main/java/com/elkazajo/lastcaravan/actors/mobs/Jackal.java`

Temporary implementation:
- extends `Mob`
- temporary `RatSprite`
- HP 18
- damage 3–5
- defenseSkill ~5
- attackSkill ~10
- EXP 0
- no loot
- starts WANDERING
- localized name/description

Intent:
- spawn exactly one Jackal around `FarmRoom`
- no enemy near starting point
- risk appears near the water objective
- use normal SPD Mob AI for first prototype

Important:
This has NOT been explicitly verified by the user yet.

Codex first action should be:
1. inspect local changes;
2. compile `desktop:debug`;
3. run/verify Jackal presence and combat;
4. fix only what is needed;
5. do not expand scope before this works.

## Known uncertainties to verify locally

Because GitHub can lag local WIP, verify:
- current branch name
- whether RouteEndRoom exists and is wired
- whether Scout loadout compiles
- whether class selection is bypassed
- whether save preview fields compile and display
- whether StatusPane changes compile
- whether WndHero old SPD block is fully removed
- whether Jackal currently exists and spawns
- whether any old `Statistics` / `DungeonSeed` imports/references remain after WndHero cleanup

Do not “fix” working local behavior based only on GitHub master.
