package de.bot;

import com.mysql.cj.Session;
import entity.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.hibernate.Transaction;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static de.bot.Algorithm.*;

public class BotCentral {

    public static void main(String[] args) throws IOException, InterruptedException {


        // Bot Token aus externer Datei abgerufen
        Properties props = new Properties();
        try {
            InputStream input = new FileInputStream("src/Token.properties");
            props.load(input);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        String token = props.getProperty("TOKEN");
        JDABuilder api = JDABuilder.createDefault(token);
        api.enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES,GatewayIntent.GUILD_MESSAGE_REACTIONS, GatewayIntent.MESSAGE_CONTENT);
        api.addEventListeners(new MyListener());
        api.build();
        System.out.println("Bot ist online");


    }
}
