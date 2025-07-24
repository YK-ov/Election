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
        //System.out.println(vote + " right after");

        //System.out.println(vote.summarize(votes));

       Candidate candidate = election.getCandidates().get(0);


       //System.out.println(vote.summarize(votes).percentage(candidate));
       //System.out.println(electionTurn.winner());
        //electionTurn.runoffCandidates();

        //electionTurn.runoffCandidates();

        //System.out.println(electionTurn.runoffCandidates());
        //election.populate();
        //System.out.println(vote.summarize(votes));

        //System.out.println(electionTurn.runoffCandidates());

        //System.out.println(vote.getLocation());
        //Vote.filterByLocation(votes, vote.getLocation());
        //System.out.println(vote.getLocation());

        //System.out.println(votes);
        //for (int i = 0; i < votes.size(); i++) {
        //    System.out.println(votes.get(i).getLocation() + " loc");
        //}

        List<String> newLocation = new ArrayList<>();
        //newLocation.add(0, "gm. Boleszkowice");
        //newLocation.add(0, "myśliborski");
        //newLocation.add(1, "gm. Boleszkowice");
        newLocation.add(0, "zachodniopomorskie");
        //newLocation.add(1, "myśliborski");
        //newLocation.add(1, "gm. Boleszkowice");
        //newLocation.add(0, "  gm. Boleszkowice, myśliborski, zachodniopomorskie");
        //System.out.println(newLocation);


        //System.out.println(Vote.filterByLocation(votes, newLocation));

        //electionTurn.runoffCandidates();
        //System.out.println(electionTurn.summarize() + " from electionTurn");
        //System.out.println(vote.summarize(votes, null) + " from vote");
        //electionTurn.winner();
        //System.out.println(electionTurn.runoffCandidates());
        //election.populate();
        //System.out.println(electionTurn.summarize());
        //System.out.println(electionTurn.summarize(newLocation));

        //VoivodeshipMap voivodeshipMap = new VoivodeshipMap();
        //voivodeshipMap.saveToSvg("src/map.svg");

        //System.out.println(Vote.filterByLocation(votes, newLocation));

        //System.out.println(secondTurn.getVotes());
        //System.out.println(electionTurn.summarize(voivodeshipMap.getStates()));
        //electionTurn.summarize(voivodeshipMap.getStates());

        //System.out.println(election.getSecondTurn().summarize() + " normal summarization");

        VoivodeshipMap voivodeshipMap = new VoivodeshipMap();// --uncomm
        //next lines for showcase (19, second tour)
        //System.out.println(voivodeshipMap.getStates());
        ElectionTurn secondTurn = new ElectionTurn(electionTurn.runoffCandidates()); // --uncomm
        List<Vote> secondTourVotes = electionTurn.populate("src/2.csv"); // -- uncomm
        election.populate(); // --uncomm
        //System.out.println(election.getSecondTurn().summarize(voivodeshipMap.getStates()));
        Vote secondTourAllStates = election.getSecondTurn().summarize(voivodeshipMap.getStates()); // -uncomm
        System.out.println(secondTourAllStates + " from object"); // --uncomm !!!
        //System.out.println(election.getSecondTurn() + " getSecondTurn");

        SelectableMap selectableMap = new SelectableMap(); // -uncomm
        //selectableMap.select("lubelskie");
        //voivodeshipMap.saveToSvg("src/map.svg"); // -uncomm

        VoteMap voteMap = new VoteMap(secondTourAllStates, election.getSecondTurn()); // --uncomm
        voteMap.saveToSvg("src/map.svg"); // --uncomm


    }

}
