package pku.ss.zyf.Utils;

import java.util.Timer;
import java.util.TimerTask;

import pku.ss.zyf.mygame_2q48.MainActivity;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-05-05
 * Time: 00:03
 */
public class GameAI {
    public static void aiThink(){
        TimerTask task = new TimerTask(){

            public void run(){

                //execute the task
                MainActivity.setStopFlag();

            }

        };

        Timer timer = new Timer();

        timer.schedule(task, 2000);
    }

}
