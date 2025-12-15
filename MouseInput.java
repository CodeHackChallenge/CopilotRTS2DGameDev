import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List; 
 
public class MouseInput extends MouseAdapter { 
    
	public Rectangle bounds;
	private	int mouseX, mouseY, mouseWidth = 8, mouseHeight =16;
	 
	private Engine engine;
	private List<Entity> entities;
	private Entity selectedEntity;
    private Position moveTarget;
    
    public MouseInput(Engine engine) {
    	this.engine = engine;
    	bounds = new Rectangle(0, 0, mouseWidth, mouseHeight);
    }
    
    @Override
    public void mouseDragged(MouseEvent event) {
    	mouseX = event.getX()  ;
    	mouseY = event.getY()  ;
    	bounds = createBounds(mouseX, mouseY, mouseWidth, mouseHeight);
    }
    @Override
    public void mouseMoved(MouseEvent event) {
    	mouseX = event.getX()  ;
    	mouseY = event.getY()  ;
    	bounds = createBounds(mouseX, mouseY, mouseWidth, mouseHeight);
    	 

    }
    @Override
    public void mousePressed(MouseEvent event) { 
     
    	boolean clickedOnEntity = false;
    	for (Entity entity : entities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && bc.bounds.intersects(this.bounds)) {
            	Sprite sprite = entity.getComponent(Sprite.class);
            	//sprite.color = Color.GREEN;//change color when selected
                selectedEntity = entity;
                clickedOnEntity = true; 
            }
        }
    	//this is waiting for a location to move to 
    	if(!clickedOnEntity && selectedEntity != null) {
    		System.out.println("Selected entity " + selectedEntity.getId());
    		 //Position pos = selectedEntity.getComponent(Position.class);
    		//bug herer
    		 moveTarget = new Position(this.bounds.x, this.bounds.y);
    		 
    	}
    }
    
    public Rectangle createBounds(int x, int y, int w, int h) {
    	return new Rectangle(x, y, w, h);
    }

	public void addEntity(List<Entity> entities) {
		// TODO Auto-generated method stub
		this.entities = entities;
	}
    
	public Entity getSelectedEntity() { return selectedEntity; }
    public Position getMoveTarget() { return moveTarget; }
    public Rectangle bounds() {return bounds;}
    public void clearMoveTarget() { moveTarget = null; }
    
      
}
