# GAME_DESIGN_DOCUMENT.md — LAST CARAVAN

## 1. High concept

**LAST CARAVAN** is a mobile-first, turn-based survival roguelike about a lone scout exploring dangerous territories ahead of a caravan of survivors.

The player is not simply gathering loot for personal power.
Every useful item creates a decision:

> Keep it and survive the next expedition more easily — or return it to the people who depend on you.

Core emotional premise:

> Я один иду вперёд, потому что остальные зависят от того, что я найду.

Elevator pitch:

> LAST CARAVAN — мобильный пошаговый survival roguelike, где вы исследуете опасные территории впереди каравана выживших и решаете, оставить найденные ресурсы себе или отдать людям, которые зависят от вас.

USP:

> **Every resource can save you. Or someone else.**

Russian:

> **Каждая находка может спасти тебя. Или кого-то другого.**

## 2. Genre

Primary:
- turn-based survival roguelike / narrative survival roguelite

Secondary:
- tactical exploration
- resource dilemma
- light persistent caravan management
- event-driven narrative

Not:
- RTS
- colony sim
- city builder
- crafting sandbox
- open-world sandbox
- real-time action

## 3. Platform / business model

Target:
- Android first
- portrait
- touch-friendly
- playable in short sessions
- pause at any time

Desired session:
- 3–10 minutes per expedition segment

Campaign concept:
- approximately 6–10 hours for a full run/campaign in a mature version

Business model:
- premium buy once
- no ads
- no P2W
- no energy timers
- no gacha

## 4. Inspirations

Mechanical:
- Shattered Pixel Dungeon
- Into the Breach
- Hoplite
- Slice & Dice
- Slay the Spire
- Darkest Dungeon

Narrative / survival:
- The Banner Saga
- This War of Mine
- Roadwarden
- FTL

Important:
These are references, not templates to copy verbatim.

## 5. Design pillars

### Pillar 1 — Every Resource Is a Decision

Loot should not be automatically good.
The most interesting resources should have at least two competing uses:
- personal survival
- caravan survival
- sometimes immediate benefit vs future benefit

### Pillar 2 — Caravan Must Feel Human

The caravan is not a spreadsheet.
The player should recognize people, remember their problems, and feel consequences.

### Pillar 3 — Risk Must Always Be Optional

The player should often see:
- safer route / poorer reward
- dangerous POI / valuable resource
- known threat / unknown opportunity

Avoid unavoidable punishment with no meaningful choice.

### Pillar 4 — Depth Through Systems, Not Production

Prefer reusable systemic interactions:
- terrain
- visibility
- noise
- item tradeoffs
- route choice
- persistent caravan state

Avoid requiring huge animation/art/content budgets.

### Pillar 5 — Short Session, Long Journey

A single expedition is compact.
The journey across regions gives long-term continuity.

## 6. Main loop

```text
CARAVAN
  ↓
Choose route / understand need
  ↓
Prepare Scout
  ↓
EXPEDITION
  ↓
Explore / avoid / fight / loot
  ↓
Return or escape
  ↓
RESOURCE SHARING
  ↓
Caravan consequences / event
  ↓
Travel tick
  ↓
Next expedition
```

Moment-to-moment:

**Explore → Risk → Loot → Return → Sacrifice → Consequence → Continue**

## 7. MVP validation question

> Is it fun to loot when you must choose between self and caravan?

Every MVP feature should serve this question.

## 8. The Scout

Player-facing role:
- **Scout / Разведчик**

Internal technical identity may remain `Hero` temporarily.

### MVP base stats target

Concept target:
- HP: 100 eventually
- Water: 100 eventually
- Hunger: 100 eventually
- Armor: 0 initially
- Vision: 6
- Bag: weighted capacity 12
- Knife: 5–8

Important implementation note:
Current prototype may still use SPD HP scale (e.g. 20 HP).
Do not force HP=100 until combat numbers/UI are migrated coherently.

### Starting loadout

MVP:
- Knife
- Water x2
- Bandage x1

Current prototype:
- ScoutKnife 5–8
- ScoutWater x2
- Bandage +20 HP

## 9. Caravan

Persistent stats:

- Population
- Food
- Water
- Medicine
- Morale

MVP start:

| Resource | Start |
|---|---:|
| Population | 30 |
| Food | 24 |
| Water | 24 |
| Medicine | 2 |
| Morale | 70 |

Tutorial concept may start harsher later:
- Water 16
- Medicine 1

### Travel tick target

Per travel step:
- Food −6
- Water −8

Exact balancing is provisional.

### Morale bands

- 75–100: hopeful
- 50–74: stable
- 25–49: worried
- 0–24: desperate

Morale 0:
- crisis
- NOT instant game over

Morale should affect:
- event outcomes
- willingness to take risks
- desertion/conflict possibilities
- narrative tone

Not a morality score.

## 10. Caravan NPCs — MVP

### Алия
Role:
- doctor / medical knowledge

Gameplay hooks:
- medicine dilemmas
- illness
- triage

### Ержан
Role:
- mechanic / repair

Gameplay hooks:
- vehicle/cart breakdown
- batteries
- spare parts
- generator

### Тимур
Role:
- security

Gameplay hooks:
- defense
- raider risk
- controversial hard choices

### Сауле
Role:
- supplies/logistics

Gameplay hooks:
- rationing
- storage
- route preparation

### Мира
Role:
- child / emotional anchor

Gameplay hooks:
- human stakes
- fever event
- morale consequences

## 11. First expedition — “Высохшая степь”

Opening concept:

> День 12 пути. Воды осталось примерно на два дня. Впереди замечены заброшенные фермы. Возможно, там есть колодец.

Current prototype route:

```text
Steppe Entrance
   ↓
Open Steppe
   ↓
Road
   ↓
Open / Road
   ↓
Abandoned Farm
   ↓
Water Supply Cache
   ↓
Return to starting point
```

Current objective:
- recover Water Supply Cache
- return it to caravan
- +4 caravan water

First threat:
- Jackal at/near farm

## 12. First narrative dilemma

Concept event:
Aliya’s child has a fever.

If player has a medkit/medicine resource:

Option A — Keep:
- personal healing / safety
- caravan receives nothing

Option B — Give:
- caravan Medicine +2 or consume medicine to resolve crisis
- Morale +3
- Mira/Aliya relationship/narrative reaction

The key:
no “good/evil” meter.

## 13. Exploration structure

Do NOT build a huge true open world.

Use SPD’s:
- `RegularLevel`
- Room graph
- Builder
- Painter

But visually disguise rooms as:
- open steppe pockets
- roads
- farms
- ruins
- clinics
- storage
- chokepoints
- terrain areas

Target expedition map:
- 35x35 to 50x50 conceptually
- 5–8 meaningful zones
- 1–3 POIs
- 5–10 minute session

Rule:
> If the mechanic can be represented through Room + Builder + Painter, prefer the existing SPD level system.

## 14. Procedural room library

MVP:
- OpenSteppeRoom
- RoadRoom
- FarmRoom
- RouteEndRoom
- later WellRoom if separated
- later ClinicRoom
- later StorageRoom
- later RaiderCampRoom

Future:
- DeadCityStreetRoom
- ApartmentRoom
- PharmacyRoom
- GasStationRoom
- CheckpointRoom
- SaltFlatRoom
- MountainPassRoom

## 15. Combat

Preserve:
- turn/action time
- grid
- pathfinding
- line-of-sight
- melee/ranged
- dodge/defense
- buffs
- equipment
- saves
- terrain

Change:
- no kill-centric XP progression
- no fantasy spell focus
- HP should not automatically reset after each fight
- kills should often be avoidable

### Enemy awareness model

Target:
1. **Unaware**
2. **Alerted**
3. **Engaged**

Unaware:
- patrol/wander
- has not confirmed scout

Alerted:
- heard noise / saw suspicious movement
- investigates a cell/area

Engaged:
- confirmed scout
- hunts/attacks

Use existing Mob states where possible:
- WANDERING
- INVESTIGATING
- HUNTING

Map LC semantics onto SPD states instead of rewriting AI.

## 16. Noise system

Noise is a major future system.

Each noisy action creates:
- source cell
- radius/intensity
- optional duration

Examples:
- knife: near silent
- walking: 0 or very low
- running later: low
- pistol: 5
- rifle: 7
- shotgun: 10
- breaking door/window: medium

Enemies:
- if noise reaches them and they do not see scout:
  go Alerted/Investigating toward source
- if they see scout:
  Engaged/Hunting

Noise must create tactical choice, not merely punishment.

## 17. Cover

Target lightweight model:
- partial cover: −25% ranged accuracy
- full cover: blocks shot / line where appropriate

Avoid a complex XCOM simulator.

Use terrain flags/line-of-sight where possible.

## 18. Weapons — target balance concepts

| Weapon | Damage | Range | Noise | Notes |
|---|---:|---:|---:|---|
| Knife | 5–8 | 1 | ~0 | starter |
| Machete | 7–11 | 1 | ~0 | stronger melee |
| Spear | 6–10 | 2 | ~0 | reach |
| Axe | high | 1 | low | slower |
| Pistol | 8–13 | 5 | 5 | ammo |
| Shotgun | 12–20 | 3 | 10 | very loud |
| Rifle | 10–16 | 8 | 7 | 2 slots |

Numbers are design targets, not frozen implementation values.

## 19. Enemies

### Jackal
- HP 18
- Damage 3–5
- melee
- low defense
- first threat

### Knife Raider
- HP 24
- Damage 5–8
- melee
- more aggressive/intelligent

### Gun Raider
- HP 22–26
- Damage 6–9
- Range 5
- creates noise

### Heavy Raider
- HP 45–55
- Damage 10–14
- slow
- dangerous frontal target

Later:
- feral dogs
- infected? only if setting direction supports it
- scavengers
- ambush raiders
- regional variants

Avoid huge bestiary in MVP.

## 20. Inventory

Target:
- weighted inventory
- capacity 12

Item sizes:
- small: 1
- medium: 2
- large: 3–4

This replaces pure “bag slots” as a meaningful expedition constraint.

Do not implement durability in MVP.

Do not create junk loot for the sake of loot density.

## 21. MVP item list

1. Water
2. Dirty Water
3. Dry Ration
4. Bandage
5. Medkit
6. Antibiotics
7. Knife
8. Spear
9. Pistol
10. Shotgun
11. Rifle
12. Pistol Ammo
13. Shells
14. Rifle Ammo
15. Water Filter
16. Battery
17. Water Canister
18. Food Crate
19. Spare Parts
20. Generator

Current prototype already has:
- ScoutKnife
- ScoutWater
- Bandage
- WaterSupplyCache

## 22. Resource sharing

This is the signature screen/system.

When returning:
- show recovered resources
- classify personal vs caravan-relevant
- allow player to choose:
  - keep
  - give
  - sometimes split

Examples:
Water:
- keep for future expedition
- give caravan water

Medicine:
- keep medkit for own HP
- give medicine to sick NPC

Battery:
- keep for flashlight/device later
- give mechanic to repair caravan system

Resource sharing should produce immediate readable consequences.

## 23. Survival meters

Target:
- HP
- Water
- Hunger

Do not add temperature, fatigue, radiation, sanity, etc. in MVP unless a region specifically requires one temporary mechanic.

Water:
- decreases during expedition based on actions/time
- can drink ScoutWater
- dehydration causes penalties then damage

Hunger:
- slower cadence
- may reuse/adapt SPD Hunger buff internally

Important:
Do not implement both in one giant rewrite.
Add Water first, validate, then Hunger.

## 24. Progression

Do not use kill XP as the primary progression.

Target XP/progression sources:
- objectives
- exploration
- successful returns
- narrative milestones

MVP Scout levels:
- 1–5
- perks at 2 and 4

Possible perk categories:
- movement/scouting
- medicine
- carrying
- stealth
- ranged
- survival

Do not use six fantasy classes.

Long-term:
Scout archetypes/perks may emerge, but not class-select at game start in MVP.

## 25. Death

Core statement:

> Разведчики приходят и уходят. Караван продолжает путь.

On Scout death:
- expedition ends
- carried loot lost
- Population −1
- Morale −5
- choose a new scout candidate
- caravan/journey persists
- armory/storage remains if previously deposited

This is a major identity feature and should be implemented only after the basic expedition loop is fun.

## 26. Caravan upgrades

MVP candidate upgrades:
- Medical Tent
- Workshop
- Water Collector
- Guard Post

Keep upgrade system small.
No city-building grid.

Upgrades should unlock:
- better event outcomes
- resource efficiency
- limited preparation options

## 27. Narrative event format

Low-production format:
- static portrait
- 2–5 sentences
- 2–3 choices
- immediate and/or delayed consequence

No expensive cutscenes required.

MVP:
- 5 recurring NPCs
- 15–20 events
- 2 small event chains

## 28. Route choice

After caravan events/travel, choose route.

Example:
- Old Bridge
- Settlement

Route preview should communicate:
- estimated danger
- likely resource type
- uncertainty

Do not fully reveal outcomes.

## 29. Biomes / regions

Campaign concept:

1. Dry Steppe
2. Dead City
3. Saltlands
4. Mountain Pass
5. Red Valley
6. Last Road

MVP:
- Dry Steppe first
- Dead City second only after core loop proves fun

Each region should add at most:
- one terrain/system twist
- a few new POIs
- 1–2 enemies
- relevant events

## 30. First region — Dry Steppe

Themes:
- thirst
- exposure
- abandoned farms
- roads
- dry wells
- sparse human threats
- animal scavengers

Gameplay teaching order:
1. movement/exploration
2. objective cargo
3. return
4. first enemy
5. personal water
6. resource sharing
7. simple event
8. route choice

## 31. Second region — Dead City

Only after Steppe loop is stable.

Themes:
- tighter sight lines
- interiors
- medicine
- batteries
- raiders
- cover/noise matters more

Potential POIs:
- pharmacy
- apartment
- clinic
- garage
- checkpoint
- supermarket storage

## 32. UX principles

Portrait / mobile:
- map 70–80% of screen
- top: HP / Water / Hunger
- bottom: quickslots / weapon / bag / return when relevant

Tap movement:
- auto-path
- stop auto-path if:
  - enemy appears
  - hazard discovered
  - important loot encountered
  - path state changes

One-handed priority:
- common actions near lower half
- no tiny desktop-only controls

## 33. Tutorial philosophy

Teach through first expedition:
- no long tutorial popups
- one concept at a time
- use goals and consequences

Example:
- collect water
- return to caravan
- see water +4
This teaches the entire macro loop with almost no explanation.

## 34. Content budget philosophy

Prefer:
- static portraits
- limited sprites
- palette/gear variants
- systemic procedural reuse
- event text

Avoid:
- large animation sets
- cinematic cutscenes
- hundreds of weapons
- large crafting trees

## 35. Success criteria for MVP

The MVP succeeds if players:
- understand why they are scouting
- care about returning
- hesitate before consuming caravan-relevant resources
- understand that avoiding a fight can be smart
- want to do “one more expedition”
- can remember at least one caravan NPC/event
- feel a difference when caravan resources become low

## 36. MVP non-goals

Do NOT add before core loop validation:
- crafting tree
- base building
- multiplayer
- PvP
- procedural dialogue generation
- open world
- vehicles as direct driving simulation
- dozens of status meters
- complex faction diplomacy
- large skill tree
- weapon durability
- rarity colors/loot tiers
