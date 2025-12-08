class CameraController implements KeyListener {
    private Camera camera;

    public CameraController(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> camera.pan(0, -1); // up
            case KeyEvent.VK_S -> camera.pan(0, 1);  // down
            case KeyEvent.VK_A -> camera.pan(-1, 0); // left
            case KeyEvent.VK_D -> camera.pan(1, 0);  // right
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}
