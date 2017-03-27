package models;

import akka.actor.ActorRef;
import commands.DragonCommandJava;

import java.util.List;

public class FairytaleJava {

    private final String name;
    private final ActorRef dragon;
    private final Princess princess;
    private final List<DragonCommandJava> commands;


    public FairytaleJava(String name, ActorRef dragon, Princess princess, List<DragonCommandJava> commands) {
        this.name = name;
        this.dragon = dragon;
        this.commands = commands;
        this.princess = princess;
    }

    public String getName() {
        return name;
    }

    public ActorRef getDragon() {
        return dragon;
    }

    public Princess getPrincess(){
        return princess;
    }

    public List<DragonCommandJava> getCommands() {
        return commands;
    }
}
