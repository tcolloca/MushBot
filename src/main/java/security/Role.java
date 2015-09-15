package security;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a set of permissions.
 * 
 * @author Tomas
 */
public class Role {

	private String name;
	private List<Permission> permissions;

	/**
	 * @param name
	 *            Name of the role.
	 * @throws IllegalArgumentException
	 *             If {@code name} is null or is empty.
	 */
	public Role(String name) {
		this(name, new ArrayList<Permission>());
	}

	/**
	 * @param name
	 *            Name of the role.
	 * @param permissions
	 * @throws IllegalArgumentException
	 *             If {@code permissions} is null or {@code name} is null or is
	 *             empty.
	 */
	public Role(String name, List<Permission> permissions) {
		if (name == null || name.isEmpty() || permissions == null) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.permissions = permissions;
	}

	/**
	 * Adds the permission to the role.
	 * 
	 * @param permission
	 * @throws IllegalArgumentException
	 *             If {@code permission} is null or if it already has the
	 *             permission.
	 */
	public void addPermission(Permission permission) {
		if (hasPermission(permission)) {
			throw new IllegalArgumentException();
		}
		permissions.add(permission);
	}

	/**
	 * Adds the permissions to the role.
	 * 
	 * @param permissions
	 * @throws IllegalArgumentException
	 *             If {@code permissions} is null, if any of the permissions is
	 *             null, or if it already has any of them.
	 */
	public void addPermissions(List<Permission> permissions) {
		if (permissions == null) {
			throw new IllegalArgumentException();
		}
		for (Permission permission : permissions) {
			addPermission(permission);
		}
	}

	/**
	 * Removes the permission from the role.
	 * 
	 * @param permission
	 * @throws IllegalArgumentException
	 *             If {@code permission} is null or if the role doesn't have
	 *             such permission.
	 */
	public void removePermission(Permission permission) {
		if (!hasPermission(permission)) {
			throw new IllegalArgumentException();
		}
		permissions.remove(permission);
	}
	
	/**
	 * Removes the permissions to the role.
	 * 
	 * @param permissions
	 * @throws IllegalArgumentException
	 *             If {@code permissions} is null, if any of the permissions is
	 *             null, or if it doesn't have any of them.
	 */
	public void removePermissions(List<Permission> permissions) {
		if (permissions == null) {
			throw new IllegalArgumentException();
		}
		for (Permission permission : permissions) {
			removePermission(permission);
		}
	}

	/**
	 * Returns {@code true} if it has the permission.
	 * 
	 * @param permission
	 * @return {@code true} if it has the permission.
	 * @throws IllegalArgumentException
	 *             If {@code permission} is null.
	 */
	public boolean hasPermission(Permission permission) {
		if (permission == null) {
			throw new IllegalArgumentException();
		}
		return permissions.contains(permission);
	}

	public String getName() {
		return name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}
}
