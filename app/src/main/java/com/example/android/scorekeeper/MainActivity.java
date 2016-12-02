package com.example.android.scorekeeper;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Player player1 = new Player("Player1");
    private Player player2 = new Player("Player2");

    private TextView mResults;
    private TextView p1Set, p2Set;
    private TextView p1Points, p2Points;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mResults = (TextView) findViewById(R.id.result);
        p1Set = (TextView) findViewById(R.id.player1_set);
        p2Set = (TextView) findViewById(R.id.player2_set);
        p1Points = (TextView) findViewById(R.id.player1_points);
        p2Points = (TextView) findViewById(R.id.player2_points);

        Button p1Scores = (Button) findViewById(R.id.player1_score);
        Button p2Scores = (Button) findViewById(R.id.player2_score);
        p1Scores.setOnClickListener(this);
        p2Scores.setOnClickListener(this);

        mResults.setText(getResult());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.player1_score:
                player2.setAdvantage(false);
                updatePlayer(player1, player2);
                break;
            case R.id.player2_score:
                player1.setAdvantage(false);
                updatePlayer(player2, player1);
                break;
        }
    }

    private void updatePlayer(Player p1, Player p2) {
        if (p1.getPoints() == 3 && p2.getPoints() == 3) {
            if (p1.getAdvantage()) {
                p1.incPoints();
                p2.looseGame();
            } else {
                Toast.makeText(MainActivity.this, "Deuce, " + p1.getName() + " has advantage", Toast.LENGTH_SHORT).show();
                p1.setAdvantage(true);
                p2.setAdvantage(false);
            }
        } else {
            p1.incPoints();
            p2.setAdvantage(false);

            if (p1.getPoints() == 0) p2.looseGame();
        }
        //End of set
        if ((p1.getSetValue() == 6 && p2.getSetValue() < 5) || p1.getSetValue() == 7) {
            p1.nextSet(true);
            p2.nextSet(false);
            if (p1.getWinSets() == 2) {
                p1.decSets();
                p2.decSets();
                showWinner(p1.getName(), getResult());
            }
        }
        updatePlayerView();
        mResults.setText(getResult());
    }

    private void updatePlayerView() {
        p1Points.setText(getPointValue(player1.getPoints()) + "p");
        p1Set.setText(String.valueOf(player1.getSetValue()));
        p2Points.setText(getPointValue(player2.getPoints()) + "p");
        p2Set.setText(String.valueOf(player2.getSetValue()));
    }

    private String getResult() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  ");
        for (int i = 0; i <= player1.getSet(); i++) {
            stringBuilder.append(player1.getMatchRes()[i]).append("-").append(player2.getMatchRes()[i]);
            stringBuilder.append("  ");
        }
        return stringBuilder.toString();
    }

    private void showWinner(final String player, String s) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(player + " WON!!!");
        builder.setMessage(s);
        builder.setCancelable(false);
        builder.setPositiveButton("Repeat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                player1.reset();
                player2.reset();
                updatePlayerView();
                mResults.setText(getResult());
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String getPointValue(int points) {
        switch (points) {
            case 1:
                return "15";
            case 2:
                return "30";
            case 3:
                return "40";
            default:
                return String.valueOf(points);
        }
    }
}
