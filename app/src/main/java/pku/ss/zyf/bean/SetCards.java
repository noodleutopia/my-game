package pku.ss.zyf.bean;

import android.util.Log;

import java.util.Random;

/**
 * User: ZhangYafei(261957725@qq.com)
 * Date: 2015-04-27
 * Time: 16:02
 */
public class SetCards {


    // 声明一副扑克牌
    private Card cards[] = new Card[44];
    private static SetCards cardsInstance = null;
    private int position[] = new int[44];

    private SetCards() {
        setCard();
        shuffle();
    }

    public static SetCards getInstance() {
        cardsInstance = new SetCards();
        return cardsInstance;
    }

    public int getCardsNumber(){
        return cards.length;
    }

    public Card[] getAllCards(){
        return cards;
    }

    public Card getCard(int cardNo){
        Card card = cards[cardNo];
        return card;
    }

    /**
     *  给44张牌赋值，其中：
     * 【2】有28张，【4】有10张，【8】有6张
     * */
    private void setCard() {

        for (int i = 0; i < 28; i++) {
            cards[i] = new Card(2);
        }
        for (int i = 28; i < 38; i++) {
           cards[i] = new Card(4);
        }
        for (int i = 38; i < 44; i++) {
            cards[i] = new Card(8);
        }

        for (int i = 0; i < 44; i++ ){
           position[i] = i;
        }

    }

    /** 洗牌 */
    private void shuffle() {
        Random rdm = new Random();
        Card[] temp = new Card[44];

        for (int i = 0; i < 44; i++) {
            temp[i] = new Card(0);
            // random.nextInt();是个前闭后开的方法：0~43
            int rdmNo = rdm.nextInt(44);
            int tempNo = position[i];
            position[i] = position[rdmNo];
            position[rdmNo] = tempNo;
        }
        for (int i = 0; i < 44; i++){
            temp[(position[i])].setValue(cards[i].getValue());
        }
        cards = temp;
        for (int i = 0; i<44; i++){
            Log.d("TEST", String.valueOf(cards[i].getValue()));
        }
    }
}
