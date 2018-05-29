package de.zm4xi.languageapi;

import de.zm4xi.languageapi.database.MySQL;
import de.zm4xi.languageapi.object.files.ConfigFile;
import de.zm4xi.languageapi.object.files.DatabaseFile;
import de.zm4xi.languageapi.object.language.Language;
import de.zm4xi.languageapi.object.manager.LanguageManager;
import de.zm4xi.languageapi.object.manager.MessageManager;
import de.zm4xi.languageapi.object.manager.UserManager;
import de.zm4xi.languageapi.object.message.Message;
import de.zm4xi.languageapi.object.user.User;
import lombok.Getter;
import lombok.Setter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class LanguageAPI {

    protected static LanguageAPI instance;

    public static LanguageAPI getInstance() {
        if(instance == null) return new LanguageAPI();
        return instance;
    }

    @Getter
    protected DatabaseFile databaseFile;

    @Getter
    protected ConfigFile configFile;

    @Getter
    protected LanguageManager languageManager;

    @Getter
    protected UserManager userManager;

    @Getter
    protected MySQL mySQL;

    @Setter
    private String defaultLanguage;

    /**
     * Initializing the LanguageAPI is as simple as declaring a new int
     *
     *      LanguageAPI languageAPI = new LanguageAPI();
     *
     * Use the line above and all is set and done!
     */
    public LanguageAPI() {
        instance = this;
        databaseFile = new DatabaseFile();
        configFile = new ConfigFile();
        defaultLanguage = configFile.getString("default_language");
        mySQL = new MySQL(databaseFile.getString("host"), databaseFile.getString("port"), databaseFile.getString("database"), databaseFile.getString("username"), databaseFile.getString("password"));

        languageManager = new LanguageManager();
        userManager = new UserManager(defaultLanguage);
    }

    /**
     * Remove a message from the database identified by a {@link String}
     *
     * @param identifier a {@link String} as identifier
     * @param language a {@link Language} as categorization
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public void eraseMessage(Language language, String identifier) {
        language.getMessageManager().getByIdentifier(identifier).delete();
        language.getMessageManager().dropMessage(identifier);
    }

    /**
     * Save a new message in the database identified by a {@link String}
     * and its {@link Language}
     *
     * @param identifier a {@link String} as identifier
     * @param message a {@link String} to be saved in the database
     * @param language a {@link Language} as categorization
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public void newMessage(String identifier, String message, Language language) {
        language.getMessageManager().addMessage(identifier, message, language);
    }

    /**
     * Set a message in the database identified by a {@link String}
     *
     * @param language a {@link Language} as categorization
     * @param identifier a {@link String} as identifier
     * @param message a {@link String} to be set in the database
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public void setMessage(Language language, String identifier, String message) {
        language.getMessageManager().getByIdentifier(identifier).setMessage(message);
    }

    /**
     * Get a {@link Message} matching a specific {@link Language} and a {@link String} identifier
     *
     * @param language a {@link Language} object as identifier
     * @param identifier a {@link String} as identifier
     * @return returns a {@link Message} matching the given identifier
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public Message getMessage(Language language, String identifier) {
        return language.getMessageManager().getByIdentifier(identifier, language);
    }

    /**
     * Get a {@link Message} matching a specific {@link Language} and a {@link String} identifier
     *
     * @param uuid a {@link UUID} object to specify the user
     * @param identifier a {@link String} as identifier
     * @return returns a {@link Message} matching the given identifier
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public Message getMessage(UUID uuid, String identifier) {
        Language language = getUserManager().getByUUID(uuid).getLanguage();
        return language.getMessageManager().getByIdentifier(identifier, language);
    }



    /**
     * Get a {@link String} matching a specific {@link Language} and a {@link String} identifier
     *
     * @param language a {@link Language} object as identifier
     * @param identifier a {@link String} as identifier
     * @return returns a {@link String} matching the given identifier
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public String getMessageString(Language language, String identifier) {
        return language.getMessageManager().getByIdentifier(identifier, language).getMessage();
    }

    /**
     * Get a {@link Language} that is set for a user
     *
     * @param uuid a users {@link UUID}
     * @return returns a {@link Language} object
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public Language getLanguageOf(UUID uuid) {
        return getUserManager().getByUUID(uuid).getLanguage();
    }

    /**
     *
     * Get a {@link LinkedList} with users uuids identified by the give language
     *
     * @param languages zero or more {@link Language}'s to identify with
     * @return returns a {@link LinkedList} containing users {@link UUID} that are identified by the given {@link Language}
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public LinkedList<UUID> getUUIDsBy(Language ... languages) {
        LinkedList<UUID> list = new LinkedList<>();
        for(Language lang : languages) {
            for(User user : getUserManager().getUsers()) {
                if(user.getLanguage().equals(lang)) list.add(user.getUuid());
            }
        }
        return list;
    }

    /**
     *
     * Get a {@link LinkedList} with users identified by the give language
     *
     * @param languages zero or more {@link Language}'s to identify with
     * @return returns a {@link LinkedList} containing users {@link User} that are identified by the given {@link Language}
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public LinkedList<User> getUsersBy(Language ... languages) {
        LinkedList<User> list = new LinkedList<>();
        for(Language lang : languages) {
            for(User user : getUserManager().getUsers()) {
                if(user.getLanguage().equals(lang)) list.add(user);
            }
        }
        return list;
    }

    /**
     * Get a {@link LinkedHashMap} of all messages identified by the given languages
     *
     * @param languages zero or more {@link Language}'s to filter out specific languages
     * @return returns a {@link LinkedHashMap} containing the languages given as arguments
     *
     * @author zM4xi
     * @version a 0.0.1
     */
    public LinkedHashMap<Language, List<Message>> getMessagesBy(Language ... languages) {
        LinkedHashMap<Language, List<Message>> map = new LinkedHashMap<>();
        for(Language lang : languages) {
            LinkedList<Message> list = new LinkedList<>();
            for(Message msg : lang.getMessageManager().getMessages()) {
                if(msg.getLanguage().equals(lang)) list.add(msg);
            }
            map.put(lang, list);
        }
        return map;
    }

}
