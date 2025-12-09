class SelectionSystem implements MouseListener, MouseMotionListener {
    private boolean isSelecting = false;
    private Point startPoint;
    private Point currentPoint;
    private Rectangle selectionRect;

    private List<Entity> selectedEntities = new ArrayList<>();
    private List<Entity> worldEntities;
    private Camera camera;

    public SelectionSystem(List<Entity> worldEntities, Camera camera) {
        this.worldEntities = worldEntities;
        this.camera = camera;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
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
        if (isSelecting && SwingUtilities.isLeftMouseButton(e)) {
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
            Position pos = entity.getComponent(Position.class);
            if (bc != null && pos != null && camera.isVisible(pos.x, pos.y)) {
                if (rect.intersects(bc.bounds)) {
                    selectedEntities.add(entity);
                }
            }
        }
        System.out.println("Selected " + selectedEntities.size() + " entities.");
    }

    public List<Entity> getSelectedEntities() {
        return selectedEntities;
    }

    // Unused overrides
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}
}
