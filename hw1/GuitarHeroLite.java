/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
import edu.princeton.cs.introcs.StdAudio;
import synthesizer.GuitarString;

public class GuitarHeroLite {
    private static final double CONCERT_A = 440;
    private static final double CONCERT_C = CONCERT_A * Math.pow(2, 3.0 / 12.0);
    private static final double CONCERT_D = 587.32;
    private static final double CONCERT_G = 784;


    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        GuitarString stringA = new GuitarString(CONCERT_A);
        GuitarString stringC = new GuitarString(CONCERT_C);
        GuitarString stringD = new GuitarString(CONCERT_D);
        GuitarString stringG = new GuitarString(CONCERT_G);

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'a') {
                    stringA.pluck();
                } else if (key == 'c') {
                    stringC.pluck();
                } else if (key == 'd') {
                    stringD.pluck();
                } else if (key == 'g') {
                    stringG.pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = stringA.sample() + stringC.sample() + stringD.sample() + stringG.sample();

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            stringA.tic();
            stringC.tic();
            stringD.tic();
            stringG.tic();
        }
    }
}

