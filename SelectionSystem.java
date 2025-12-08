class SelectionSystem {
    private Camera camera;
    private List<Entity> worldEntities;
    private List<Entity> selectedEntities = new ArrayList<>();

    public SelectionSystem(List<Entity> worldEntities, Camera camera) {
        this.worldEntities = worldEntities;
        this.camera = camera;
    }

    public void selectEntities(Rectangle rect) {
        selectedEntities.clear();
        for (Entity entity : worldEntities) {
            Position pos = entity.getComponent(Position.class);
            BoundsComponent bc = entity.getComponent(BoundsComponent.class);
            if (pos != null && bc != null &&
                camera.isVisible(pos.x, pos.y) &&
                rect.intersects(bc.bounds)) {
                selectedEntities.add(entity);
            }
        }
        System.out.println("Selected " + selectedEntities.size() + " entities.");
    }

    public List<Entity> getSelectedEntities() {
        return selectedEntities;
    }
}
