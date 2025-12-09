public class SelectionSystem implements MouseListener, MouseMotionListener {
    private boolean isSelecting = false;
    private Point startPoint;
    private Point currentPoint;
    private Rectangle selectionRect;

    private List<Entity> selectedEntities = new ArrayList<>();
    private List<Entity> worldEntities; // injected from game world

    public SelectionSystem(List<Entity> worldEntities) {
        this.worldEntities = worldEntities;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            startPoint = e.getPoint();
            currentPoint = startPoint;
            isSelecting = true;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (isSelecting) {
            currentPoint = e.getPoint();
            selectionRect = createRect(startPoint, currentPoint);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isSelecting && e.getButton() == MouseEvent.BUTTON1) {
            selectionRect = createRect(startPoint, e.getPoint());
            selectEntities(selectionRect);
            isSelecting = false;
        }
    }

    private Rectangle createRect(Point p1, Point p2) {
        int x = Math.min(p1.x, p2.x);
        int y = Math.min(p1.y, p2.y);
        int w = Math.abs(p1.x - p2.x);
        int h = Math.abs(p1.y - p2.y);
        return new Rectangle(x, y, w, h);
    }

    private void selectEntities(Rectangle rect) {
        selectedEntities.clear();
        for (Entity entity : worldEntities) {
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (bc != null && rect.intersects(bc.bounds)) {
                selectedEntities.add(entity);
            }
        }
        System.out.println("Selected " + selectedEntities.size() + " entities.");
    }

    public List<Entity> getSelectedEntities() {
        return selectedEntities;
    }

    // Empty overrides for unused MouseListener methods
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
