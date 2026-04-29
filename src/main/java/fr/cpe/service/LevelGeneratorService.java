package fr.cpe.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.inject.Singleton;

@Singleton
public class LevelGeneratorService {

    private final Random random = new Random();

    public int[][] genererNiveau() {
        int[][] grille = new int[5][5];
        
        int startX = random.nextInt(5);
        int startY = random.nextInt(5);
        grille[startY][startX] = 1;

        int endX, endY;
        do {
            endX = random.nextInt(5);
            endY = random.nextInt(5);
        } while (Math.abs(endX - startX) + Math.abs(endY - startY) < 3);

        grille[endY][endX] = 2;

        List<int[]> chemin = new ArrayList<>();
        chemin.add(new int[]{startX, startY});

        tracerChemin(grille, startX, startY, endX, endY, chemin);
        genererImpasses(grille, chemin);

        return grille;
    }

    private boolean tracerChemin(int[][] grille, int x, int y, int endX, int endY, List<int[]> chemin) {
        if (x == endX && y == endY) {
            return true;
        }

        List<int[]> directions = new ArrayList<>();
        directions.add(new int[]{0, -1});
        directions.add(new int[]{0, 1});
        directions.add(new int[]{-1, 0});
        directions.add(new int[]{1, 0});
        Collections.shuffle(directions, random);

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5) {
                if (grille[ny][nx] == 0 || grille[ny][nx] == 2) {
                    boolean isEnd = (grille[ny][nx] == 2);
                    if (!isEnd) {
                        grille[ny][nx] = 3;
                        chemin.add(new int[]{nx, ny});
                    }

                    if (tracerChemin(grille, nx, ny, endX, endY, chemin)) {
                        return true;
                    }

                    if (!isEnd) {
                        grille[ny][nx] = 0;
                        chemin.remove(chemin.size() - 1);
                    }
                }
            }
        }
        return false;
    }

    private void genererImpasses(int[][] grille, List<int[]> chemin) {
        for (int[] cellule : chemin) {
            if (random.nextInt(100) < 30) {
                List<int[]> directions = new ArrayList<>();
                directions.add(new int[]{0, -1});
                directions.add(new int[]{0, 1});
                directions.add(new int[]{-1, 0});
                directions.add(new int[]{1, 0});
                Collections.shuffle(directions, random);

                for (int[] dir : directions) {
                    int nx = cellule[0] + dir[0];
                    int ny = cellule[1] + dir[1];

                    if (nx >= 0 && nx < 5 && ny >= 0 && ny < 5 && grille[ny][nx] == 0) {
                        grille[ny][nx] = 4;
                        break; 
                    }
                }
            }
        }
    }
}