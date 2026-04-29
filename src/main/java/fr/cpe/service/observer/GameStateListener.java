package fr.cpe.service.observer;

import fr.cpe.service.GameStateService.RunState;

public interface GameStateListener {
    void onStateChanged(RunState newState);
    void onStardustChanged(int newAmount);
}