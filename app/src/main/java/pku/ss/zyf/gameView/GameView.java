package pku.ss.zyf.gameView;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import pku.ss.zyf.mygame_2q48.GamePlay;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-29
 * Time: 14:41
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GamePlay gamePlay;

    public GameView(Context context) {
        super(context);
        this.getHolder().addCallback(this);
        gamePlay = new GamePlay();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }
}