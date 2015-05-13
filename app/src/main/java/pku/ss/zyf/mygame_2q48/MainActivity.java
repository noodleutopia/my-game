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

import java.util.ArrayList;
import java.util.List;

import pku.ss.zyf.Utils.GameAI;
import pku.ss.zyf.bean.Card;
import pku.ss.zyf.bean.Movement;
import pku.ss.zyf.bean.SetCards;


public class MainActivity extends Activity implements View.OnClickListener {

    private TextView gameConditionTv, aiHoldTv, playerHoldTv_1, bottomCardsTv, aiMoveTv, playMoveTv_1, scoreBoardTv;
    private Button startBtn, transformBtn, chargeBtn, transBtn_1, transBtn_2, transBtn_3;
    public static final int GAME_START = 0;
    public static final int PLAYER_1_TURN = 1;
    public static final int AI_TURN = 2;
    public static final int GAME_OVER = 9;
    public static final int GAME_WAIT = 10;

    private static final int START_GAME = 1;
    private static final int WAIT_AI = 3;
    private static final int STOP_GAME = 2;
    private List<Card> bottomCards;     //底牌

    private int nowCondition = GAME_OVER;
    private int winner = 0;
    private int round = 0;   //局数
    private int myWin = 0, aiWin = 0;   //比分
    private List<Integer> transPosition = new ArrayList<>();   //换牌手牌位置
    private List<Integer> chargePosition = new ArrayList<>();  //钓牌手牌位置

    private GamePlay gamePlay = new GamePlay();
    public GamePlay getGamePlay(){
        return this.gamePlay;
    }

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
        scoreBoardTv = (TextView) findViewById(R.id.score_board);

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
        gamePlay.setBottom(bottomCards);

    }

    /**
     * 从底牌堆抓牌
     * @param number 抓牌数量
     * @return 抓的牌
     */
    public List<Card> getBottomTop(int number){
        //若底牌不足一张，则洗牌
        if (bottomCards.size() < 1){
            initCards();
        }
        List<Card> cards = new ArrayList<>();
        while(number > 0){
            cards.add(bottomCards.remove(0));
            number --;
        }
        gamePlay.setBottom(bottomCards);
        return  cards;
    }

    private void initHold(){
        Log.d("TEST","initHold is called !");
//        gameConditionTv.setText("正在发牌...");
        aiMoveTv.setText("抓4张牌");
        playMoveTv_1.setText("抓4张牌");

        List<Card> aiHold = new ArrayList<>();
        List<Card> playerHold = new ArrayList<>();

        //首次分发手牌，每个人4张
        playerHold.addAll(getBottomTop(4));
        aiHold.addAll(getBottomTop(4));

        gamePlay.setAiHold(aiHold);
        gamePlay.setMyHold(playerHold);
        drawHolds();
   }

 /**
     * 玩家回合
     * @param playerNo 玩家编号
     */
    private void playerTurn(int playerNo){
        nowCondition = GAME_WAIT;
        int [] myHoldValue = gamePlay.getMyHoldValue();

        chargePosition = new ArrayList<>();

        //显示换牌按钮
        transformBtn.setVisibility(View.VISIBLE);
        //判断是否显示钓牌按钮
        if (myHoldValue[0] == myHoldValue[1] && myHoldValue[2] == myHoldValue[1] ||
                myHoldValue[2] == myHoldValue[3] && myHoldValue[2] == myHoldValue[1]){
            chargeBtn.setVisibility(View.VISIBLE);
            if (myHoldValue[0] == myHoldValue[1]){
                chargePosition.add(0);
            }else{
                chargePosition.add(1);
            }
        }

    }

    /**
     * AI回合
     */
    private void aiTurn(){
        nowCondition = GAME_WAIT;
        aiMoveTv.setText("AI思考中……");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(bottomCards.size() > 0)
                    new GameAI().aiThink(gamePlay);

                Message msg = Message.obtain();
                msg.what = WAIT_AI;
                mHandler.sendMessageDelayed(msg,1500);
            }
        }).start();
//        gameCondition();
    }

    private void gameCondition(){
        while(nowCondition != GAME_OVER && nowCondition != GAME_WAIT && winner == 0){
            if (nowCondition == GAME_START){
                gameConditionTv.setText("游戏开始！");
                round++;
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
        }
        //判断胜利
        if (winner != 0){
            if (winner == 1){
                gameConditionTv.setText("AI胜利！");
                aiWin ++;
            }
            if (winner == 2){
                gameConditionTv.setText("玩家胜利！");
                myWin ++;
            }
            nowCondition = GAME_OVER;
        }
        if (nowCondition == GAME_OVER){
            transformBtn.setVisibility(View.GONE);
            chargeBtn.setVisibility(View.GONE);
            startBtn.setVisibility(View.VISIBLE);
            startBtn.setText("再玩一局");
            Log.d("TEST","局数： " + round + "\nAI ： PLAYER = " + aiWin +" : "+ myWin);
            scoreBoardTv.setText("比分 Player : AI = " + myWin +" : "+ aiWin);    //更改计分板
            scoreBoardTv.setVisibility(View.VISIBLE);
            for (int i = 0; i < gamePlay.getAiMoveSeq().size(); i++){
                Log.d("TEST","AI行动: " + gamePlay.getAiMoveSeq().get(i).toString());
            }
        }

    }

    /**
     * 显示手牌，判断胜利
     *
     */
    public void drawHolds(){
        bottomCardsTv.setText("底牌数量： " + String.valueOf(bottomCards.size()));
        if (bottomCards.size() == 0){
            initCards();
            bottomCardsTv.setText("底牌数量： " + String.valueOf(bottomCards.size()));
        }

            String aiHoldStr = "";
            for (int cardValue : gamePlay.getAiHoldValue()){
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
            for (int cardValue : gamePlay.getMyHoldValue()){
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
            transPosition = new ArrayList<>();
            int i = 0;
            int j = 0;
            while(i < (myHoldValue.length - 1)){
                if (myHoldValue[i] == myHoldValue[i+1]){
                    if (btnTitle.size() == 0){
                        transPosition.add(j++,i); //记录换牌位置
                        if (myHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }else if (myHoldValue[i] == 16)
                            btnTitle.add("换个2");
                        else
                            btnTitle.add("换个" + myHoldValue[i] * 2);
                    }
                    else if (myHoldValue[i] != myHoldValue[i-1]){
                        transPosition.add(j++, i); //记录换牌位置
                        if (myHoldValue[i] == 8){
                            btnTitle.add("换个Q");
                        }else if (myHoldValue[i] == 16)
                            btnTitle.add("换个2");
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
//            chargePosition = new ArrayList<>();
            List<Card> aiHold = gamePlay.getAiHold();
            List<Card> myHold = gamePlay.getMyHold();
            int i = 0;
            while (aiHold.get(i).getValue() != myHold.get(1).getValue() * 2){
                i++;
                if (i == 4)
                    break;
            }
            if (i < 4){
                //钓牌成功
                Card card = aiHold.remove(i);   //AI移除被钓牌
//                aiHold.add(myHold.get(chargePosition.get(0)));  //AI拿一张钓牌
                aiHold.add(new Card(2));
                gamePlay.setAiHold(aiHold); //更新AI手牌
                myHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
                myHold.remove(chargePosition.get(0) + 1);
                myHold.remove(chargePosition.get(0).intValue());
                myHold.add(card);   //钓到的牌加入手牌
                myHold.addAll(getBottomTop(2));  //抓2张底牌
                gamePlay.setMyHold(myHold);     //更新手牌
                drawHolds();    //绘制手牌
                if (card.getValue() > 8)
                    playMoveTv_1.setText("钓牌成功,钓到一张Q,抓两张牌");
                else
                    playMoveTv_1.setText("钓牌成功,钓到一张" + card.getValue()
                        + ",抓两张牌");
                gamePlay.recordMove(2, new Movement(2, 1, card.getValue(), gamePlay.getMyHold()));    //记录本次行动
                nowCondition = AI_TURN;
                gameCondition();

            }else{
                //钓牌失败
                Card card = aiHold.remove(0);   //AI移除最小的手牌
                aiHold.add(myHold.get(chargePosition.get(0)));  //AI拿一张钓牌
//                aiHold.add(new Card(2));
                gamePlay.setAiHold(aiHold); //更新AI手牌
                myHold.remove(chargePosition.get(0) + 2);    //移除三张手牌
                myHold.remove(chargePosition.get(0) + 1);
                myHold.remove(chargePosition.get(0).intValue());
                myHold.add(card);   //钓到的牌加入手牌
                myHold.addAll(getBottomTop(2));  //抓2张底牌
                gamePlay.setMyHold(myHold);     //更新手牌
                drawHolds();    //绘制手牌
                if (card.getValue() > 8)
                    playMoveTv_1.setText("钓牌失败,钓到一张Q,抓两张牌");
                else
                    playMoveTv_1.setText("钓牌失败,钓到一张" + card.getValue()
                            + ",抓两张牌");
                gamePlay.recordMove(2, new Movement(2, 0, card.getValue(), gamePlay.getMyHold()));    //记录本次行动
                nowCondition = AI_TURN;
                gameCondition();


            }

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
                case WAIT_AI:
                    nowCondition = PLAYER_1_TURN;
                    aiMoveTv.setText(gamePlay.getAiMove());
                    drawHolds();
                    gameCondition();
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
            winner = 0;
            Log.d("TEST",String.valueOf(round));
            if (round > 0){

            }
            Message msg = Message.obtain();
            msg.what = START_GAME;
            mHandler.sendMessage(msg);
        }
        //点击换牌按钮
        if (v.equals(transformBtn)){

            //抓一张牌
            List<Card> cards = getBottomTop(1);
            Card card = cards.get(0);
            gamePlay.setBottom(bottomCards);
            List<Card> myHold = gamePlay.getMyHold();
            myHold.add(card);
            //更新手牌
            gamePlay.setMyHold(myHold);
            drawHolds();
            playMoveTv_1.setText("抓了一张 " + card.getValue() +",准备换牌");
            transformOrCharge(1);
            v.setVisibility(View.GONE);
            chargeBtn.setVisibility(View.GONE);

        }
        //点击钓牌按钮
        if (v.equals(chargeBtn)){
            v.setVisibility(View.GONE);
            transformBtn.setVisibility(View.GONE);

            transformOrCharge(2);


        }

        //点击换牌按钮1
        if (v.equals(transBtn_1)){
            v.setVisibility(View.GONE);
            transBtn_2.setVisibility(View.GONE);
            //更新手牌
            List<Card> playerHold = gamePlay.getMyHold();
            Card card = new Card();
            if (transPosition.size() > 0){
                if (playerHold.get(transPosition.get(0)).getValue() == 16)
                    card = new Card(2); //新增一张高一级的牌
                else
                    card = new Card(playerHold.get(transPosition.get(0)).getValue() * 2); //新增一张高一级的牌
                playerHold.remove(transPosition.get(0) + 1);    //移除两张手牌
                playerHold.remove(transPosition.get(0).intValue());
                playerHold.add(card);
            }
            gamePlay.setMyHold(playerHold);
            drawHolds();
            if (card.getValue() > 8)
                playMoveTv_1.setText("换到了一张Q");
            else
                playMoveTv_1.setText("换到了一张 " + card.getValue());
            gamePlay.recordMove(2, new Movement(1, card.getValue(), gamePlay.getMyHold()));   //记录本次行动
            //转换状态
            nowCondition = AI_TURN;
            gameCondition();
        }
        //点击换牌按钮2
        if (v.equals(transBtn_2)){
            v.setVisibility(View.GONE);
            transBtn_1.setVisibility(View.GONE);
            //更新手牌
            List<Card> playerHold = gamePlay.getMyHold();
            Card card = new Card();
            if (transPosition.size() > 1){
                if (playerHold.get(transPosition.get(1)).getValue() == 16)
                    card = new Card(2); //新增一张高一级的牌
                else
                    card = new Card(playerHold.get(transPosition.get(1)).getValue() * 2); //新增一张高一级的牌
                playerHold.remove(transPosition.get(1) + 1);    //移除两张手牌
                playerHold.remove(transPosition.get(1).intValue());
                playerHold.add(card);

            }
            gamePlay.setMyHold(playerHold);
            drawHolds();
            if (card.getValue() > 8)
                playMoveTv_1.setText("换到了一张Q");
            else
                playMoveTv_1.setText("换到了一张 " + card.getValue());
            gamePlay.recordMove(2, new Movement(1, card.getValue(), gamePlay.getMyHold()));   //记录本次行动
            nowCondition = AI_TURN;
            gameCondition();
        }
    }
}
