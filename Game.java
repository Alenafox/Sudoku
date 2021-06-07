package com.example.sudoku;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

class Game extends BaseAdapter {

    private final Context ctx;
    private final Integer mrows = 9, mcols = 9;
    private final int[][] numberArray = new int[mrows][mcols];
    private int[][] arrayRand;
    int k = 0;

    private final ArrayList<Integer> arrNumbers;
    int[] openPosition = new int [mrows*mcols];

    public Game(Context context, int cols, int rows){
        ctx = context;
        arrNumbers = new ArrayList<Integer>();
        createField();
    }

    @Override
    public int getCount() {
        return mrows*mcols;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d("mytag", "k = "+ k);
        TextView textView;
        if (view == null) {
            textView = new TextView(ctx);
        }else{
            textView = (TextView) view;
        }
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 30);
        textView.setPadding(30,3,0,0);
        if (arrNumbers.get(position) != null) {
            textView.setText(arrNumbers.get(position).toString());
        }
        else
            textView.setText(" ");
        if (k > 0 && k < 84)
            textView.setTextColor(Color.parseColor("#FF3700B3"));
        else
            textView.setTextColor(Color.parseColor("#008B8B"));
        return textView;
    }

    private void createField(){

        init(); //инициализацирую массив

        shiftNumbers(3,1);  // сдвигаю элементы
        shiftNumbers(6,2);
        shiftNumbers(1,3);
        shiftNumbers(4,4);
        shiftNumbers(7,5);
        shiftNumbers(2,6);
        shiftNumbers(5,7);
        shiftNumbers(8,8);

        transpose(numberArray); //транспонирую матрицу
        shake(); //перемешиваю
        transpose(numberArray);

        for (int i = 0; i < mrows; i++){
            for (int j = 0; j < mcols; j++){
                arrNumbers.add(numberArray[i][j]);
                k++;
            }
        }
        arrayRand = numberArray;
        Random r = new Random();
        int i = 0;
        while(i < 5){
            int i2 = r.nextInt(80);
            arrNumbers.set(i2, null);
            openPosition[i] = i2;
            arrayRand[getRow(i2)][getCol(i2)] = -1;
            i++;
        }
    }
    public int getCol(int pos){
        if (pos <= 8){
            return pos;
        } else {
            return pos % 9;
        }
    }
    public int getRow(int pos){
        int row =1;
        if (pos <= 8){
            return 0;
        } else {
            while (pos >= 0 && pos < 9)
                row++;
            while (pos >= 9){
                pos = pos - 9;
                row++;
            }
            return row-1;
        }
    }
    private void transpose(int array[][]){
        for (int i = 0; i < mrows; i++){
            for (int j = 0; j < mcols; j++){
                int tmp = array[i][j];
                array[i][j] = array[j][i];
                array[j][i] = tmp;
            }
        }
    }
    private void shiftNumbers(int count, int row){
        int index;

        for(int j = 0; j < mcols; j++){
            index = (j + count) % 9 + 1;
            numberArray[row][j] = index;
        }
    }
    private void init(){
        for (int i = 0; i < mrows; i++){
            for (int j = 0; j < mcols; j++){
                numberArray[i][j] = j + 1;
            }
        }
    }
    private void shake(){
        int i = 0;
        do {
            int tmpArr[] = numberArray[i];
            int tmpArr2[] = numberArray[i+1];

            numberArray[i] = numberArray[i+2];
            numberArray[i+1] = tmpArr;
            numberArray[i+2] = tmpArr2;

            i += 3;
        } while(i < mrows);
    }
    public boolean checkRepeats(String openPosition){
        int repeatX = 0;
        int repeatY = 0;

        int number = Integer.parseInt(openPosition.substring(3));
        Log.d("mytag", "number: " + number);
        for (int i = 0; i < mrows; i++){
            for (int j = 0; j < mcols; j++){
                if (arrayRand[i][j] == number)
                    repeatX++;
                if (arrayRand[j][i] == number)
                    repeatY++;
            }
            if (repeatX >= 2 || repeatY >= 2)
                return true;

            repeatX = 0;
            repeatY = 0;
        }
        return false;
    }
    public boolean checkWin(){
        int i1 = 0,i2 = 0, i3 = 0, i4 = 0, i5 = 0, i6 = 0, i7 = 0, i8 = 0, i9 = 0;
        for (int i = 0; i < mrows; i++){
            for (int j = 0; j < mcols; j++){
                if (arrayRand[i][j] == 1) i1++;
                if (arrayRand[i][j] == 2) i2++;
                if (arrayRand[i][j] == 3) i3++;
                if (arrayRand[i][j] == 4) i4++;
                if (arrayRand[i][j] == 5) i5++;
                if (arrayRand[i][j] == 6) i6++;
                if (arrayRand[i][j] == 7) i7++;
                if (arrayRand[i][j] == 8) i8++;
                if (arrayRand[i][j] == 9) i9++;
            }
        }
        if (i1 == 9 && i2 == 9 && i3 == 9 && i4 == 9 && i5 == 9 && i6 == 9 && i7 == 9 && i8 == 9 && i9 == 9)
            return true;
        return false;
    }
    public void setNumber(int pos, String selectedBtn){
        for (int i = 0; i < openPosition.length; i++) {
            if (openPosition[i] == pos) {
                int selectedNumberBtn = Integer.parseInt(selectedBtn.substring(3));
                arrNumbers.set(pos, selectedNumberBtn);
                arrayRand[getRow(pos)][getCol(pos)] = Integer.parseInt(selectedBtn.substring(3));
                notifyDataSetChanged();
            }
        }
    }
    public void clearField(int pos) {
        arrNumbers.set(pos, null);
    }
}
