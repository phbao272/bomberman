package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.audio.Audio;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.PowerUps.Bombs;
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

    public int cntBomb = 1;
    private int currentLevel = 1;
    public static int NumberofBombs = 1;
    public static int bombRadius = 1;

    public int cntBrick = 0;

    public static GraphicsContext gc;
    private Canvas canvas;

    public static List<Movable> _mobs = new ArrayList<Movable>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Bomb> listBombs = new ArrayList<>();
    public char[][] mapMatrix;

    public static Bomber bomberman;

    private Audio myAudio = new Audio();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.setTitle("Bomberman");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };

        timer.start();
        setKeyListener(scene);
        createMap();


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
                    bomberman.setAlive(false);
                    myAudio.playSound("res/audio/dead.wav", 0);
                    break;
                case N:
                    // TODO: Test next level
                    System.out.println("Num of entities: " + entities.size());
                    nextMap();
                    break;
                case M:
                    // TODO: Test destroy brick

                    for (Entity entity : stillObjects) {
                        if (entity instanceof Brick) {
                            ((Brick) entity).setDestroyed(true);
                            System.out.println("Num of bricks: " + (--cntBrick));
                            break;
                        }
                    }
                    break;
                case SPACE:
                    if (listBombs.size() < NumberofBombs) {
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

    public void nextMap() {
        // TODO: Clear map
        entities.clear();
        stillObjects.clear();
        listBombs.clear();

        // TODO: Create new map
        currentLevel++;
        createMap();
    }

    public void createMap() {
        createMapFromFile("res/levels/Level" + currentLevel + ".txt");
//        createMapFromFile("res/levels/test.txt");

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
        _mobs.add(bomberman);


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
                    case '2' :
                        object = new Oneal(i, j, Sprite.oneal_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '3' :
                        object = new Doll(i, j, Sprite.doll_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '4' :
                        object = new Minvo(i, j, Sprite.minvo_left1.getFxImage());
                        entities.add(object);
                        break;
                    case '5' :
                        object = new Kondoria(i, j, Sprite.kondoria_left1.getFxImage());
                        entities.add(object);
                        break;
                    case 'b':
                        object = new Bombs(i, j, Sprite.powerup_bombs.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        cntBrick++;
                        break;
                    case 'f':
                        object = new Flames(i, j, Sprite.powerup_flames.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        cntBrick++;
                        break;
                    case 's':
                        object = new Speed(i, j, Sprite.powerup_speed.getFxImage());
                        stillObjects.add(object);
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        cntBrick++;
                        break;
                }
            }
        }
        System.out.println("Số gạch trong map: " + cntBrick);
    }

    /**
     * Tạo map từ file
     *
     * @param URL Đường dẫn để tạo map theo Level
     */
    public void createMapFromFile(String URL) {
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
        Iterator<Movable> itr = _mobs.iterator();

        Movable cur;
        while(itr.hasNext()) {
            cur = itr.next();

            if(cur instanceof Bomber)
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

        for (int i = 0; i < _mobs.size(); i++) {
            Movable a = _mobs.get(i);
            if(((Entity)a).isRemoved()) _mobs.remove(i);
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
}
