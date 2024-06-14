import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Tetris {

    private Game game = null;

    public static void main(String[] args) {
        System.out.println("starting");
        Frame frame = new Frame("Tetris");
        final Game game = new Game();

        game.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("PCE " + evt.getPropertyName() + " " + evt.getNewValue());
            }
        });

        final TextArea taHiScores = new TextArea("", 10, 10, TextArea.SCROLLBARS_NONE);

        taHiScores.setBackground(Color.black);
        taHiScores.setForeground(Color.white);
        taHiScores.setFont(new Font("monospaced", 0, 11));
        taHiScores.setText(" 득점자 점수판                  \n" +
                " -----------------------------\n\n" +
                " PLAYER     LEVEL    SCORE    \n\n" +
                " 김씨         12 1  50280     \n" +
                " 양씨         12 1  50280     \n"
        );
        taHiScores.setEditable(false);

        final TextField txt = new TextField();
        txt.setEnabled(false);

        game.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                if (evt.getPropertyName().equals("state")) {
                    int state = ((Integer) evt.getNewValue()).intValue();
                    if (state == Game.STATE_GAMEOVER) {
                        txt.setEnabled(true);
                        txt.requestFocus();
                        txt.addActionListener(new ActionListener() {
                            public void actionPerformed(ActionEvent e) {
                                txt.setEnabled(false);
                                game.init();
                            }
                        });
                    }
                }
            }
        });

        Button btnStart = new Button("Start");
        btnStart.setFocusable(false);
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                game.start();
            }
        });

        final Container c = new Container();
        c.setLayout(new BorderLayout());
        c.add(txt, BorderLayout.NORTH);
        c.add(game.getSquareBoardComponent(), BorderLayout.CENTER);
        c.add(btnStart, BorderLayout.SOUTH);

        final Container c2 = new Container();
        c2.setLayout(new GridLayout(1, 2));
        c2.add(c);
        c2.add(taHiScores);

        frame.add(c2);

        System.out.println("packing");

        frame.pack();

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.show();
    }
}