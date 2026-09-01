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
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.StartScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ActionIndicator;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Game;

import java.util.Locale;

public class WndGameInProgress extends Window {

	private static final int WIDTH = 120;

	private int GAP = 6;

	private float pos;

	public WndGameInProgress(final int slot) {

		final GamesInProgress.Info info = GamesInProgress.check(slot);

		IconTitle title = new IconTitle();
		title.icon(
				Icons.get(Icons.ENTER));
		title.label(
				Messages.get(
						"lastcaravan.windows.gameinprogress.scout").toUpperCase(Locale.ENGLISH));
		title.color(Window.TITLE_COLOR);
		title.setRect(0, 0, WIDTH, 0);
		add(title);

		if (info.challenges > 0)
			GAP -= 2;

		pos = title.bottom() + GAP;

		if (info.challenges > 0) {
			RedButton btnChallenges = new RedButton(Messages.get(this, "challenges")) {
				@Override
				protected void onClick() {
					Game.scene().add(new WndChallenges(info.challenges, false));
				}
			};
			btnChallenges.icon(Icons.get(Icons.CHALLENGE_COLOR));
			float btnW = btnChallenges.reqWidth() + 2;
			btnChallenges.setRect((WIDTH - btnW) / 2, pos, btnW, 18);
			add(btnChallenges);

			pos = btnChallenges.bottom() + GAP;
		}

		pos += GAP;

		if (info.shld > 0) {

			statSlot(
					Messages.get(
							"lastcaravan.windows.gameinprogress.health"),
					info.hp
							+ "+"
							+ info.shld
							+ "/"
							+ info.ht);

		} else {

			statSlot(
					Messages.get(
							"lastcaravan.windows.gameinprogress.health"),
					info.hp
							+ "/"
							+ info.ht);
		}

		pos += GAP;

		statSlot(
				Messages.get(
						"lastcaravan.windows.gameinprogress.status"),
				Messages.get(
						info.atCaravan
								? "lastcaravan.windows.gameinprogress.caravan"
								: "lastcaravan.windows.gameinprogress.expedition"));

		statSlot(
				Messages.get(
						"lastcaravan.windows.gameinprogress.population"),
				info.caravanPopulation);

		statSlot(
				Messages.get(
						"lastcaravan.windows.gameinprogress.food"),
				info.caravanFood);

		statSlot(
				Messages.get(
						"lastcaravan.windows.gameinprogress.water"),
				info.caravanWater);

		statSlot(
				Messages.get(
						"lastcaravan.windows.gameinprogress.expedition_number"),
				info.expeditionNumber);

		pos += GAP;

		RedButton cont = new RedButton(Messages.get(this, "continue")) {
			@Override
			protected void onClick() {
				super.onClick();

				GamesInProgress.curSlot = slot;

				Dungeon.hero = null;
				Dungeon.daily = Dungeon.dailyReplay = false;
				ActionIndicator.clearAction();
				InterlevelScene.mode = InterlevelScene.Mode.CONTINUE;
				ShatteredPixelDungeon.switchScene(InterlevelScene.class);
			}
		};

		RedButton erase = new RedButton(Messages.get(this, "erase")) {
			@Override
			protected void onClick() {
				super.onClick();

				ShatteredPixelDungeon.scene().add(new WndOptions(Icons.get(Icons.WARNING),
						Messages.get(WndGameInProgress.class, "erase_warn_title"),
						Messages.get(WndGameInProgress.class, "erase_warn_body"),
						Messages.get(WndGameInProgress.class, "erase_warn_yes"),
						Messages.get(WndGameInProgress.class, "erase_warn_no")) {
					@Override
					protected void onSelect(int index) {
						if (index == 0) {
							Dungeon.deleteGame(slot, true);
							ShatteredPixelDungeon.switchNoFade(StartScene.class);
						}
					}
				});
			}
		};

		cont.icon(Icons.get(Icons.ENTER));
		cont.setRect(0, pos, WIDTH / 2 - 1, 20);
		add(cont);

		erase.icon(Icons.get(Icons.CLOSE));
		erase.setRect(WIDTH / 2 + 1, pos, WIDTH / 2 - 1, 20);
		add(erase);

		resize(WIDTH, (int) cont.bottom() + 1);
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
}
