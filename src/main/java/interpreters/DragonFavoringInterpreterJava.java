package interpreters;

import akka.actor.ActorRef;
import akka.dispatch.OnSuccess;
import commands.CapturePrincess;
import commands.DragonCommandJava;
import commands.GiveMeThePrincess;
import commands.KillTheBloodyWoman;
import models.FairytaleJava;
import models.Princess;
import scala.concurrent.ExecutionContext;

import java.util.Arrays;

import static akka.pattern.Patterns.ask;

public class DragonFavoringInterpreterJava {
    public static void tellAll(ExecutionContext executionContext, FairytaleJava... fairytaleJavas) {
        Arrays.stream(fairytaleJavas).forEach( (fairytaleJava) -> tellTale(executionContext, fairytaleJava));
    }

    private static void tellTale(ExecutionContext executionContext, FairytaleJava fairytaleJava) {

        new Thread(() -> {
            for (DragonCommandJava command : fairytaleJava.getCommands()) {
                if (command instanceof CapturePrincess) {
                    fairytaleJava.getDragon().tell(command, ActorRef.noSender());
                    System.out.println(String.format("%30s -- Our faboulous beast caught %s", fairytaleJava.getName(), fairytaleJava.getPrincess().name()));
                } else if (command instanceof KillTheBloodyWoman) {
                    fairytaleJava.getDragon().tell(command, ActorRef.noSender());
                    System.out.println(String.format("%30s -- Die bloody woman", fairytaleJava.getName()));
                } else if (command instanceof GiveMeThePrincess) {
                    handleGivePrincessResponse(fairytaleJava, command, executionContext);
                }
            }

            System.out.println(String.format("%30s -- And they lived happily ever after...", fairytaleJava.getName()));
        }).run();
    }

    private static void handleGivePrincessResponse(FairytaleJava fairytaleJava, DragonCommandJava command, ExecutionContext executionContext){
        try {
            ask(fairytaleJava.getDragon(), command, 100).onSuccess( new OnSuccess<Object>(){

                @Override
                public void onSuccess(Object result) throws Throwable {
                    printResult(fairytaleJava.getName(), result);
                }
            }, executionContext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printResult(String fairytaleName, Object response) {
        if (response instanceof String) {
            System.out.println(String.format("%30s -- Our great dragon says: '%s'", fairytaleName, response.toString()));
        } else if (response instanceof Princess) {
            System.out.println(String.format("%30s -- Our great dragon says: '%s'", fairytaleName, response.toString()));
        }
    }
}
