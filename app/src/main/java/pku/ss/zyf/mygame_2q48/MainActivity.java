package pku.ss.zyf.mygame_2q48;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pku.ss.zyf.bean.Card;
import pku.ss.zyf.bean.SetCards;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView gameConditionTv, aiHoldTv, playerHoldTv_1, bottomCardsTv, aiMoveTv, playMoveTv_1 ;
    private Button startBtn, transformBtn, chargeBtn;
    public static final int GAME_START = 0;
    public static final int PLAYER_1_TURN = 1;
    public static final int AI_TURN = 2;
    public static final int GAME_OVER = 9;
    public static final int GAME_WAIT = 10;

    private static final int START_GAME = 1;
    private static final int STOP_GAME = 2;
    private List<Card> bottomCards;

    private int nowCondition = GAME_OVER;
//    private int nowbottomCards = 44;
    private int winner = 0;
    private int flag = 0;

    private GamePlay gamePlay = new GamePlay();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView(){
        gameConditionTv = (TextView) findViewById(R.id.game_condition);
        bottomCardsTv = (TextView) findViewById(R.id.bottom_cards);
        aiHoldTv = (TextView) findViewById(R.id.ai_hold_cards);
        playerHoldTv_1 = (TextView) findViewById(R.id.player_1_hold_cards);
        aiMoveTv = (TextView) findViewById(R.id.ai_move);
        playMoveTv_1 = (TextView) findViewById(R.id.player_1_move);

        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(this);
        transformBtn = (Button) findViewById(R.id.transform_card);
        transformBtn.setOnClickListener(this);
        chargeBtn = (Button) findViewById(R.id.charge_card);
        chargeBtn.setOnClickListener(this);

    }

    /**
     * 洗底牌
     */
    private void initCards(){
        bottomCards  = new ArrayList<Card>();
        Log.d("TEST","initCards is called !");
        SetCards setCards = SetCards.getInstance();
        //初始化底牌
        for (int i = 0; i < setCards.getCardsNumber(); i++ ){
            bottomCards.add(setCards.getCard(i));
        }

    }

    private void initHold(){
        Log.d("TEST","initHold is called !");
        gameConditionTv.setText("正在发牌...");
        aiMoveTv.setText("抓4张牌");
        playMoveTv_1.setText("抓4张牌");

        Card [] aiHold = new Card[5];
        Card [] playerHold = new Card[5];

        for (int i = 0; i < 5; i++){
            playerHold[i] = new Card(0);
            aiHold[i] = new Card(0);
        }
        //首次分发手牌，每个人4张
        for (int i = 0; i < 4; i++){
                playerHold[i].setValue(bottomCards.remove(0).getValue());
            }
        for (int i = 0; i < 4; i++){
                aiHold[i].setValue(bottomCards.remove(0).getValue());
            }
//        if (flag % 2 == 0){
//            for (int i = 0; i < 4; i++){
//                playerHold[i].setValue(bottomCards.remove(i).getValue());
//            }
//            for (int i = 0; i < 4; i++){
//                bottomCards.remove(0);
//                if (i == 0)
//                    aiHold[i].setValue(2);
//                if (i == 1)
//                    aiHold[i].setValue(4);
//                if (i == 2)
//                    aiHold[i].setValue(8);
//                if (i == 3)
//                    aiHold[i].setValue(16);
//            }
//        }else{
//            for (int i = 0; i < 4; i++){
//                aiHold[i].setValue(bottomCards.remove(i).getValue());
//            }
//            for (int i = 0; i < 4; i++){
//                bottomCards.remove(0);
//                if (i == 0)
//                    playerHold[i].setValue(2);
//                if (i == 1)
//                    playerHold[i].setValue(4);
//                if (i == 2)
//                    playerHold[i].setValue(8);
//                if (i == 3)
//                    playerHold[i].setValue(16);
//            }
//        }


        gamePlay.setAiHold(aiHold);
        gamePlay.setMyHold(playerHold);

    }

    private void playerTurn(int playerNo){
        nowCondition = GAME_WAIT;
        int [] myHoldValue = gamePlay.getMyHoldValue();
        int chooseMove; //行动标记，1为换牌，2为钓牌

        transformBtn.setVisibility(View.VISIBLE);
        chargeBtn.setVisibility(View.VISIBLE);


        //判断当前手牌可能行动
        if (myHoldValue[0] == myHoldValue[1]){
            //可以换牌
            if(myHoldValue[1] == myHoldValue[2]){
                //可以钓牌
            }
        }
        else if (myHoldValue[1] == myHoldValue[2]){
            //可以换牌
            if (myHoldValue[2] == myHoldValue[3]){
                //可以钓牌
            }
        }
        else if(myHoldValue[2] == myHoldValue[3]){
            //可以换牌
        }

    }

    private void aiTurn(){
        nowCondition = GAME_WAIT;
    }

    private void gameCondition(){
        while(nowCondition != GAME_OVER && nowCondition != GAME_WAIT){
            if (nowCondition == GAME_START){
                gameConditionTv.setText("游戏开始！");
                initCards();
                initHold();
                nowCondition = PLAYER_1_TURN;
            }
            else if (nowCondition == PLAYER_1_TURN){
                gameConditionTv.setText("你的回合");
                playerTurn(1);
            }
            else if (nowCondition == AI_TURN){
                gameConditionTv.setText("AI回合");
                aiTurn();
            }

            //显示手牌
            if (gamePlay.getMyHoldValue() != null && gamePlay.getAiHoldValue() != null){
                drawHolds(gamePlay.getAiHoldValue(), gamePlay.getMyHoldValue());
            }
            //判断胜利
            if (winner != 0){
                if (winner == 1){
                    gameConditionTv.setText("AI胜利！");
                }
                if (winner == 2){
                    gameConditionTv.setText("玩家胜利！");
                }
                nowCondition = GAME_OVER;
            }
        }
        if (nowCondition == GAME_OVER){
            startBtn.setVisibility(View.VISIBLE);
            startBtn.setText("再玩一局");
            flag ++;
        }
    }

    /**
     * 显示手牌，判断胜利
     * @param aiHold ai的手牌
     * @param myHold 玩家手牌
     */
    public void drawHolds(int[] aiHold, int[] myHold){
        bottomCardsTv.setText("底牌数量： " + String.valueOf(bottomCards.size()));
        if (bottomCards.size() == 0){
            initCards();
            bottomCardsTv.setText("底牌数量： " + String.valueOf(bottomCards.size()));
        }

            String aiHoldStr = "";
            for (int cardValue : aiHold){
                if (cardValue != 0 && cardValue != 16){
                    aiHoldStr += String.valueOf(cardValue);
                }
                else if (cardValue == 16){
                    aiHoldStr += "Q";
                }
            }
            aiHoldTv.setText(aiHoldStr);
            if (aiHoldStr.equals("248Q")){
                winner = 1;
            }

            String myHoldStr = "";
            for (int cardValue : myHold){
                if (cardValue != 0 && cardValue != 16){
                    myHoldStr += String.valueOf(cardValue);
                }else if (cardValue == 16){
                    myHoldStr += "Q";
                }
            }
            playerHoldTv_1.setText(myHoldStr);
        if (myHoldStr.equals("248Q")){
            winner = 2;
        }

    }

    /**
     * 主线程消息处理机
     */
    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_GAME:
                    gameCondition();
                    break;
                case STOP_GAME:
                    Toast.makeText(MainActivity.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
                default:
                    break;
            }
        }
    };
    private void gamePlay(){

    }

    @Override
    public void onClick(View v) {
        if (v.equals(startBtn)){
            startBtn.setVisibility(View.GONE);
            nowCondition = GAME_START;

            Log.d("TEST",String.valueOf(flag));
            Message msg = new Message();
            msg.what = START_GAME;
            mHandler.sendMessage(msg);
        }
        if (v.equals(transformBtn)){
            playMoveTv_1.setText("准备换牌");
            //抓一张牌
            Card card = bottomCards.remove(0);
            Card [] myHold = gamePlay.getMyHold();
            myHold[4] = card;
            //更新手牌
            gamePlay.setMyHold(myHold);
            drawHolds(gamePlay.getAiHoldValue(), gamePlay.getMyHoldValue());
        }
        if (v.equals(chargeBtn)){

        }
    }
}
