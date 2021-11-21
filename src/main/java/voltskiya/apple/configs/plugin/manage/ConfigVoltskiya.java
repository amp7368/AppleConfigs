package voltskiya.apple.configs.plugin.manage;

import apple.utilities.structures.Pair;
import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.bukkit.configuration.InvalidConfigurationException;
import org.jetbrains.annotations.Nullable;
import plugin.util.plugin.plugin.util.plugin.FileIOServiceNow;
import plugin.util.plugin.plugin.util.plugin.PluginManagedModule;
import ycm.yml.manager.fields.YcmField;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public interface ConfigVoltskiya<Config> {
    static String getYcmFieldName(Field field) {
        YcmField annotation = field.getAnnotation(YcmField.class);
        if (annotation != null) {
            String pathname = annotation.pathname();
            return pathname.isBlank() ? field.getName() : pathname;
        }
        return null;
    }

    void setIsBeingManaged();

    boolean getIsBeingManaged();

    Config getConfigInstance();

    void setConfigInstance(Config instance);

    void saveInstance() throws IOException;

    default void saveNow() {
        try {
            saveInstance();
        } catch (IOException ex) {
            getModule().log(Level.SEVERE, getLogIOExceptionSaving());
        }
    }

    default Config loadNow() {
        if (!getIsBeingManaged()) manageConfig();

        try {
            Config instance = loadInstance();
            if (instance == null) {
                setConfigInstance(createEmptyInstance());
            } else {
                setConfigInstance(instance);
            }
        } catch (FileNotFoundException e) {
            setConfigInstance(createEmptyInstance());
        } catch (IOException e) {
            getModule().log(Level.SEVERE, getLogIOExceptionLoading());
        } catch (InvalidConfigurationException e) {
            getModule().log(Level.SEVERE, getLogInvalidConfig());
        }
        saveNow();
        return getConfigInstance();
    }

    Config loadInstance() throws IOException, InvalidConfigurationException;

    default String getLogInvalidConfig() {
        return String.format("The config file '%s' has an invalid format", getFile().getAbsolutePath());
    }

    default String getLogIOExceptionLoading() {
        return String.format("There was an IOException loading the config file '%s'", getFile().getAbsolutePath());
    }

    default String getLogIOExceptionSaving() {
        return String.format("There was an IOException saving the config file '%s'", getFile().getAbsolutePath());
    }

    Config createEmptyInstance();

    File getFile();

    Class<Config> getConfigClass();

    PluginManagedModule getModule();

    String getName();

    default boolean shouldAutoRegister() {
        return true;
    }

    default void manageConfig() {
        if (getIsBeingManaged()) return;
        setIsBeingManaged();
        if (shouldBeManaged())
            ConfigsVoltskiyaManager.get().addConfig(this);
    }

    default boolean shouldBeManaged() {
        return true;
    }

    default void saveQueue() {
        FileIOServiceNow.get().queueVoid(this::saveNow);
    }

    @Nullable
    default Pair<FieldThing, List<String>> getFieldNamesUnderPath(List<String> propertyPath) {
        FieldThing fieldThing = getFieldInPath(propertyPath);
        if (fieldThing == null) return null;
        Field[] fieldsInSubClass = fieldThing.clazz.getFields();
        List<String> fieldNames = new ArrayList<>();
        for (Field field : fieldsInSubClass) {
            String fieldName = getYcmFieldName(field);
            if (fieldName != null) fieldNames.add(fieldName);
        }
        return new Pair<>(fieldThing, fieldNames);
    }

    @Nullable
    default FieldThing getFieldInPath(List<String> propertyPath) {
        FieldThing fieldThing = new FieldThing(getConfigClass(), this.getConfigInstance());
        while (!propertyPath.isEmpty()) {
            String subPath = propertyPath.remove(0);
            if (subPath.isBlank()) continue;
            try {
                fieldThing = new FieldThing(fieldThing, subPath);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                return null;
            }
        }
        return fieldThing;
    }

    class FieldThing {
        private static final Gson GSON = new Gson();
        private final Class<?> clazz;
        private final FieldThing parent;
        private Object instance;
        @Nullable
        private Field field;

        public FieldThing(Class<?> clazz, Object instance) {
            this.clazz = clazz;
            this.instance = instance;
            this.parent = null;
        }

        public FieldThing(FieldThing fieldThing, String subPath) throws NoSuchFieldException, IllegalAccessException {
            field = fieldThing.clazz.getField(subPath);
            instance = field.get(fieldThing.instance);
            clazz = instance.getClass();
            parent = fieldThing;
        }

        private static void setComplexField(Object obj, Field field, String setValue) throws IllegalAccessException, JsonParseException {
            field.set(obj, GSON.fromJson(setValue, field.getType()));
        }

        private static void setPrimitiveField(Object obj, Field field, String setValue) throws IllegalAccessException {
            Class<?> clazz = field.getType();
            if (clazz == boolean.class) {
                field.setBoolean(obj, Boolean.parseBoolean(setValue));
            } else if (clazz == char.class) {
                field.setChar(obj, setValue.charAt(0));
            } else if (clazz == byte.class) {
                field.setByte(obj, Byte.parseByte(setValue));
            } else if (clazz == short.class) {
                field.setShort(obj, Short.parseShort(setValue));
            } else if (clazz == int.class) {
                int i = Integer.parseInt(setValue);
                field.setInt(obj, i);
            } else if (clazz == long.class) {
                field.setLong(obj, Long.parseLong(setValue));
            } else if (clazz == float.class) {
                field.setFloat(obj, Float.parseFloat(setValue));
            } else if (clazz == double.class) {
                field.setDouble(obj, Double.parseDouble(setValue));
            } else {
                setComplexField(obj, field, setValue);
            }
        }

        public Class<?> getClazz() {
            return clazz;
        }

        public Object getInstance() {
            return instance;
        }

        @Nullable
        public Field getField() {
            return field;
        }

        public String getTypeName() {
            return clazz.getTypeName();
        }

        @Nullable
        public String getFieldName() {
            return field == null ? "root" : ConfigVoltskiya.getYcmFieldName(field);
        }

        public boolean setFieldToValue(String setValue) {
            if (this.field == null || setValue.isBlank()) return false;
            try {
                this.field.trySetAccessible();
                if (field.getType().isPrimitive()) {
                    setPrimitiveField(parent.instance, field, setValue);
                } else {
                    setComplexField(parent.instance, field, setValue);
                }
                this.instance = setValue;
                return true;
            } catch (IllegalAccessException | JsonParseException e) {
                return false;
            }
        }
    }

}
