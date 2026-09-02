# TECHNICAL_ARCHITECTURE.md — LAST CARAVAN

## 1. Architecture strategy

LAST CARAVAN should be an evolutionary fork, not a rewrite.

The main rule:
> Preserve SPD's proven tactical/runtime systems while replacing content, progression, presentation and meta-loop with LAST CARAVAN systems.

## 2. Ownership boundaries

### SPD-owned / protected backbone

Keep stable:
- `Actor`
- `Char`
- `Mob`
- `Hero`
- `Dungeon`
- `Level`
- `RegularLevel`
- `PathFinder`
- FOV/shadow casting
- `GameScene`
- `InterlevelScene`
- `Bundle`
- item/bag/equipment backbone
- rendering/platform modules

### LAST CARAVAN-owned systems

Use package root:
`com.elkazajo.lastcaravan`

Recommended structure:

```text
com.elkazajo.lastcaravan
├── LastCaravan.java
├── LastCaravanRun.java
├── actors
│   └── mobs
├── caravan
│   ├── CaravanState.java
│   ├── CaravanService.java        (future)
│   └── upgrades                  (future)
├── events
│   ├── CaravanEvent.java         (future)
│   ├── EventChoice.java          (future)
│   └── EventResolver.java        (future)
├── items
├── journey
│   ├── RouteOption.java          (future)
│   ├── Region.java               (future)
│   └── JourneyState.java         (future)
├── levels
│   ├── SteppeLevel.java
│   ├── rooms
│   └── painters
├── noise                       (future)
├── scout
│   ├── ScoutLoadout.java
│   ├── ScoutGameStart.java
│   └── ScoutState.java           (future)
├── scenes
└── windows                     (future LC windows)
```

## 3. Run-level state

`LastCaravanRun` currently acts as LC meta-state owner.

Current:
- CaravanState
- Phase
- expeditionNumber

Future candidates:
- JourneyState
- current region
- route index
- pending caravan event
- scout identity/id
- run-wide flags

Avoid turning `LastCaravanRun` into a giant god object.

As systems grow:
- keep `LastCaravanRun` as orchestration/root state
- delegate to Bundlable subobjects

Example:

```java
private static CaravanState caravanState;
private static JourneyState journeyState;
private static ScoutState scoutState;
```

## 4. Save architecture

SPD save remains authoritative.

LC state should be serialized into the same `game.dat` Bundle.

Requirements:
- every persistent LC state object implements `Bundlable` or has explicit store/restore
- old dev saves should have safe defaults when fields are missing
- preview UI must read without mutating live global state

Patterns:
- `storeInBundle(Bundle bundle)`
- `restoreFromBundle(Bundle bundle)`
- separate preview parsing if needed

Never require loading a level just to display save-slot caravan stats.

## 5. Expedition state

Level-local objective state belongs on `SteppeLevel` or relevant Level:
- objective completed
- return unlocked
- expedition-specific POI state

Run-wide persistent state belongs in `LastCaravanRun` / substate:
- caravan resources
- current phase
- expedition count
- route progression
- living scout state if persistent between expeditions

## 6. Procedural generation

Continue using SPD:
`RegularLevel -> rooms -> Builder -> Painter`

LC convention:
- `Room` represents meaningful outdoor area/POI, not literal dungeon room
- `ConnectionRoom` can represent road/trail/junction
- `EMPTY_SP` can represent visually distinct roads/pads
- custom painters remove dungeon feeling

Seed strategy:
- base run seed
- expedition number offsets depth-1 generation
- later region/route can be included in deterministic seed derivation

Future seed function concept:

```java
seed = hash(
    runSeed,
    regionIndex,
    routeIndex,
    expeditionNumber
);
```

Keep deterministic generation for reproducibility/debugging.

## 7. Mob architecture

First enemies should extend `Mob`.

Reuse:
- WANDERING
- INVESTIGATING
- HUNTING
- SLEEPING where useful

LC semantic mapping:
- Unaware -> WANDERING/SLEEPING
- Alerted -> INVESTIGATING
- Engaged -> HUNTING

Do not implement a second AI framework.

Create small mob classes that specify:
- stats
- behavior overrides only when needed
- sprite
- loot policy
- noise reaction traits later

## 8. Noise architecture

Recommended future package:
`com.elkazajo.lastcaravan.noise`

Minimal data model:

```java
public final class NoiseEvent {
    public final int cell;
    public final int intensity;
    public final int sourceId;
}
```

Potential service:
`NoiseSystem.emit(Level level, int cell, int intensity, Object source)`

Iteration:
1. find enemy mobs
2. determine effective distance/occlusion
3. if heard and not engaged:
   - set investigation target
   - change state to INVESTIGATING

Avoid persistent acoustic simulation initially.

## 9. Item architecture

Use SPD Item backbone.

Categories:
- Scout consumables
- Scout equipment
- Ammo
- Caravan cargo
- Utility items

Important distinction:
`ScoutWater` != `WaterSupplyCache`

Personal resources:
- used by scout
- carried between expeditions if kept

Caravan cargo:
- represents bulk/supply transfer
- consumed/deposited on successful return

Later Resource Sharing may convert generic loot into caravan changes.

## 10. Weighted inventory

Do NOT replace Belongings/Bag immediately.

Safer migration:
1. keep SPD bag for storage mechanics
2. add LC weight API to LC items
3. calculate carried weight across backpack
4. block pickup or show overweight state at capacity
5. hide irrelevant SPD bag capacity concepts
6. only later refactor bags if necessary

Suggested interface:

```java
public interface WeightedItem {
    int weight();
}
```

Or base LC item:
`LastCaravanItem extends Item`

Avoid modifying every SPD item because LC should stop generating most SPD items anyway.

## 11. Survival water

Implement before hunger migration.

Recommended:
`ScoutState` or a Buff-backed state.

Option A:
persistent `ScoutState.water`.

Option B:
reuse Buff framework for ticking.

Preferred hybrid:
- canonical number in ScoutState
- Actor/Buff hook handles turn-time consumption and penalties

Need define action-to-consumption cadence to avoid every step draining too fast.

## 12. Hunger

SPD already has Hunger logic.
Do not delete it until inspected.

Possible approach:
- re-theme/rebalance SPD Hunger
- hide old fantasy food text
- feed with LC rations
- migrate UI to Hunger meter

If SPD Hunger has too much potion/talent coupling, create LC wrapper but still reuse turn tick mechanisms.

## 13. Resource Sharing architecture

Recommended scene/window:
`ResourceSharingScene` or `WndResourceSharing`

Data:
- carried shareable items
- keep quantity
- give quantity
- projected caravan delta

Flow:
1. arrive at caravan
2. collect shareable inventory
3. show choices
4. apply transfer
5. detach given items
6. update CaravanState
7. save
8. continue to event/travel

Current WaterSupplyCache auto-deposit is a tutorial shortcut.
Later it should be generalized.

## 14. Caravan events

Recommended immutable-ish definition:

```java
CaravanEvent
- id
- titleKey
- bodyKey
- list<EventChoice>
- optional conditions
```

Choice:
```java
EventChoice
- textKey
- condition
- effect
- followupEventId
```

Effects should be data-oriented where practical:
- water delta
- food delta
- medicine delta
- morale delta
- population delta
- consume item
- add flag

Do not hard-code all event flow inside Scene classes.

## 15. Journey architecture

Future:
`JourneyState`

Fields:
- region
- day/step
- route node
- next route choices
- flags

Route generation can be simple:
- authored sequence of route-choice nodes with weighted event/expedition types
instead of a huge map.

## 16. Death architecture

Scout death must not call full SPD run wipe in final LC behavior.

Eventually intercept death/failure flow:
- mark scout dead
- lose expedition inventory
- apply caravan penalties
- preserve LastCaravanRun
- transition to caravan/new scout selection

This is high-risk.
Do only after core expedition loop and save model are stable.

## 17. UI architecture

Current pragmatic strategy:
- hide old SPD UI where safe
- replace with LC values
- keep internal structures

Later create LC-specific widgets rather than endlessly patching SPD UI.

Candidate:
- `ScoutStatusPane`
- `CaravanResourceBar`
- `ReturnButton`
- `WaterMeter`
- `HungerMeter`

But only fork a UI class when patching existing `StatusPane` becomes harder than owning a small LC version.

## 18. Localization

All player text must use message properties.

Use namespaces:
- `lastcaravan.items.*`
- `lastcaravan.actors.*`
- `lastcaravan.levels.*`
- `lastcaravan.scenes.*`
- `lastcaravan.windows.*`

Maintain EN + RU.

## 19. Art migration

Temporary SPD sprites are acceptable only for mechanics tests.

Track replacements:
- scout avatar/sprite
- Jackal
- Knife
- Water
- Bandage
- Water Supply Cache
- Steppe terrain
- UI chrome/icons
- caravan portraits

Before commercial release:
- replace/review all identifiable SPD-specific art where needed for branding and presentation
- maintain GPL compliance for inherited assets/code

## 20. Performance

Mobile priorities:
- avoid large allocations every turn
- reuse existing Actor/pathfinding systems
- keep levels modest
- avoid per-frame expensive scans for systems like Noise
- event/resource logic should be turn/event driven, not frame driven
