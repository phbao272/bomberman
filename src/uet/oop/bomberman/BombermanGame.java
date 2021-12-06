package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.PowerUps.Bombs;
import uet.oop.bomberman.entities.PowerUps.Detonator;
import uet.oop.bomberman.entities.PowerUps.Flames;
import uet.oop.bomberman.entities.PowerUps.Speed;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.Movable;
import uet.oop.bomberman.entities.movable.enemy.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    public static int currentLevel = 1;
    public static int numberOfBombs = 1;
    public static int bombRadius = 1;
    public static int lives = 3;

    public static int cntBrick = 0;
    public static long startTime = 181;
    public static long prevTime = 0;

    public static GraphicsContext gc;
    public static GraphicsContext gc1;
    private Canvas canvas;
    private Canvas canvas1;

    public static AnimationTimer timer;

    public static List<Movable> mobs = new ArrayList<Movable>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Bomb> listBombs = new ArrayList<>();
    public static char[][] mapMatrix;

    public static Bomber bomberman;

    private Audio myAudio = new Audio();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        canvas1 = new Canvas(Sprite.SCALED_SIZE * WIDTH, 50);

        gc = canvas.getGraphicsContext2D();
        gc1 = canvas1.getGraphicsContext2D();

        canvas.setTranslateY(50);

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas1);
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();

        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();

                drawInfoBar();

                if (bomberman.isGameOver()) {
                    drawGameOver();
                }

                if (currentLevel == 0) {
                    drawWinGame();
                }
            }
        };

        timer.start();
        setKeyListener(scene);
        createMap();

//        myAudio.playSound("res/audio/background_music.wav", -1);
    }

    public void setKeyListener(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case D:
                case RIGHT:
                    bomberman.setLastDirection("RIGHT");
                    bomberman.setMoving(true);
                    break;
                case A:
                case LEFT:
                    bomberman.setLastDirection("LEFT");
                    bomberman.setMoving(true);
                    break;
                case W:
                case UP:
                    bomberman.setLastDirection("UP");
                    bomberman.setMoving(true);
                    break;
                case S:
                case DOWN:
                    bomberman.setLastDirection("DOWN");
                    bomberman.setMoving(true);
                    break;
                case K:
                    // TODO: Test player die
                    bomberman.kill();
                    myAudio.playSound("res/audio/dead.wav", 0);
                    break;
                case N:
                    // TODO: Test next level
                    myAudio.playSound("res/audio/next_level.wav", 0);
                    nextMap();
                    break;
                case M:
                    // TODO: Test destroy brick
                    for (Entity entity : stillObjects) {
                        if (entity instanceof Brick) {
                            ((Brick) entity).setDestroyed(true);
                            break;
                        }
                    }
                    break;
                case L:
                    // TODO: Test kill enemy
                    for (Entity entity : entities) {
                        if (entity instanceof Enemy) {
                            ((Enemy) entity).kill();
                            break;
                        }
                    }
                    break;
                case SPACE:
                    if (listBombs.size() < numberOfBombs) {
                        int xTilePlayer = bomberman.getTileX();
                        int yTilePlayer = bomberman.getTileY();

                        Bomb bomb = new Bomb(xTilePlayer, yTilePlayer, Sprite.bomb.getFxImage());
                        listBombs.add(bomb);
                        myAudio.playSound("res/audio/place_bomb.wav", 0);
                        System.out.println("Đặt bom bùm bùm...");
                    }
                    break;
            }
        });

        scene.setOnKeyReleased(keyEvent -> {
            bomberman.setMoving(false);
            switch (keyEvent.getCode()) {
                case D:
                case RIGHT:
                    bomberman.moveRight();
                    break;
                case A:
                case LEFT:
                    bomberman.moveLeft();
                    break;
                case W:
                case UP:
                    bomberman.moveUp();
                    break;
                case S:
                case DOWN:
                    bomberman.moveDown();
                    break;
                default:
                    break;
            }
        });
    }

    public static void clearMap() {
        entities.clear();
        stillObjects.clear();
        listBombs.clear();
        mobs.clear();
    }

    public static void resetGame() {
        numberOfBombs = 1;
        bombRadius = 1;
        bomberman.setSpeed(4);
        startTime = 181;
        prevTime = 0;
    }

    public static void nextMap() {
        // TODO: Clear map
        clearMap();

        // TODO: Create new map
        currentLevel++;
        if (currentLevel > 5) {
            currentLevel = 0;
        }
        createMap();

        //TODO: Reset Game
        resetGame();
    }

    public static void restartMap() {
        // TODO: Clear map
        clearMap();

        // TODO: Create map
        createMap();

        //TODO: Reset Game
        resetGame();
    }

    public static void createMap() {
        if (currentLevel != 0) {
            createMapFromFile("res/levels/Level" + currentLevel + ".txt");
        }
//        createMapFromFile("res/levels/test.txt");

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        mobs.add(bomberman);


        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                object = new Grass(i, j, Sprite.grass.getFxImage());
                stillObjects.add(object);
                switch (mapMatrix[j][i]) {
                    case '#':
                        stillObjects.remove(object);
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        stillObjects.add(object);
                        break;
                    case '*':
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        cntBrick++;
                        break;
                    case '1':
                        object = new Balloon(i, j, Sprite.balloom_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '2':
                        object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '3':
                        object = new Doll(i, j, Sprite.doll_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '4':
                        object = new Minvo(i, j, Sprite.minvo_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '5':
                        object = new Kondoria(i, j, Sprite.kondoria_left1.getFxImage());
                        entities.add(object);
                        break;
                    case 'b':
                        object = new Bombs(i, j, Sprite.powerup_bombs.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    case 'f':
                        object = new Flames(i, j, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    case 's':
                        object = new Speed(i, j, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    case 'd':
                        object = new Detonator(i, j, Sprite.powerup_detonator.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                    case 'x':
                        object = new Portal(i, j, Sprite.portal.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        break;
                }
            }
        }
    }

    /**
     * Tạo map từ file
     *
     * @param URL Đường dẫn để tạo map theo Level
     */
    public static void createMapFromFile(String URL) {
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(URL);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();

            String[] tokens = line.split("\\s");

            int numLevel = Integer.parseInt(tokens[0]);
            int numRow = Integer.parseInt(tokens[1]);
            int numColumn = Integer.parseInt(tokens[2]);

            // Nếu điều kiện không thỏa mãn thì return
            if (numLevel < 1 || (numRow < 1 && numColumn < 1)) {
                return;
            }

            mapMatrix = new char[numRow][numColumn];

            line = reader.readLine();
            int cntRow = -1;
            while (line != null && line.startsWith("#")) {
                cntRow++;
                for (int i = 0; i < line.length(); i++) {
                    mapMatrix[cntRow][i] = line.charAt(i);
                }
                line = reader.readLine();
            }

            /**
             * In ma trận chứa map
             */
            for (int i = 0; i < numRow; i++) {
                for (int j = 0; j < numColumn; j++) {
                    System.out.print(mapMatrix[i][j]);
                }
                System.out.println("");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("Insert From File successful!!!");
        }
    }

    public static Entity getEntity(int x, int y) {
        if (getBrick(x, y) != null) {
            return getBrick(x, y);
        } else {
            for (Entity entity : entities) {
                if (entity.getX() == x && entity.getY() == y) {
                    return entity;
                }
            }

            for (Entity entity : stillObjects) {
                if (entity.getX() == x && entity.getY() == y) {
                    return entity;
                }
            }
        }
        return new Grass(x, y, Sprite.grass.getFxImage());
    }

    public static Brick getBrick(int x, int y) {
        for (Entity entity : stillObjects) {
            if (entity instanceof Brick) {
                if (entity.getX() == x && entity.getY() == y) {
                    return (Brick) entity;
                }
            }
        }
        return null;
    }

    public static Bomber getPlayer() {
        Iterator<Movable> itr = mobs.iterator();

        Movable cur;
        while (itr.hasNext()) {
            cur = itr.next();

            if (cur instanceof Bomber)
                return (Bomber) cur;
        }

        return null;
    }

    public void update() {
//        entities.forEach(Entity::update)
//        listBombs.forEach(Entity::update);

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }

        for (int i = 0; i < listBombs.size(); i++) {
            listBombs.get(i).update();
        }

        for (int i = 0; i < mobs.size(); i++) {
            Movable a = mobs.get(i);
            if (((Entity) a).isRemoved()) mobs.remove(i);
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//        entities.forEach(g -> g.render(gc));
//        stillObjects.forEach(g -> g.render(gc));

        for (int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).render(gc);
        }

        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(gc);
        }

        for (int i = 0; i < listBombs.size(); i++) {
            listBombs.get(i).render(gc);
        }
    }

    public void drawInfoBar() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - prevTime >= 1000) {
            prevTime = currentTime;
            startTime -= 1;
            if (startTime <= 0) {
                bomberman.setGameOver(true);
            }
        }
        gc1.setFill(Color.BLACK);
        gc1.fillRect(0, 0, Sprite.SCALED_SIZE * WIDTH, 50);
        gc1.setTextAlign(TextAlignment.CENTER);
        gc1.setFill(Color.WHITE);
        gc1.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        gc1.fillText(
                "Time: " + (startTime),
                Math.round(canvas.getWidth() / 8),
                Math.round(27)
        );
        gc1.fillText(
                "Level: " + currentLevel,
                Math.round(canvas.getWidth() / 2),
                Math.round(27)
        );
        gc1.fillText(
                "Lives: " + lives,
                Math.round(canvas.getWidth() / 8 * 7),
                Math.round(27)
        );
    }

    public void drawGameOver() {
        configDraw();
        gc.fillText(
                "Game Over",
                Math.round(canvas.getWidth() / 2),
                Math.round((canvas.getHeight() - 50) / 2)
        );

        timer.stop();
    }

    public void drawWinGame() {
        configDraw();
        gc.fillText(
                "You Win!!!",
                Math.round(canvas.getWidth() / 2),
                Math.round((canvas.getHeight() - 50) / 2)
        );

        timer.stop();
    }

    private void configDraw() {
        gc1.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc1.setFill(Color.BLACK);
        gc1.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        Font font = new Font("Arial", 48);
        gc.setFont(font);
    }
}
