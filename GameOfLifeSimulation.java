import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JPanel;
/**
 * Gra w życie. Komponent gry w życie.
 * @author Adam Tomaja
 *
 */
class GameOfLifeSimulation extends JPanel{
		private static final long serialVersionUID = 3208847195315663369L;
		
		/**
		 * Flaga decydująca o zakończeniu wątku symulacji
		 */
		public boolean running = false;
		/**
		 * Mapa podstawowa
		 */
		boolean [][] map = new boolean[128][128];
		/**
		 * Tymczasowa mapa do przechowywania następnej iteracji
		 */
		boolean [][] newMap = new boolean[map.length][map[0].length];
		Random rand = new Random();
		/**
		 * Czas pomiędzy iteracjami symulacji
		 */
		int delay = 100;
		/**
		 * Mapa możliwych lokalizacji sąsiadów komórki
		 */
		int [][] neigboursLocations = {{-1,-1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
		/**
		 * Pokazywanie siatki
		 */
		boolean grid = true;
		/**
		 * Konstruktor
		 */
		public GameOfLifeSimulation(){
			super();
			generateRect(50, 50, 100, 100);
		}
		/**
		 * Pobiera szerokość planszy
		 * @return
		 */
		public int getGridWidth(){
			return map[0].length;
		}
		/**
		 * Pobiera wysokość planszy
		 * @return
		 */
		public int getGridHeight(){
			return map.length;
		}
		/**
		 * Włącza lub wyłącza widok siatki
		 * @param gridVisibility
		 */
		public void setGridVisibility(boolean gridVisibility){
			this.grid = gridVisibility;
			repaint();
		}
		/**
		 * Czyści planszę
		 */
		public void clearGrid(){
			for(int y = 0; y < map.length; y++ )
				for(int x = 0; x < map[0].length; x++)
					newMap[y][x] = map[y][x] = false;
			repaint();
		}
		/**
		 * Rysuje aktualny widok symulacji
		 */
		public void paint(Graphics g){
			
			int yBlockSize = (int)Math.round(getHeight() / (map.length * 1.0f));
			int xBlockSize = (int)Math.round(getWidth() / (map[0].length * 1.0f));
			
			g.clearRect(0, 0, getWidth(), getHeight());
			
			g.setColor(Color.red);
			
			for(int y = 0; y < map.length; y++)
				for(int x = 0; x < map[0].length; x++)
				{
					if(map[y][x])
						g.setColor(Color.black);
					else
						g.setColor(Color.white);
					
					g.fillRect(x*xBlockSize, y*yBlockSize, xBlockSize, yBlockSize);
				}
			
			if(grid){
				g.setColor(Color.gray);
				for(int y = 0; y < map.length; y++)
					g.drawLine(0, y * yBlockSize, getGridWidth() * xBlockSize, y * yBlockSize);
				for(int x = 0; x < map[0].length + 1; x++) 
					g.drawLine(x * xBlockSize, 0, x * xBlockSize, yBlockSize * getGridHeight());
			}
		}
		/**
		 * Pobiera czas w milisekundach między iteracjami symulacji
		 * @return
		 */
		public int getDelay(){
			return delay;
		}
		/**
		 * Ustawia czas w milisekundach pomiędzy iteracjami symulacji
		 * @param delay
		 */
		public void setDelay(int delay){
			this.delay = delay;
		}
		/**
		 * Generuje całkowicie losową planszę
		 */
		public void generateRandomMap()
		{	
			for(int y = 0; y < map.length; y++)
				for(int x = 0; x < map[0].length; x++)
					map[y][x] = false;
			
			int count = rand.nextInt(map.length * map[0].length);
			for(int i = 0; i < count; i++ )
			{
				int y = rand.nextInt(map.length);
				int x = rand.nextInt(map[0].length);
				
				map[y][x] = true;
			}
////			
//			map[5][5] = true;
//			map[6][5] = true;
//			map[7][5] = true;
//			
//			System.out.println(isLiving(5, 6));
//			System.out.println(getLivingNeighbours(5, 6));
			
			repaint();
		}
		/**
		 * Generuje wypełniony kwadrat na środku planszy
		 */
		public void generateCenterRect(){
			int width = (int)Math.round(10.f/100.f*getGridWidth());
			int height = (int)Math.round(10.f/100.f*getGridHeight());
			
			for(int y = getGridHeight() / 2 - height/2; y < getGridHeight() / 2 + height/2; y++)
				for(int x = getGridWidth() / 2 - width / 2; x < getGridWidth() / 2 + width / 2; x++)
					map[y][x] = true;
			
			repaint();	
		}
		/**
		 * Generuje wzór linii na planszy. 
		 */
		public void generateLines(){
			for(int x = 0; x < map[0].length; x+=2)
				for(int y = 0; y < map.length; y++)
					map[y][x] = true;
			repaint();
		}
		/**
		 * Generuje określony prostokąt na planszy
		 * @param x - pozycja x
		 * @param y - pozycja y
		 * @param width - szerokość
		 * @param height - wysokość
		 */
		public void generateRect(int x, int y, int width, int height){
			for(int Y = y; Y < map.length && Y < (y + height); Y++ )
				for(int X = x; X < map[0].length && X <( x + width); X++ )
					map[Y][X] = true;
		}
		/**
		 * Generuje losowe prostokąty na planszy
		 */
		public void generateRandomRects(){
			int count = rand.nextInt(10);
			for(int i = 0; i < count; i++)
				generateRect(rand.nextInt(getGridWidth()), rand.nextInt(getGridHeight()), rand.nextInt(100), rand.nextInt(100));
			repaint();
		}
		/**
		 * Generuje wzór funkcji sinus na planszy
		 */
		public void generateSinus(){
			double start = Math.PI / 2;
			double end = 3./2 * Math.PI;
			double x = start;
			for(int step = 0; step < map[0].length; step++){
				x += 0.05;
				
				double y = Math.sin(x);
				
				map[(int)(y * 20) + 50][step] = true;
				
			}
			repaint();
		}
		/**
		 * Sprawdza czy podana komórka żyje czy nie.
		 * @param x
		 * @param y
		 * @return true - jeśli komórka żyje, false - jeśli komórka jest martwa, bądź kordynaty wychodzą poza planszę
		 */
		public boolean isLiving(int x, int y){
			if(x >= 0 && y >= 0 && x < map[0].length && y < map.length)
				return map[y][x];
			else
				return false;
		}
		/**
		 * Pobiera liczbę żyjących sąsiadów komórki o podanej pozycji
		 * @param x
		 * @param y
		 * @return
		 */
		public int getLivingNeighbours(int x, int y){
			int living = 0;
			for(int [] neighbour : neigboursLocations)
				if(isLiving(x + neighbour[0], y + neighbour[1]))
					living++;
			
			return living;
		}
		/**
		 * Kolejna iteracja symulacji
		 */
		public void tick(){
			for(int y = 0; y < map.length; y++)
				for(int x = 0; x < map[0].length; x++){
					if(map[y][x]){
						//Komórka żywa
						int livingNeigbours = getLivingNeighbours(x, y);
						if(livingNeigbours > 3 || livingNeigbours < 2)
							newMap[y][x] = false;
						else
							newMap[y][x] = true;
					} else 
						if(getLivingNeighbours(x, y) == 3)
							newMap[y][x] = true;
				}
			
			for(int y = 0; y < map.length; y++)
				for(int x = 0; x < map[0].length; x++)
					map[y][x] = newMap[y][x];
		}
		/**
		 * Uruchamia wątek symulacji
		 */
		public void start()
		{
			running = true;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try{
						while(running){
							tick();
							repaint();
							Thread.sleep(delay);
						}
					} catch (InterruptedException e){
						System.err.println("Wątek symulacji został przerwany z powodu: " + e.getMessage());
					}
					
				}
			}).start();
		}
		/**
		 * Zatrzymuje wątek iteracji
		 */
		public void stop(){
			running = false;
		}
	}