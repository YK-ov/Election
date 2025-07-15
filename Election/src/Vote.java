import java.util.*;

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

        System.out.println(location);
        //System.out.println(votesForCandidate);
        //System.out.println(Collections.singleton(votesForCandidate));  -- other ways to sout a hashmap
        for (Map.Entry<Candidate, Integer> entry : votesForCandidate.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Vote votesFromline = new Vote(votesForCandidate, location);

        return votesFromline;
    }

    public void summarize(List<Vote> votes){

    }


}
