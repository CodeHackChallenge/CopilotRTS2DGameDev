class PathfindingComponent implements Component {
    Point destination;
    Queue<Point> path = new LinkedList<>();

    void setDestination(Point dest) { this.destination = dest; }
    Point getDestination() { return destination; }

    void setPath(List<Point> newPath) {
        path.clear();
        path.addAll(newPath);
    }

    Point nextStep() {
        return path.poll();
    }

    boolean hasPath() {
        return !path.isEmpty();
    }
}
