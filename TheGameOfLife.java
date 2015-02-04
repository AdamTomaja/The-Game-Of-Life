import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
 
/**
 * Klasa okna głównego
 * @author unborn
 *
 */
public class TheGameOfLife extends JFrame{
	private static final long serialVersionUID = 8006837774925481688L;
	GameOfLifeSimulation display;
	@SuppressWarnings("unchecked")
	public TheGameOfLife() {
		setSize(800, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		display = new GameOfLifeSimulation();
		display.setSize(getWidth(), getHeight() - 100);
		getContentPane().add(display);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setEnabled(false);
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.start();
				btnStart.setEnabled(false);
				btnStop.setEnabled(true);
				System.out.println("Started.");
			}
		});
		btnStart.setBounds(10, 728, 89, 23);
		getContentPane().add(btnStart);
		
		
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				display.stop();
				btnStop.setEnabled(false);
				btnStart.setEnabled(true);
			}
		});
		btnStop.setBounds(109, 728, 89, 23);
		getContentPane().add(btnStop);
		
		JSlider slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				display.setDelay(slider.getValue());
			}
		});
		
		slider.setMinimum(10);
		slider.setMaximum(500);
		slider.setBounds(313, 728, 200, 23);
		getContentPane().add(slider);
		
		JCheckBox chckbxSiatka = new JCheckBox("Siatka");
		chckbxSiatka.setToolTipText("Włączanie i wyłączanie siatki");
		chckbxSiatka.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				display.setGridVisibility(chckbxSiatka.isSelected());
			}
		});
		chckbxSiatka.setBounds(519, 728, 69, 23);
		getContentPane().add(chckbxSiatka);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Preset preset = (Preset)comboBox.getSelectedItem();
				switch(preset){
				case CENTER_RECTANGLE:
					display.generateCenterRect();
					break;
				case RANDOM:
					display.generateRandomMap();
					break;
				case LINES:
					display.generateLines();
					break;
				case RANDOM_RECTS:
					display.generateRandomRects();
					break;
				case SINUS:
					display.generateSinus();
					break;
				}
			}
		});
		 
	 
		comboBox.setModel(new DefaultComboBoxModel(Preset.values()));
		comboBox.setBounds(594, 729, 121, 20);
		getContentPane().add(comboBox);
		
		JButton btnNewButton_1 = new JButton("Czyść");
		btnNewButton_1.setToolTipText("Czyści planszę");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display.clearGrid();
			}
		});
		btnNewButton_1.setBounds(208, 728, 89, 23);
		getContentPane().add(btnNewButton_1);
		
		JLabel lblZaadujPreset = new JLabel("Załaduj preset");
		lblZaadujPreset.setBounds(594, 706, 81, 14);
		getContentPane().add(lblZaadujPreset);
		
		display.setDelay(slider.getValue());
		
		JLabel lblCzasPomidzyIteracjami = new JLabel("Czas pomiędzy iteracjami");
		lblCzasPomidzyIteracjami.setBounds(313, 711, 200, 14);
		getContentPane().add(lblCzasPomidzyIteracjami);
	}
	/**
	 * Początek programu
	 * @param args
	 */
	public static void main(String[] args) {
		TheGameOfLife game = new TheGameOfLife();
		game.setTitle("The game of life");
		game.setVisible(true);
	}
}
