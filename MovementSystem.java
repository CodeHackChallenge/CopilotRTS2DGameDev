class MovementSystem {
    public void process(List<Entity> entities) {
        for (Entity e : entities) {
            Position pos = e.getComponent(Position.class);
            PathfindingComponent path = e.getComponent(PathfindingComponent.class);

            if (pos != null && path != null && path.hasPath()) {
                Point next = path.nextStep();
                pos.x = next.x;
                pos.y = next.y;
                System.out.println("Entity " + e.getId() + " moved to (" + pos.x + "," + pos.y + ")");
            }
        }
    }
}
