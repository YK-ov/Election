import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Vote {
    private Map<Candidate, Integer> votesForCandidate = new HashMap<>();
    private List<String> location = new ArrayList<>();

    public Vote(Map<Candidate, Integer> votesForCandidate, List<String> location) {
        this.votesForCandidate = votesForCandidate;
        this.location = location;
    }

    public static Vote fromCsvLine(String line){
        String[] row = line.split(",");
        List<String> location = new ArrayList<>();
        Map<Candidate, Integer> votesForCandidate = new HashMap<>();
        Election election = new Election();
        election.populateCandidates("src/kandydaci.txt");
        List<Candidate> newCandidates = new ArrayList<>();

        for (int i = 0; i < election.getCandidates().size(); i++) {
            newCandidates.add(election.getCandidates().get(i));
        }

        for (int i = 0; i < row.length; i++) {
            if (i < 3) {
                location.add(String.valueOf(row[i]));
            }
            else {
                votesForCandidate.put(newCandidates.get(i - 3), Integer.valueOf(row[i]));
            }
        }

        //System.out.println(location);
        //System.out.println(votesForCandidate);
        //System.out.println(Collections.singleton(votesForCandidate));  -- other ways to sout a hashmap
        //for (Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()) {
        //    System.out.println(entry.getKey() + " " + entry.getValue());
        //}

        Vote votesFromline = new Vote(votesForCandidate, location);

        return votesFromline;
    }

    public Map<Candidate, Integer> getVotesForCandidate() {
        return votesForCandidate;
    }

    public List<String> getLocation() {
        return location;
    }

    public Vote summarize(List<Vote> votes){
        Vote vote = null;
        HashMap<Candidate, Integer> newVotesForCandidate = new HashMap<>();

        for (int i = 0; i < votes.size(); i++) {
            for (Map.Entry<Candidate, Integer> entry : votes.get(i).getVotesForCandidate().entrySet()) {
                Candidate key = entry.getKey();
                Integer value = entry.getValue();

                //vote = new Vote(newVotesForCandidate, null); -- better out of the loop
                newVotesForCandidate.merge(key, value, Integer::sum);
            }
        }

        vote = new Vote(newVotesForCandidate, null);

        return vote;
    }

    public int votes(Candidate candidate) {
        return votesForCandidate.getOrDefault(candidate, 0); //returning value from hashmap
    }

    public double percentage(Candidate candidate) {
        int votesForGivenCandidate = votes(candidate);
        int totalVotes = 0;

        for (int i : votesForCandidate.values()){
            totalVotes = totalVotes + i;
        }

        double percentOfVotesForGivenCandidate = ((double) votesForGivenCandidate / totalVotes) * 100;

        return percentOfVotesForGivenCandidate;
    }

    //@Override
    public String toStringOverriden() {
        return "Vote{" +
                "votesForCandidate=" + votesForCandidate +
                ", location=" + location +
                '}';
    }

    public String toString() {
        int totalVotes = 0;
        String result = "";
        int votesForGivenCandidate = 0;
        double percentage;

        for (int i : votesForCandidate.values()){
            totalVotes = totalVotes + i;
        }

        for (Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()) {
            votesForGivenCandidate = entry.getValue();
            percentage = ((double) votesForGivenCandidate / totalVotes) * 100;  //percentage.(entry.getKey())
            result = result + entry.getKey() + "=" + entry.getValue() +", percentage=" + percentage + ", "; //possible with StringBuilder or result.append
        }

        result = "Vote{" + "votesForCandidate={" + result + "}, location=" + location + '}';

        result = result.replaceFirst(", }", "}");

        return result;
    }

}