/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2026 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDAction;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIcon;
import com.shatteredpixel.shatteredpixeldungeon.ui.BuffIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.input.KeyBindings;
import com.watabou.input.KeyEvent;
import com.watabou.noosa.Gizmo;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;
import com.elkazajo.lastcaravan.LastCaravanRun;
import com.elkazajo.lastcaravan.caravan.CaravanState;

import java.util.ArrayList;
import java.util.Locale;

public class WndHero extends WndTabbed {

	private static final int WIDTH = 120;
	private static final int HEIGHT = 120;

	private StatsTab stats;
	private BuffsTab buffs;

	public static int lastIdx = 0;

	public WndHero() {

		super();

		resize(WIDTH, HEIGHT);

		stats = new StatsTab();
		add(stats);

		buffs = new BuffsTab();
		add(buffs);
		buffs.setRect(0, 0, WIDTH, HEIGHT);
		buffs.setupList();

		add(new IconTab(Icons.get(Icons.RANKINGS)) {
			protected void select(boolean value) {
				super.select(value);
				if (selected) {
					lastIdx = 0;
					if (!stats.visible) {
						stats.initialize();
					}
				}
				stats.visible = stats.active = selected;
			}
		});

		add(new IconTab(Icons.get(Icons.BUFFS)) {

			protected void select(boolean value) {

				super.select(value);

				if (selected) {
					lastIdx = 1;
				}

				buffs.visible = buffs.active = selected;
			}
		});

		layoutTabs();

		select(Math.min(lastIdx, 1));
	}

	@Override
	public boolean onSignal(KeyEvent event) {
		if (event.pressed && KeyBindings.getActionForKey(event) == SPDAction.HERO_INFO) {
			onBackPressed();
			return true;
		} else {
			return super.onSignal(event);
		}
	}

	@Override
	public void offset(
			int xOffset,
			int yOffset) {

		super.offset(
				xOffset,
				yOffset);

		buffs.layout();
	}

	private class StatsTab extends Group {

		private static final int GAP = 6;

		private float pos;

		public StatsTab() {
			initialize();
		}

		public void initialize() {

			for (Gizmo g : members) {
				if (g != null) {
					g.destroy();
				}
			}

			clear();

			Hero hero = Dungeon.hero;

			IconTitle title = new IconTitle();

			title.icon(
					HeroSprite.avatar(hero));

			title.label(
					Messages.get(
							"lastcaravan.windows.hero.title").toUpperCase(Locale.ENGLISH));

			title.color(
					Window.TITLE_COLOR);

			title.setRect(
					0,
					0,
					WIDTH,
					0);

			add(title);

			pos = title.bottom()
					+ 2 * GAP;

			// Scout health
			if (hero.shielding() > 0) {

				statSlot(
						Messages.get(
								"lastcaravan.windows.hero.health"),
						hero.HP
								+ "+"
								+ hero.shielding()
								+ "/"
								+ hero.HT);

			} else {

				statSlot(
						Messages.get(
								"lastcaravan.windows.hero.health"),
						hero.HP
								+ "/"
								+ hero.HT);
			}

			LastCaravanRun.Phase phase = LastCaravanRun.phase();

			statSlot(
					Messages.get(
							"lastcaravan.windows.hero.status"),
					Messages.get(
							phase == LastCaravanRun.Phase.CARAVAN
									? "lastcaravan.windows.hero.caravan"
									: "lastcaravan.windows.hero.expedition"));

			statSlot(
					Messages.get(
							"lastcaravan.windows.hero.expedition_number"),
					LastCaravanRun.expeditionNumber() + 1);

			pos += GAP;

			CaravanState caravan = LastCaravanRun.caravan();

			statSlot(
					Messages.get(
							"lastcaravan.windows.hero.population"),
					caravan.population());

			statSlot(
					Messages.get(
							"lastcaravan.windows.hero.food"),
					caravan.food());

			statSlot(
					Messages.get(
							"lastcaravan.windows.hero.water"),
					caravan.water());

			pos += GAP;
		}

		private void statSlot(String label, String value) {

			int size = 8;
			RenderedTextBlock txt;
			do {
				txt = PixelScene.renderTextBlock(label, size);
				size--;
			} while (txt.width() >= WIDTH * 0.55f);
			txt.setPos(0, pos + (6 - txt.height()) / 2);
			PixelScene.align(txt);
			add(txt);

			size = 8;
			do {
				txt = PixelScene.renderTextBlock(value, size);
				size--;
			} while (txt.width() >= WIDTH * 0.45f);
			txt.setPos(WIDTH * 0.55f, pos + (6 - txt.height()) / 2);
			PixelScene.align(txt);
			add(txt);

			pos += GAP + txt.height();
		}

		private void statSlot(String label, int value) {
			statSlot(label, Integer.toString(value));
		}

		public float height() {
			return pos;
		}
	}

	private class BuffsTab extends Component {

		private static final int GAP = 2;

		private float pos;
		private ScrollPane buffList;
		private ArrayList<BuffSlot> slots = new ArrayList<>();

		@Override
		protected void createChildren() {

			super.createChildren();

			buffList = new ScrollPane(new Component()) {
				@Override
				public void onClick(float x, float y) {
					int size = slots.size();
					for (int i = 0; i < size; i++) {
						if (slots.get(i).onClick(x, y)) {
							break;
						}
					}
				}
			};
			add(buffList);
		}

		@Override
		protected void layout() {
			super.layout();
			buffList.setRect(0, 0, width, height);
		}

		private void setupList() {
			Component content = buffList.content();
			for (Buff buff : Dungeon.hero.buffs()) {
				if (buff.icon() != BuffIndicator.NONE) {
					BuffSlot slot = new BuffSlot(buff);
					slot.setRect(0, pos, WIDTH, slot.icon.height());
					content.add(slot);
					slots.add(slot);
					pos += GAP + slot.height();
				}
			}
			content.setSize(buffList.width(), pos);
			buffList.setSize(buffList.width(), buffList.height());
		}

		private class BuffSlot extends Component {

			private Buff buff;

			Image icon;
			RenderedTextBlock txt;

			public BuffSlot(Buff buff) {
				super();
				this.buff = buff;

				icon = new BuffIcon(buff, true);
				icon.y = this.y;
				add(icon);

				txt = PixelScene.renderTextBlock(Messages.titleCase(buff.name()), 8);
				txt.setPos(
						icon.width + GAP,
						this.y + (icon.height - txt.height()) / 2);
				PixelScene.align(txt);
				add(txt);

			}

			@Override
			protected void layout() {
				super.layout();
				icon.y = this.y;
				txt.maxWidth((int) (width - icon.width()));
				txt.setPos(
						icon.width + GAP,
						this.y + (icon.height - txt.height()) / 2);
				PixelScene.align(txt);
			}

			protected boolean onClick(float x, float y) {
				if (inside(x, y)) {
					GameScene.show(new WndInfoBuff(buff));
					return true;
				} else {
					return false;
				}
			}
		}
	}
}
