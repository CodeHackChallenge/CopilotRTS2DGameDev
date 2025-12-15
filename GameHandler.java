import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List; 
 
 
public class GameHandler {

	public static final int CLASS_WARRIOR = 0, CLASS_SORCERER = 1;
	public static final int RANK_1STAR = 0;
	public static final int TILE_SIZE = 32;
	
	
	private final List<Entity> entities = new ArrayList<>();
	private final RenderSystem renderSystem = new RenderSystem();

	private Engine engine;
	private MouseInput mouseInput;
	private KeyInput keyInput;
	
	private final MovementSystem movementSystem = new MovementSystem();
	private final TileMapSystem tileMapSystem;
	private TileMapComponent tileMap;
	     
	// Viewport offsets
    private int offsetX = 0;
    private int offsetY = 0;
    
	public GameHandler(MouseInput mouseInput, KeyInput keyInput) {
		this.mouseInput = mouseInput;
		this.keyInput = keyInput; 
		
		tileMapSystem = new TileMapSystem();
		loadMap();	
		loadAssets(); 
		
	}
	
	private void loadAssets() {
		//TextureManager.getInstance().preloadAll();
		
//		// Build animations for all 8 directions from soldier spritesheet
//        EnumMap<Direction, Animation> soldierAnims = new EnumMap<>(Direction.class);
//        
//        for (int dirIndex = 0; dirIndex < 8; dirIndex++) {
//            BufferedImage[] frames = new BufferedImage[4];
//            for (int i = 0; i < 4; i++) {
//                frames[i] = SpritesheetManager.getInstance()
//                            .getSubImage("/sprite/walk.png", i * 64, dirIndex * 64, 64, 64);
//                		  //.getSubImage("/sprite/walk.png",  dirIndex * 64, i * 64, 64, 64);
// 
//            }
//            soldierAnims.put(Direction.values()[dirIndex], new Animation(frames, 8));
//        }
        
        SoldierAnimation soldierAnim = new SoldierAnimation();
       // Sprite sprite = new Sprite("/sprite/walk.png", 64, 64);
        Position pos = new Position(TILE_SIZE, TILE_SIZE); // starting pixel position
        BoundsComponent bounds = new BoundsComponent((int)pos.getX(), (int)pos.getY(), 64, 64);
    	        
        Entity soldier = new Entity(CLASS_WARRIOR);
	    
	    soldier.addComponent(pos);
	    soldier.addComponent(soldierAnim);
	    soldier.addComponent(bounds);
	    entities.add(soldier);
	    
	    mouseInput.addEntity(entities);
	}

	private void loadMap() {
		// Load map
        try {
            MapConfig cfg = MapConfig.defaultConfig();
            tileMap = TileMapLoader.loadFromFile("/map/map_96x96.txt", cfg);
        } catch (Exception e) {
            e.printStackTrace();
        }
		
	} 
	
	public void update() {
		// WASD panning
        int panSpeed = 8;
        if (keyInput.isLeft())  offsetX = Math.max(0, offsetX - panSpeed);
        if (keyInput.isRight()) offsetX = Math.min(tileMap.getConfig().widthTiles() * tileMap.getConfig().tileSize() - Engine.WIDTH, offsetX + panSpeed);
        if (keyInput.isUp())    offsetY = Math.max(0, offsetY - panSpeed);
        if (keyInput.isDown())  offsetY = Math.min(tileMap.getConfig().heightTiles() * tileMap.getConfig().tileSize() - Engine.HEIGHT, offsetY + panSpeed);

		 // Move selected entity toward target
        Entity selected = mouseInput.getSelectedEntity();
        Position target = mouseInput.getMoveTarget();
        if (selected != null && target != null) { 
        	 movementSystem.moveTowards(selected, target);
        	 
        	 Position pos = selected.getComponent(Position.class);
             if (pos.getX() == target.getX() && pos.getY() == target.getY()) {
                 mouseInput.clearMoveTarget();
             }

        } 
        // Update animations
        for (Entity e : entities) {
            SoldierAnimation soldierAnim = e.getComponent(SoldierAnimation.class);
            if (soldierAnim != null) {   
                soldierAnim.getCurrentAnimation().update();
            }
        }
	}
	
	public void render(Graphics2D graphics2D) {
		
		// Draw map
        if (tileMap != null) {
            tileMapSystem.render(graphics2D, tileMap, offsetX, offsetY);
            
            
        }
        
        if (keyInput.isDebugOn()) {
        	tileMapSystem.renderGrid(graphics2D, tileMap, offsetX, offsetY);
        }
         
        renderSystem.render(graphics2D, entities, offsetX, offsetY);
		//mouse bounds
		graphics2D.drawRect((int)mouseInput.bounds.getX(), 
							(int)mouseInput.bounds.getY(), 
							(int)mouseInput.bounds.getWidth(), 
							(int)mouseInput.bounds.getHeight());
		
	}
}

