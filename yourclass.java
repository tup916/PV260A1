import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Window;
import java.util.List;

public class yourclass extends Core {

	List<Player> players;
	private static int moveAmount = 5;

	public void init() {

		super.init();

		Window w = screenManager.getFullScreenWindow();
		w.addKeyListener(new KeyListenerHandler(players));
		w.addMouseListener(new MouseListenerHandler());
		w.addMouseMotionListener(new MouseMotionListenerHandler());
	}

	public static void main(String[] args) {

		new yourclass().run();
	}

	public void draw(Graphics2D g) {

		for (Player player : players) {
			switch (player.getCurrentDirection()) {
			case UP:
				if (player.getCentreY() > 0) {
					player.setCentreY(player.getCentreY() - moveAmount);
				}
				else {
					player.setCentreY(screenManager.getHeight());
				}
				break;
			case RIGHT:
				if (player.getCentreX() < screenManager.getWidth()) {
					player.setCentreX(player.getCentreX() + moveAmount);
				}
				else {
					player.setCentreX(0);
				}
				break;
			case DOWN:
				if (player.getCentreY() < screenManager.getHeight()) {
					player.setCentreY(player.getCentreY() + moveAmount);
				}
				else {
					player.setCentreY(0);
				}
				break;
			case LEFT:
				if (player.getCentreX() > 0) {
					player.setCentreX(player.getCentreX() - moveAmount);
				}
				else {
					player.setCentreX(screenManager.getWidth());
				}
				break;
			}

		}

		exitOnCollision();

		for (Player player : players) {
			player.appendPath(player.getCentreX(), player.getCentreY());
		}

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenManager.getWidth(), screenManager.getHeight());

		for (Player player : players) {
			for (Coordinates coordinate : player.getPath()) {
				g.setColor(player.getColor());
				g.fillRect(coordinate.getX(), coordinate.getY(), 10, 10);
			}
		}
	}

	private void exitOnCollision() {

		for (Player playerA : players) {
			for (Player playerB : players) {
				if (!playerA.equals(playerB)) {
					for (int i = 0; i < playerA.getPath().size(); i++) {
						Coordinates a = playerA.getCoordinateAt(i);
						Coordinates b = playerB.getCoordinateAt(i);
						if (((playerA.getCentreX() == a.getX()) && (playerA.getCentreY() == a.getY()))
								|| ((playerB.getCentreX() == b.getX()) && (playerB.getCentreY() == b.getY()))
								|| ((playerA.getCentreX() == b.getX()) && (playerA.getCentreY() == b.getY()))
								|| ((playerB.getCentreX() == a.getX()) && (playerB.getCentreY() == a.getY()))) {
							System.exit(0);
						}
					}

				}
			}

		}
	}
}
