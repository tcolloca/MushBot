package security;

import java.util.List;

/**
 * It represents a user that has different permissions according its roles.
 * 
 * @author Tomas
 */
public interface AuthUserToDelete {

	/**
	 * Adds the role to the roles of the user.
	 * 
	 * @param role
	 * @throws IllegalArgumentException
	 *             If {@code role} is null or if it already has the role.
	 */
	public void addRole(Role role);

	/**
	 * Removes the role from the user's roles.
	 * 
	 * @param role
	 * @throws IllegalArgumentException
	 *             If {@code role} is null or if the user doesn't have such
	 *             role.
	 */
	public void removeRole(Role role);

	/**
	 * Returns {@code true} if it has the role.
	 * 
	 * @param role
	 * @return {@code true} if it has the role.
	 * @throws IllegalArgumentException
	 *             If {@code role} is null.
	 */
	public boolean hasRole(Role role);

	/**
	 * Returns {@code true} if the user has the permission.
	 * 
	 * @param permission
	 * @return {@code true} if the user has the permission.
	 * @throws IllegalArgumentException
	 *             If {@code permission} is null.
	 */
	public boolean hasPermission(Permission permission);

	/**
	 * Returns the list of permissions of the user. Any empty list if it doesn't
	 * have anyone.
	 * 
	 * @return the list of permissions of the user.
	 */
	public List<Permission> getPermissions();

	/**
	 * Returns the list of roles of the user. Any empty list if it doesn't have
	 * anyone.
	 * 
	 * @return the list of roles of the user.
	 */
	public List<Role> getRoles();
}
