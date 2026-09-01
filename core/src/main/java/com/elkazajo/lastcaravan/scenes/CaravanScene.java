package com.elkazajo.lastcaravan.scenes;

import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.TitleScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.ExitButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.watabou.noosa.Camera;
import com.watabou.utils.RectF;
import com.elkazajo.lastcaravan.LastCaravanRun;
import com.elkazajo.lastcaravan.caravan.CaravanState;

public class CaravanScene extends PixelScene {

    {
        inGameScene = true;
    }

    @Override
    public void create() {

        super.create();

        RectF insets = getCommonInsets();

        float usableWidth = Camera.main.width - insets.left - insets.right;

        float usableHeight = Camera.main.height - insets.top - insets.bottom;

        int textWidth = (int) Math.min(180, usableWidth - 20);

        RenderedTextBlock title = PixelScene.renderTextBlock(
                Messages.get(
                        "lastcaravan.scenes.caravanscene.title"),
                12);

        title.maxWidth(textWidth);

        title.setPos(
                insets.left
                        + (usableWidth - title.width()) / 2f,
                insets.top + usableHeight * 0.25f);

        align(title);
        add(title);

        CaravanState caravan = LastCaravanRun.caravan();

        RenderedTextBlock summary =
        PixelScene.renderTextBlock(
                Messages.get(
                        "lastcaravan.scenes.caravanscene.summary",
                        caravan.population(),
                        caravan.food(),
                        caravan.water(),
                        caravan.medicine(),
                        caravan.morale()
                ),
                8
        );

        summary.maxWidth(textWidth);

        summary.setPos(
                insets.left
                        + (usableWidth - summary.width()) / 2f,
                title.bottom() + 12);

        align(summary);
        add(summary);

        ExitButton exitButton = new ExitButton();

        exitButton.setPos(
                Camera.main.width
                        - exitButton.width()
                        - insets.right,
                insets.top);

        add(exitButton);

        fadeIn();
    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(
                TitleScene.class);
    }
}