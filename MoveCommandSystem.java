public class MoveCommandSystem {
    private GameMap map; // wrapper around TileMapComponent

    public MoveCommandSystem(GameMap map) {
        this.map = map;
    }

    public void moveSoldiers(List<Entity> soldiers, Point target) {
        Set<Point> assignedPositions = new HashSet<>();
        Queue<Point> searchQueue = new LinkedList<>();
        searchQueue.add(target);

        for (Entity soldier : soldiers) {
            Point destination = findNearestFreeTile(searchQueue, assignedPositions);
            if (destination != null) {
                assignedPositions.add(destination);
                PathfindingComponent path = soldier.getComponent(PathfindingComponent.class);
                if (path != null) {
                    path.setDestination(destination);
                    System.out.println("Soldier " + soldier.getId() + " moving to " + destination);
                }
            }
        }
    }

    private Point findNearestFreeTile(Queue<Point> searchQueue, Set<Point> assigned) {
        while (!searchQueue.isEmpty()) {
            Point candidate = searchQueue.poll();
            if (map.isWalkable(candidate) && !assigned.contains(candidate)) {
                return candidate;
            }
            for (Point neighbor : map.getNeighbors(candidate)) {
                if (!assigned.contains(neighbor)) {
                    searchQueue.add(neighbor);
                }
            }
        }
        return null;
    }
}
