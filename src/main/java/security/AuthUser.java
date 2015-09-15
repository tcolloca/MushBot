package security;

import java.util.ArrayList;
import java.util.List;

public class AuthUser implements AuthUserToDelete {

	private List<Role> roles;

	public AuthUser() {
		this(new ArrayList<Role>());
	}

	/**
	 * @param roles
	 * @throws IllegalArgumentException
	 *             If {@code roles} is null.
	 */
	public AuthUser(List<Role> roles) {
		if (roles == null) {
			throw new IllegalArgumentException();
		}
		this.roles = roles;
	}

	/**
	 * Adds the role to the roles of the user.
	 * 
	 * @param role
	 * @throws IllegalArgumentException
	 *             If {@code role} is null or if it already has the role.
	 */
	public void addRole(Role role) {
		if (hasRole(role)) {
			throw new IllegalArgumentException();
		}
		roles.add(role);
	}

	/**
	 * Removes the role from the user's roles.
	 * 
	 * @param role
	 * @throws IllegalArgumentException
	 *             If {@code role} is null or if the user doesn't have such
	 *             role.
	 */
	public void removeRole(Role role) {
		if (!hasRole(role)) {
			throw new IllegalArgumentException();
		}
		roles.remove(role);
	}

	/**
	 * Returns {@code true} if it has the role.
	 * 
	 * @param role
	 * @return {@code true} if it has the role.
	 * @throws IllegalArgumentException
	 *             If {@code role} is null.
	 */
	public boolean hasRole(Role role) {
		if (role == null) {
			throw new IllegalArgumentException();
		}
		return roles.contains(role);
	}

	/**
	 * Returns {@code true} if the user has the permission.
	 * 
	 * @param permission
	 * @return {@code true} if the user has the permission.
	 * @throws IllegalArgumentException
	 *             If {@code permission} is null.
	 */
	public boolean hasPermission(Permission permission) {
		if (permission == null) {
			throw new IllegalArgumentException();
		}
		for (Role role : roles) {
			if (role.hasPermission(permission)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the list of permissions of the user. Any empty list if it doesn't
	 * have anyone.
	 * 
	 * @return the list of permissions of the user.
	 */
	public List<Permission> getPermissions() {
		List<Permission> permissions = new ArrayList<Permission>();
		for (Role role : roles) {
			permissions.addAll(role.getPermissions());
		}
		return permissions;
	}

	/**
	 * Returns the list of roles of the user. Any empty list if it doesn't have
	 * anyone.
	 * 
	 * @return the list of roles of the user.
	 */
	public List<Role> getRoles() {
		return roles;
	}
}
