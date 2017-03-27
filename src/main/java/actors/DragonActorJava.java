package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import commands.CapturePrincess;
import commands.GiveMeThePrincess;
import commands.KillTheBloodyWoman;
import models.Princess;

public class DragonActorJava extends AbstractActor {

    public static Props props() {
        return Props.create(DragonActorJava.class, DragonActorJava::new);
    }

    private Princess princess;

    public DragonActorJava() {
        receive(ReceiveBuilder
                .match(CapturePrincess.class, capturePrincess -> {
                    if(this.princess == null){
                        this.princess = capturePrincess.getPrincess();
                        sender().tell(princess, self());
                    } else{
                        sender().tell("I already have one!", self());
                    }
                })
                .match(KillTheBloodyWoman.class, killCommand -> {
                    if (this.princess != null) {
                        System.out.println("Die " + princess.name()+"!!!");
                        this.princess = null;
                    }
                })
                .match(GiveMeThePrincess.class, giveMeThePrincessCommand -> {
                    if(this.princess != null){
                        sender().tell(princess, self());
                    }else{
                        sender().tell("I don't have a princess.", self());
                    }
                })
                .matchAny(o -> System.out.println("Unknown message type received!!!"))
                .build()
        );
    }
}
