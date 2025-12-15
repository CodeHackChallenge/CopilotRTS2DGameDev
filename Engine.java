import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;
import javax.swing.JPanel;
 

public class Engine extends Canvas implements Runnable {

	
	public static final int UPS = 200;
	public static final int FPS = 120;//120;
	 
	private JFrame window;
	private JPanel panel;
	private BufferStrategy buffereStrategy;
	
	private final String title ="RTS with Copilot dev v.1"; 
	private	boolean isRunning = true; 
	
	public static final int WIDTH = 1200; 
	public static final int	HEIGHT = 720; 
	
	private Thread thread;
	private GameHandler handler;
	private MouseInput mouseInput;
    private KeyInput keyInput;   
	//private long targetTime = 1000 / FPS;
	     
//	//Class Objects 
//	private Game game = new Game();
// 	private RenderSystem render = new RenderSystem(game);
// 	private UpdateSystem update = new UpdateSystem(game);
 	
	public Engine(){
		window = new JFrame(title);
		panel = (JPanel) window.getContentPane();
		//panel.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		panel.setLayout(null);
		panel.add(this);
		
		setBounds(0,0,WIDTH,HEIGHT);
		setIgnoreRepaint(true);
		
		window.pack();
		//window.setResizable(false);
		window.setVisible(true);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		keyInput = new KeyInput();
		mouseInput = new MouseInput(this);
		handler = new GameHandler(mouseInput, keyInput);
		       
		
		addMouseListener(mouseInput);
        addMouseMotionListener(mouseInput);
		addKeyListener(keyInput);
		requestFocus();
		
		createBufferStrategy(2);
		buffereStrategy = getBufferStrategy();
				
	}   
	 
	public void update(double dt){ //how to use dt here?
		 	  
		handler.update();
		
	}
	public void render(){
		Graphics2D graphics2D = (Graphics2D) buffereStrategy.getDrawGraphics();
		graphics2D.setColor(Color.GRAY);
		graphics2D.fillRect(0, 0, WIDTH, HEIGHT);
		/***************draw here******************************/ 
		 
		handler.render(graphics2D);
		
		/********************end*************************/
		graphics2D.dispose();
		buffereStrategy.show();
		
	}
	
	public void tick(){}
	
	private final double UPDATE_RATE = 120.0;
	private final double NS_PER_UPDATE = 1_000_000_000.0 / UPDATE_RATE; //120fps
	
	public void run() {		 
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;
		long previousTime = System.nanoTime();
		
		int frames = 0;			
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		double deltaU = 0;
		double deltaF = 0;
		
		while(isRunning) {
			long currentTime = System.nanoTime();
			
			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if(deltaU >= 1) {
				update(NS_PER_UPDATE / 1_000_000_000.0);

				updates++;
				deltaU--;
			}
			
			if(deltaF >= 1) {
				//gameTime += (double) 1 / FPS;
				render();
				
				frames++;
				deltaF--;
			}
	 
			
			 if(System.currentTimeMillis() - lastCheck >= 1000) {
				 lastCheck = System.currentTimeMillis();
				 
				 System.out.println("FPS:"+frames+" | UPS:"+updates);
				 frames = 0;
				 updates = 0;
			 }
					
		}//while
		
		try {
			Thread.sleep(1);
			
		}catch(InterruptedException e) {}
		
	}
		
	public void start(){
		thread = new Thread(this);
		thread.start();
	}
	public void stop(){
		if(!isRunning)
			return;
		else
			isRunning = false;
		System.exit(1);
	}
	
	public static void main(String[] args){
		new Engine().start();
	} 
	 
}

