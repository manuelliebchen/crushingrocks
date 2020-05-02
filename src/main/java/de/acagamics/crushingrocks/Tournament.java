package de.acagamics.crushingrocks;

import de.acagamics.crushingrocks.controller.IPlayerController;
import de.acagamics.crushingrocks.logic.Game;
import de.acagamics.crushingrocks.types.GameMode;
import de.acagamics.crushingrocks.types.MatchSettings;
import de.acagamics.framework.simulation.Simulator;
import de.acagamics.framework.types.SimulationSettings;
import de.acagamics.framework.types.SimulationStatistic;
import de.acagamics.framework.types.Student;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Tournament extends Thread {
    private static final Logger LOG = LogManager.getLogger(Tournament.class.getName());

    List<Class<?>> controllersClasses;
    Random random;
    int runs;
    int threads;

    int[][] victories;
    int draws;

    int runcounter;
    long startTime;
    float timeElapsed;

    public Tournament(List<Class<?>> controllersClasses, long seed, int threads, int runs) {
        this.controllersClasses = controllersClasses;
        this.random = new Random(seed);
        this.runs = runs;
        this.threads = threads;

        victories = new int[controllersClasses.size()][controllersClasses.size()];
    }

    @Override
    public void run() {
        runcounter = 0;
        startTime = System.currentTimeMillis();
        for(int i = 0; i < controllersClasses.size(); ++i){
            for(int j = 0; j < i; ++j){
                MatchSettings matchSettings = new MatchSettings(GameMode.NORMAL, Arrays.asList(controllersClasses.get(i), controllersClasses.get(j)), random.nextLong());
                Simulator<Game, IPlayerController> simulator = new Simulator<>(new SimulationSettings(threads, runs), matchSettings);
                simulator.start();
                try {
                    simulator.join();
                } catch (InterruptedException e) {
                    LOG.error(e);
                }
                SimulationStatistic<IPlayerController> statistic = simulator.getStatistics();
                victories[i][j] = statistic.getVictories(controllersClasses.get(i));
                victories[j][i] = statistic.getVictories(controllersClasses.get(j));
                draws += statistic.getDraws();
                runcounter += 1;
            }
        }
        timeElapsed = getTimeElapsed();
    }

    public float getTimeElapsed() {
        if(timeElapsed == 0) {
            return (System.currentTimeMillis() - startTime) / 1000.0f;
        }
        return timeElapsed;
    }

    public float getProgress() {
        return (float) (runcounter / ((controllersClasses.size() * (controllersClasses.size() + 1)) / 2.0 - controllersClasses.size()));
    }

    public String getCSV() {
        StringBuilder bld = new StringBuilder();

        bld.append("    ,                 ,                 ,                                                                 , ");
        for(int i = 0; i < controllersClasses.size(); ++i){
            bld.append(String.format("%4d, ", i));
        }
        bld.append("   sum,\n");

        for(int i = 0; i < controllersClasses.size(); ++i){
            Class<?> entry = controllersClasses.get(i);
            Student student = entry.getAnnotation(Student.class);
            bld.append(String.format("%4d, %16d, %16s, %64s, ", i, student.matrikelnummer(), student.name(), entry.getName()));

            int sum = 0;
            for (int j = 0; j < controllersClasses.size(); ++j){
                bld.append(String.format("%4d, ", victories[i][j]));
                sum += victories[i][j];
            }
            bld.append(String.format("%6d, ", sum));
            bld.append("\n");
        }
        bld.append(String.format("draws, %6d,%n", draws));
        return bld.toString();
    }
}
