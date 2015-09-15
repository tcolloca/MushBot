package chatmushbot.security;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import util.LinkedProperties;

public class SecurityProperties implements SecurityValues {

	private static Map<String, Map<String, Boolean>> rolesPermissions;
	private static Map<String, List<Object>> usersRoles;

	static {
		rolesPermissions = new HashMap<String, Map<String, Boolean>>();
		usersRoles = new HashMap<String, List<Object>>();
		try {
			Properties properties = new LinkedProperties();
			InputStream input = ClassLoader
					.getSystemResourceAsStream(SECURITY_PROPERTIES);
			properties.load(input);
			for (String roleName : properties.getProperty(ROLES).split(
					ROLES_SEPARATOR)) {
				loadRolePermissions(roleName, properties);
				loadUsersWithRole(roleName, properties);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, Map<String, Boolean>> getRolesPermissions() {
		return rolesPermissions;
	}

	public static Map<String, List<Object>> getUsersRoles() {
		return usersRoles;
	}

	private static void loadRolePermissions(String roleName,
			Properties properties) {
		Map<String, Boolean> rolePermissionsMap = new LinkedHashMap<String, Boolean>();
		for (Object o : properties.keySet()) {
			String key = (String) o;
			if (key.startsWith(roleName)) {
				String permissions = key.substring(roleName.length(),
						key.length());
				rolePermissionsMap.put(permissions,
						Boolean.valueOf(properties.getProperty(key)));
			}
		}
		rolesPermissions.put(roleName, rolePermissionsMap);
	}

	private static void loadUsersWithRole(String roleName, Properties properties) {
		if (!properties.containsKey(USERS + DOT + roleName)) {
			return;
		}
		usersRoles.put(roleName, Arrays.asList(properties.getProperty(
				USERS + DOT + roleName).split(ROLES_SEPARATOR)));
	}
}
