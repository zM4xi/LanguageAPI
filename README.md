# LanguageAPI

This plugin provides a database to store, modify and call text identified by so called messageId's and a languageCode to specifiy which language the message is written in!

The plugin is written in a way so u can use it for BungeeCord or Spigot above 1.8.X either way its mostly based on runtime independent code. The only thing thats based on the runtime is the command and the listener to register/update the users.

The plugin uses a concept where it cahces the database on every startup, so the traffic between server and database can be at minimum.
Changes still will be updated to the database but the normal request of an message or a users language is the cached data!

Each language has it's own table with messages, so u can ask for messages viathe Build-In API for example

```
-> get message -> language 'English' -> messageId 'welcomeMsg' -> returns "&eWelcome &c$player &eto my server! \n&6Hope you'll have fun here!"
```

Following a few examples how to start and use the API:

Example database `english`:

| messageIdentifier | message |
| --- | --- |
| testMessageId | simple test message to display |
| otherMessageId | yet another simple display text |

Starting with the basic to use the LanguageAPI simply write following:

```java
LanguageAPI languageAPI = new LanguageAPI();
```

Now we get to the use it:

```java
        LanguageAPI languageAPI = new LanguageAPI();
        UUID uuid = null; //Your current users uuid for presentation purpose its null
        String message = languageAPI.getMessageString(languageAPI.getLanguageOf(uuid), "testMesssageId");

        System.out.println(message);
```

This would output:
> simple test message to display

***

Now to get all messages of a given language:

```java
        LanguageAPI languageAPI = new LanguageAPI();
        Language language = languageAPI.getLanguageManager().getByCode("EN");
        LinkedList<Message> messages = language.getMessageManager().getMessages();

        for(Message message : messages) {
            System.out.println(message.getMessage());
        }
 ```
 
 The output would be:
 > simple test message to display
 
 > yet another simple display text
 
 ***
 
 Now to change a message do as follows:
 
 ```java
        LanguageAPI languageAPI = new LanguageAPI();
        Language language = languageAPI.getLanguageManager().getByCode("EN");
        Message message = languageAPI.getMessage(language, "otherMessageId");

        message.setMessage("this is the new text");
 ```
 
 This will change the cached message text and automatically update the database.
 
 ***
 
 Now some statistic work:
 
 ```java
        LanguageAPI languageAPI = new LanguageAPI();
        Language language = languageAPI.getLanguageManager().getByCode("EN");
        LinkedList<User> users = languageAPI.getUsersBy(language);

        System.out.println("There are " + users.size() + " users using the language " + language.getName());
 ```
 
 This will output:
 > There are `X` users using the language English
 
***

## For request and support send a mail to [maxi@zm4xi.de](mailto:maxi@zm4xi.de "Opens your mail programm")
 
