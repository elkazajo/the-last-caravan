# ROADMAP.md — LAST CARAVAN

This roadmap is ordered by dependency and MVP value.

Do not execute it as one giant batch.
Complete one small slice, compile/test, then continue.

---

# Phase A — Stabilize current WIP

## A0 — Verify local LC-004 / LC-005 state

First Codex task.

Checklist:
- inspect working tree
- compile `gradlew.bat desktop:debug`
- ensure no WndHero stale references
- ensure save preview compiles
- ensure StatusPane compiles
- verify new game bypasses class selection
- verify start loadout
- verify Steppe loop
- verify Jackal code if present

Acceptance:
- no compile errors
- new game enters Steppe
- no obvious class/XP UI
- save/Continue works

If fixes are needed, keep them minimal.

Possible commit if only LC-004 cleanup is fixed:
`LC-004: finalize scout progression UI cleanup`

---

# LC-005 — First threat and awareness

## LC-005.1 — Jackal basic combat

Goal:
One Jackal near FarmRoom.

Stats:
- HP 18
- damage 3–5
- melee
- attackSkill around 10
- low defense
- EXP 0
- no loot
- temporary RatSprite acceptable

Acceptance:
- no Jackal at spawn
- one near farm
- sees/hunts/attacks
- knife can kill it
- no loot
- no visible XP reward
- objective/return still works

Commit:
`LC-005: add first steppe jackal encounter`

## LC-005.2 — Awareness semantics

Goal:
Turn generic SPD states into readable LC behavior.

Implement:
- Unaware
- Alerted
- Engaged

Prefer mapping to existing Mob states.

For Jackal:
- starts Unaware/Wandering
- if sees scout -> Engaged/Hunting
- later if hears noise -> Alerted/Investigating

Add debug logs only if needed, not permanent spam.

Acceptance:
- Jackal does not permanently aggro from impossible distance
- loses/investigates appropriately based on existing AI behavior

Commit:
`LC-005: add enemy awareness states`

## LC-005.3 — First Noise prototype

Create minimal NoiseSystem.

First sources:
- Knife: 0
- optional debug action/noisy test event
- later guns

Noise event:
- cell
- radius/intensity

If Jackal hears but does not see scout:
- investigate source

Acceptance:
- noisy event pulls Jackal toward source
- silence does not
- direct sight still engages

Commit:
`LC-005: add basic noise investigation`

## LC-005.4 — Jackal sprite placeholder replacement

Only if a usable original/owned sprite is ready.
Otherwise defer.

Commit:
`LC-005: add jackal sprite`

---

# LC-006 — Personal water survival

## LC-006.1 — ScoutState

Create:
`com.elkazajo.lastcaravan.scout.ScoutState`

Persistent:
- water
- maxWater
- later hunger
- later scout id/name/perks

Default conceptual target:
100/100

But calibrate display/consumption for SPD turn scale.

Save through LastCaravanRun.

Acceptance:
- new run initializes
- save/continue preserves water

Commit:
`LC-006: add persistent scout survival state`

## LC-006.2 — Water consumption

Consume water based on turn/action cadence.

Do NOT consume every trivial turn at a rate that drains in minutes.

Suggested prototype:
- 1 water every N movement/action turns
or
- fractional accumulator

Acceptance:
- water decreases predictably
- waiting/moving behavior defined
- save works

Commit:
`LC-006: add expedition water consumption`

## LC-006.3 — Drink ScoutWater

Make ScoutWater usable.

Effect:
- restore X water
- consumes one item
- no magic effects

Acceptance:
- cannot waste at full water unless design intentionally allows
- stack decrements
- UI updates

Commit:
`LC-006: make personal water consumable`

## LC-006.4 — Dehydration penalties

Bands:
- normal
- thirsty
- critical
- dehydrated

Keep first implementation simple:
- warning logs/UI
- eventually speed/accuracy penalty
- then HP damage at zero

Commit:
`LC-006: add dehydration consequences`

## LC-006.5 — Water HUD

Add water meter near HP.

Android portrait test required.

Commit:
`LC-006: add scout water HUD`

---

# LC-007 — Inventory weight and meaningful loot

## LC-007.1 — LC item weight API

Introduce weight for LC items.

Target:
- bag capacity 12

Acceptance:
- total weight can be calculated
- UI/debug visible

Commit:
`LC-007: add item weight system`

## LC-007.2 — Pickup restriction / overweight

Behavior:
- show `current/capacity`
- block pickup or require dropping when exceeding 12

Avoid rewriting Belongings.

Commit:
`LC-007: enforce scout carry capacity`

## LC-007.3 — Add first meaningful items

Add:
- DryRation
- Medkit
- Antibiotics
- Battery
- SpareParts
- FoodCrate

Use temporary icons if needed.

Each item needs:
- weight
- personal/caravan use
- localization

Commit in small batches.

## LC-007.4 — Farm optional loot

Farm should contain:
- main WaterSupplyCache
- optional small personal resource
- optional risky side area later

Do not fill the map with junk.

---

# LC-008 — Resource Sharing (signature feature)

## LC-008.1 — Shareable item model

Define which items can transfer to caravan and effects.

Examples:
- WaterCanister -> Water
- FoodCrate -> Food
- Antibiotics -> Medicine
- SpareParts -> future mechanic/event currency or direct repair flags

Commit:
`LC-008: add caravan resource transfer model`

## LC-008.2 — ResourceSharing UI

After return, before CaravanScene summary:
show recovered shareable items.

For each:
- Keep
- Give
- quantity if stackable

Show projected caravan delta.

Commit:
`LC-008: add resource sharing screen`

## LC-008.3 — Convert tutorial WaterSupplyCache

Remove hardcoded auto +4 logic or route it through sharing model.

For first tutorial, choice may be forced or strongly guided if desired.

Commit:
`LC-008: route water objective through sharing system`

## LC-008.4 — Save/continue robustness

Test:
- close app during sharing
- continue
- no duplication
- no double-transfer

Commit:
`LC-008: persist resource sharing state`

---

# LC-009 — Hunger and rations

## LC-009.1 — Inspect SPD Hunger dependencies

Before code:
- search Hunger/Food references
- decide adapt vs wrapper

## LC-009.2 — Personal Hunger meter

Target:
100/100 concept.

Slower than water.

## LC-009.3 — Dry Ration

Consume to restore hunger.

## LC-009.4 — Hunger penalties

Simple bands.

## LC-009.5 — HUD

HP / Water / Hunger.

Android readability test.

Commit family:
`LC-009: ...`

---

# LC-010 — First caravan narrative event

## LC-010.1 — Event data model

Create:
- CaravanEvent
- EventChoice
- EventResult/effects

Keep implementation data-oriented.

Commit:
`LC-010: add caravan event model`

## LC-010.2 — EventScene / window

Static portrait + text + 2–3 choices.

Commit:
`LC-010: add caravan event presentation`

## LC-010.3 — Aliya / fever event

Requirements:
- references medicine/medkit
- keep vs give tension
- impacts Medicine/Morale
- human reaction text

Commit:
`LC-010: add Aliya fever event`

---

# LC-011 — Travel tick and route choice

## LC-011.1 — Travel consumption

After caravan phase:
- Food −6
- Water −8
(provisional balancing)

Handle low resources clearly.

Commit:
`LC-011: add caravan travel consumption`

## LC-011.2 — JourneyState

Persistent:
- day/step
- route progression
- region
- flags

Commit:
`LC-011: add persistent journey state`

## LC-011.3 — Route choices

First choice:
- Old Bridge
- Settlement

Each shows:
- risk hint
- likely resource hint
- uncertainty

Commit:
`LC-011: add first route choice`

---

# LC-012 — Steppe content expansion

## LC-012.1 — ClinicRoom

Potential:
- Medicine
- Antibiotics
- risk

## LC-012.2 — StorageRoom

Potential:
- FoodCrate
- Battery
- SpareParts

## LC-012.3 — Conditional POIs

Not every expedition has all POIs.

## LC-012.4 — RouteEnd landmark

Replace temporary square with authored-looking landmark:
- lookout
- signpost
- abandoned vehicle
- ruined checkpoint

No dungeon stairs.

---

# LC-013 — Raiders and ranged combat

## LC-013.1 — Knife Raider

- HP 24
- damage 5–8

## LC-013.2 — Pistol + ammo

- range ~5
- damage 8–13
- noise 5

## LC-013.3 — Gun Raider

- HP ~24
- ranged behavior
- noise

## LC-013.4 — Cover prototype

- partial −25% ranged accuracy
- full blocks where logical

## LC-013.5 — Shotgun/Rifle

Only after pistol/noise loop works.

---

# LC-014 — Scout progression without kill XP

## LC-014.1 — Progress source model

Award progress for:
- objective
- exploration milestone
- successful return

## LC-014.2 — Scout levels 1–5

No class system.

## LC-014.3 — Perks at 2 and 4

Small perk pool:
- +carry
- quieter movement
- better bandage
- improved vision
- water efficiency
- ranged handling

## LC-014.4 — LC progression UI

Do not resurrect old SPD talent screen.

---

# LC-015 — Scout death / caravan persistence

High-risk milestone.

## LC-015.1 — Detect scout death without ending run

Intercept SPD fail/death flow carefully.

## LC-015.2 — Apply penalties

On death:
- lose carried expedition loot
- Population −1
- Morale −5

## LC-015.3 — New scout candidate

Simple first version:
- generic new scout
- reset ScoutState
- caravan inventory persists

## LC-015.4 — Save/continue

Test death across restart thoroughly.

Commit family:
`LC-015: ...`

---

# LC-016 — Caravan upgrades

MVP:
- Medical Tent
- Workshop
- Water Collector
- Guard Post

Implement one at a time.

Effects should alter:
- event options
- resource efficiency
- starting supplies
not require base-building UI.

---

# LC-017 — Dry Steppe MVP content

Target:
- 3–5 expedition variants
- 3 POI types
- Jackal
- Knife Raider
- Gun Raider
- water/hunger
- resource sharing
- 5 recurring NPCs
- 8–10 Steppe events
- 2 route-choice nodes
- one short narrative chain

At this point conduct playtest focused on:
> Do players hesitate over resources?

Do not start second biome before this is fun.

---

# LC-018 — Mobile UX pass

Android real-device mandatory.

Tasks:
- portrait layout
- tap targets
- one-handed placement
- map readable at phone scale
- stop auto-path on threat/hazard/loot
- quickslots
- return button
- bag weight display
- water/hunger bars
- accessibility/text size review

---

# LC-019 — Art / audio identity pass

Replace temporary SPD-visible identity:
- title screen
- scout sprite
- jackal
- items
- terrain
- UI chrome
- portraits
- sounds/music

Keep licensing inventory.

---

# LC-020 — Dead City vertical slice

Only after Steppe validation.

Add:
- street/interior rooms
- clinic/pharmacy
- cover relevance
- batteries/medicine
- more ranged enemies

No new giant system unless required.

---

# LC-021 — Campaign structure

Regions:
1. Dry Steppe
2. Dead City
3. Saltlands
4. Mountain Pass
5. Red Valley
6. Last Road

Create progression through regions with:
- route events
- resource pressure
- recurring NPC arcs
- limited new mechanics per region

---

# LC-022 — Balancing / analytics plan

Track in playtests:
- expedition length
- return rate
- resource kept vs given
- player deaths
- caravan resource lows
- optional POI participation
- fight avoidance rate
- item usage
- event choice distribution

Do not tune from intuition only.

---

# LC-023 — Release hardening

- save migration/default tests
- crash testing
- Android lifecycle
- rotation policy
- background/resume
- performance on mid-range device
- localization completeness
- license/credits/source compliance
- remove dev-only text
- review all SPD naming/art remnants
- versioning
- Play Store assets/policies
