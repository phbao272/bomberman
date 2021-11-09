package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.movable.Bomber;
import uet.oop.bomberman.entities.movable.enemy.Balloon;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    public char[][] mapMatrix;
    static Entity bomberman;

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

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
    }

    public void setKeyListener(Scene scene) {
        scene.setOnKeyPressed(keyEvent -> {
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
            }
        });
    }

    public void createMap() {
        createMapFromFile("res/levels/Level1.txt");
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Entity object;
                switch (mapMatrix[j][i]) {
                    case '#':
                        object = new Wall(i, j, Sprite.wall.getFxImage());
                        stillObjects.add(object);

                        break;
                    case '*':
                        object = new Brick(i, j, Sprite.brick.getFxImage());
                        stillObjects.add(object);

                        break;
                    case '1':
                        object = new Balloon(i, j, Sprite.balloom_left1.getFxImage());
                        stillObjects.add(object);
                        object = new Grass(i, j, Sprite.grass.getFxImage());
                        stillObjects.add(object);
                        break;
                    default:
                        object = new Grass(i, j, Sprite.grass.getFxImage());
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
    public void createMapFromFile(String URL) {
        FileInputStream fis = null;
        BufferedReader reader = null;

        try {
            fis = new FileInputStream(URL);
            reader = new BufferedReader(new InputStreamReader(fis));
            String line = reader.readLine();
            ;

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

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }
}
