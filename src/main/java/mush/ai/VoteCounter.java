package mush.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import mush.player.Player;

import org.pircbotx.User;

public class VoteCounter {

	List<Player> voters;
	List<Player> votees;
	Map<String, User> prefixes;
	Map<User, Vote> votersVotes;
	SortedSet<Vote> votesPerUser;
	private int totalVotes;
	private int votesCount;

	public VoteCounter(List<Player> voters, List<Player> votees) {
		this.voters = voters;
		this.votees = votees;
		votersVotes = new HashMap<User, Vote>();
		initPrefixes(votees);
		votesPerUser = new TreeSet<Vote>();
		for (User user : prefixes.values()) {
			votesPerUser.add(new Vote(user, 0));
		}
		totalVotes = calculateTotalVotes(voters);
		votesCount = 0;
	}

	public User vote(User user, String prefix) {
		return vote(user, prefix, 1);
	}

	public boolean hasVoted(User user) {
		return votersVotes.containsKey(user);
	}

	public User vote(User user, String prefix, int i) {
		for (String key : prefixes.keySet()) {
			if (prefix.toLowerCase().startsWith(key)) {
				User voted = prefixes.get(key);
				addVotes(voted, i);
				votersVotes.put(user, new Vote(user, i));
				return voted;
			}
		}
		return null;
	}

	public void removeVote(User voter) {
		Vote vote = votersVotes.get(voter);
		addVotes(vote.voted, -vote.votes);
	}

	public boolean isConcluded() {
		if (votesDifference() > remainingVotes()) {
			return true;
		}
		if (everyoneHasVoted()) {
			return true;
		}
		return false;
	}

	public List<User> mostVoted() {
		if (votesPerUser.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<User>();
		if (votesDifference() == 0) {
			int amount = votesPerUser.first().votes;
			for (Vote vote : votesPerUser) {
				if (vote.votes == amount) {
					users.add(vote.voted);
				}
			}
		} else {
			users.add(votesPerUser.first().voted);
		}
		return users;
	}

	private void initPrefixes(List<Player> players) {
		prefixes = new HashMap<String, User>();
		List<Player> playersCopy = new ArrayList<Player>(players);
		for (int i = 0; !playersCopy.isEmpty(); i++) {
			Iterator<Player> it = playersCopy.iterator();
			List<String> finalNicks = new ArrayList<String>();
			List<String> removedPrefixes = new ArrayList<String>();
			while (it.hasNext()) {
				User user = it.next().getUser();
				if (!prefixes.containsValue(user)) {
					String nick = user.getNick().toLowerCase();
					if (i == nick.length() - 1) {
						finalNicks.add(user.getNick());
						prefixes.put(nick, user);
					} else if (i < nick.length()) {
						String prefix = nick.substring(0, i + 1);
						if (prefixes.containsKey(prefix)
								&& !finalNicks.contains(prefixes.get(prefix)
										.getNick())) {
							prefixes.remove(prefix);
							removedPrefixes.add(prefix);
						} else if (!removedPrefixes.contains(prefix)
								&& !finalNicks.contains(prefix)) {
							prefixes.put(prefix, user);
						}
					}
				} else {
					it.remove();
				}
			}
		}
	}

	private void addVotes(User user, int i) {
		votesCount += i;
		Vote voteToModify = null;
		for (Vote vote : votesPerUser) {
			System.out.println(vote.voted.getNick());
			if (vote.voted.getNick().equals(user.getNick())) {
				voteToModify = vote;
				break;
			}
		}
		votesPerUser.remove(voteToModify);
		voteToModify.votes += i;
		votesPerUser.add(voteToModify);
	}

	private int calculateTotalVotes(List<Player> players) {
		int total = 0;
		for (Player player : players) {
			total += player.getAvailableVotes();
		}
		return total;
	}

	private int votesDifference() {
		Vote firstVote = null, secondVote = null;
		for (Vote vote : votesPerUser) {
			if (firstVote == null) {
				firstVote = vote;
			} else if (secondVote == null) {
				secondVote = vote;
			}
		}
		if (firstVote == null) {
			return 0;
		} else if (secondVote == null) {
			return firstVote.votes;
		} else {
			return firstVote.votes - secondVote.votes;
		}
	}

	private int remainingVotes() {
		return totalVotes - votesCount;
	}

	private boolean everyoneHasVoted() {
		for (Player player : voters) {
			if (!votersVotes.containsKey(player)) {
				return false;
			}
		}
		return true;
	}

	private class Vote implements Comparable<Vote> {

		private User voted;
		private int votes;

		public Vote(User voted, int votes) {
			super();
			this.voted = voted;
			this.votes = votes;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getOuterType().hashCode();
			result = prime
					* result
					+ ((voted.getNick() == null) ? 0 : voted.getNick()
							.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Vote other = (Vote) obj;
			if (!getOuterType().equals(other.getOuterType()))
				return false;
			if (voted == null) {
				if (other.voted != null)
					return false;
			} else if (!voted.getNick().equals(other.voted.getNick()))
				return false;
			return true;
		}

		private VoteCounter getOuterType() {
			return VoteCounter.this;
		}

		public int compareTo(Vote o) {
			int votesDiff = -(votes - o.votes);
			if (votesDiff == 0) {
				return voted.getNick().compareTo(o.voted.getNick());
			}
			return votesDiff;
		}
	}

	/*
	 * public static void main(String[] args) { Player ana, beto, carlos,
	 * damian, e; List<Player> voters = Lists.newArrayList(ana = new
	 * Player("Ana"), beto = new Player("Beto"), carlos = new Player("Carlos"),
	 * damian = new Player("Damian")); List<Player> voted =
	 * Lists.newArrayList(new Player("F"), new Player("G"), new Player("H"));
	 * VoteCounter v = new VoteCounter(voters, voted);
	 * System.out.println("total votes: " + v.totalVotes);
	 * System.out.println("votes count: " + v.votesCount);
	 * System.out.println("biggestDiff: " + v.votesDifference());
	 * System.out.println("mostVoted: " + v.mostVoted().getNick());
	 * System.out.println(); v.vote(ana.getUser(), "h");
	 * System.out.println("Ana voted"); System.out.println("total votes: " +
	 * v.totalVotes); System.out.println("votes count: " + v.votesCount);
	 * System.out.println("biggestDiff: " + v.votesDifference());
	 * System.out.println("mostVoted: " + v.mostVoted().getNick());
	 * System.out.println("isConculded: " + v.isConcluded());
	 * System.out.println(); v.vote(beto.getUser(), "h");
	 * System.out.println("Beto voted"); System.out.println("total votes: " +
	 * v.totalVotes); System.out.println("votes count: " + v.votesCount);
	 * System.out.println("biggestDiff: " + v.votesDifference());
	 * System.out.println("mostVoted: " + v.mostVoted().getNick());
	 * System.out.println("isConculded: " + v.isConcluded());
	 * System.out.println(); v.vote(carlos.getUser(), "f");
	 * System.out.println("Beto voted"); System.out.println("total votes: " +
	 * v.totalVotes); System.out.println("votes count: " + v.votesCount);
	 * System.out.println("biggestDiff: " + v.votesDifference());
	 * System.out.println("mostVoted: " + v.mostVoted().getNick());
	 * System.out.println("isConculded: " + v.isConcluded());
	 * System.out.println(); v.vote(damian.getUser(), "f");
	 * System.out.println("Damian voted"); System.out.println("total votes: " +
	 * v.totalVotes); System.out.println("votes count: " + v.votesCount);
	 * System.out.println("biggestDiff: " + v.votesDifference());
	 * System.out.println("mostVoted: " + v.mostVoted().getNick());
	 * System.out.println("isConculded: " + v.isConcluded());
	 * System.out.println(); }
	 * 
	 * public static class Player {
	 * 
	 * User user;
	 * 
	 * public Player(String string) { super(); this.user = new User(string); }
	 * 
	 * public User getUser() { return user; }
	 * 
	 * public void setUser(User user) { this.user = user; }
	 * 
	 * public int getAvailableVotes() { return 1; }
	 * 
	 * }
	 * 
	 * public static class User {
	 * 
	 * String nick;
	 * 
	 * public User(String string) { this.nick = string; }
	 * 
	 * public String getNick() { return nick; }
	 * 
	 * public void setNick(String nick) { this.nick = nick; } }
	 */
}
