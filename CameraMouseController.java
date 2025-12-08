class CameraMouseController implements MouseListener {
    private Camera camera;
    private List<Entity> entities;

    public CameraMouseController(Camera camera, List<Entity> entities) {
        this.camera = camera;
        this.entities = entities;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e)) {
            Point click = e.getPoint();
            for (Entity entity : entities) {
                BoundsComponent bc = entity.getComponent(BoundsComponent.class);
                if (bc != null && bc.bounds.contains(click)) {
                    Position pos = entity.getComponent(Position.class);
                    if (pos != null) {
                        camera.follow(pos);
                        System.out.println("Camera snapped to entity " + entity.getId());
                    }
                }
            }
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
