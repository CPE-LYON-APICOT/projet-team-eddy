package fr.cpe.service;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.Singleton;

import fr.cpe.service.observer.GameStateListener;

@Singleton
public class GameStateService {

    public enum RunState {
        MENU,
        IN_GAME,
        DEATH_SCREEN,
        VICTORY_SCREEN
    }

    private RunState currentState;
    private int poussiereEtoile;
    private final List<GameStateListener> listeners = new ArrayList<>();

    public GameStateService() {
        this.currentState = RunState.MENU;
        this.poussiereEtoile = 0;
    }

    public void subscribe(GameStateListener listener) {
        if (!listeners.contains(listener)) {
            listeners.add(listener);
        }
    }

    public void unsubscribe(GameStateListener listener) {
        listeners.remove(listener);
    }

    public RunState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(RunState newState) {
        this.currentState = newState;
        notifyStateChanged();
    }

    public int getpoussiereEtoile() {
        return poussiereEtoile;
    }

    public void addStardust(int amount) {
        if (amount > 0) {
            this.poussiereEtoile += amount;
            notifyStardustChanged();
        }
    }
    
    public void resetRun() {
        this.currentState = RunState.IN_GAME;
        notifyStateChanged();
    }

    private void notifyStateChanged() {
        for (GameStateListener listener : listeners) {
            listener.onStateChanged(this.currentState);
        }
    }

    private void notifyStardustChanged() {
        for (GameStateListener listener : listeners) {
            listener.onStardustChanged(this.poussiereEtoile);
        }
    }
}