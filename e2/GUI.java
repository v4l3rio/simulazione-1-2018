package e2;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;


public class GUI extends JFrame {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// private final List<JButton> buttons = new ArrayList<>();
	private final Map<JButton,Pair<Integer, Integer>> cords = new HashMap<>();

	private Pair<Integer, Integer> horsePosition = positionRandom();
	private Pair<Integer, Integer> pownPosition = positionRandom();

	public GUI() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500, 500);

		JPanel panel = new JPanel(new GridLayout(5, 5));
		this.getContentPane().add(BorderLayout.CENTER, panel);

		ActionListener al = (e) -> {
			final JButton bt = (JButton) e.getSource();
			
			if(verifyMove(cords.get(bt))) {
				horsePosition = new Pair<>(cords.get(bt).getX(), cords.get(bt).getY());
				if(horsePosition.equals(pownPosition)) {
					System.out.println("Hai vinto");
					System.exit(1);
				}
				Iterator<JButton> iter = cords.keySet().iterator();
				while(iter.hasNext()) {
					JButton button = iter.next();
					if(button.getText().equals("K")) {
						button.setText("");
					}
				}
				
				bt.setText("K");
				
			}

		};

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 5; i++) {
				final JButton jb = new JButton(" ");
				if (horsePosition.getX().equals(i) && horsePosition.getY().equals(j)) {
					jb.setText("K");
					System.out.println("Le coordinate del cavallo sono: " + i + ", " + j);
				}
				if (pownPosition.getX().equals(i) && pownPosition.getY().equals(j)) {
					jb.setText("*");
					System.out.println("Le coordinate del pedone sono: " + i + ", " + j);
				}
				jb.addActionListener(al);
				// this.buttons.add(jb);
				this.cords.put(jb,new Pair<Integer, Integer>(i, j));
				panel.add(jb);
			}
		}

		this.setVisible(true);
	}

	private Pair<Integer, Integer> positionRandom() {

		Random rnd = new Random();

		return new Pair<Integer, Integer>(rnd.nextInt(5), rnd.nextInt(5));

	}

	private boolean verifyMove(Pair<Integer, Integer> position) {

		/*
		 * -2,-1 -1,-2 +2,-1 -1,+2 -2,+1 +1,-2 +2,+1 +1,+2
		 */

		int diffX = Math.abs(horsePosition.getX() - position.getX());
		int diffY = Math.abs(horsePosition.getY() - position.getY());

		if (diffX != diffY) {
			if ((diffX == 2 && diffY == 1) || (diffX == 1 && diffY == 2)) {
				return true;
			}

		}
		return false;

	}

}
