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

    public Vote summarize(List<Vote> votes, List<String> location) {
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

        vote = new Vote(newVotesForCandidate, location);

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

    public static List<Vote> filterByLocation(List<Vote> votes, List<String> location) {
        List<Vote> filteredVotes = new ArrayList<>();
        String wholeLine = "";
        String gmina = "";
        String powiat = "";
        String wojewodztwo = "";

        int locationSize = votes.size();
        String[] row;

        for (int i = 0; i < location.size(); i++) {
            row = location.get(i).split(",");
                for (int j = 0; j < row.length; j++) {
                    wholeLine = wholeLine + row[j] + ",";
                }
           // System.out.println(location.get(i) + " location list" + i + " i");
        }
        wholeLine = wholeLine.substring(0, wholeLine.length() - 1);
        System.out.println(wholeLine + "whole line");

        String[] array = new String[wholeLine.length()];
        for (int i = 0; i < wholeLine.length(); i++) {
            array = wholeLine.split(",");
        }

        String lastThree = "";
        String lastTwo = "";

        int wojewodztwaCount = 0;
        int powiatyCount = 0;
        int gminyCount = 0;

        for (int i = 0; i < array.length; i++) {
            lastThree = array[i].substring(array[i].length() - 3);
            lastTwo = array[i].substring(array[i].length() - 2);
            if (lastThree.equals("kie") && !lastTwo.equals("ki")) {
                wojewodztwaCount++;
            }
            else if (lastTwo.equals("ki") && !lastThree.equals("kie")) {
                powiatyCount++;
            }
            else {
                gminyCount++;
            }

        }

        System.out.println(wojewodztwaCount + " wojewodztwa count " + powiatyCount + " powiaty count " + gminyCount + " gminy count ");

        String[] allWojewodztwa = new String[wojewodztwaCount];
        String[] allPowiaty = new String[powiatyCount];
        String[] allGminy = new String[gminyCount];

        List<String> allWojewodztwaList = new ArrayList<>();
        List<String> allPowiatyList = new ArrayList<>();
        List<String> allGminyList = new ArrayList<>();

        for (int i = 0; i < array.length; i++) {
            lastThree = array[i].substring(array[i].length() - 3);
            lastTwo = array[i].substring(array[i].length() - 2);

            if (lastThree.equals("kie") && !lastTwo.equals("ki")) {

                wojewodztwo = array[i].trim();


            }
            else if (lastTwo.equals("ki") && !lastThree.equals("kie")) {
                powiat = array[i].trim();
            }
            else {
                gmina = array[i].trim();
            }

        }

        //System.out.println(wojewodztwo + " wojewodztwo " + powiat + " powiat " + gmina + " gmina");

        for (int i = 0; i < votes.size(); i++) {
            //System.out.println(votes.get(i).getLocation().get(i) + " from votes"); -- number of lists and their elements
            for (int j = 0; j < votes.get(i).getLocation().size(); j++) {
                if (gmina.equals(votes.get(i).getLocation().get(j)) && powiat.equals(votes.get(i).getLocation().get(j+1)) && wojewodztwo.equals(votes.get(i).getLocation().get(j+2)) && !wojewodztwo.isEmpty() && !powiat.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }
                else if (gmina.equals(votes.get(i).getLocation().get(j)) && powiat.equals(votes.get(i).getLocation().get(j+1)) && wojewodztwo.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }
                else if (gmina.equals(votes.get(i).getLocation().get(j)) && wojewodztwo.equals(votes.get(i).getLocation().get(j+2)) && powiat.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }
                else if (powiat.equals(votes.get(i).getLocation().get(j)) && wojewodztwo.equals(votes.get(i).getLocation().get(j+1)) && gmina.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }
                else if (powiat.equals(votes.get(i).getLocation().get(j)) && gmina.isEmpty() && wojewodztwo.isEmpty()) {
                    filteredVotes.add(votes.get(i));

                }
                else if (gmina.equals(votes.get(i).getLocation().get(j)) && wojewodztwo.isEmpty() && powiat.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }
                else if (wojewodztwo.equals(votes.get(i).getLocation().get(j)) && gmina.isEmpty() && powiat.isEmpty()) {
                    filteredVotes.add(votes.get(i));
                }

                }
            }

        return filteredVotes;
    }


}