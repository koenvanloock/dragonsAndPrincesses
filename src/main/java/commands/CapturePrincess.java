package commands;

import models.Princess;

public class CapturePrincess implements DragonCommandJava {

    private final Princess princess;

    public CapturePrincess(Princess princess) {
        this.princess = princess;
    }

    public Princess getPrincess() {
        return princess;
    }
}
