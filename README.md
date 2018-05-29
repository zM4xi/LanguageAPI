# LanguageAPI
Minecraft - Provides a database with to save text identified by different languages

This plugin provides a database to store, modify and call text identified by so called messageId's and a languageCode to specifiy which language the message is written in!

The plugin is written in a way so u can use it for BungeeCord or Spigot above 1.8.X either way its mostly based on runtime independent code. The only thing thats based on the runtime is the command and the listener to register/update the users.

Each language has it's own table with messages, so u can ask for messages viathe Build-In API for example

```
-> get message -> language 'English' -> messageId 'welcomeMsg' -> returns "&eWelcome &c$player &eto my server! \n&6Hope you'll have fun here!"
```

Following a few examples how to start and use the API:

Example database:

| messageIdentifier | message                         |
| testMessageId     | simple test message to display  |
| otherMessageId    | yet another simple display text |

```java

