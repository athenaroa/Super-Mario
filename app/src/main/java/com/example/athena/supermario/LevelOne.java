package com.example.athena.supermario;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.ArrayList;

public class LevelOne {

    Bitmap background;
    private int backFrame;

    public int lives;
    Bitmap heart;
    private float marioLeftX, marioLeftY, marioRightX, marioRightY;

    private int screenWidth, screenHeight;

    private ArrayList<Bitmap> lifeArray;
    private ArrayList<Rect> coinLoc;
    private ArrayList<Rect> blockLoc;
    private ArrayList<Rect> flowerLoc;

    private int score;

    private Coins coin;
    private Blocks block;
    private FireFlower flower;
    private float marioNewYPos;
    private float blockXPos;
    private int hitType;
    private int marioForm;

    public LevelOne(Context context, int screenX, int screenY) {

        score = 0;
        marioLeftX = 0;
        marioLeftY = 0;
        marioRightX = 0;
        marioRightY = 0;
        marioNewYPos = 0;
        blockXPos = 0;
        marioForm = 1;


        coin = new Coins(context, screenX, screenY);
        block = new Blocks(context, screenX, screenY);
        flower = new FireFlower(context, screenX,screenY);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
        heart = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        screenWidth = screenX;
        screenHeight = screenY;


        lifeArray = new ArrayList<>(2);
        lifeArray.add(heart);
        lifeArray.add(heart);
        lifeArray.add(heart);

        coinLoc = coin.levelOneCoinLoc(screenX,screenY);
        blockLoc =  block.levelOneBlockLoc(screenX,screenY);
        flowerLoc =  flower.levelOneFlowerLoc(screenX,screenY);
    }

    public void updateCoinPos( int move){
        for(int i = 0; i < coinLoc.size(); i++){
            Rect newPos = coinLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            coinLoc.set(i,newPos);
        }
    }

    public void updateBlockPos( int move){
        for(int i = 0; i < blockLoc.size(); i++){
            Rect newPos = blockLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            blockLoc.set(i,newPos);
        }
    }

    public void updateFlowerPos(int move){
        for(int i = 0; i < blockLoc.size(); i++){
            Rect newPos = blockLoc.get(i);
            newPos.set(newPos.left + move, newPos.top,newPos.right + move,newPos.bottom);
            blockLoc.set(i,newPos);
        }
    }

    public Bitmap getbackground() {
        return background;
    }

    public int getLives() {
        return lives;
    }

    public int getScore() {
        return this.score;
    }

    public ArrayList<Bitmap> getLifeArray() {
        return lifeArray;
    }

    public ArrayList<Rect> getCoinLoc() {
        return coinLoc;
    }

    public ArrayList<Rect> getBlockLoc() {
        return blockLoc;
    }

    public ArrayList<Rect> getFlowerLoc() {
        return flowerLoc;
    }

    public Bitmap getCoinBitmap(){
        return coin.getCoinBitmap();
    }

    public Bitmap getBlockBitmap(){
        return block.getBlockBitmap();
    }

    public Bitmap getFlowerBitmap() {return flower.getFlowerBitmap();}

    public void updateMarioVar(float marioXPos, float marioYPos, int marioWidth, int marioHeight){
        this.marioLeftX = marioXPos;
        this.marioLeftY = marioYPos;
        this.marioRightX = marioXPos + marioWidth;
        this.marioRightY = marioYPos + marioHeight;
    }

    public int updateMarioForm(){
        return marioForm;

    }


    public void update(float marioXPos, float marioYPos, int marioWidth, int marioHeight, int backFrame){
        updateMarioVar(marioXPos,marioYPos, marioWidth, marioHeight); //locally updates mario's position
        this.backFrame = backFrame;
        marioHitCoin();
        marioHitFlower();
        //updateMarioVar(marioXPos,marioYPos, marioWidth, marioHeight);
    }

    public void marioHitFlower(){
        //Removing collected flowers
        if(flowerLoc != null) {
            for (int i = 0; i < flowerLoc.size(); i++) {
                Rect f = flowerLoc.get(i);

                //Mario hits left of flower
                if ((marioRightX >= f.left) && (marioRightX <= f.right) && (marioRightY >= f.top) && (marioLeftY <= f.bottom)) {
                    if(i == 0) {
                        flowerLoc = null;
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                    else {
                        flowerLoc.remove(i);
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                }

                //Mario hits right of flower
                if ((marioLeftX <= f.right) && (marioLeftX >= f.left) && (marioLeftY <= f.bottom) && (marioRightY >= f.top)) {
                    if(i == 0) {
                        flowerLoc = null;
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                    else {
                        flowerLoc.remove(i);
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                }

                //Mario hits bottom on coin
                if ((marioLeftX <= f.right) && (marioRightX >= f.left) && (marioLeftY <= f.bottom) && (marioLeftY >= f.top)) {
                    if (i == 0) {
                        flowerLoc = null;
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    } else {
                        flowerLoc.remove(i);
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }

                }

                //Mario hits top of coin
                if ((marioLeftX <= f.right) && (marioRightX >= f.left) && (marioRightY >= f.top) && (marioRightY <= f.bottom)) {
                    if(i == 0) {
                        flowerLoc = null;
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                    else {
                        flowerLoc.remove(i);
                        score += flower.getValue();
                        marioForm = 3;
                        break;
                    }
                }
            }
        }
    }

    public void marioHitCoin(){

        //Removing collected coins
        if(coinLoc != null) {
            for (int i = 0; i < coinLoc.size(); i++) {
                Rect c = coinLoc.get(i);

                //Mario hits left of coin
                if ((marioRightX >= c.left) && (marioRightX <= c.right) && (marioRightY >= c.top) && (marioLeftY <= c.bottom)) {
                    if(coinLoc.size() == 1) {
                        coinLoc = null;
                        score += coin.getValue();
                        break;
                    }
                    else {
                        coinLoc.remove(i);
                        score += coin.getValue();
                        break;
                    }
                }

                //Mario hits right of coin
                if ((marioLeftX <= c.right) && (marioLeftX >= c.left) && (marioLeftY <= c.bottom) && (marioRightY >= c.top)) {
                    if(coinLoc.size() == 1) {
                        coinLoc = null;
                        score += coin.getValue();
                        break;
                    }
                    else {
                        coinLoc.remove(i);
                        score += coin.getValue();
                        break;
                    }
                }

                //Mario hits bottom on coin
                if ((marioLeftX <= c.right) && (marioRightX >= c.left) && (marioLeftY <= c.bottom) && (marioLeftY >= c.top)) {
                    if(coinLoc.size() == 1) {
                        coinLoc = null;
                        score += coin.getValue();
                        break;
                    }
                    else {
                        coinLoc.remove(i);
                        score += coin.getValue();
                        break;
                    }
                }

                //Mario hits top of coin
                if ((marioLeftX <= c.right) && (marioRightX >= c.left) && (marioRightY >= c.top) && (marioRightY <= c.bottom)) {
                    if(coinLoc.size() == 1) {
                        coinLoc = null;
                        score += coin.getValue();
                        break;
                    }
                    else {
                        coinLoc.remove(i);
                        score += coin.getValue();
                        break;
                    }
                }


            }
        }
    }

    public boolean marioOnBlock(){

        boolean result = false;
        float marioCenterX = marioLeftX + (marioRightX-marioLeftX)/2;


        for(int i = 0; i < blockLoc.size(); i++)
        {
            Rect b = blockLoc.get(i);
            //Mario hits top of block
            if((marioCenterX <= b.right) && (marioCenterX >= b.left ) && (marioRightY <= b.top))
            {
                System.out.println("On top of Block i: " + i);
                result = true;
                System.out.println("Mario in on top of Block");
                break;
            }
        }

        return result;
    }

    public boolean marioHitBlock(int marioWidth, int marioHeight){
        boolean output = false;

        for(int i = 0; i < blockLoc.size(); i++)
        {
            Rect b = blockLoc.get(i);

            //Mario hits left of block
            if((marioRightX  >= b.left)  && (marioRightX <= b.right) && (marioRightY >= b.top) && (marioLeftY <= b.bottom))
            {
                output = true;
                marioNewYPos = b.top;
                blockXPos = b.left;
                System.out.println("Mario hits from Left");
                hitType = 1;
            }

            //Mario hits right of block
            else if((marioLeftX <= b.right) && (marioLeftX >= b.left) && (marioLeftY <= b.bottom) && (marioRightY >= b.top))
            {
                output = true;
                marioNewYPos = b.top;
                blockXPos = b.left;
                System.out.println("Mario Hits from right");
                hitType = 2;

            }

            //Mario hits bottom on block
            else if((marioLeftX <= b.right) && (marioRightX >= b.left) && (marioLeftY <= b.bottom) && (marioLeftY >= b.top))
            {
                output = true;
                marioNewYPos = b.bottom;
                blockXPos = b.left;
                System.out.println("Mario hits from bottom");
                hitType = 3;

            }

            //Mario hits top of block
            else if((marioLeftX <= b.right) && (marioRightX >= b.left) && (marioRightY >= b.top) && (marioRightY <= b.bottom))
            {
                output = true;
                marioNewYPos = b.top;
                blockXPos = b.left;
                System.out.println("Mario hits from top");
                hitType = 4;

            }
            else
            {
                //System.out.println("WENT INTO ELSE: LEVEL ONE BLOCK CHECK");
            }


            if(output == true){
                break;
            }

        }
        return output;
    }

    public float getHitBlockLoc(){ //returns the upper left position of the block
        return marioNewYPos;
    }

    public int getHitType() {
        return hitType;
    }

    public float getBlockXPos() {
        return blockXPos;
    }

    public int getBlockWidth(){
        return block.getBlockWidth();
    }
}

