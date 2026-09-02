# QA_CHECKLIST.md — LAST CARAVAN

## Smoke test — every milestone

- app starts
- new game starts
- Steppe generates
- scout can move
- inventory opens
- no obvious crash
- save/exit/Continue works
- return to caravan works
- next expedition works

## LC-003 loop test

Start:
- Caravan Water 24

Expedition 1:
- find WaterSupplyCache
- objective complete
- return
- cache removed
- Caravan Water 28

Exit to title:
- Continue
- if CARAVAN phase, return to CaravanScene
- Water still 28

Next expedition:
- new procedural seed/map
- expedition number increments

Expedition 2:
- return with cache
- Water 32

## LC-004 UI test

New game:
- no class-selection screen
- no Warrior/Mage/etc

Inventory:
- Knife
- Water x2
- Bandage x1
- no SPD class gear

HUD:
- HP visible
- no XP bar
- no level
- no dungeon-exit compass
- no talent blink

Portrait window:
- Scout
- Health
- Status
- Expedition
- Population
- Food
- Water
- Buffs tab
- no talents tab
- no STR/EXP/Gold/Depth/Seed

Save preview:
- Scout
- Health
- Status
- Population
- Food
- Water
- Expedition

## LC-005 Jackal test

- exactly one Jackal in intended first prototype
- not adjacent to spawn
- near Farm
- name localized
- HP correct
- damage 3–5
- attacks correctly
- can be killed with Knife
- drops nothing
- awards no visible XP
- Water objective unaffected
- save/continue with living/dead Jackal behaves

## Save regression

For any persistent feature:
- save in expedition
- resume
- state identical
- save in caravan
- resume
- correct scene
- repeat next expedition
- no duplicate resources
- no double event resolution

## Procedural generation regression

Test at least several fresh expeditions:
- entrance reachable
- farm reachable
- no sealed route
- route endpoint does not behave as dungeon exit
- objective item reachable
- return transition reachable
- Jackal spawns on valid passable cell
- no mob/item inside wall

## Android checklist

- portrait correct
- text readable
- buttons large enough
- no clipped Russian text
- no UI under system cutout
- back button behavior sensible
- pause/resume app
- screen lock/resume
- no accidental desktop-only key dependency

## Release-later legal checklist

- GPLv3 included
- credits preserved/updated
- source availability plan
- no misleading official SPD branding
- asset licenses reviewed
- third-party attribution reviewed
