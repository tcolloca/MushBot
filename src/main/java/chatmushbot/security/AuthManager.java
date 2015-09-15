package chatmushbot.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import properties.DefaultProperties;
import security.Permission;
import security.Role;
import chat.ChatUser;
import chat.NoSuchUserException;
import chatmushbot.ChatMushBot;
import chatmushbot.command.CommandName;
import chatmushbot.command.util.ChatMushBotCommandManager;

public class AuthManager implements SecurityValues {

	private Map<Object, ChatAuthUser> users;
	private Map<String, Role> roles;
	private ChatMushBot bot;

	/**
	 * @param bot
	 *            Bot that holds the AuthManager.
	 * @throws IllegalArgumentException
	 *             If bot is null.
	 */
	public AuthManager(ChatMushBot bot) {
		if (bot == null) {
			throw new IllegalArgumentException();
		}
		this.bot = bot;
		users = new HashMap<Object, ChatAuthUser>();
		roles = new HashMap<String, Role>();
		initializeRoles();
	}

	// TODO "and every time a new user joins a channel."
	/**
	 * Intializes the users' roles. This method should be called after
	 * connection, and every time a new user joins a channel.
	 */
	public void initializeUsers() {
		Map<String, List<Object>> usersRoles = SecurityProperties
				.getUsersRoles();
		for (Entry<String, List<Object>> entry : usersRoles.entrySet()) {
			Role role = getRole(entry.getKey());
			for (Object userId : entry.getValue()) {
				ChatUser user;
				try {
					user = bot.getUser(userId);
					if (!getUser(user).hasRole(role)) {
						getUser(user).addRole(role);
					}
				} catch (NoSuchUserException e) {
					// TODO
					System.out.println("El usuario " + userId + " no existe.");
				}
			}
		}
	}

	/**
	 * Returns the {@link ChatAuthUser} associated with the user. If there was
	 * no one associated yet, then the default role is added to the user.
	 * 
	 * @param user
	 * @return the {@link ChatAuthUser} associated with the user.
	 * @throws IllegalArgumentException
	 *             If {@code user} is null.
	 */
	public ChatAuthUser getUser(ChatUser user) {
		if (user == null) {
			throw new IllegalArgumentException();
		}
		if (!users.containsKey(user.getId())) {
			ChatAuthUser authUser = new ChatAuthUser(user);
			authUser.addRole(getRole(DefaultProperties.roleName()));
			users.put(user.getId(), authUser);
		}
		return users.get(user.getId());
	}

	/**
	 * Returns the Role associated with roleName.
	 * 
	 * @param roleName
	 * @return the Role associated with roleName.
	 * @throws IllegalArgumentException
	 *             If roleName is null.
	 */
	public Role getRole(String roleName) {
		if (roleName == null) {
			throw new IllegalArgumentException();
		}
		return roles.get(roleName);
	}

	private void initializeRoles() {
		Map<String, Map<String, Boolean>> rolesPermissions = SecurityProperties
				.getRolesPermissions();
		// Iterates over the different roles
		for (Entry<String, Map<String, Boolean>> roleMapEntry : rolesPermissions
				.entrySet()) {
			String roleName = roleMapEntry.getKey();
			Map<String, Boolean> permissionsMap = roleMapEntry.getValue();
			Role role = new Role(roleName);
			// Iterates over the permissions of the role
			for (Entry<String, Boolean> permissionsValueEntry : permissionsMap
					.entrySet()) {
				String location = permissionsValueEntry.getKey();
				boolean isValueTrue = Boolean.valueOf(permissionsValueEntry
						.getValue());
				if (location.endsWith(ALL)) {
					location = location.substring(0,
							location.length() - (ALL.length() + 1));
				}
				// Iterates over the different commandNames associated with the
				// permission name
				for (CommandName commandName : ChatMushBotCommandManager
						.getInLocation(location)) {
					Permission permission = commandName;
					if (!role.hasPermission(permission) && isValueTrue) {
						role.addPermission(permission);
					} else if (role.hasPermission(permission) && !isValueTrue) {
						role.removePermission(permission);
					}
				}
			}
			roles.put(roleName, role);
		}
	}
}
