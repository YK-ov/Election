import java.util.List;

public class Main {
    public static void main(String[] args) {
        //Candidate candidate = new Candidate(candidate);
        Election election = new Election();
        election.populateCandidates("src/kandydaci.txt");
        //election.printCandidates();

        /*
        for (int i = 0; i < election.getCandidates().size(); i++) {
            System.out.println(election.getCandidates().get(i));
        }
*/

        //Vote.fromCsvLine("m. Bolesławiec,bolesławiecki,dolnośląskie,450,1131,6465,2899,32,255,13,19,7183,24,43");

        List<Candidate> candidates = election.getCandidates();

        ElectionTurn electionTurn = new ElectionTurn(candidates);
        electionTurn.populate("src/1.csv");

    }

}
