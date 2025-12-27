public class Engine extends Canvas implements Runnable {

    public static final int UPS = 60;     // Updated: smooth MMO tick rate
    public static final int FPS = 120;    // Your original choice is perfect

    private boolean isRunning = true;
    private Thread thread;

    private BufferStrategy bufferStrategy;

    private List<Entity> entities;

    // Systems
    private RenderSystem renderSystem;
    private CombatSystem combatSystem;
    private DamageTextSystem damageTextSystem;
    private HealthBarSystem healthBarSystem;
    private AnimationSystem animationSystem; // NEW recommended system

    public Engine() {

        JFrame window = new JFrame("RuneScape2D Demo v.1");
        JPanel panel = (JPanel) window.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);
        panel.add(this);

        setBounds(0, 0, WIDTH, HEIGHT);
        setIgnoreRepaint(true);

        window.pack();
        window.setResizable(false);
        window.setVisible(true);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        requestFocus();

        createBufferStrategy(2);
        bufferStrategy = getBufferStrategy();

        gameSetup();
    }

    private void gameSetup() {

        entities = new ArrayList<>();

        renderSystem = new RenderSystem();
        combatSystem = new CombatSystem();
        damageTextSystem = new DamageTextSystem();
        healthBarSystem = new HealthBarSystem();
        animationSystem = new AnimationSystem(); // NEW

        // Your entity creation stays the same...
        // warrior, sorc2, etc.

        entities.add(warrior);
        entities.add(sorc2);
    }

    // ---------------------------------------------------------
    // UPDATED UPDATE METHOD — now uses delta
    // ---------------------------------------------------------
    public void update(float delta) {

        animationSystem.update(entities, delta);
        combatSystem.update(entities, delta);
        healthBarSystem.update(entities, delta);
        damageTextSystem.update(entities, delta);
    }

    // ---------------------------------------------------------
    // RENDER METHOD (unchanged)
    // ---------------------------------------------------------
    public void render() {

        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Grid drawing...
        g.setColor(new Color(255, 255, 255, 80));
        for (int y = 0; y < HEIGHT; y += SPRITE_SIZE) g.drawLine(0, y, WIDTH, y);
        for (int x = 0; x < WIDTH; x += SPRITE_SIZE) g.drawLine(x, 0, x, HEIGHT);

        renderSystem.render(g, entities);

        g.dispose();
        bufferStrategy.show();
    }

    // ---------------------------------------------------------
    // ⭐ UPDATED MAIN LOOP — MMO‑ready, fixed timestep
    // ---------------------------------------------------------
    @Override
    public void run() {

        final double timePerUpdate = 1_000_000_000.0 / UPS;
        final double timePerFrame  = 1_000_000_000.0 / FPS;

        long previousTime = System.nanoTime();
        double deltaU = 0;
        double deltaF = 0;

        long timer = System.currentTimeMillis();
        int frames = 0;
        int updates = 0;

        while (isRunning) {

            long currentTime = System.nanoTime();
            long elapsed = currentTime - previousTime;
            previousTime = currentTime;

            deltaU += elapsed / timePerUpdate;
            deltaF += elapsed / timePerFrame;

            // FIXED UPDATE LOOP
            while (deltaU >= 1) {
                update(1f / UPS); // pass correct delta
                updates++;
                deltaU--;
            }

            // RENDER LOOP
            if (deltaF >= 1) {
                render();
                frames++;
                deltaF--;
            }

            // Prevent CPU from maxing out
            try { Thread.sleep(1); } catch (Exception e) {}

            // Debug output
            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void start() {
        thread = new Thread(this);
        thread.start();
    }

    public void stop() {
        isRunning = false;
    }
}
