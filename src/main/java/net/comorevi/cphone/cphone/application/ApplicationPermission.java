package net.comorevi.cphone.cphone.application;

import cn.nukkit.Player;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.sql.ApplicationSQLManager;

import java.util.Arrays;
import java.util.List;

public enum ApplicationPermission {

    ATTRIBUTE_DEFAULT("default"),

    ATTRIBUTE_EVERYONE("everyone", ATTRIBUTE_DEFAULT),

    ATTRIBUTE_OPERATOR("operator", ATTRIBUTE_EVERYONE, ATTRIBUTE_DEFAULT),

    ATTRIBUTE_OWNER("owner", ATTRIBUTE_EVERYONE, ATTRIBUTE_OPERATOR, ATTRIBUTE_DEFAULT);

    private String name;
    private List<ApplicationPermission> parents;

    ApplicationPermission(String name, ApplicationPermission... parents) {
        this.name = name;
        this.parents = Arrays.asList(parents);
    }

    public String getName() {
        return name;
    }

    public boolean contains(ApplicationPermission permission) {
        return parents.contains(permission);
    }

    // Usage: USER_PERMISSION.canAccept(APPLICATION_PERMISSION)
    public boolean canAccept(ApplicationPermission permission) {
        if (permission == ApplicationPermission.ATTRIBUTE_DEFAULT) return true;
        return (this == permission) || contains(permission);
    }

    public static ApplicationPermission fromName(String name) {
        switch (name) {
            case "default":
                return ATTRIBUTE_DEFAULT;

            case "operator":
                return ATTRIBUTE_OPERATOR;

            case "owner":
                return ATTRIBUTE_OWNER;

            case "everyone":
                return ATTRIBUTE_EVERYONE;

            default:
                throw new IllegalArgumentException("attribute must be <default|operator|owner|everyone>.");
        }
    }

    public static void updatePermission(Player player) {
        ApplicationPermission permission = ApplicationPermission.ATTRIBUTE_EVERYONE;
        if (player.getName().equals(RuntimeData.config.getString("ServerOwner")))
            permission = ApplicationPermission.ATTRIBUTE_OWNER;
        else if (player.isOp()) permission = ApplicationPermission.ATTRIBUTE_OPERATOR;
        ApplicationSQLManager.setPermission(player.getName(), permission);
    }


}
