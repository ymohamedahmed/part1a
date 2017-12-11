package uk.ac.cam.rkh23.oop.tick5;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUILife extends JFrame implements ListSelectionListener {
	private World mWorld;
	private PatternStore mStore;
	private ArrayList<World> mCachedWorlds = new ArrayList<World>();
	private int generationInCache = 0;
	private GamePanel mGamePanel;
	private JButton mPlayButton;
	private Timer mTimer;
	private boolean mPlaying;

	public GUILife(PatternStore ps) {
		super("Game of Life");
		mStore = ps;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024, 768);

		add(createPatternsPanel(), BorderLayout.WEST);
		add(createControlPanel(), BorderLayout.SOUTH);
		add(createGamePanel(), BorderLayout.CENTER);

	}

	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch, title);
		component.setBorder(tb);
	}

	private JPanel createGamePanel() {
		mGamePanel = new GamePanel();
		addBorder(mGamePanel, "Game Panel");
		return mGamePanel;
	}

	private JPanel createPatternsPanel() {
		JPanel patt = new JPanel();
		addBorder(patt, "Patterns");
		patt.setLayout(new GridLayout());
		// TODO
		List<Pattern> arrList = mStore.getPatternsNameSorted();
		Pattern[] arr = new Pattern[arrList.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = arrList.get(i);
		}
		JList<Pattern> list = new JList<Pattern>(arr);
		list.addListSelectionListener(this);
		JScrollPane sPanel = new JScrollPane(list);
		patt.add(sPanel);
		return patt;
	}

	private void runOrPause() {
		if (mPlaying) {
			mTimer.cancel();
			mPlaying = false;
			mPlayButton.setText("Play");
		} else {
			mPlaying = true;
			mPlayButton.setText("Stop");
			mTimer = new Timer(true);
			mTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						moveForward(false);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			}, 0, 500);
		}
	}

	private JPanel createControlPanel() {
		JPanel ctrl = new JPanel();
		addBorder(ctrl, "Controls");
		ctrl.setLayout(new GridLayout());
		// TODO
		JButton back = new JButton("< Back");
		back.addActionListener(e -> moveBack());
		mPlayButton = new JButton("Play");
		mPlayButton.addActionListener(e -> runOrPause());
		JButton forward = new JButton("Forward >");
		forward.addActionListener(e -> {
			try {
				moveForward(true);
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
		});
		ctrl.add(back);
		ctrl.add(mPlayButton);
		ctrl.add(forward);
		return ctrl;
	}

	private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
		if (!useCloning) {
			if (mWorld instanceof PackedWorld) {
				return new PackedWorld((PackedWorld) mWorld);
			} else if (mWorld instanceof ArrayWorld) {
				return new ArrayWorld((ArrayWorld) mWorld);
			}
		} else {
			return (World) mWorld.clone();
		}
		return null;
	}

	private void moveBack() {
		if (mPlaying) {
			runOrPause();
		} else {
			if (mWorld.getGenerationCount() > 0) {
				mWorld = mCachedWorlds.get(mWorld.getGenerationCount() - 1);
			} else {
				mWorld = mCachedWorlds.get(0);
			}
			mGamePanel.display(mWorld);
		}
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (!mPlaying) {
			JList<Pattern> list = (JList<Pattern>) e.getSource();
			Pattern p = list.getSelectedValue();
			// TODO
			// Based on size, create either a PackedWorld or ArrayWorld
			// from p. Clear the cache, set mWorld and put it into
			// the now-empty cache. Tell the game panel to display
			// the new mWorld.
			if (p.getWidth() * p.getHeight() > 64) {
				try {
					mWorld = new ArrayWorld(p);
				} catch (PatternFormatException e1) {
					e1.printStackTrace();
				}
			} else {
				try {
					mWorld = new PackedWorld(p);
				} catch (PatternFormatException e1) {
					e1.printStackTrace();
				}
			}
			generationInCache = 0;
			mCachedWorlds.clear();
			mCachedWorlds.add(mWorld);
			mGamePanel.display(mWorld);
		} else {
			runOrPause();
		}
	}

	private void moveForward(boolean forwardClicked) throws CloneNotSupportedException {
		if (mPlaying && forwardClicked) {
			runOrPause();
		} else {
			if (mWorld.getGenerationCount() < generationInCache) {
				mWorld = mCachedWorlds.get(mWorld.getGenerationCount() + 1);
			} else {
				World copy = copyWorld(true);
				copy.nextGeneration();
				mCachedWorlds.add(copy);
				mWorld = copy;
				generationInCache++;
			}
			mGamePanel.display(mWorld);
		}
	}

	public static void main(String[] args) throws IOException {
		GUILife gui = new GUILife(new PatternStore("http://www.cl.cam.ac.uk/teaching/1617/OOProg/ticks/life.txt"));
		gui.setVisible(true);

	}

}
