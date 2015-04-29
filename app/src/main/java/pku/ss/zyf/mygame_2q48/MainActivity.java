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
    private Button startBtn, transformBtn, chargeBtn, transBtn_1, transBtn_2, transBtn_3;
    public static final int GAME_START = 0;
    public static final int PLAYER_1_TURN = 1;
    public static final int AI_TURN = 2;
    public static final int GAME_OVER = 9;
    public static final int GAME_WAIT = 10;

    private static final int START_GAME = 1;
    private static final int STOP_GAME = 2;
    private List<Card> bottomCards;     //底牌

    private int nowCondition = GAME_OVER;
    private int stopFlag = 0;
    private int winner = 0;
    private int flag = 0;
    private int[] transPosition = new int[2];   //换牌手牌位置
    private int[] chargePosition = new int[2];  //钓牌手牌位置

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
        transBtn_1 = (Button) findViewById(R.id.transform_1);
        transBtn_1.setOnClickListener(this);
        transBtn_2 = (Button) findViewById(R.id.transform_2);
        transBtn_2.setOnClickListener(this);
        transBtn_3 = (Button) findViewById(R.id.transform_3);
        transBtn_3.setOnClickListener(this);

    }

    /**
     * 洗底牌
     */
    private void initCards(){
        bottomCards  = new ArrayList<Card>();
        Log.d("TEST", "initCards is called !");
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

        List<Card> aiHold = new ArrayList<Card>();
        List<Card> playerHold = new ArrayList<Card>();

//        for (int i = 0; i < 5; i++){
//            playerHold[i] = new Card(0);
//            aiHold[i] = new Card(0);
//        }
        //首次分发手牌，每个人4张
        for (int i = 0; i < 4; i++){
                playerHold.add(bottomCards.remove(0));
            }
        for (int i = 0; i < 4; i++){
                aiHold.add(bottomCards.remove(0));
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

    /**
     * 玩家回合
     * @param playerNo 玩家编号
     */
    private void playerTurn(int playerNo){
        nowCondition = GAME_WAIT;
        int [] myHoldValue = gamePlay.getMyHoldValue();

        transformBtn.setVisibility(View.VISIBLE);
        chargeBtn.setVisibility(View.VISIBLE);

    }

    /**
     * AI回合
     */
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
     * 换牌或钓牌逻辑
     * @param flag 标记
     */
    private void transformOrCharge(int flag){
        int [] myHoldValue = gamePlay.getMyHoldValue();
        //若换牌，手牌一定有5张
        if (flag == 1){
            List<String> btnTitle = new ArrayList<>();
            int i = 0;
            int j = 0;
            while(i < (myHoldValue.length - 1)){
                if (myHoldValue[i] == myHoldValue[i+1]){
                    if (btnTitle.size() == 0){
                        transPosition[j++] = i; //记录换牌位置
                        if (myHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }
                        else
                            btnTitle.add("换个" + myHoldValue[i] * 2);
                    }
                    else if (myHoldValue[i] != myHoldValue[i-1]){
                        transPosition[j++] = i; //记录换牌位置
                        if (myHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }
                        else
                            btnTitle.add("换个" + myHoldValue[i] * 2);
                    }
                }
                i++;
            }//endWhile;
            //显示按钮
            if (btnTitle.size() > 0){
                transBtn_1.setVisibility(View.VISIBLE);
                transBtn_1.setText(btnTitle.get(0));
                if (btnTitle.size() > 1){
                    transBtn_2.setVisibility(View.VISIBLE);
                    transBtn_2.setText(btnTitle.get(1));
                }
            }
        }

        //若钓牌
        if (flag == 2){

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
        //点击开始按钮
        if (v.equals(startBtn)){
            startBtn.setVisibility(View.GONE);
            nowCondition = GAME_START;

            Log.d("TEST",String.valueOf(flag));
            Message msg = new Message();
            msg.what = START_GAME;
            mHandler.sendMessage(msg);
        }
        //点击换牌按钮
        if (v.equals(transformBtn)){

            //抓一张牌
            Card card = bottomCards.remove(0);
//            Log.d("TEST","bottomTop is : " + card.getValue());
            List<Card> myHold = gamePlay.getMyHold();
            myHold.add(card);
            //更新手牌
            gamePlay.setMyHold(myHold);
            drawHolds(gamePlay.getAiHoldValue(), gamePlay.getMyHoldValue());
            playMoveTv_1.setText("抓了一张 " + card.getValue() +",准备换牌");
            transformOrCharge(1);
            v.setVisibility(View.GONE);
            chargeBtn.setVisibility(View.GONE);

        }
        //点击钓牌按钮
        if (v.equals(chargeBtn)){
            playMoveTv_1.setText("准备钓牌");
            v.setVisibility(View.GONE);
            transformBtn.setVisibility(View.GONE);
        }

        if (v.equals(transBtn_1)){
            v.setVisibility(View.GONE);
            transBtn_2.setVisibility(View.GONE);
            //更新手牌
            List<Card> playerHold = gamePlay.getMyHold();
            Card card = new Card();
            if (transPosition.length > 0){
                card = new Card(playerHold.get(transPosition[0]).getValue() * 2); //新增一张高一级的牌
                playerHold.add(card);
                playerHold.remove(transPosition[0] + 1);    //移除两张手牌
                playerHold.remove(transPosition[0]);
            }
            gamePlay.setMyHold(playerHold);
            drawHolds(gamePlay.getAiHoldValue(), gamePlay.getMyHoldValue());
            playMoveTv_1.setText("换到了一张 " + card.getValue());
            //转换状态
            nowCondition = AI_TURN;
            gameCondition();
        }
        if (v.equals(transBtn_2)){
            v.setVisibility(View.GONE);
            transBtn_1.setVisibility(View.GONE);
            //更新手牌
            List<Card> playerHold = gamePlay.getMyHold();
            Card card = new Card();
            if (transPosition.length > 1){
                card = new Card(playerHold.get(transPosition[1]).getValue() * 2); //新增一张高一级的牌
                playerHold.add(card);
                playerHold.remove(transPosition[1] + 1);    //移除两张手牌
                playerHold.remove(transPosition[1]);
            }
            gamePlay.setMyHold(playerHold);
            drawHolds(gamePlay.getAiHoldValue(), gamePlay.getMyHoldValue());
            playMoveTv_1.setText("换到了一张 " + card.getValue());
            //转换状态
            nowCondition = AI_TURN;
            gameCondition();
        }
    }
}
