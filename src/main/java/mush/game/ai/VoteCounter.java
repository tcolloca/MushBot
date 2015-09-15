package mush.game.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import chat.ChatUser;
import mush.game.player.Player;

public class VoteCounter {

	List<Player> voters;
	List<Player> votees;
	Map<String, ChatUser> prefixes;
	Map<ChatUser, Vote> votersVotes;
	SortedSet<Vote> votesPerUser;
	private int totalVotes;
	private int votesCount;
	private Player leader;

	public VoteCounter(List<Player> voters, List<Player> votees) {
		this.voters = voters;
		this.votees = votees;
		votersVotes = new HashMap<ChatUser, Vote>();
		initPrefixes(votees);
		votesPerUser = new TreeSet<Vote>();
		for (ChatUser user : prefixes.values()) {
			votesPerUser.add(new Vote(user, 0));
		}
		totalVotes = calculateTotalVotes(voters);
		votesCount = 0;
	}

	public ChatUser vote(ChatUser user, String prefix) {
		return vote(user, prefix, 1);
	}

	public boolean hasVoted(ChatUser user) {
		return votersVotes.containsKey(user);
	}

	public ChatUser vote(ChatUser user, String prefix, int i) {
		for (String key : prefixes.keySet()) {
			if (prefix.toLowerCase().startsWith(key)) {
				ChatUser voted = prefixes.get(key);
				addVotes(voted, i);
				votersVotes.put(user, new Vote(voted, i));
				return voted;
			}
		}
		return null;
	}

	public boolean isVotable(String prefix) {
		return getVoted(prefix) != null;
	}

	public void removeVote(ChatUser voter) {
		Vote vote = getVote(voter);
		addVotes(vote.voted, -vote.votes);
	}

	public boolean isConcluded() {
		if (votesDifference() > remainingVotes()) {
			return true;
		}
		return false;
	}

	public List<ChatUser> mostVoted() {
		if (votesPerUser.isEmpty()) {
			return null;
		}
		List<ChatUser> users = new ArrayList<ChatUser>();
		if (isVotationEven()) {
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

	public boolean isVotationEven() {
		return votesDifference() == 0;
	}

	public ChatUser getElected() {
		if (isVotationEven()) {
			return getVote(leader.getUser()).voted;
		}
		return votesPerUser.first().voted;
	}

	public boolean hasVotedMostVoted(Player voter) {
		List<ChatUser> mostVotedUsers = mostVoted();
		if (mostVotedUsers == null) {
			return false;
		}
		return mostVotedUsers.contains(getVote(voter.getUser()).voted);
	}

	public boolean everyoneHasVoted() {
		for (Player player : voters) {
			if (!votersVotes.containsKey(player)) {
				return false;
			}
		}
		return true;
	}

	public boolean hasLeaderVoter() {
		for (Player player : voters) {
			if (player.isLeaderVoter()) {
				leader = player;
				return true;
			}
		}
		return false;
	}

	public boolean leaderHasVotedMostVoted() {
		if (leader == null) {
			throw new IllegalStateException();
		}
		return hasVotedMostVoted(leader);
	}

	private Vote getVote(ChatUser voter) {
		return votersVotes.get(voter);
	}

	private void initPrefixes(List<Player> players) {
		prefixes = new HashMap<String, ChatUser>();
		List<Player> playersCopy = new ArrayList<Player>(players);
		for (int i = 0; !playersCopy.isEmpty(); i++) {
			Iterator<Player> it = playersCopy.iterator();
			List<String> finalNicks = new ArrayList<String>();
			List<String> removedPrefixes = new ArrayList<String>();
			while (it.hasNext()) {
				ChatUser user = it.next().getUser();
				if (!prefixes.containsValue(user)) {
					String nick = user.getUsername().toLowerCase();
					if (i == nick.length() - 1) {
						finalNicks.add(user.getUsername());
						prefixes.put(nick, user);
					} else if (i < nick.length()) {
						String prefix = nick.substring(0, i + 1);
						if (prefixes.containsKey(prefix)
								&& !finalNicks.contains(prefixes.get(prefix)
										.getUsername())) {
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

	public ChatUser getVoted(String prefix) {
		for (String key : prefixes.keySet()) {
			if (prefix.toLowerCase().startsWith(key)) {
				return prefixes.get(key);
			}
		}
		return null;
	}

	private void addVotes(ChatUser user, int i) {
		votesCount += i;
		Vote voteToModify = null;
		for (Vote vote : votesPerUser) {
			if (vote.voted.getUsername().equals(user.getUsername())) {
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

	private class Vote implements Comparable<Vote> {

		private ChatUser voted;
		private int votes;

		public Vote(ChatUser voted, int votes) {
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
					+ ((voted.getUsername() == null) ? 0 : voted.getUsername()
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
			} else if (!voted.getUsername().equals(other.voted.getUsername()))
				return false;
			return true;
		}

		private VoteCounter getOuterType() {
			return VoteCounter.this;
		}

		public int compareTo(Vote o) {
			int votesDiff = -(votes - o.votes);
			if (votesDiff == 0) {
				return voted.getUsername().compareTo(o.voted.getUsername());
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
