# DECISIONS.md — LAST CARAVAN

## D-001 — Fork, not rewrite

Decision:
Build LAST CARAVAN by adapting SPD incrementally.

Reason:
SPD already solves the hardest tactical/mobile roguelike systems.

## D-002 — No mass Java package rename

Decision:
Keep `com.shatteredpixel.shatteredpixeldungeon` core packages for inherited code.
Put new code under `com.elkazajo.lastcaravan`.

Reason:
Mass rename creates enormous merge/debug risk with little gameplay value.

## D-003 — `Hero` and `Dungeon` remain internal names for now

Player-facing:
- Scout
- expedition
- route
- caravan

Internal:
- Hero
- Dungeon
- depth

Reason:
Names are deeply coupled; UI can be re-themed first.

## D-004 — Class system hidden before deletion

No Warrior/Mage/Rogue/etc selection.

Temporary internal class:
- WARRIOR for compatibility if needed

Reason:
Deleting HeroClass/Talent immediately is high risk.

## D-005 — Kill XP is not core progression

Player progression should eventually come from:
- objectives
- exploration
- successful returns

Reason:
The game should reward survival and judgment, not clearing maps.

## D-006 — Room graph remains level-generation backbone

Outdoor maps are still built through:
- Room
- Builder
- Painter

Reason:
Cheap, deterministic, proven generation.

## D-007 — Caravan is persistent, scout is expendable

Long-term identity:
- caravan/journey persists through scout death

Reason:
Creates emotional and strategic continuity.

## D-008 — Five caravan resources only in MVP

- Population
- Food
- Water
- Medicine
- Morale

No:
- wood
- metal
- fuel
- dozens of materials

Reason:
Clarity and scope.

## D-009 — Resource Sharing is the signature system

Current auto-deposit of water is tutorial scaffolding.
Generalized keep/give decisions must be built before MVP is considered complete.

## D-010 — Water before hunger

Implement personal Water survival first.
Then adapt Hunger.

Reason:
Steppe theme and first expedition revolve around water.

## D-011 — Noise reuses Mob AI states

Map:
- Unaware -> Wandering/Sleeping
- Alerted -> Investigating
- Engaged -> Hunting

Do not build a parallel AI state machine.

## D-012 — Minimal content, systemic depth

Prefer 20 meaningful items and 4 enemies over hundreds of shallow variants.

## D-013 — Premium product

No ads/P2W/gacha/energy.

## D-014 — Commit rhythm

One coherent logical slice per commit.
User commits via VS Code UI.
Assistant/Codex only proposes message.
