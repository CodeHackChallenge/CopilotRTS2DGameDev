class PathfindingSystem {
    private GameMap map;

    public PathfindingSystem(GameMap map) {
        this.map = map;
    }

    public void computePath(Entity e) {
        Position pos = e.getComponent(Position.class);
        PathfindingComponent pathComp = e.getComponent(PathfindingComponent.class);

        if (pos == null || pathComp == null || pathComp.getDestination() == null) return;

        Point start = new Point(pos.x, pos.y);
        Point goal = pathComp.getDestination();

        List<Point> path = aStar(start, goal);
        pathComp.setPath(path);
    }

    private List<Point> aStar(Point start, Point goal) {
        Set<Point> closed = new HashSet<>();
        Map<Point, Point> cameFrom = new HashMap<>();
        Map<Point, Integer> gScore = new HashMap<>();
        Map<Point, Integer> fScore = new HashMap<>();

        Comparator<Point> comparator = Comparator.comparingInt(fScore::get);
        PriorityQueue<Point> open = new PriorityQueue<>(comparator);

        gScore.put(start, 0);
        fScore.put(start, heuristic(start, goal));
        open.add(start);

        while (!open.isEmpty()) {
            Point current = open.poll();
            if (current.equals(goal)) {
                return reconstructPath(cameFrom, current);
            }

            closed.add(current);

            for (Point neighbor : map.getNeighbors(current)) {
                if (!map.isWalkable(neighbor) || closed.contains(neighbor)) continue;

                int tentativeG = gScore.get(current) + 1;
                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeG);
                    fScore.put(neighbor, tentativeG + heuristic(neighbor, goal));
                    if (!open.contains(neighbor)) open.add(neighbor);
                }
            }
        }
        return Collections.emptyList(); // no path found
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y); // Manhattan distance
    }

    private List<Point> reconstructPath(Map<Point, Point> cameFrom, Point current) {
        List<Point> path = new ArrayList<>();
        path.add(current);
        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
            path.add(current);
        }
        Collections.reverse(path);
        return path;
    }
}
