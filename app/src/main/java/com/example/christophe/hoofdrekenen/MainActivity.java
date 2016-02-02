package com.example.christophe.hoofdrekenen;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewSwitcher;
public class MainActivity extends Activity {

    int firstValue, secondValue, resultValue;
    int counter = 0, score = 0;


    enum Operation {add,substract,mulitply, divide}


    TextView firstTextView;
    TextView secondTextView;
    TextView scoreTextView;
    EditText answerEditText;

    String scoreString = "0/0";



    ViewSwitcher switcher;
    TextView myTV;
    EditText myEV;

    int randomWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }

    private  void initViewElements(){
        //init
        scoreTextView = (TextView)findViewById(R.id.scoreView);
        firstTextView = (TextView)findViewById(R.id.firstNumber);
        secondTextView = (TextView)findViewById(R.id.secondNumber);
        answerEditText = (EditText) findViewById(R.id.editText);


        switcher = (ViewSwitcher) findViewById(R.id.my_switcher);
        myTV = (TextView) switcher.findViewById(R.id.clickable_text_view);
        myEV = (EditText) switcher.findViewById(R.id.clickable_edit_view);

        scoreTextView.setText(scoreString);

    }

    private void createListeners(){

        answerEditText.setOnEditorActionListener(new MathResponseListener());
        myEV.setOnEditorActionListener(new EditorViewListener());
        myTV.setOnClickListener(new TextView.OnClickListener(){
            @Override
            public void onClick(View v) {
                TextViewClicked();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViewElements();
        generateNumbers();
        createListeners();

}


    public void TextViewClicked() {
        switcher.showNext(); //or switcher.showPrevious();
    }


    public void generateNumbers(){
        firstValue = randomWithRange(1, 100);
        secondValue = randomWithRange(1, 100);
        resultValue = firstValue + secondValue;

        //  scoreTextView.setText(String.valueOf(resultValue));

        firstTextView.setText(String.valueOf(firstValue));
        secondTextView.setText(String.valueOf(secondValue));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    //Inner classes Listneers

    public class MathResponseListener implements EditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d("onEditorAction","onEditorAction");
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                if (Integer.parseInt(answerEditText.getText().toString()) == resultValue) {
                    ++score;
                }
                ++counter;
                scoreString = score + "/" + counter;
                scoreTextView.setText(scoreString);
                answerEditText.setText("");
                generateNumbers();
                return true;
            }
            return false;
        }
    }

    public class EditorViewListener implements EditText.OnEditorActionListener {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                myTV.setText(myEV.getText());
                TextViewClicked();

                return true;
            }
            return false;
        }
    }

}
