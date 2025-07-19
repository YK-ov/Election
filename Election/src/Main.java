import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        //Candidate candidate = new Candidate(candidate);
        Election election = new Election();
        election.populateCandidates("src/kandydaci.txt");
        //election.printCandidates();

        Vote vote = Vote.fromCsvLine("m. Bolesławiec,bolesławiecki,dolnośląskie,450,1131,6465,2899,32,255,13,19,7183,24,43");

        List<Candidate> candidates = election.getCandidates();

        ElectionTurn electionTurn = new ElectionTurn(candidates);
        List<Vote> votes = electionTurn.populate("src/1.csv");


        //System.out.println(vote.summarize(votes));

       Candidate candidate = election.getCandidates().get(0);


       //System.out.println(vote.summarize(votes).percentage(candidate));
       //System.out.println(electionTurn.winner());


        //electionTurn.runoffCandidates();

        //System.out.println(electionTurn.runoffCandidates());
        election.populate();
        //System.out.println(vote.summarize(votes));

        //System.out.println(electionTurn.runoffCandidates());
    }

}
