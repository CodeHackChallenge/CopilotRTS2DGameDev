public class Game {
    public static void main(String[] args) throws Exception {
        MapConfig config = MapConfig.defaultRts();
        TileMapComponent tileMap = TileMapLoader.loadFromFile("map.txt", config);

        List<Entity> entities = new ArrayList<>();
        Entity soldier = new Entity(1);
        soldier.addComponent(new Position(10, 10));
        soldier.addComponent(new BoundsComponent(10*64, 10*64, 64, 64));
        soldier.addComponent(new Sprite("soldier.png"));
        entities.add(soldier);

        Camera camera = new Camera(config, 20, 15);
        camera.follow(soldier.getComponent(Position.class));

        JFrame frame = new JFrame("RTS Game");
        GamePanel panel = new GamePanel(tileMap, entities, camera);
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        panel.start();
    }
}
