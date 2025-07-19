import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class ElectionTurn {
    private List<Candidate> candidates = new ArrayList<Candidate>();
    private List<Vote> votes = new ArrayList<>();

    public ElectionTurn(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public List<Vote> populate(String path) {
        BufferedReader reader;
        String line = "";
        int lineCounter = 0;
        try {
            reader = new BufferedReader(new FileReader(path));
            while((line = reader.readLine()) != null){
                lineCounter++;
                if (lineCounter > 1) {
                    Vote vote = Vote.fromCsvLine(line);
                    votes.add(vote);
                }
            }
            reader.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return votes;
    }

    public List<Candidate> getCandidates() {
        return candidates;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void setCandidates(List<Candidate> candidates) {
        this.candidates = candidates;
    }

    public Candidate winner() throws NoWinnerException {
        Vote vote;
        Vote summaryVote;
        Candidate winner;

        for (int j = 0; j < votes.size(); j++) {
            vote = votes.get(j);
            summaryVote = vote.summarize(votes);
             for (int i = 0; i < candidates.size(); i++) {
                 if(summaryVote.percentage(candidates.get(i)) > 50){
                        winner = candidates.get(i);
                        System.out.println("Winner: " + winner + " " + summaryVote.percentage(winner));
                        return winner;
                    }
                 }

            }

        NoWinnerException exception = new NoWinnerException("No winner");

        throw exception;
    }

    public List<Candidate> runoffCandidates(){
        Vote vote = null;
        Vote summaryVote = null;
        List<Double> allVotes = new ArrayList<>();
        List<Candidate> twoHighestCandidates = new ArrayList<>();
        Object e = null;
        Map<Candidate, Integer> votesForCandidate = new HashMap<>();
        
        for (int i = 0; i < votes.size(); i++) {
            vote = votes.get(i);
            summaryVote = vote.summarize(votes);
        }
        
        for (int j = 0; j < candidates.size(); j++) {
            summaryVote.percentage(candidates.get(j));
            allVotes.add(summaryVote.percentage(candidates.get(j)));
        }
        Collections.sort(allVotes);
        
        ListIterator<Double> i = allVotes.listIterator(allVotes.size());

                while (i.previousIndex() != allVotes.size() - 3) {  //-3 for two last (working very slow, but working)
                    e = i.previous();
                    for (int j = 0; j < candidates.size(); j++) {
                        //System.out.println(summaryVote.percentage(candidates.get(j)));
                        if (e.equals(summaryVote.percentage(candidates.get(j)))) {
                            twoHighestCandidates.add(candidates.get(j));
                            votesForCandidate.put(candidates.get(j), summaryVote.votes(candidates.get(j)));
                        }
                    }
                }
                //System.out.println(votesForCandidate + " map");
                vote = new Vote(votesForCandidate, null);
                votes.clear();
                //System.out.println(votes);
        List<Vote> singleToneVotes = new ArrayList<Vote>(Collections.singleton(vote));
        //System.out.println(singleToneVotes + " from singleton");
        setVotes(singleToneVotes);
        //      votes.add(vote);
                //System.out.println(votes + " in field list");
                //System.out.println(getVotes() + " from getter");

        //System.out.println(twoHighestCandidates);

        return twoHighestCandidates;
    }


    @Override
    public String toString() {
        return "ElectionTurn{" +
                "candidates=" + candidates +
                ", votes=" + votes +
                '}';
    }
}