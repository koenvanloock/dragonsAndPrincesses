import actors.DragonActorJava;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.ExecutionContexts;
import commands.CapturePrincess;
import commands.GiveMeThePrincess;
import commands.KillTheBloodyWoman;
import interpreters.DragonFavoringInterpreterJava;
import models.FairytaleJava;
import models.Princess;
import scala.concurrent.ExecutionContext;

import java.util.Arrays;
import java.util.concurrent.Executors;

public class QuestRunnerJava {

    public static void main(String[] args) {
        ExecutionContext ec = ExecutionContexts.fromExecutorService(Executors.newFixedThreadPool(4));
        final ActorSystem system = ActorSystem.create();
        final ActorRef dragon = system.actorOf(DragonActorJava.props(), "dragon");

        FairytaleJava snowWhite = new FairytaleJava("snowWhite", dragon, new Princess("Snowwhite"), Arrays.asList(
                new CapturePrincess(new Princess("Snowwhite")),
                new GiveMeThePrincess()
        ));

        FairytaleJava sleepingBeauty = new FairytaleJava("Sleeping Beauty", dragon, new Princess("SleepingBeauty"), Arrays.asList(
                new CapturePrincess(new Princess("SleepingBeauty")),
                new KillTheBloodyWoman(),
                new GiveMeThePrincess()
        ));


        DragonFavoringInterpreterJava.tellAll(ec, snowWhite, sleepingBeauty);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        system.terminate();
    }

}
